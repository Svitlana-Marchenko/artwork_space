import React, {useCallback, useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import {Button} from "../../components/Button";
import CountdownTimer from "../../components/countdown/CountdownTimer";
import ArtworkHeading from "../../components/artwork/ArtworkHeading";
import ArtworkDescription from "../../components/artwork/ArtworkDescription";
import {Auction as AuctionType} from "../../types/auctionsTypes";
import AuctionService from "../../API/AuctionService";
import {PlaceBidModal} from "../../components/modals/PlaceBidModal";
import {calculateAverageRating} from "../../utils/ratingUtils";
import {User} from "../../types/usersTypes";
import Empty from "../../empty";

const Auction = () => {
    const { id } = useParams();
    const [auction, setAuction] = useState<AuctionType>();
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User= storedUserString ? JSON.parse(storedUserString) : null;
    const [isOpenPlaceBid, setIsOpenPlaceBid] = useState(false);

    const toggleOpenPlaceBid = useCallback(() => {
        setIsOpenPlaceBid((value) => !value);
    }, []);

    useEffect(() => {
        if (id) {
            AuctionService.getAuctionById(id)
                .then((data) => {
                    setAuction(data)
                })
                .catch((error) =>
                    console.error('Error fetching auction by ID:', error)
                );
        }
    }, [id]);

    if (!auction) {
        return <Empty message={"Theres no such auction"} link={`/auctions`} dist={'all the auctions'}/>;
    }

    const averageRating = calculateAverageRating(auction.artwork?.ratings || []);

    const {
        artwork,
        bid,
        closingTime,
        currentBid,
        startingPrice,
        user
    } = auction

    const {
        imageURL,
        title,
        technique,
        width,
        height
    } = artwork;
    const artist = artwork.user;

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
                        title={title}
                        averageRating={averageRating}
                        showAverageRating={artwork.ratings.length>0}/>
                    <div className={'h-[160px]'}>
                        <CountdownTimer targetDate={new Date(closingTime)}/>
                    </div>
                </div>
                <ArtworkDescription
                    technique={technique}
                    width={width}
                    height={height}
                    currentBuyer={user}
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
                    currentUser?.role === 'COLLECTIONEER'
                        ?
                        <Button label={'Place bid'} onClick={toggleOpenPlaceBid}/>
                        :
                        null
                }
                <PlaceBidModal isOpen={isOpenPlaceBid} toggle={toggleOpenPlaceBid} auction={auction}/>

            </div>
        </div>
    );
};

export default Auction;
