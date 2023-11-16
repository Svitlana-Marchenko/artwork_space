import React, {useEffect, useState} from 'react';
import {User} from "../../mockup/mockup_users";
import {getUserByIdAsync} from "../../actions/getUserById";
import {convertToInt} from "../../actions/functions";

const ArtworksHeading: React.FC<{ id: string}> = ({ id }) => {
    const [artist, setArtist] = useState<User>();
    const currentUser = {
        role: "artist",
        id: 1,
        // role: "curator",
        // role: "collectioneer",
    }
    useEffect(() => {
        const fetchData = async () => {
            const user = await getUserByIdAsync(id);
            setArtist(user);
        };
    fetchData();
    }, [id]);

    return (
        <div>
            {artist && (
                <>
                    <p className={'text-3xl font-bold my-8'}>
                        {currentUser.id === convertToInt(id) ? "MY" : `${artist.firstName.toUpperCase()} ${artist.lastName.toUpperCase()}`} ARTWORKS
                    </p>
                </>
            )}
        </div>
    );
};

export default ArtworksHeading;
