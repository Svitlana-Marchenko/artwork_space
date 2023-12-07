import {useCallback, useEffect, useState} from "react";
import { toast } from "react-hot-toast";
import React from "react";
import {User} from "../mockup/mockup_users";
import CollectionService from "../API/CollectionService";
import {Artwork} from "../mockup/mockup_artworks";

interface IUseFavorite {
    artworkId: number;
    currentUser: User
}

const useFavorite = ({ artworkId, currentUser }: IUseFavorite) => {
    const [hasFavorite, setHasFavorite] = useState<boolean | null>(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const list: Artwork[] | null = await CollectionService.getArtworksFromCollection(currentUser.id) || [];
                const isFavorite = list ? list.some((artwork) => artwork.id === artworkId) : false;
                setHasFavorite(isFavorite);
            } catch (error) {
                console.error('Помилка при отриманні даних з сервера:', error);
                setHasFavorite(false);
            }
        };

        fetchData();
    }, [currentUser, artworkId]);

    const toggleFavorite = useCallback(async (e: React.MouseEvent<HTMLDivElement>) => {
            e.stopPropagation();

            try {
                let request;

                if (hasFavorite) {
                    request = () => CollectionService.deleteArtworkFromCollection(currentUser.id, artworkId);
                } else {
                    request = () => CollectionService.addArtworkToCollection(currentUser.id, artworkId);
                }

                await request();
                if (!hasFavorite) {
                    toast.success('Added to collection');
                } else {
                    toast.success('Deleted from collection');
                }

                setHasFavorite(!hasFavorite);
            } catch (error) {
                toast.error('Failed to add to collection');
            }
        },
        [
            currentUser,
            hasFavorite,
            artworkId,
        ]);

    return {
        hasFavorite,
        toggleFavorite,
    };
};


export default useFavorite;