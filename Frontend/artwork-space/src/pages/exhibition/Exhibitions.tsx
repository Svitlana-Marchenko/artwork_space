import React, {useEffect, useState} from 'react';
import {Exhibition} from "../../mockup/mockup_exhibitions";
import ExhibitionList from "../../components/lists/ExhibitionList";
import {useParams} from "react-router-dom";
import {User} from "../../mockup/mockup_users";

import ExhibitionService from "../../API/ExhibitionService";
import toast from "react-hot-toast/headless";
import Empty from "../../empty";

const Exhibitions = () => {
    const { id } = useParams();
    const [exhibitions, setExhibitions] = useState<Exhibition[]>([])
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User | null = storedUserString ? JSON.parse(storedUserString) : null;

    useEffect(() => {
        if (id !== undefined) {
            ExhibitionService.getExhibitionByCuratorId(id)
                .then(data => {setExhibitions(data);
                })
                .catch(error => console.error('Помилка при отриманні даних про список виставок:', error));

        } else {
            ExhibitionService.getAllActiveExhibitions()
                .then(data => {
                    setExhibitions(data);
                })
                .catch(error => {toast.error("Shiiit")});
        }
    }, []);

    return (
        <div className="mx-32">
            {
                id
                    ?
                    currentUser && (
                        <>
                            <h2 className={'text-3xl font-bold mt-8'}>
                                {currentUser.role === "CURATOR"
                                    ?
                                    "MY EXHIBITIONS"
                                    :
                                    `EXHIBITIONS BY ${currentUser.firstName.toUpperCase()} ${currentUser.lastName.toUpperCase()}`}
                            </h2>
                        </>
                    )
                    :
                    <p className={'text-3xl font-bold mt-8'}>ACTIVE EXHIBITIONS</p>
            }

            {exhibitions.length === 0 &&
                <Empty message={"Theres no exhibitions"} link={`/`} dist={'all the main page'}/>
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