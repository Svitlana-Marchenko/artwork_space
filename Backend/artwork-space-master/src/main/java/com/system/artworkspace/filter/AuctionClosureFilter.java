package com.system.artworkspace.filter;

import com.system.artworkspace.auction.Auction;
import com.system.artworkspace.auction.AuctionCollectioneerServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter("/collectioneer/auctions/**")
public class AuctionClosureFilter implements Filter {
    private AuctionCollectioneerServiceImpl auctionService;
    private final Logger logger = LoggerFactory.getLogger(AuctionClosureFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            Long paintingId = Long.parseLong(request.getParameter("id"));
            Auction auction = auctionService.getAuctionByPaintingId(paintingId);

            if (auction != null && auction.isClosed()) {
                logger.warn("Access denied. Auction with ID {} is closed.", auction.getId());
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (NumberFormatException e) {
            logger.warn("Invalid number");
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
