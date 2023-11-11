import React from 'react';
import {useParams} from "react-router-dom";
import {artworks} from '../mockup/mockup_artworks'
import {Button} from "../components/Button";
import {convertToInt} from "../functions";

const Artwork = () => {
    const { id } = useParams();

    const artwork = artworks[convertToInt(id)];
    const currentUser = {
        role: "artist",
        // role: "curator",
        // role: "collectioneer",
    }
    return (
        <div className="flex flex-row items-center justify-center gap-10">
            <img src={artwork.imageURL} alt={artwork.title} className="w-auto max-w-3xl h-[600px] object-cover"/>
            <section className="w-1/3">
                <p className="underline underline-offset-1 text-sm">@{artwork.username}</p>
                <p className={"text-gray-400 mb-1"}>Painting by {artwork.firstName} {artwork.lastName}</p>
                <h2 className={"text-3xl font-bold"}>{artwork.title.toUpperCase()}</h2>

                <div className="grid grid-cols-2 gap-x-8 mt-4 mb-8">
                    <p>Technique</p>
                    <p className={"text-gray-400"}>{artwork.technique}</p>
                    <p>Size</p>
                    <p className={"text-gray-400"}>{artwork.width}&times;{artwork.height} cm</p>
                </div>
                <div className={"flex flex-row gap-4"}>
                    {
                        currentUser.role === 'artist'
                        ?
                          <>
                              <Button label={"Edit"} onClick={()=>{}}/>
                              <Button label={"Delete"} onClick={()=>{}} outline/>
                          </>
                            :
                            currentUser.role === "curator"
                        ?
                                <>
                                    <Button label={"Add review"} onClick={()=>{}}/>
                                </>
                                :
                                <>
                                    <Button label={"Save"} onClick={()=>{}}/>
                                </>
                    }

                </div>
            </section>
        </div>
    );
};

export default Artwork;
