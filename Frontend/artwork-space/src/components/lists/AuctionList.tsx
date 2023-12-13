import React from 'react';
import {Auction} from "../../types/auctionsTypes";
import AuctionCard from "../listings/AuctionCard";
import Empty from "../../empty";
interface AuctionListProps {
    auctions: Auction[];
}

const AuctionList: React.FC<AuctionListProps> = ({ auctions }) => {

    if (auctions.length === 0) {
        return <Empty message={"Theres no auctions here"} link={`/artworks`} dist={'all the artworks'}/>;
    }

    return (
        <div className="grid grid-cols-3 gap-6">
            {auctions.map((auction) => (
                <AuctionCard
                    key={auction.id}
                    artwork={auction.artwork}
                    id={auction.id}
                    bid={auction.bid}
                    currentBid={auction.currentBid}
                    user={auction.user}
                    closingTime={auction.closingTime}
                    startingPrice={auction.startingPrice}
                />
            ))}
        </div>
    );
};

export default AuctionList;