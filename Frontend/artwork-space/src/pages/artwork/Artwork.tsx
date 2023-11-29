import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import {Artwork as ArtworkType} from '../../mockup/mockup_artworks'
import {Button} from "../../components/Button";
import ArtworkHeading from "../../components/artwork/ArtworkHeading";
import ArtworkDescription from "../../components/artwork/ArtworkDescription";
import BookmarkButton from "../../components/BookmarkButton";
import ArtworkService from "../../API/ArtworkService";


const Artwork = () => {
    const { id } = useParams();
    const [artwork, setArtwork] = useState<ArtworkType>();
    const currentUser = {
        //role: "artist",
        role: "curator",
        // role: "collectioneer",
    };

    useEffect(() => {
        ArtworkService.getArtworkById(id)
            .then((data) => setArtwork(data))
            .catch((error) => console.error('Помилка при отриманні даних з сервера:', error));
    }, [id]);


    //todo add normal error page (if artwork with given id is undefined)
    if (!artwork) {
        return <div>Loading...</div>; // або інша обробка завантаження
    }

    return (
        <div className="flex flex-row items-center justify-center gap-10">
            <img src={artwork.imageURL} alt={artwork.title} className="w-auto max-w-3xl h-[600px] object-cover"/>
            <section className="w-1/3">
                <div className={'flex flex-row justify-between align-top'}>
                    <ArtworkHeading
                        title={artwork.title}
                        id={artwork.user.id}
                        username={artwork.user.username}
                        firstName={artwork.user.firstName}
                        lastName={artwork.user.lastName}
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
                    technique={artwork.technique}
                    width={artwork.width}
                    height={artwork.height}
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
