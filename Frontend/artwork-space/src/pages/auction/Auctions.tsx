import React from 'react';
import {auctions} from "../../mockup/mockup_auctions";
import AuctionCard from "../../components/listings/AuctionCard";


const Auctions = () => {
    return (
        <div className="mx-32 grid grid-cols-3 gap-6">
            {
                auctions.map((auction) => {
                    return (
                        <AuctionCard
                            artwork={auction.artwork}
                            id={auction.id}
                            title={auction.title}
                            bid={auction.bid}
                            currentBid={auction.currentBid}
                            currentBuyer={auction.currentBuyer}
                            description={auction.description}
                            endDate={auction.endDate}
                            startingPrice={auction.startingPrice}
                        />
                    );
                })
            }
        </div>
    );
};

export default Auctions;
