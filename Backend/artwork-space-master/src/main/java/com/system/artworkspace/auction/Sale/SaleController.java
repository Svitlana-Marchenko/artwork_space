package com.system.artworkspace.auction.Sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping("/byArtist")
    @PreAuthorize("hasAuthority('ARTIST')")
    public List<SaleDto> getSalesForArtist(@RequestParam Long id) {
        return saleService.getSalesForArtist(id).stream().map(SaleMapper.INSTANCE::saleToSaleDto).collect(Collectors.toList());
    }
    @GetMapping("/byCollectioneer")
    @PreAuthorize("hasAuthority('COLLECTIONEER')")
    public List<SaleDto> getSalesForCollectioneer(@RequestParam Long id) {
        return saleService.getSalesForCollectioneer(id).stream().map(SaleMapper.INSTANCE::saleToSaleDto).collect(Collectors.toList());
    }
    @PostMapping
    @PreAuthorize("hasAuthority('ARTIST')")
    public SaleDto createSale(@RequestBody @Valid SaleDto saleDto) {
        return SaleMapper.INSTANCE.saleToSaleDto(saleService.createSale(SaleMapper.INSTANCE.saleDtoToSale(saleDto)));
    }
}
