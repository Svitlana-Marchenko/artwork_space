import React, {useEffect, useState} from 'react';
import {Auction} from "../../types/auctionsTypes";
import AuctionCard from "../../components/listings/AuctionCard";
import AuctionService from "../../API/AuctionService";
import Empty from "../../empty";

const Auctions = () => {

    const [auctions, setAuctions] = useState<Auction[]>([]);

    useEffect(() => {
        AuctionService.getAllAuctions()
            .then(data => {
                setAuctions(data);
            })
            .catch(error => {
                console.error('Помилка при отриманні даних про аукціон:', error);
            });
    }, []);

    if (auctions.length === 0) {
        return <Empty message={"Theres no auctions here"} link={`/artworks`} dist={'all the artworks'}/>;
    }

    return (
        <div className="mx-32 grid grid-cols-3 gap-6">
            {
                auctions.map((auction) => {
                    return (
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
                    );
                })
            }
        </div>
    );
};

export default Auctions;
