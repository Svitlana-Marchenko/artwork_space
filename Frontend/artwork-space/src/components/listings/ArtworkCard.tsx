import React from 'react';
import HeartButton from "../HeartButton";
import {useNavigate} from "react-router-dom";
import ArtistLink from "../ArtistLink";

interface ArtworkCardProps {
    id:number;
    title: string;
    technique: string;
    firstName: string;
    lastName: string;
    username: string;
    artistId: number;
    imageURL: string;
    width:number;
    height: number;
    sm?:boolean
}

const ArtworkCard:React.FC<ArtworkCardProps> = ({
                                                    id,
                                                    title,
                                                    technique,
                                                    firstName,
                                                    lastName,
                                                    username,
                                                    artistId,
                                                    imageURL,
                                                    width,
                                                    height,
                                                    sm
                                                }) => {
    const navigate = useNavigate();
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
                    <div onClick={()=>{navigate(`/artwork/${id}`)}} className={"cursor-pointer"}>
                        <p className="text-2xl font-bold">{title}</p>
                        <p className="text-xs">{technique}</p>
                    </div>
                    <HeartButton/>
                </div>
                <div className="flex flex-row justify-between items-end">
                    <div>
                        <ArtistLink id={artistId} username={username}/>
                        <p>{firstName} {lastName}</p>
                    </div>
                    <p className="text-xs">{width} W &times; {height} H cm</p>
                </div>
            </div>
            <img src={imageURL} alt={title} className={imageStyle}/>
        </div>
    );
};

export default ArtworkCard;
