package com.system.artworkspace.filter;

import com.system.artworkspace.auction.Auction;
import com.system.artworkspace.auction.AuctionCollectioneerService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebFilter("/collectioneer/auctions/**")
@Slf4j
public class AuctionClosureFilter implements Filter {

    private AuctionCollectioneerService auctionService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            Long paintingId = Long.parseLong(request.getParameter("id"));
            Auction auction = auctionService.getAuctionByPaintingId(paintingId);

            if (auction != null && auction.isClosed()) {
                log.warn("Access denied. Auction with ID {} is closed.", auction.getId());
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (NumberFormatException e) {
            log.warn("Invalid number");
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
