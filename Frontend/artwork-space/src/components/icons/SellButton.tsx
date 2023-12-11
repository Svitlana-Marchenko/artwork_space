    import React, {useCallback, useEffect, useState} from "react";
    import {MdOutlineSell, MdSell} from "react-icons/md";
    import useAuction from "../../hooks/useAuction";
    import {User} from "../../mockup/mockup_users";
    import {Artwork} from "../../mockup/mockup_artworks";
    import {Auction} from "../../mockup/mockup_auctions";
    import AuctionModal from "../modals/AuctionModal";
    import toast from "react-hot-toast";
    interface SellButtonProps {
        artwork: Artwork;
        currentUser: User;
    }
    const SellButton:React.FC<SellButtonProps> = ({artwork, currentUser}) => {
        const {hasAuction, checkAuction} = useAuction({artwork});
        const [isOpen, setIsOpen] = useState(false);
        const toggleOpen = useCallback(async () => {
            await checkAuction();
            if (!hasAuction) {
                setIsOpen((value) => !value);
            } else {
                toast.error("Artwork is already on auction");
            }
        }, [checkAuction, hasAuction]);
        return (
            <>
            <div
                onClick={toggleOpen}
                className="
                    relative
                    hover:opacity-80
                    transition
                    cursor-pointer
                  ">
            <MdOutlineSell
                size={32}
                className="
                     fill-black
                    absolute
                    -top-[2px]
                    -right-[2px]"
            />
            <MdSell
                size={30}
                className={
                    hasAuction ? 'fill-[#a62c2a]' : 'fill-transparent'
                }
            />
        </div>
                <AuctionModal isOpen={isOpen} toggle={toggleOpen} artwork={artwork}/>
            </>
    );
    }

    export default SellButton;
