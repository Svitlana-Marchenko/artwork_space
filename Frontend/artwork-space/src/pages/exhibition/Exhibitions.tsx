import React, {useEffect, useState} from 'react';
import {Exhibition} from "../../mockup/mockup_exhibitions";
import ExhibitionList from "../../components/lists/ExhibitionList";
import {useParams} from "react-router-dom";
import {User} from "../../mockup/mockup_users";
import {getUserByIdAsync} from "../../actions/getUserById";
import {convertToInt} from "../../actions/functions";
import ArtworkService from "../../API/ArtworkService";

import ExhibitionService from "../../API/ExhibitionService";

const Exhibitions = () => {
    const { id } = useParams();
    const [curator, setCurator] = useState<User>();
    const [exhibitions, setExhibitions] = useState<Exhibition[]>([])
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


    useEffect(() => {
        if (id !== undefined) {
            ExhibitionService.getExhibitionByCuratorId(id)
                .then(data => {setExhibitions(data);
                })
                .catch(error => console.error('Помилка при отриманні даних про список виставок:', error));

        } else {
            ExhibitionService.getAllActiveExhibitions()
                .then(data => {setExhibitions(data);
                })
                .catch(error => console.error('Помилка при отриманні даних про список виставок:', error));
        }
    }, [id]);

    //todo normal empty page

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
            {exhibitions.length === 0 &&
            <div>No exhibition</div>
            }
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
        </div>
    );
};

export default Exhibitions;

//todo show Olya bug with link in exhibition carousel