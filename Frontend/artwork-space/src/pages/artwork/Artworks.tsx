import React, {useEffect, useState} from 'react';
import ArtworksList from "../../components/lists/ArtworksList";
import {useParams} from "react-router-dom";
import {User} from "../../mockup/mockup_users";
import {convertToInt} from "../../actions/functions";
import ArtworkService from "../../API/ArtworkService";
import {Artwork} from "../../mockup/mockup_artworks";

const Artworks = () => {
    const { id } = useParams();
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User | null = storedUserString ? JSON.parse(storedUserString) : null;
    const [artworks, setArtworks] = useState<Artwork[]>([]);

    //todo check artistid role (like unable to see artwork of non-artist users)

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
        }
    }, [id]);

    return (
        <div className="mx-32">
            {
                (currentUser?.id === convertToInt(id))&&(
                    <p className={'text-3xl font-bold my-8'}> MY ARTWORKS</p>
                )
            }
            <ArtworksList artworks={artworks}/>
        </div>
    );
};

export default Artworks;
