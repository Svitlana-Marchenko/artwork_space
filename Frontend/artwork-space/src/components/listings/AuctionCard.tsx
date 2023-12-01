import React from 'react';
import {Auction} from "../../mockup/mockup_auctions";
import {useNavigate} from "react-router-dom";
import HeartButton from "../HeartButton";
import {Button} from "../Button";

const AuctionCard:React.FC<Auction> = ({
                                           id,
                                           artwork,
                                           bid,
                                           closingTime,
                                           currentBid}) => {
    const navigate = useNavigate();
    const currentUser = {
        //role: "artist",
         role: "curator",
        // role: "collectioneer",
    }
    return (
        <div className="">
            <div className={"cursor-pointer"} onClick={()=>{navigate(`/auction/${id}`)}}>
                <img src={artwork.imageURL} alt={artwork.title} className={"w-full h-[500px] object-cover"}/>
                <div className="flex flex-col">
                    <p className="text-sm font-bold mt-2">{artwork.technique}</p>
                    <p className={"text-3xl font-bold"}>{artwork.title.toUpperCase()}</p>
                    <p className={"mt-1"}>{new Date(closingTime).toDateString()}</p>
                </div>
            </div>
            {
                currentUser.role === "curator"
                ?
                    <div className={"mt-3"}>
                        <Button label={`Place bid`} onClick={()=>{navigate(`/auction/${id}`)}} outline/>
                    </div>
                    :
                    null
            }

        </div>
    );
};

export default AuctionCard;
