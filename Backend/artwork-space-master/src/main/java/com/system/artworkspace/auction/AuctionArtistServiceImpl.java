package com.system.artworkspace.auction;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.artwork.ArtworkService;
import com.system.artworkspace.auction.Sale.*;
import com.system.artworkspace.user.User;
import com.system.artworkspace.user.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
public class AuctionArtistServiceImpl implements AuctionArtistService {
    static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private ArtworkService artworkService;
    @Autowired
    private SaleService saleService;
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @Override
    public Auction createAuction(Auction auction) {
        auction.setCurrentBid(auction.getStartingPrice());
        auctionRepository.save(AuctionMapper.INSTANCE.auctionToAuctionEntity(auction));
        logger.info(AUCTIONS_EVENTS, "Created auction with ID: {}", auction.getId());
        return auction;
    }

    @Override
    public Auction getAuctionById(Long id) {
        Optional<AuctionEntity> auction = auctionRepository.findById(id);
        logger.info("Finding auction by id ");
        if (auction.isPresent())
            return AuctionMapper.INSTANCE.auctionEntityToAuction(auction.get());
        else
            throw new EntityNotFoundException("Auction with id " + id + " not found");

    }

    @Override
    public double displayCurrentBid(Long id) {
        Optional<AuctionEntity> auction = auctionRepository.findById(id);
        logger.info(AUCTIONS_EVENTS, "Retrieved current bid for auction with ID: {}", id);
        return auction.map(AuctionEntity::getCurrentBid).orElse(0.0);
    }

    @Override
    public User displayCurrentBuyer(Long id) {
        logger.info(CONFIDENTIAL_EVENTS, "Displaying current buyer for auction with ID: {}", id);
        if (auctionRepository.findById(id).isPresent())
            return UserMapper.INSTANCE.userEntityToUser(auctionRepository.findById(id).get().getUser());
        return null;
    }

    @Override
    public List<Auction> getAllActiveAuctions() {
        Date currentDate = new Date();

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

        logger.info(AUCTIONS_EVENTS, "Retrieved {} active auctions.", validAuctionEntities.size());

        return validAuctionEntities.stream()
                .map(x -> AuctionMapper.INSTANCE.auctionEntityToAuction(x))
                .collect(Collectors.toList());
    }


    @Override
    public void closeAuction(Long id) {
        Auction auction = getAuctionById(id);
        saleService.createSale(new Sale(auction.getArtwork(),auction.getUser(),auction.getArtwork().getUser(),auction.getCurrentBid(),auction.getClosingTime()));
        deleteAuctionById(id);
        logger.info(AUCTIONS_EVENTS, "Closed auction with ID: {}", id);
    }

    @Override
    public void deleteAuctionById(Long id) {
        logger.info("Deleting auction with ID: {}", id);
        auctionRepository.deleteById(id);
        logger.info("Auction deleted with ID: {}", id);
    }

    //@Scheduled(fixedRate = 5000) //for testing
    @Scheduled(cron = "0 59 23 * * *") // Execute every day at 23:59
    public void performAuctionClosingJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            logger.error("Error executing auctionClosingJob", e);
        }
    }

}
