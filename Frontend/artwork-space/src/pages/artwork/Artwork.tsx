import React, {useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import {Artwork as ArtworkType} from '../../mockup/mockup_artworks'
import {Button} from "../../components/Button";
import ArtworkHeading from "../../components/artwork/ArtworkHeading";
import ArtworkDescription from "../../components/artwork/ArtworkDescription";
import ArtworkService from "../../API/ArtworkService";
import toast from 'react-hot-toast';
import {User} from "../../mockup/mockup_users";
import SellButton from "../../components/icons/SellButton";
import HeartButton from "../../components/icons/HeartButton";
import Empty from "../../empty";


const Artwork = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User= storedUserString ? JSON.parse(storedUserString) : null;
    const [artwork, setArtwork] = useState<ArtworkType>();

    useEffect(() => {
        if (id) {
            ArtworkService.getArtworkById(id)
                .then((data) => setArtwork(data))
                .catch((error) => {
                    console.error('Помилка при отриманні даних з сервера:', error)
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
                        navigate(-1)
                        console.log(response)
                        toast.success('Artwork deleted successfully');
                    } else {
                        response.text().then(errorMessage => {
                            toast.error(errorMessage);
                        });

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
                                averageRating={0}
                            />
                            {
                                currentUser?.role === "ARTIST"
                                    ?
                                    <SellButton artwork={artwork} currentUser={currentUser}/>
                                    :
                                    null
                            }
                            {
                                currentUser?.role === "CURATOR" || currentUser?.role === "COLLECTIONEER"
                                    ?
                                    <HeartButton artworkId={artwork.id} dark/>
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
                                currentUser?.role === 'ARTIST'
                                    ?
                                    <>
                                        <Button label={"Edit"} onClick={()=>{}}/>
                                        <Button label={"Delete"} onClick={handleDelete} outline/>
                                    </>
                                    :
                                    currentUser?.role === "CURATOR"
                                        ?
                                        <>
                                            <Button label={"Add review"} onClick={()=>{}}/>
                                        </>
                                        :
                                        null
                            }
                        </div>
                    </section> </>
            ) : (
                <Empty message={"Theres no such artwork here"} link={`/artworks`} dist={'all artworks'}/>
            )}
        </div>
    );
};

export default Artwork;
