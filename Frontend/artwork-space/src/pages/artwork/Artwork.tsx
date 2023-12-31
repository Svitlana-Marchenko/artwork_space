import React, {useEffect, useRef, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import {Artwork as ArtworkType} from '../../types/artworkTypes'
import {Button} from "../../components/Button";
import ArtworkHeading from "../../components/artwork/ArtworkHeading";
import ArtworkDescription from "../../components/artwork/ArtworkDescription";
import ArtworkService from "../../API/ArtworkService";
import toast from 'react-hot-toast';
import {User} from "../../types/usersTypes";
import SellButton from "../../components/icons/SellButton";
import ArtworkRatings from "../../components/ratings/ArtworkRatings";
import HeartButton from "../../components/icons/HeartButton";
import Empty from "../../empty";
import {calculateAverageRating, hasUserRatedArtwork} from "../../utils/ratingUtils";
import useMyArtwork from "../../hooks/useMyArtwork";
import RatingModal from "../../components/modals/RatingModal";
import ExhibitionService from "../../API/ExhibitionService";
import artworks from "./Artworks";
import CollectionService from "../../API/CollectionService";
import {Collection} from "../../types/collectionTypes";


const Artwork = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User= storedUserString ? JSON.parse(storedUserString) : null;
    const [artwork, setArtwork] = useState<ArtworkType>();
    const ratingFormRef = useRef<HTMLDivElement  | null>(null);
    const { isMyArtwork } = useMyArtwork({
        user: currentUser,
        artwork
    });
    useEffect(() => {
        if (id) {
            ArtworkService.getArtworkById(id)
                .then((data) => setArtwork(data))
                .catch((error) => {
                    console.error('Помилка при отриманні даних з сервера:', error)
                    navigate(`/artworks`);
                    toast.error('Artwork not found');
                });
            if(currentUser && (currentUser.role === "CURATOR" || currentUser.role === "COLLECTIONEER")) {
                CollectionService.getAllCollectionsByUser(currentUser.id)
                    .then((data) => setAllCollections(data || []))
                    .catch((error) => console.error('Error getting favourites:', error));
            }
        }
    }, [id]);
    const [newRatingAdded, setNewRatingAdded] = useState(false); // State to track new rating

    useEffect(() => {
        if (newRatingAdded) {
            // Refresh the page when a new rating is added
            window.location.reload();
        }
    }, [newRatingAdded]);
// Check if the current user has already rated the artwork
    const hasUserRated = hasUserRatedArtwork(artwork?.ratings || [], currentUser);
    const [isArtworkSold, setIsArtworkSold] = useState<boolean | null>(null);
   // const [favoriteArtworksByCollection, setFavoriteArtworksByCollection] = useState<Record<number, ArtworkType[]>>({});

    const [allCollections, setAllCollections] = useState<Collection[]>([]);

    useEffect(() => {
        if (artwork?.id && currentUser?.role === "ARTIST") {
            ArtworkService.isArtworkSold(artwork.id)
                .then((data) => setIsArtworkSold(data))
                .catch((error) => console.error('Error checking if artwork is sold:', error));
        } else {
            setIsArtworkSold(false);
        }
    }, [artwork?.id, currentUser?.role]);

    function handleAddReview() {
        if (currentUser?.role === "CURATOR" && ratingFormRef.current) {
            ratingFormRef.current.scrollIntoView({ behavior: 'smooth' });
        }
    }
    const averageRating = calculateAverageRating(artwork?.ratings || []);

    function handleDelete() {
        if (id) {
            ArtworkService.deleteArtworkById(id)
                .then(() => {
                    navigate(-1)
                    toast.success('Artwork was deleted');
                })
                .catch((error) => {
                    toast.error('Failed to delete artwork.');
                    console.log(error)
                });
        }
    }

    return (
        <div className="flex flex-col items-center justify-center gap-10">
            {artwork ? (
                <>
                    <div className="flex flex-row items-center justify-center gap-10">
                        <img src={artwork.imageURL} alt={artwork.title} className="w-auto max-w-3xl h-[600px] object-cover"/>
                        <section className="w-[400px]">
                            <div className={'flex flex-row justify-between align-top'}>
                                <ArtworkHeading
                                    title={artwork.title}
                                    id={artwork.user.id}
                                    username={artwork.user.username}
                                    firstName={artwork.user.firstName}
                                    lastName={artwork.user.lastName}
                                    averageRating={averageRating}
                                    showAverageRating={artwork.ratings.length>0}
                                />
                                {
                                    currentUser?.role === "ARTIST"
                                        ?
                                        isMyArtwork && !isArtworkSold && (<SellButton artwork={artwork} currentUser={currentUser}/>)
                                        :
                                        null
                                }
                                {
                                    currentUser?.role === "CURATOR" || currentUser?.role === "COLLECTIONEER"
                                        ?
                                        <HeartButton artworkId={artwork.id} dark favouriteArtworks={allCollections} menuWidth={"1/4"} menuRight={"3"} menuTop={"6"}/>
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
                                        isMyArtwork && (<>
                                            <Button label={"Edit"} onClick={()=>{navigate(`/edit-artwork/${id}`)}}/>
                                            <Button label={"Delete"} onClick={handleDelete} outline/>
                                        </>)
                                        :
                                        null
                                }
                            </div>
                        </section>
                    </div>
                    <div className={'w-2/3'}>
                        <h4 className="text-lg font-semibold mb-2" style={{ textAlign: 'left' }}>Description</h4>
                        <p>{artwork.description}</p>
                    </div>
                    <div style={{ width: '100%' }} className={"mb-8"}>
                        {currentUser?.role === "CURATOR" && !hasUserRated && (
                            <div ref={ratingFormRef}>
                                <RatingModal currentUser={currentUser} currentArtworkId={Number(id)}
                                             onRatingAdded={() => setNewRatingAdded(true)}/>
                            </div>
                        )}
                        <ArtworkRatings ratings={artwork.ratings} currentArtworkId={Number(id)} showRatingForm={currentUser?.role === "CURATOR"} currentUser={currentUser} />
                    </div>
                </>
            ) : (
                <Empty message={"Theres no such artwork here"} link={`/artworks`} dist={'all artworks'}/>
            )}
        </div>
    );
};

export default Artwork;
