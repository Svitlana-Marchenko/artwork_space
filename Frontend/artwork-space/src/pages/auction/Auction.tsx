import React from 'react';
import {useNavigate, useParams} from "react-router-dom";
import {convertToInt} from "../../actions/functions";
import {Button} from "../../components/Button";
import {auctions} from "../../mockup/mockup_auctions";
import CountdownTimer from "../../components/countdown/CountdownTimer";
import UserLink from "../../components/UserLink";
import ArtworkHeading from "../../components/artwork/ArtworkHeading";
import ArtworkDescription from "../../components/artwork/ArtworkDescription";

const Auction = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const {
        artwork,
        bid,
        endDate,
        currentBid,
        startingPrice,
        currentBuyer
    } = auctions[convertToInt(id)]

    const {
        imageURL,
        title,
        technique,
        width,
        height
    } = artwork;
    const artist = artwork.artist;

    const currentUser = {
        //role: "artist",
         role: "curator",
        // role: "collectioneer",
    }

    return (
        <div className="flex flex-row items-center justify-center gap-10">
            <img src={imageURL} alt={title} className="w-auto max-w-3xl h-[600px] object-cover"/>
            <div className="w-1/3">
                <div className={'flex flex-row justify-between'}>
                    <ArtworkHeading
                        id={artist.id}
                        username={artist.username}
                        firstName={artist.firstName}
                        lastName={artist.lastName}
                        title={title}/>
                    <CountdownTimer targetDate={endDate}/>
                </div>
                <ArtworkDescription
                    technique={technique}
                    width={width}
                    height={height}
                />
                {
                    startingPrice === currentBid
                    ?
                        null
                        :
                        <p className={'text-gray-400 line-through'}>${startingPrice}</p>
                }
                <p className={'text-3xl font-bold mb-3'}>${currentBid}</p>

                {
                    currentUser.role === 'curator'
                        ?
                        <Button label={'Place bid'} onClick={()=>{navigate(`/auction/${id}`)}}/>
                        :
                        null
                }
            </div>
        </div>
    );
};

export default Auction;
