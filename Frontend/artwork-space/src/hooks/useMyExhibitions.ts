import {useEffect, useState} from "react";
import ExhibitionService from "../API/ExhibitionService";
import {User} from "../types/usersTypes";
import {Exhibition} from "../types/exhibitionsTypes";

interface IUseMyExhibition {
    exhibition: Exhibition | undefined;
    user: User;
}

const useMyExhibition = ({ user, exhibition }: IUseMyExhibition) => {
    const [isMyExhibition, setIsMyExhibition] = useState<boolean | null>(null);

    useEffect(() => {
        if(exhibition&&user) {
            const fetchData = async () => {
                try {
                    const list: Exhibition[] | null = await ExhibitionService.getExhibitionByCuratorId(user.id) || [];
                    const isMine = list ? list.some((myexhibition) => myexhibition.id === exhibition.id) : false;
                    setIsMyExhibition(isMine);
                } catch (error) {
                    console.error('Помилка при отриманні даних з сервера:', error);
                    setIsMyExhibition(false);
                }
            };

            fetchData();
        }
    }, [user, exhibition]);
    return {
        isMyExhibition
    };
};

export default useMyExhibition;