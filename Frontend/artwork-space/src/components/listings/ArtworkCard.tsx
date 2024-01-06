import React, {useEffect, useState} from 'react';
import HeartButton from "../icons/HeartButton";
import {useNavigate} from "react-router-dom";
import UserLink from "../UserLink";
import {User} from "../../types/usersTypes";
import {Artwork as ArtworkType, Artwork} from "../../types/artworkTypes";
import PlusButton from "../icons/PlusButton";
import {Collection} from "../../types/collectionTypes";

interface ArtworkCardProps {
    artwork: Artwork;
    sm?:boolean;
    disabled?: boolean;
    onAddToExhibition?:  (artwork: Artwork) => void;
    favoriteArtworks:Collection[]
    chosenCollection?:Collection
}

const ArtworkCard:React.FC<ArtworkCardProps> = ({
                                                    artwork,
                                                    sm,
                                                    onAddToExhibition,
                                                    disabled,
                                                    favoriteArtworks,
                                                    chosenCollection
                                                }) => {
    const navigate = useNavigate();
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User | null = storedUserString ? JSON.parse(storedUserString) : null;

    const imageStyle = sm
        ? "object-cover h-[360px] w-auto"
        : "object-cover w-full h-auto";

    const handleClick = () => {
        if (!disabled) {
            navigate(`/artwork/${artwork.id}`);
        }
    };

    return (
        <div className="relative">
            <div
                className="
                absolute
                bottom-0
                left-0
                flex
                flex-col
                justify-between
                h-full
                w-full
                p-4
                bg-black/30
                opacity-0
                hover:opacity-100
                transition-opacity
                duration-300
                text-white"
            >
                <div className="flex flex-row justify-between items-center" >
                    <div onClick={handleClick} className={ disabled ? 'cursor-default' : "cursor-pointer"}>
                        <p className="text-2xl font-bold">{artwork.title}</p>
                        <p className="text-xs">{artwork.technique}</p>
                    </div>
                    {
                        onAddToExhibition
                        ?
                            <PlusButton artwork={artwork} onAddToExhibition={onAddToExhibition}/>
                            :
                            currentUser && (
                                <>
                                {
                                    (currentUser.role === "CURATOR" || currentUser.role === "COLLECTIONEER")&&!disabled
                                    ?
                                        <HeartButton artworkId={artwork.id} favouriteArtworks={favoriteArtworks} chosenCollection={chosenCollection} menuWidth={"1/2"} menuRight={"4"} menuTop={"10"}/>
                                        :
                                        null
                                }
                                </>
                            )
                    }

                </div>
                <div className="flex flex-row justify-between items-end">
                    <div>
                        {
                            !disabled&&(
                                <UserLink id={artwork.user.id} username={artwork.user.username}/>
                            )
                        }
                        <p>{artwork.user.firstName} {artwork.user.lastName}</p>
                    </div>
                    <p className="text-xs">{artwork.width} W &times; {artwork.height} H cm</p>
                </div>
            </div>
            <img src={artwork.imageURL} alt={artwork.title} className={imageStyle}/>
        </div>
    );
};

export default ArtworkCard;
