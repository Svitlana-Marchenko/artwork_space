import React, {useCallback, useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import {NavLink} from "react-router-dom";
import toast from "react-hot-toast";
import {ChangePasswordModal} from "../components/modals/ChangePasswordModal";
import {EditProfileInfoModal} from "../components/modals/EditProfileInfoModal";
import {User} from "../mockup/mockup_users";
import ArtworkService from "../API/ArtworkService";
import {Artwork} from "../mockup/mockup_artworks";
import Collection from "../components/Collection";
import {Button} from "../components/Button";
import useFavorite from "../hooks/useFavorite";
import CollectionService from "../API/CollectionService";
import UserService from "../API/UserService";

const Profile = () => {
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User= storedUserString ? JSON.parse(storedUserString) : null;
    // const { hasFavorite, toggleFavorite } = useFavorite({
    //     artworkId,
    //     currentUser
    // });
    const navigate = useNavigate();
    const [artworks, setArtworks] = useState<Artwork[]>([]);

    const [isOpenPassword, setIsOpenPassword] = useState(false);
    const [isOpenEditProfile, setIsOpenEditProfile] = useState(false);

    const toggleOpenPassword = useCallback(() => {
        setIsOpenPassword((value) => !value);
    }, []);

    const toggleOpenEditProfile = useCallback(() => {
        setIsOpenEditProfile((value) => !value);
    }, []);

    //todo add edit button if it is user currentUser
    function handleDelete() {
        if (currentUser) {
            UserService.deleteUserById(currentUser.id)
                .then(() => {
                    localStorage.removeItem("currentUser");
                    navigate("/")
                    toast.success('Profile deleted successfully');
                })
                .catch(() => {
                    toast.error('Failed to delete your profile');
                });
        }
    }

    useEffect(() => {
        if (currentUser) {
            CollectionService.getArtworksFromCollection(currentUser.id)
                .then(data => {
                    setArtworks(data);
                })
                .catch(error => console.error('Помилка при отриманні даних про список картин:', error));

        }
    }, []);

    return (
        <div className="mx-32 mt-16">
            {currentUser ? (
                <>
                    <div className={"flex flex-row justify-between align-top"}>
                        <div>
                            <p className={"text-gray-400"}>@{currentUser.username}</p>
                            <p className={"text-3xl font-bold mt-2 mb-4"}>{currentUser.firstName.toUpperCase()} {currentUser.lastName.toUpperCase()}</p>
                        </div>
                        <div className={"flex flex-row space-x-4 w-1/4 h-1/2"}>
                            <Button label={"Edit profile"} onClick={()=>{}}/>
                            <Button label={"Delete"} onClick={handleDelete} outline/>
                        </div>
                    </div>
                    <hr className={'mb-8'}/>
                    <Collection artworks={artworks}/>
                </>
            ) : (
                <p>Smth went wrong</p>
            )}
            <ChangePasswordModal isOpen={isOpenPassword} toggle={toggleOpenPassword}/>
            <EditProfileInfoModal isOpen={isOpenEditProfile} toggle={toggleOpenEditProfile}/>
        </div>
    );

};

export default Profile;
