import React, {useEffect, useState} from 'react';
import {Auction} from "../../types/auctionsTypes";
import AuctionService from "../../API/AuctionService";
import AuctionList from "../../components/lists/AuctionList";
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

    return (
        <div className={'mx-32'}>
            <AuctionList auctions={auctions}/>
        </div>
    );
};

export default Auctions;
