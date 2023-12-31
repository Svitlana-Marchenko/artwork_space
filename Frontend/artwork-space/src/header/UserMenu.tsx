import React, {useCallback, useState} from 'react';
import {AiOutlineMenu} from "react-icons/ai";
import MenuItem from "./MenuItem";
import {useNavigate} from "react-router-dom";
import {User} from "../types/usersTypes"
import {LoginModal} from "../components/modals/LoginModal";
import {RegisterModal} from "../components/modals/RegisterModal";

interface UserMenuProps {
    currentUser?: User | null
}
const UserMenu:React.FC<UserMenuProps> = ({}) => {
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User | null = storedUserString ? JSON.parse(storedUserString) : null;
    const [isOpenUserMenu, setIsOpenUserMenu] = useState(false);
    const [isOpenLogin, setIsOpenLogin] = useState(false);
    const [isOpenRegister, setIsOpenRegister] = useState(false);
    const [isOpenNewArtwork, setOpenNewArtwork] = useState(false);
    const navigate = useNavigate();

    const toggleOpen = useCallback(() => {
        setIsOpenUserMenu((value) => !value);
    }, []);
    const toggleOpenLogin = useCallback(() => {
        setIsOpenUserMenu(false);
        setIsOpenLogin((value) => !value);
    }, []);
    const toggleOpenRegister = useCallback(() => {
        setIsOpenRegister((value) => !value);
    }, []);
    const toggleNewArtwork = useCallback(() => {
        setIsOpenRegister((value) => !value);
    }, []);
    const logout = () => {
        localStorage.removeItem("currentUser");
        navigate("/");
        setIsOpenUserMenu(false);
    }

    const nav = (path: string) => {
        navigate(path);
        setIsOpenUserMenu(false);
    }
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
                        {currentUser.role === "ARTIST" && (
                            <>
                                <MenuItem label="My artworks" onClick={()=>{nav(`/artworks/${currentUser.id}`)}}/>
                                <MenuItem label="Add new artwork" onClick={()=>{nav('/new-artwork')}}/>
                            </>
                        )}

                        {currentUser.role === "CURATOR" && (
                            <>
                                <MenuItem label="My exhibitions" onClick={()=>{nav(`/exhibitions/${currentUser.id}`)}}/>
                                <MenuItem label="Create exhibition" onClick={()=>{nav('/new-exhibition')}}/>
                            </>
                        )}
                        <MenuItem
                            label="My profile"
                            onClick={()=>{nav(`/my`)}}
                        />
                        <hr />
                        <MenuItem
                            label="Logout"
                            onClick={logout}
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
