import React from 'react';
import {Artwork} from "../types/artworkTypes";
import ArtworksList from "./lists/ArtworksList";
interface CollectionProps {
    artworks?: Artwork[];
}
const Collection:React.FC<CollectionProps> = ({artworks}) => {
    return (
        <div>
            <p className={'text-3xl font-bold my-4'}> MY COLLECTION</p>
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