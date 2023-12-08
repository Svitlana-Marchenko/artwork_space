import React, {useCallback, useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import toast from "react-hot-toast";
import {ChangePasswordModal} from "../components/modals/ChangePasswordModal";
import {EditProfileInfoModal} from "../components/modals/EditProfileInfoModal";
import {User} from "../mockup/mockup_users";
import {Artwork} from "../mockup/mockup_artworks";
import Collection from "../components/Collection";
import {Button} from "../components/Button";
import CollectionService from "../API/CollectionService";
import UserService from "../API/UserService";
import ArtworksList from "../components/lists/ArtworksList";
import ExhibitionList from "../components/lists/ExhibitionList";
import ArtworkService from "../API/ArtworkService";
import {Exhibition} from "../mockup/mockup_exhibitions";
import ExhibitionService from "../API/ExhibitionService";

const Profile = () => {
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User= storedUserString ? JSON.parse(storedUserString) : null;
    const navigate = useNavigate();
    const {id} = useParams();
    const [likedArtworks, setLikedArtworks] = useState<Artwork[]>([]);
    const [artworks, setArtworks] = useState<Artwork[]>([]);
    const [exhibitions, setExhibitions] = useState<Exhibition[]>([]);
    const [profile, setProfile] = useState<User>();

    const [isOpenPassword, setIsOpenPassword] = useState(false);
    const [isOpenEditProfile, setIsOpenEditProfile] = useState(false);

    const toggleOpenPassword = useCallback(() => {
        setIsOpenPassword((value) => !value);
    }, []);

    const toggleOpenEditProfile = useCallback(() => {
        setIsOpenEditProfile((value) => !value);
    }, []);

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
        if (id) {
            UserService.getUserById(id)
                .then((data) => {
                    setProfile(data);
                })
            ArtworkService.getAllArtworksByArtistId(id)
                .then(data => {
                    console.log(data)
                    setArtworks(data);
                })
                .catch(error => console.error('Помилка при отриманні даних про список картин:', error));
        }
        if (currentUser&&(!id)) {
            CollectionService.getArtworksFromCollection(currentUser.id)
                .then(data => {
                    console.log(data)
                    setLikedArtworks(data);
                })
                .catch(error => console.error('Помилка при отриманні даних про список картин:', error));

        }
    }, []);
    useEffect(()=>{
        if (id) {
            ExhibitionService.getExhibitionByCuratorId(id)
                .then((data) => {
                    setExhibitions(data);
                })
        }
    }, [profile])
    const profileContent = (user: User) => {
        return (
            <>
                <div className={"flex flex-row justify-between align-top"}>
                    <div>
                        <p className={"text-gray-400"}>@{user.username}</p>
                        <p className={"text-3xl font-bold my-2"}>{user.firstName.toUpperCase()} {user.lastName.toUpperCase()}</p>
                    </div>
                    {
                        currentUser&&(!id)&&(
                            <div className={"flex flex-row space-x-4 w-1/4 h-1/2"}>
                                <Button label={"Edit profile"} onClick={toggleOpenEditProfile}/>
                                <Button label={"Delete"} onClick={handleDelete} outline/>
                            </div>
                        )
                    }
                </div>
                {
                    currentUser&&(!id)&&(
                        <span
                            className="font-bold hover:border-b-2 hover:border-black cursor-pointer"
                            onClick={toggleOpenPassword}
                        >Change password</span>
                    )
                }
                <hr className={'my-8'}/>
                {
                    currentUser&&(!id)&&(
                        currentUser.role === "CURATOR" || "COLLECTIONEER"
                            ?
                            <Collection artworks={likedArtworks}/>
                            :
                            null
                    )
                }
                {profile && (
                    <>
                        {profile.role === "ARTIST" && (
                            <ArtworksList artworks={artworks}/>
                        )}

                        {profile.role === "CURATOR" && (
                            <>
                            {exhibitions.length > 0 && exhibitions.map((exhibition) => {
                                    return (
                                        <ExhibitionList
                                            key={exhibition.id.toString()}
                                            id={exhibition.id.toString()}
                                            curator={exhibition.curator}
                                            title={exhibition.title}
                                            artworks={exhibition.artworks}
                                            startDate={exhibition.startDate}
                                            endDate={exhibition.endDate}
                                        />
                                    )
                                })}
                            </>
                        )}
                    </>
                )}
            </>
        )}
    return (
        <div className="mx-32 mt-16">
            {
                id
                    ?
                    profile && profileContent(profile)
                    :
                    currentUser && profileContent(currentUser)
            }
            <ChangePasswordModal isOpen={isOpenPassword} toggle={toggleOpenPassword}/>
            <EditProfileInfoModal isOpen={isOpenEditProfile} toggle={toggleOpenEditProfile}/>
        </div>
    );

};

export default Profile;
