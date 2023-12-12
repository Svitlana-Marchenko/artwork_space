import { useCallback, useEffect, useState } from "react";
import AuctionService from "../API/AuctionService";
import { Artwork } from "../types/artworkTypes";
import {Auction} from "../types/auctionsTypes";

interface IUseAuction {
    artwork: Artwork;
}

const useAuction = ({ artwork }: IUseAuction) => {
    const [hasAuction, setHasAuction] = useState<boolean>(false);

    const checkAuction = useCallback(async () => {
        try {
            const list: Auction[] | null = await AuctionService.getAllAuctions() || [];
            const isAuction = list ? list.some((auction) => auction.artwork.id === artwork.id) : false;
            setHasAuction(isAuction);
        } catch (error) {
            console.error('Error fetching data from the server:', error);
            setHasAuction(false);
        }
    }, [artwork.id]);

    useEffect(() => {
        checkAuction();
    }, [checkAuction]);

    return {
        hasAuction,
        checkAuction,
    };
};

export default useAuction;
