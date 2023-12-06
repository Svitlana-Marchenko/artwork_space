import React, {useCallback, useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import {Button} from "../../components/Button";
import CountdownTimer from "../../components/countdown/CountdownTimer";
import ArtworkHeading from "../../components/artwork/ArtworkHeading";
import ArtworkDescription from "../../components/artwork/ArtworkDescription";
import {Auction as AuctionType} from "../../mockup/mockup_auctions";
import AuctionService from "../../API/AuctionService";
import {ChangePasswordModal} from "../../components/modals/ChangePasswordModal";
import {PlaceBidModal} from "../../components/modals/PlaceBidModal";

const Auction = () => {
    const { id } = useParams();
    const [auction, setAuction] = useState<AuctionType>();
    const navigate = useNavigate();

    const [isOpenPlaceBid, setIsOpenPlaceBid] = useState(false);

    const toggleOpenPlaceBid = useCallback(() => {
        setIsOpenPlaceBid((value) => !value);
    }, []);


    useEffect(() => {
        if (id) {
            AuctionService.getAuctionById(id)
                .then((data) => setAuction(data))
                .catch((error) =>
                    console.error('Error fetching auction by ID:', error)
                );
        }
    }, [id]);

    //todo normal page for error
    if (!auction) {
        return <div>Loading...</div>;
    }


    const {
        artwork,
        bid,
        closingTime,
        currentBid,
        startingPrice,
        currentBuyer
    } = auction

    const {
        imageURL,
        title,
        technique,
        width,
        height
    } = artwork;
    const artist = artwork.user;

    const currentUser = {
       // role: "artist",
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
                    <CountdownTimer targetDate={new Date(closingTime)}/>
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
                        <Button label={'Place bid'} onClick={toggleOpenPlaceBid}/>
                        :
                        null
                }
                <PlaceBidModal isOpen={isOpenPlaceBid} toggle={toggleOpenPlaceBid} auctionId={auction.id} minVal={auction.currentBid+auction.bid}/>

            </div>
            </div>
    );
};

export default Auction;
