import React, {useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import {Artwork as ArtworkType} from '../../mockup/mockup_artworks'
import {Button} from "../../components/Button";
import ArtworkHeading from "../../components/artwork/ArtworkHeading";
import ArtworkDescription from "../../components/artwork/ArtworkDescription";
import BookmarkButton from "../../components/BookmarkButton";
import ArtworkService from "../../API/ArtworkService";
import toast from 'react-hot-toast';


const Artwork = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const [artwork, setArtwork] = useState<ArtworkType>();
    const currentUser = {
        role: "artist",
       //role: "curator",
        // role: "collectioneer",
    };

    useEffect(() => {
        if (id) {
            ArtworkService.getArtworkById(id)
                .then((data) => setArtwork(data))
                .catch((error) => {
                    console.error('Помилка при отриманні даних з сервера:', error)

                    navigate(`/artworks`);
                    toast.error('Artwork not found');
                });
        }
    }, [id]);


    //todo change to custom window.confirm
    //todo допиши хендлер помилок, коли артворк в виставці, коли у колекції та коли на аукціоні
    //todo add link to api folder
    function handleDelete() {
        if (window.confirm('Are you sure you want to delete this artwork?')) {
            fetch(`http://localhost:8080/artworks/${id}`, {
                method: 'DELETE',
            })
                .then((response) => {
                    if (response.ok) {
                        navigate(`/artworks`)
                        toast.success('Artwork deleted successfully');
                    } else {
                        toast.error('Failed to delete artwork. This artwork may be used in exhibition or auction');
                    }
                })
                .catch((error) => {
                    console.error('Error while deleting artwork:', error);
                });
        }
    }

    return (
        <div className="flex flex-row items-center justify-center gap-10">
            {artwork ? (
                <>
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
                              <Button label={"Delete"} onClick={handleDelete} outline/>
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
            </section> </>
            ) : (
                <div>Loading</div>
            )}
        </div>
    );
};

//todo normal loading page

export default Artwork;
