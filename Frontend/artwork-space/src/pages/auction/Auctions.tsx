import React, {useEffect, useState} from 'react';
import {Auction} from "../../mockup/mockup_auctions";
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
        return <Empty message={"Theres no such auctions here"} link={`/auctions`} dist={'all the auctions'}/>;
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
