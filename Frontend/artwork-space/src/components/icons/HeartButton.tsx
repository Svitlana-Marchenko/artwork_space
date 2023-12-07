import { AiFillHeart, AiOutlineHeart } from "react-icons/ai";
import React, {useEffect, useState} from "react";
import {User} from "../../mockup/mockup_users";
import useFavorite from "../../hooks/useFavorite";

interface HeartButtonProps {
    artworkId: number;
}

const HeartButton:React.FC<HeartButtonProps> = ({artworkId}) => {
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User= storedUserString ? JSON.parse(storedUserString) : null;
    const { hasFavorite, toggleFavorite } = useFavorite({
        artworkId,
        currentUser
    });


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
