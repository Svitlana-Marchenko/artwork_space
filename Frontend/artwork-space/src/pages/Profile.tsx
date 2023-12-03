import React, {useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import {NavLink} from "react-router-dom";
import UserService from "../API/UserService";

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

    //todo add toast error
    //todo find normal avatar photo
    //todo add edit/delete button if it is user profile

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

    return (
        <div>
            {profile ? (
                <>
                    <div className="bg-gray-200 font-sans h-screen w-full flex flex-row justify-center items-center">
                        <div className="card w-96 mx-auto bg-white shadow-xl hover:shadow">
                            <img
                                className="w-32 mx-auto rounded-full -mt-20 border-8 border-white"
                                src="/avatar/avatar1.jpg"
                                alt=""
                            />
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

                                    <div className="flex p-4">
                                        <div className="w-1/2 text-center">

                                    <span
                                        onClick={()=>{}}
                                        className="
              text-neutral-800
              cursor-pointer
              hover:underline
            "
                                    > Edit account</span>

                                        </div>
                                        <div className="w-0 border border-gray-300"></div>
                                        <div className="w-1/2 text-center">
                                            <span
                                                onClick={()=>{}}
                                                className="
              text-neutral-800
              cursor-pointer
              hover:underline
            "
                                            > Delete account</span>
                                        </div>
                                    </div>
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
