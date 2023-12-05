import React, { useState } from "react";
import { Button } from "../Button";

interface AuctionBidProps {
    minValue: number;
}

const AuctionBid: React.FC<AuctionBidProps> = ({ minValue }) => {
    const [currentBid, setCurrentBid] = useState(minValue);
    const [inputValue, setInputValue] = useState(currentBid.toString());

    const handleIncreaseBid = () => {
        setCurrentBid(currentBid + 1);
        setInputValue((currentBid + 1).toString());
    };

    const handleDecreaseBid = () => {
        if (currentBid > minValue) {
            setCurrentBid(currentBid - 1);
            setInputValue((currentBid - 1).toString());
        }
    };

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const newValue = parseInt(event.target.value);
        if (!isNaN(newValue) && newValue >= minValue) {
            setCurrentBid(newValue);
        }
        setInputValue(event.target.value);
    };

    return (
        <div>
            <p>Current Bid: ${currentBid}</p>
            <div>
                Current Bid:
                <input
                    type="number"
                    value={inputValue}
                    onChange={handleInputChange}
                    min={minValue}
                />

            </div>
        </div>
    );
};

export default AuctionBid;
