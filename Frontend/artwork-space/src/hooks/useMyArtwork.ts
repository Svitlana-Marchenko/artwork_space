import {useEffect, useState} from "react";
import {Artwork as ArtworkType} from "../mockup/mockup_artworks";
import ArtworkService from "../API/ArtworkService";
import {User} from "../mockup/mockup_users";

interface IUseMyArtwork {
    artwork: ArtworkType | undefined;
    user: User;
}

const useMyArtwork = ({ user, artwork }: IUseMyArtwork) => {
    const [isMyArtwork, setIsMyArtwork] = useState<boolean | null>(null);

    useEffect(() => {
        if(artwork&&user) {
            const fetchData = async () => {
                try {
                    const list: ArtworkType[] | null = await ArtworkService.getAllArtworksByArtistId(user.id) || [];
                    const isMine = list ? list.some((myartwork) => myartwork.id === artwork.id) : false;
                    setIsMyArtwork(isMine);
                } catch (error) {
                    console.error('Помилка при отриманні даних з сервера:', error);
                    setIsMyArtwork(false);
                }
            };

            fetchData();
        }
    }, [user, artwork]);
    return {
        isMyArtwork
    };
};

export default useMyArtwork;