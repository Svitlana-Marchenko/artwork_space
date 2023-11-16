import {useState} from "react";
import {IoBookmark, IoBookmarkOutline} from "react-icons/io5";

// import useFavorite from "@/app/hooks/useFavorite";
// import { SafeUser } from "@/app/types";

// interface HeartButtonProps {
//     listingId: string
//     currentUser?: SafeUser | null
// }

const BookmarkButton = () => {
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
            <IoBookmarkOutline
                size={28}
                className="
          fill-white
          absolute
          -top-[2px]
          -right-[2px]
        "
            />
            <IoBookmark
                size={24}
                className={
                    hasFavorite ? 'fill-[#a62c2a]' : 'fill-transparent'
                }
            />
        </div>
    );
}

export default BookmarkButton;
