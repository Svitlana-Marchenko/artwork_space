'use client';

import { AiFillHeart, AiOutlineHeart } from "react-icons/ai";
import {useState} from "react";

// import useFavorite from "@/app/hooks/useFavorite";
// import { SafeUser } from "@/app/types";

// interface HeartButtonProps {
//     listingId: string
//     currentUser?: SafeUser | null
// }

const HeartButton = () => {
    // const { hasFavorite, toggleFavorite } = useFavorite({
    //     listingId,
    //     currentUser
    // });
    const [hasFavorite, setHasFavorite] = useState(false);

    function toggleFavorite() {
        setHasFavorite(!hasFavorite)
    }

    return (
        <div
            onClick={toggleFavorite}
            className="
        relative
        hover:opacity-80
        transition
        cursor-pointer
      "
        >
            <AiOutlineHeart
                size={28}
                className="
          fill-white
          absolute
          -top-[2px]
          -right-[2px]
        "
            />
            <AiFillHeart
                size={24}
                className={
                    hasFavorite ? 'fill-[#a62c2a]' : 'fill-transparent'
                }
            />
        </div>
    );
}

export default HeartButton;
