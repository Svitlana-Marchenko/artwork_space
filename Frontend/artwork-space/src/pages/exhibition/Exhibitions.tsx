import React, {useEffect, useState} from 'react';
import {exhibitions} from "../../mockup/mockup_exhibitions";
import ExhibitionList from "../../components/lists/ExhibitionList";
import {useParams} from "react-router-dom";
import {User} from "../../mockup/mockup_users";
import {getUserByIdAsync} from "../../actions/getUserById";
import {convertToInt} from "../../actions/functions";

const Exhibitions = () => {
    const { id } = useParams();
    const [curator, setCurator] = useState<User>();
    const currentUser = {
        role: "artist",
        id: 1,
        // role: "curator",
        // role: "collectioneer",
    }
    useEffect(() => {
        if (id) {
            const fetchData = async () => {
                const user = await getUserByIdAsync(id);
                setCurator(user);
            };
            fetchData();
        }
    }, [id]);
    return (
        <div className="mx-32">
            {
                id
                    ?
                    curator && (
                        <>
                            <p className={'text-3xl font-bold mt-8'}>
                                {currentUser.id === convertToInt(id)
                                    ?
                                    "MY EXHIBITIONS"
                                    :
                                    `EXHIBITIONS BY ${curator.firstName.toUpperCase()} ${curator.lastName.toUpperCase()}`}
                            </p>
                        </>
                    )
                    :
                    null
            }
            {exhibitions.map((exhibition) => {
                return (
                    <ExhibitionList
                        id={exhibition.id.toString()}
                        curator={exhibition.curator}
                        title={exhibition.title}
                        artworks={exhibition.artworks}
                        startDate={exhibition.startDate}
                        endDate={exhibition.endDate}
                    />
                )
            })}
        </div>
    );
};

export default Exhibitions;
