package com.system.artworkspace.auction.Sale;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SaleServiceImpl implements SaleService{

    private final SaleRepository repository;

    @Autowired
    public SaleServiceImpl(SaleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Sale createSale(Sale sale) {
        log.info("Adding sale with ID: {}", sale.getId());
        repository.save(SaleMapper.INSTANCE.saleToSaleEntity(sale));
        log.info("Sale added successfully.");
        return sale;
    }

    @Override
    public Sale getSaleById(Long id) {
        log.debug("Finding sale by id: {} ", id);
        Optional<SaleEntity> sale = repository.findById(id);
        if (sale.isPresent())
            return SaleMapper.INSTANCE.saleEntityToSale(sale.get());
        else
            throw new EntityNotFoundException("Sale with id " + id + " not found");

    }

    @Override
    public List<Sale> getAllSales() {
        return repository.findAll().stream().map(SaleMapper.INSTANCE::saleEntityToSale).collect(Collectors.toList());
    }

    @Override
    public List<Sale> getSalesForArtist(Long id) {
        return repository.findAllBySellerId(id).stream().map(SaleMapper.INSTANCE::saleEntityToSale).collect(Collectors.toList());
    }

    @Override
    public List<Sale> getSalesForCollectioneer(Long id) {
        return repository.findAllByBuyerId(id).stream().map(SaleMapper.INSTANCE::saleEntityToSale).collect(Collectors.toList());
    }
}
