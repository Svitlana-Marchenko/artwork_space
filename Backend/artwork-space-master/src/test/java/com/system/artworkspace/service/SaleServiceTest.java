package com.system.artworkspace.service;

import com.system.artworkspace.auction.Sale.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SaleServiceTest {

    @Mock
    private SaleRepository repository;

    @InjectMocks
    private SaleServiceImpl saleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateSale_whenValidSaleProvided() {
        Sale sale = new Sale();
        sale.setId(1L);

        SaleEntity saleEntity = new SaleEntity();
        saleEntity.setId(1L);

        when(repository.save(any(SaleEntity.class))).thenReturn(saleEntity);

        Sale createdSale = saleService.createSale(sale);

        assertNotNull(createdSale);
        assertEquals(sale.getId(), createdSale.getId());
        verify(repository, times(1)).save(any(SaleEntity.class));
    }

    @Test
    public void shouldReturnSaleById_whenSaleExists() {
        Long saleId = 1L;
        SaleEntity saleEntity = new SaleEntity();
        saleEntity.setId(saleId);

        when(repository.findById(saleId)).thenReturn(Optional.of(saleEntity));

        Sale sale = saleService.getSaleById(saleId);

        assertNotNull(sale);
        assertEquals(saleId, sale.getId());
        verify(repository, times(1)).findById(saleId);
    }

    @Test
    public void shouldThrowException_whenSaleNotFoundById() {
        Long saleId = 1L;
        when(repository.findById(saleId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> saleService.getSaleById(saleId));

        verify(repository, times(1)).findById(saleId);
    }

    @Test
    public void shouldReturnAllSales_whenSalesExist() {
        List<SaleEntity> saleEntities = new ArrayList<>();
        SaleEntity saleEntity = new SaleEntity();
        saleEntities.add(saleEntity);

        when(repository.findAll()).thenReturn(saleEntities);

        List<Sale> sales = saleService.getAllSales();

        assertFalse(sales.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void shouldReturnSalesForArtist_whenArtistHasSales() {
        Long artistId = 1L;
        List<SaleEntity> saleEntities = new ArrayList<>();
        SaleEntity saleEntity = new SaleEntity();
        saleEntities.add(saleEntity);

        when(repository.findAllBySellerId(artistId)).thenReturn(saleEntities);

        List<Sale> sales = saleService.getSalesForArtist(artistId);

        assertFalse(sales.isEmpty());
        verify(repository, times(1)).findAllBySellerId(artistId);
    }

    @Test
    public void shouldReturnSalesForCollectioneer_whenCollectioneerHasSales() {
        Long collectioneerId = 1L;
        List<SaleEntity> saleEntities = new ArrayList<>();
        SaleEntity saleEntity = new SaleEntity();
        saleEntities.add(saleEntity);

        when(repository.findAllByBuyerId(collectioneerId)).thenReturn(saleEntities);

        List<Sale> sales = saleService.getSalesForCollectioneer(collectioneerId);

        assertFalse(sales.isEmpty());
        verify(repository, times(1)).findAllByBuyerId(collectioneerId);
    }
}
