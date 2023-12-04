import React, {useCallback, useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import {NavLink} from "react-router-dom";
import UserService from "../API/UserService";
import toast from "react-hot-toast";
import {ChangePasswordModal} from "../components/modals/ChangePasswordModal";
import {EditProfileInfoModal} from "../components/modals/EditProfileInfoModal";

interface UserProfile {
    id: number;
    username: string;
    firstName?: string;
    lastName?: string;
    email?: string;
    role?: string;
}

const Profile = () => {
    const {id} = useParams();
    const [profile, setProfile] = useState<UserProfile | null>(null);
    const navigate = useNavigate();

    const [isOpenPassword, setIsOpenPassword] = useState(false);
    const [isOpenEditProfile, setIsOpenEditProfile] = useState(false);

    const toggleOpenPassword = useCallback(() => {
        setIsOpenPassword((value) => !value);
    }, []);

    const toggleOpenEditProfile = useCallback(() => {
        setIsOpenEditProfile((value) => !value);
    }, []);

    //todo add toast error
    //todo add edit button if it is user profile

    useEffect(() => {
        if (id) {
            UserService.getUserById(id)
                .then((data) => setProfile(data)
                )
                .catch((error) => {
                    console.error('Error fetching user profile:', error);
                    navigate(-1)
                });
        }
    }, [id]);
//todo normal link from api
    //todo custom window confirm
    function handleDelete() {
        if (window.confirm('Are you sure you want to delete your profile?')) {
            fetch(`http://localhost:8080/users/${id}`, {
                method: 'DELETE',
            })
                .then((response) => {
                    if (response.ok) {
                        //todo add logout
                        navigate("/")
                        toast.success('Profile deleted successfully');
                    } else {
                        toast.error('Failed to delete profile');
                    }
                })
                .catch((error) => {
                    console.error('Error while deleting profile:', error);
                });
        }
    }

    return (
        <div>
            {profile ? (
                <>
                    <div className="bg-gray-200 font-sans h-screen w-full flex flex-row justify-center items-center">
                        <div className="card w-96 mx-auto bg-white shadow-xl hover:shadow">
                            {profile.role === 'ARTIST' && ( <img
                                className="w-40 mx-auto rounded-full -mt-20 border-8 border-white"
                                src="/avatar/av2.png"
                                alt=""
                            />)}
                            {profile.role === 'COLLECTIONEER' && ( <img
                                className="w-40 mx-auto rounded-full -mt-20 border-8 border-white"
                                src="/avatar/av3.png"
                                alt=""
                            />)}
                            {profile.role === 'CURATOR' && ( <img
                                className="w-40 mx-auto rounded-full -mt-20 border-8 border-white"
                                src="/avatar/av1.png"
                                alt=""
                            />)}

                            <div
                                className="text-center mt-2 text-3xl font-medium">{profile.firstName} {profile.lastName}</div>
                            <div className="text-center mt-2 font-normal text-lg">@{profile.username}</div>
                            <div className="text-center font-normal text-sm">{profile.email}</div>
                            <div className="px-6 text-center mt-2 font-light text-sm">

                                {profile.role === 'ARTIST' && (
                                    <NavLink to={`/artworks/${profile.id}`}
                                             className="underline underline-offset-1 text-sm mb-2">
                                        View Artworks
                                    </NavLink>
                                )}

                                {profile.role === 'CURATOR' && (
                                    <NavLink to={`/exhibitions/${profile.id}`}
                                             className="underline underline-offset-1 text-sm">
                                        View Exhibitions
                                    </NavLink>
                                )}

                            </div>
                            <hr className="mt-8"/>
                            {profile.id === 1 && (
                                <>

                                    <div className="flex items-center justify-center h-full">
                                        <div className="flex flex-col p-4">
                                            <div className="w-full text-center">
      <span
          onClick={toggleOpenEditProfile}
          className="text-neutral-800 cursor-pointer hover:underline"
      >
        Edit account
      </span>
                                            </div>


                                            <div className="w-full text-center">
      <span
          onClick={toggleOpenPassword}
          className="text-neutral-800 cursor-pointer hover:underline"
      >
        Change password
      </span>
                                            </div>

                                            <div className="w-full text-center">
      <span
          onClick={handleDelete}
          className="text-neutral-800 cursor-pointer hover:underline"
      >
        Delete account
      </span>
                                            </div>
                                        </div>
                                    </div>
                                    <ChangePasswordModal isOpen={isOpenPassword} toggle={toggleOpenPassword}/>
                                    <EditProfileInfoModal isOpen={isOpenEditProfile} toggle={toggleOpenEditProfile}/>
                                </>
                            )}

                        </div>
                    </div>
                </>
            ) : (
                <p>Loading...</p>
            )}
        </div>
    );

};

//todo add normal loading page
export default Profile;
