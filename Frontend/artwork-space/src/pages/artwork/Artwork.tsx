import React from 'react';
import {useParams} from "react-router-dom";
import {artworks} from '../../mockup/mockup_artworks'
import {Button} from "../../components/Button";
import {convertToInt} from "../../actions/functions";
import UserLink from "../../components/UserLink";
import ArtworkHeading from "../../components/artwork/ArtworkHeading";
import ArtworkDescription from "../../components/artwork/ArtworkDescription";
import BookmarkButton from "../../components/BookmarkButton";

const Artwork = () => {
    const { id } = useParams();
    const {
        imageURL,
        title,
        technique,
        width,
        height
    } = artworks[convertToInt(id)];
    const artist = artworks[convertToInt(id)].artist;
    const currentUser = {
        //role: "artist",
         role: "curator",
        // role: "collectioneer",
    }

    return (
        <div className="flex flex-row items-center justify-center gap-10">
            <img src={imageURL} alt={title} className="w-auto max-w-3xl h-[600px] object-cover"/>
            <section className="w-1/3">
                <div className={'flex flex-row justify-between align-top'}>
                    <ArtworkHeading
                        title={title}
                        id={artist.id}
                        username={artist.username}
                        firstName={artist.firstName}
                        lastName={artist.lastName}
                    />
                    {
                        currentUser.role === "curator"
                            ?
                            <BookmarkButton/>
                            :
                            null
                    }
                </div>
                <ArtworkDescription
                    technique={technique}
                    width={width}
                    height={height}
                />
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
