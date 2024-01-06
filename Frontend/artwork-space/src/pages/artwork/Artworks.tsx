import React, {useEffect, useState} from 'react';
import ArtworksList from "../../components/lists/ArtworksList";
import {useParams} from "react-router-dom";
import {User} from "../../types/usersTypes";
import {convertToInt} from "../../actions/functions";
import ArtworkService from "../../API/ArtworkService";
import {Artwork} from "../../types/artworkTypes";
import {Collection} from "../../types/collectionTypes";
import CollectionService from "../../API/CollectionService";

const Artworks = () => {
    const { id } = useParams();
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User | null = storedUserString ? JSON.parse(storedUserString) : null;
    const [artworks, setArtworks] = useState<Artwork[]>([]);
    const [allCollections, setAllCollections] = useState<Collection[]>([]);


    useEffect(() => {
        if (id) {
            ArtworkService.getAllArtworksByArtistId(id)
                .then(data => {setArtworks(data);
                })
                .catch(error => console.error('Помилка при отриманні даних про список картин:', error));
        } else {
            ArtworkService.getAllArtworks()
                .then(data => {
                    setArtworks(data);
                })
                .catch(error => console.error('Помилка при отриманні даних про список картин:', error));
            if(currentUser && (currentUser.role === "CURATOR" || currentUser.role === "COLLECTIONEER")) {
                CollectionService.getAllCollectionsByUser(currentUser.id)
                    .then(data => {setAllCollections(data);
                    })
                    .catch(error => console.error('Error getting collections:', error));
            }
        }
    }, [id]);

    return (
        <div className="mx-32">
            {
                (currentUser?.id === convertToInt(id))&&(
                    <p className={'text-3xl font-bold my-8'}> MY ARTWORKS</p>
                )
            }
            <ArtworksList artworks={artworks} collections={allCollections}/>
        </div>
    );
};

export default Artworks;
