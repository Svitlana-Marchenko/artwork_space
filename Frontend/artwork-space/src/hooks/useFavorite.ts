import axios from "axios";
import { useCallback, useMemo } from "react";
import { toast } from "react-hot-toast";

import React from "react";
import {User} from "../mockup/mockup_users";
import {useNavigate} from "react-router-dom";
import CollectionService from "../API/CollectionService";

interface IUseFavorite {
    listingId: string;
    currentUser: User
}

const useFavorite = ({ listingId, currentUser }: IUseFavorite) => {
    const navigate = useNavigate();

    const hasFavorite = useMemo(() => {
        const list = CollectionService.getArtworksFromCollection(currentUser.id)
        //return list.includes(listingId);
        return true;
    }, [currentUser, listingId]);

    const toggleFavorite = useCallback(async (e: React.MouseEvent<HTMLDivElement>) => {
            e.stopPropagation();

            try {
                let request;

                if (hasFavorite) {
                    request = () => axios.delete(`/api/favorites/${listingId}`);
                } else {
                    request = () => axios.post(`/api/favorites/${listingId}`);
                }

                await request();
                //navigate('/', { replace: true });
                toast.success('Added to collection');
            } catch (error) {
                toast.error('Failed to add to collection');
            }
        },
        [
            currentUser,
            hasFavorite,
            listingId,
        ]);

    return {
        hasFavorite,
        toggleFavorite,
    }
}

export default useFavorite;