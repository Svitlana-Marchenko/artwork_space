import React, {useState} from "react";
import {IoAddCircleOutline, IoCheckmarkCircle} from "react-icons/io5";
import {Artwork} from "../../mockup/mockup_artworks";
interface PlusButtonProps {
    artwork: Artwork
    onAddToExhibition: (artwork: Artwork) => void;
}

const PlusButton:React.FC<PlusButtonProps> = ({onAddToExhibition, artwork}) => {
    const [isAdded, setIsAdded] = useState(false);

    function togglePlus() {
        setIsAdded(!isAdded)
        onAddToExhibition(artwork);
    }

    return (
        <div
            onClick={togglePlus}
    className="
    relative
    hover:opacity-80
    transition
    cursor-pointer
    "
    >
            {
                isAdded
                ?
                    <IoCheckmarkCircle
                        size={28}
                        className={'fill-[#a62c2a]'}
                    />
                    :
                    <IoAddCircleOutline
                        size={28}
                        className="fill-white"
                    />
            }
    </div>
);
}

export default PlusButton;
