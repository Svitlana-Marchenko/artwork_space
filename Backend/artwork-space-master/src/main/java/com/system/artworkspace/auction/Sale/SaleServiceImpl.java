package com.system.artworkspace.auction.Sale;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.artwork.ArtworkEntity;
import com.system.artworkspace.artwork.ArtworkMapper;
import com.system.artworkspace.artwork.ArtworkRepository;
import com.system.artworkspace.exceptions.NoSuchArtworkException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.system.artworkspace.logger.LoggingMarkers.ARTWORK_EVENTS;

@Service
public class SaleServiceImpl implements SaleService{
    static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);

    private final SaleRepository repository;

    @Autowired
    public SaleServiceImpl(SaleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Sale createSale(Sale sale) {
        logger.info("Adding sale with ID: {}", sale.getId());
        repository.save(SaleMapper.INSTANCE.saleToSaleEntity(sale));
        logger.info("Sale added successfully.");

        return sale;
    }

    @Override
    public Sale getSaleById(Long id) {
        Optional<SaleEntity> sale = repository.findById(id);
        logger.info("Finding sale by id ");
        if (sale.isPresent())
            return SaleMapper.INSTANCE.saleEntityToSale(sale.get());
        else
            throw new EntityNotFoundException("Sale with id " + id + " not found");

    }

    @Override
    public List<Sale> getAllSales() {
        return repository.findAll().stream().map(x -> SaleMapper.INSTANCE.saleEntityToSale(x)).collect(Collectors.toList());
    }

    @Override
    public List<Sale> getSalesForArtist(Long id) {
        return repository.findAllBySellerId(id).stream().map(x -> SaleMapper.INSTANCE.saleEntityToSale(x)).collect(Collectors.toList());
    }

    @Override
    public List<Sale> getSalesForCollectioneer(Long id) {
        return repository.findAllByBuyerId(id).stream().map(x -> SaleMapper.INSTANCE.saleEntityToSale(x)).collect(Collectors.toList());
    }
}
