import React, {useEffect, useState} from 'react';
import ArtworksList from "../../components/lists/ArtworksList";
import {useParams} from "react-router-dom";
import {User} from "../../mockup/mockup_users";
import {getUserByIdAsync} from "../../actions/getUserById";
import {convertToInt} from "../../actions/functions";

const Artworks = () => {
    const { id } = useParams();
    const [artist, setArtist] = useState<User>();
    const currentUser = {
        role: "artist",
        id: 1,
        // role: "curator",
        // role: "collectioneer",
    }

    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                const user = await getUserByIdAsync(id);
                setArtist(user);
            };
            fetchData();
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
            <ArtworksList artistId={id}/>
        </div>
    );
};

export default Artworks;
