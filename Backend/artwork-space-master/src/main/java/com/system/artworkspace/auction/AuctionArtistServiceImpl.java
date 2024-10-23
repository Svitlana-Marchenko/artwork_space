package com.system.artworkspace.auction;

import com.system.artworkspace.auction.Sale.*;
import com.system.artworkspace.exceptions.NoSuchAuctionException;
import com.system.artworkspace.user.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static com.system.artworkspace.logger.LoggingMarkers.AUCTIONS_EVENTS;
import static com.system.artworkspace.logger.LoggingMarkers.CONFIDENTIAL_EVENTS;

@Service
@Slf4j
public class AuctionArtistServiceImpl implements AuctionArtistService {
    
    @Autowired
    private AuctionRepository auctionRepository;
    
    @Autowired
    private UserService userService;

    @Autowired
    private SaleService saleService;
    
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;
    
    @Override
    public Auction createAuction(Auction auction) {
        auction.setCurrentBid(auction.getStartingPrice());
        AuctionEntity a = auctionRepository.save(AuctionMapper.INSTANCE.auctionToAuctionEntity(auction));
        log.info(AUCTIONS_EVENTS, "Created auction with ID: {}", auction.getId());
        return AuctionMapper.INSTANCE.auctionEntityToAuction(a);
    }

    @Override
    @Cacheable(cacheNames="auction", key="#id")
    public Auction getAuctionById(Long id) {
        Optional<AuctionEntity> auction = auctionRepository.findById(id);
        log.info("Finding auction by id ");
        if (auction.isPresent())
            return AuctionMapper.INSTANCE.auctionEntityToAuction(auction.get());
        else
            throw new EntityNotFoundException("Auction with id " + id + " not found");

    }

    @Override
    public double displayCurrentBid(Long id) {
        Optional<AuctionEntity> auction = auctionRepository.findById(id);
        log.info(AUCTIONS_EVENTS, "Retrieved current bid for auction with ID: {}", id);
        return auction.map(AuctionEntity::getCurrentBid).orElseThrow(()-> new NoSuchAuctionException("Auction with id "+id+" not found"));
    }

    @Override
    public User displayCurrentBuyer(Long id) {
        log.info(CONFIDENTIAL_EVENTS, "Displaying current buyer for auction with ID: {}", id);
        if (auctionRepository.findById(id).isPresent())
            return UserMapper.INSTANCE.userEntityToUser(auctionRepository.findById(id).get().getUser());
        return null;
    }

    @Override
    public List<Auction> getAllActiveAuctions() {
        Sort sort = Sort.by(Sort.Order.asc("closingTime"));

        List<AuctionEntity> activeAuctionEntities = auctionRepository.findAll(sort);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedCurrentDate = dateFormat.format(new Date());

        List<AuctionEntity> validAuctionEntities = activeAuctionEntities.stream()
                .filter(entity -> {
                    try {
                        Date closingTime = dateFormat.parse(entity.getClosingTime().toString());
                        return !closingTime.before(dateFormat.parse(formattedCurrentDate));
                    } catch (Exception e) {
                        return false;
                    }
                })
                .toList();

        log.info(AUCTIONS_EVENTS, "Retrieved {} active auctions.", validAuctionEntities.size());

        return validAuctionEntities.stream()
                .map(AuctionMapper.INSTANCE::auctionEntityToAuction)
                .collect(Collectors.toList());
    }


    @Override
    public void closeAuction(Long id) {
        Auction auction = getAuctionById(id);
        saleService.createSale(new Sale(auction.getArtwork(),auction.getUser(),auction.getArtwork().getUser(),auction.getCurrentBid(),auction.getClosingTime()));
        deleteAuctionById(id);
        log.info(AUCTIONS_EVENTS, "Closed auction with ID: {}", id);
    }

    @Override
    @CacheEvict(cacheNames="auction", key="#id")
    public void deleteAuctionById(Long id) {
        log.info("Deleting auction with ID: {}", id);
        auctionRepository.deleteById(id);
        log.info("Auction deleted with ID: {}", id);
    }

    @Override
    public List<Auction> getAllAuctionsByUserId(Long id) {
        log.debug("Getting auctions, where artist id "+id);
       User user = userService.getUserById(id);

        if(!user.getRole().equals(Role.ARTIST)){
            throw new RuntimeException("Trying to get auctions from non artist user");
        }
        List<AuctionEntity> activeAuctionEntities = auctionRepository.findAllAuctionsByArtworkArtistId(id);
        return activeAuctionEntities.stream()
                .map(AuctionMapper.INSTANCE::auctionEntityToAuction)
                .collect(Collectors.toList());
    }

    @Scheduled(cron = "0 59 23 * * *") // Execute every day at 23:59
    public void performAuctionClosingJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            log.error("Error executing auctionClosingJob", e);
        }
    }
}
