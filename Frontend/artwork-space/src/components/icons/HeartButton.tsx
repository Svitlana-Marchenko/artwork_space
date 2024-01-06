import { AiFillHeart, AiOutlineHeart } from "react-icons/ai";
import React, {useCallback, useEffect, useState} from "react";
import {User} from "../../types/usersTypes";
import useFavorite from "../../hooks/useFavorite";
import {Artwork as ArtworkType, Artwork} from "../../types/artworkTypes";
import {Collection} from "../../types/collectionTypes";
import MenuItem from "../../header/MenuItem";
import CollectionService from "../../API/CollectionService";
import toast from "react-hot-toast";
import AuctionModal from "../modals/AuctionModal";
import CollectionModal from "../modals/CollectionModal";

interface HeartButtonProps {
    artworkId: number;
    favouriteArtworks: Collection[];
    dark?: boolean;
    chosenCollection?: Collection;
    menuWidth:string;
    menuTop:string;
    menuRight:string;
}

const HeartButton:React.FC<HeartButtonProps> = ({artworkId, dark,favouriteArtworks,chosenCollection,menuWidth,menuRight,menuTop}) => {
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User= storedUserString ? JSON.parse(storedUserString) : null;
    const { hasFavorite, toggleFavorite, setHasFavoriteDirectly } = useFavorite({
        artworkId,
        currentUser,
        favouriteArtworks,
        chosenCollection
    });
    const [isOpen, setIsOpen] = useState(false);
    const toggleOpen = useCallback(async () => {
        setIsOpen((value) => !value);
    }, []);
    const [isHeartHovered, setHeartHovered] = useState(false);
    const [isMenuHovered, setMenuHovered] = useState(false);

    const handleMouseEnterHeart = () => {
        setHeartHovered(true);
        //setMenuHovered(true); // Show the menu when the mouse enters the heart
    };

    const handleMouseLeaveHeart = () => {
        setHeartHovered(false);
        // Keep the menu visible when the mouse leaves the heart but is still on the menu
    };

    const handleMouseEnterMenu = () => {
        setMenuHovered(true);
    };

    const handleMouseLeaveMenu = () => {
        setMenuHovered(false);
    };
    const handleCollectionClick = (collectionId: number) => {
        // Perform the request to add artwork to the collection
        CollectionService.addArtworkToCollection(collectionId, artworkId)
            .then((response) => {
                // Display success message
                toast.success("Artwork added successfully to the collection");
                // Hide the menu
                setMenuHovered(false);

                setHasFavoriteDirectly(true);
            })
            .catch((error) => {
                // Display error message
                toast.error("Failed to add artwork to the collection");
            });

    };
    const dynamicClassName = (width: string, right: string, top: string) => `
  absolute
  rounded-xl
  shadow-md
  w-${width}
  bg-white
  overflow-hidden
  right-${right}
  top-${top}
  text-sm
  z-30
`;
    return (
        <>
        <div
            onMouseEnter={handleMouseEnterHeart}
            onMouseLeave={handleMouseLeaveHeart}
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
                className={`
                      ${dark ? "fill-[#a62c2a]" : "fill-white"}
                      absolute
                      -top-[2px]
                      -right-[2px]
                        `}
            />
            <AiFillHeart
                size={24}
                className={
                    hasFavorite ? 'fill-[#a62c2a]' : 'fill-transparent'
                }
            />
        </div>
            {(isHeartHovered || isMenuHovered) && !hasFavorite && chosenCollection == null && (
                <div
                    onMouseEnter={handleMouseEnterMenu}
                    onMouseLeave={handleMouseLeaveMenu}
                    className={dynamicClassName(menuWidth,menuRight,menuTop)}
                >
                    <div className="flex flex-col cursor-pointer">
                        {favouriteArtworks.map((collection) => (
                            <MenuItem
                                key={collection.id}
                                label={collection.title}
                                style={{color: 'black' }}
                                onClick={() => handleCollectionClick(collection.id)}
                            />
                        ))}
                        <hr />
                        <MenuItem
                            label="Create New"
                            style={{color: 'black' }}
                            onClick={toggleOpen}
                        />
                    </div>
                </div>
            )}
            <CollectionModal isOpen={isOpen} toggle={toggleOpen}/>
        </>
    );
}

export default HeartButton;
