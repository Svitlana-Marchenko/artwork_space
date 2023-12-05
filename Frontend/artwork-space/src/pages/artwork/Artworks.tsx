import React, {useEffect, useState} from 'react';
import ArtworksList from "../../components/lists/ArtworksList";
import {useParams} from "react-router-dom";
import {User} from "../../mockup/mockup_users";
import {getUserByIdAsync} from "../../actions/getUserById";
import {convertToInt} from "../../actions/functions";
import ArtworkService from "../../API/ArtworkService";
import {Artwork} from "../../mockup/mockup_artworks";;

const Artworks = () => {
    const { id } = useParams();
    const [artist, setArtist] = useState<User>();
    const currentUser = {
        role: "artist",
        id: 1,
        // role: "curator",
         //role: "collectioneer",
    }

    const [artworks, setArtworks] = useState<Artwork[]>([]);

    //todo check artistid role (like unable to see artwork of non-artist users)

    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                const user = await getUserByIdAsync(id);
                setArtist(user);
            };
            fetchData();
        }
    }, [id]);

    useEffect(() => {
        if (id !== undefined) {
            ArtworkService.getAllArtworksByArtistId(id)
                .then(data => {setArtworks(data);
                })
                .catch(error => console.error('Помилка при отриманні даних про список картин:', error));

        } else {
            ArtworkService.getAllArtworks()
                .then(data => {setArtworks(data);
                })
                .catch(error => console.error('Помилка при отриманні даних про список картин:', error));
        }
    }, [id]);

    return (
        <div className="mx-32">
            {
                id
                ?
                    artist && (
                        <>
                            <p className={'text-3xl font-bold my-8'}>
                                {currentUser.id === convertToInt(id)
                                    ?
                                    "MY"
                                    :
                                    `${artist.firstName.toUpperCase()} ${artist.lastName.toUpperCase()}`} ARTWORKS
                            </p>
                        </>
                    )
                    :
                    null
            }
            <ArtworksList artworks={artworks}/>
        </div>
    );
};

export default Artworks;
