import React from 'react';
import {Auction} from "../../types/auctionsTypes";
import {useNavigate} from "react-router-dom";

const AuctionCard:React.FC<Auction> = ({
                                           id,
                                           artwork,
                                           closingTime,
                                           }) => {
    const navigate = useNavigate();

    return (
        <div>
            <div className={"cursor-pointer"} onClick={()=>{navigate(`/auction/${id}`)}}>
                <img src={artwork.imageURL} alt={artwork.title} className={"w-full h-[500px] object-cover"}/>
                <div className="flex flex-col">
                    <p className="text-sm font-bold mt-2">{artwork.technique}</p>
                    <p className={"text-3xl font-bold"}>{artwork.title.toUpperCase()}</p>
                    <p className={"mt-1"}>{new Date(closingTime).toDateString()}</p>
                </div>
            </div>
        </div>
    );
};

export default AuctionCard;
