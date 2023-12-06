import React from 'react';
import HeartButton from "../icons/HeartButton";
import {useNavigate} from "react-router-dom";
import UserLink from "../UserLink";
import {User} from "../../mockup/mockup_users";
import MenuItem from "../../header/MenuItem";
import {Artwork} from "../../mockup/mockup_artworks";
import PlusButton from "../icons/PlusButton";

interface ArtworkCardProps {
    artwork: Artwork;
    sm?:boolean;
    onAddToExhibition?:  (artwork: Artwork) => void;
}

const ArtworkCard:React.FC<ArtworkCardProps> = ({
                                                    artwork,
                                                    sm,
                                                    onAddToExhibition
                                                }) => {
    const navigate = useNavigate();
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User | null = storedUserString ? JSON.parse(storedUserString) : null;
    const imageStyle = sm
        ? "object-cover h-[360px] w-auto"
        : "object-cover w-full h-auto";

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
                    <div onClick={()=>{navigate(`/artwork/${artwork.id}`)}} className={"cursor-pointer"}>
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
                                    currentUser.role === "CURATOR" || currentUser.role === "COLLECTIONEER"
                                    ?
                                        <HeartButton/>
                                        :
                                        null
                                }
                                </>
                            )
                    }

                </div>
                <div className="flex flex-row justify-between items-end">
                    <div>
                        <UserLink id={artwork.user.id} username={artwork.user.username}/>
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
