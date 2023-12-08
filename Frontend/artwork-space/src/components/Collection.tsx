import React from 'react';
import {Artwork} from "../mockup/mockup_artworks";
import ArtworksList from "./lists/ArtworksList";
interface CollectionProps {
    artworks?: Artwork[];
}
const Collection:React.FC<CollectionProps> = ({artworks}) => {
    return (
        <div>
            <p className={"text-2xl font-bold pb-4"}>My Collection</p>
            {
                artworks
                ?
                    <ArtworksList artworks={artworks}/>
                    :
                    <p className={"text-gray-400"}>You added no artworks to your collection</p>
            }
        </div>
    );
};

export default Collection;