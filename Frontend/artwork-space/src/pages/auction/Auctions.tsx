import React, {useEffect, useState} from 'react';
import {Auction} from "../../mockup/mockup_auctions";
import AuctionCard from "../../components/listings/AuctionCard";
import AuctionService from "../../API/AuctionService";

//todo if current user is artist => get only his/her auctions
const Auctions = () => {

    const [auctions, setAuctions] = useState<Auction[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        AuctionService.getAllAuctions()
            .then(data => {
                setAuctions(data);
                setLoading(false);
            })
            .catch(error => {
                console.error('Помилка при отриманні даних про аукціон:', error);
                setLoading(false);
            });
    }, []);

    //todo normal loading page
    if (loading) {
        return <div>Loading...</div>;
    }

    //TODO add normal error page (if there is no auctions)
    if (auctions.length === 0) {

        return <div>
           No auctions here

        </div>
    }
    //todo normal error page
    return (
        <div className="mx-32 grid grid-cols-3 gap-6">
            {
                auctions.map((auction) => {
                    return (
                        <AuctionCard
                            key={auction.id}
                            artwork={auction.artwork}
                            id={auction.id}
                            title={auction.title}
                            bid={auction.bid}
                            currentBid={auction.currentBid}
                            currentBuyer={auction.currentBuyer}
                            description={auction.description}
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
