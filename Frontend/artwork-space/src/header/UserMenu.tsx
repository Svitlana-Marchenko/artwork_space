import React, {useCallback, useState} from 'react';
import {AiOutlineMenu} from "react-icons/ai";
import MenuItem from "./MenuItem";
import {useNavigate} from "react-router-dom";

const UserMenu = () => {
    const [isOpen, setIsOpen] = useState(false);
    const [isAuthorized, setIsAuthorized] = useState(true);
    const navigate = useNavigate();

    const currentUser = {
        role: "artist",
        id: 1,
        // role: "curator",
        // role: "collectioneer",
    }

    const toggleOpen = useCallback(() => {
        setIsOpen((value) => !value);
    }, []);

    return (
        <>
        <div>
            <div
                onClick={toggleOpen}
                className="
                      p-2
                      border-2
                      border-black
                      rounded-full
                      cursor-pointer
                      transition
                      "
            >
                <AiOutlineMenu size={20}/>
            </div>
        </div>
    {isOpen && (
        <div
            className="
            absolute
            rounded-xl
            shadow-md
            w-1/4
            bg-white
            overflow-hidden
            right-14
            top-20
            text-sm
            z-50
          "
        >
            <div className="flex flex-col cursor-pointer">
                {currentUser ? (
                    <>
                        {currentUser.role === "artist" && (
                            <>
                                <MenuItem label="My artworks" onClick={()=>{navigate(`/artworks/${currentUser.id}`)}}/>
                                <MenuItem label="Add new artwork" onClick={()=>{}}/>
                            </>
                        )}

                        {currentUser.role === "curator" && (
                            <>
                                <MenuItem label="My exhibitions" onClick={()=>{}}/>
                                <MenuItem label="Create exhibition" onClick={()=>{}}/>
                            </>
                        )}

                        {currentUser.role === "collectioneer" && (
                            <>
                                <MenuItem label="My collections" onClick={()=>{}}/>
                                <MenuItem label="Create collection" onClick={()=>{}}/>
                            </>
                        )}
                        <hr />
                        <MenuItem
                            label="Logout"
                            onClick={()=>{}}
                        />
                    </>
                ) : (
                    <>
                        <MenuItem
                            label="Login"
                            onClick={()=>{}}
                        />
                        <MenuItem
                            label="Sign up"
                            onClick={()=>{}}
                        />
                    </>
                )}
            </div>
        </div>
    )}
    </>
    )
};

export default UserMenu;
