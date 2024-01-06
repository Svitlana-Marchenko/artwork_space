import {useCallback, useEffect, useState} from "react";
import { toast } from "react-hot-toast";
import React from "react";
import {User} from "../types/usersTypes";
import CollectionService from "../API/CollectionService";
import {Artwork as ArtworkType, Artwork} from "../types/artworkTypes";
import {Collection} from "../types/collectionTypes";

interface IUseFavorite {
    artworkId: number;
    currentUser: User;
    favouriteArtworks: Collection[];
    chosenCollection?: Collection;
}

const useFavorite = ({ artworkId, currentUser, favouriteArtworks,chosenCollection }: IUseFavorite) => {
    const [hasFavorite, setHasFavorite] = useState<boolean>(false);

    const [collectionId, setCollectionId] = useState<number | null>(null);

    useEffect(() => {
        const foundCollectionId = findCollectionIdByArtworkId(favouriteArtworks, artworkId);
        setCollectionId(foundCollectionId);
        setHasFavorite(foundCollectionId !== null);
    }, [favouriteArtworks, artworkId]);

    const findCollectionIdByArtworkId = (collections: Collection[], artworkId: number): number | null => {
        for (const collection of collections) {
            const artworkIdsInCollection = collection.artworks.map((artwork: Artwork) => artwork.id);
            if (artworkIdsInCollection.includes(artworkId)) {
                return collection.id;
            }
        }
        return null;
    };
    const toggleFavorite = useCallback(async () => {
            try {
                let request;

                if(chosenCollection){
                    if(hasFavorite){
                        request = () => CollectionService.deleteArtworkFromCollection(chosenCollection.id, artworkId);
                    }else {
                        request = () => CollectionService.addArtworkToCollection(chosenCollection.id, artworkId);
                        toast.success('Added to collection');
                        setHasFavorite(!hasFavorite);
                    }
                    await request();
                }
                else if (collectionId !== null) {
                    request = () => CollectionService.deleteArtworkFromCollection(collectionId, artworkId);
                    await request();
                }

                if (hasFavorite) {
                    toast.success('Deleted from collection');
                    setHasFavorite(!hasFavorite);
                }

            } catch (error) {
                toast.error('Failed to perform the action');
            }
        },
        [
            currentUser,
            hasFavorite,
            artworkId,
            chosenCollection,
        ]);

    const setHasFavoriteDirectly = (value: boolean) => {
        setHasFavorite(value);
    };

    return {
        hasFavorite,
        toggleFavorite,
        setHasFavoriteDirectly, // Provide the direct setter
    };
};


export default useFavorite;


/*
*   const toggleFavorite = useCallback(async (e: React.MouseEvent<HTMLDivElement>) => {
            e.stopPropagation();

            try {
                let request;

                if (collectionId !== null) {
                    request = () => CollectionService.deleteArtworkFromCollection(currentUser.id, artworkId);
                    await request();
                    toast.success('Deleted from collection');
                    setHasFavorite(!hasFavorite);
                }


                if (hasFavorite) {
                    toast.success('Deleted from collection');
                    setHasFavorite(!hasFavorite);
                }

            } catch (error) {
                toast.error('Failed to perform the action');
            }
        },
        [
            currentUser,
            hasFavorite,
            artworkId,
        ]);*/