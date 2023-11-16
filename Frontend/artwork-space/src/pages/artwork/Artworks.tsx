import React from 'react';
import ArtworksList from "../../components/lists/ArtworksList";
import {useParams} from "react-router-dom";
import ArtworksHeading from "../../components/artwork/ArtworksHeading";

const Artworks = () => {
    const { id } = useParams();

    return (
        <div className="mx-32">
            {
                id
                ?
                    <ArtworksHeading id={id}/>
                    :
                    null
            }
            <ArtworksList artistId={id}/>
        </div>
    );
};

export default Artworks;
