import React, {useCallback, useState} from 'react';
import {AiOutlineMenu} from "react-icons/ai";
import MenuItem from "./MenuItem";
import {useNavigate} from "react-router-dom";
import {User} from "../mockup/mockup_users"
import {LoginModal} from "../components/modals/LoginModal";
import {RegisterModal} from "../components/modals/RegisterModal";

interface UserMenuProps {
    currentUser?: User | null
}
const UserMenu:React.FC<UserMenuProps> = ({currentUser}) => {
    const [isOpenUserMenu, setIsOpenUserMenu] = useState(false);
    const [isOpenLogin, setIsOpenLogin] = useState(false);
    const [isOpenRegister, setIsOpenRegister] = useState(false);
    const [isAuthorized, setIsAuthorized] = useState(true);
    const navigate = useNavigate();

    const toggleOpen = useCallback(() => {
        setIsOpenUserMenu((value) => !value);
    }, []);
    const toggleOpenLogin = useCallback(() => {
        setIsOpenLogin((value) => !value);
    }, []);
    const toggleOpenRegister = useCallback(() => {
        setIsOpenRegister((value) => !value);
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
    {isOpenUserMenu && (
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
            z-30
          "
        >
            <div className="flex flex-col cursor-pointer">
                {currentUser ? (
                    <>
                        {currentUser.role === "artist" && (
                            <>
                                <MenuItem label="My artworks" onClick={()=>{navigate(`/artworks/${currentUser.id}`)}}/>
                                <MenuItem label="Add new artwork" onClick={()=>{navigate('/new-artwork')}}/>
                            </>
                        )}

                        {currentUser.role === "curator" && (
                            <>
                                <MenuItem label="My exhibitions" onClick={()=>{navigate(`/exhibitions/${currentUser.id}`)}}/>
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
                            onClick={toggleOpenLogin}
                        />
                        <MenuItem
                            label="Sign up"
                            onClick={toggleOpenRegister}
                        />
                    </>
                )}
            </div>
        </div>
    )}
            <LoginModal isOpen={isOpenLogin} toggle={toggleOpenLogin}/>
            <RegisterModal isOpen={isOpenRegister} toggle={toggleOpenRegister}/>
    </>
    )
};

export default UserMenu;
