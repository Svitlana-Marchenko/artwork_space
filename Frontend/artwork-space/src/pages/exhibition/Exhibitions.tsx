import React, {useEffect, useState} from 'react';
import {Exhibition} from "../../types/exhibitionsTypes";
import ExhibitionList from "../../components/lists/ExhibitionList";
import {useParams} from "react-router-dom";
import {User} from "../../types/usersTypes";

import ExhibitionService from "../../API/ExhibitionService";
import toast from "react-hot-toast/headless";
import Empty from "../../empty";
import CollectionService from "../../API/CollectionService";
import {Collection} from "../../types/collectionTypes";

const Exhibitions = () => {
    const { id } = useParams();
    const [exhibitions, setExhibitions] = useState<Exhibition[]>([])
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User | null = storedUserString ? JSON.parse(storedUserString) : null;
    const [allCollections, setAllCollections] = useState<Collection[]>([]);

    useEffect(() => {
        if (id !== undefined) {
            ExhibitionService.getExhibitionByCuratorId(id)
                .then(data => {setExhibitions(data);
                })
                .catch(error => console.error('Помилка при отриманні даних про список виставок:', error));
            if(currentUser && (currentUser.role === "CURATOR" || currentUser.role === "COLLECTIONEER")) {
                CollectionService.getAllCollectionsByUser(currentUser.id)
                    .then(data => {setAllCollections(data);
                    })
                    .catch(error => console.error('Error getting collections:', error));
            }
        } else {
            ExhibitionService.getAllActiveExhibitions()
                .then(data => {
                    setExhibitions(data);
                })
                .catch(error => {toast.error("Shiiit")});
            if(currentUser){
                CollectionService.getAllCollectionsByUser(currentUser.id)
                    .then(data => {setAllCollections(data);
                    })
                    .catch(error => console.error('Error getting collections:', error));
            }
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
                        collections={allCollections}
                    />
                )
            })}
        </div>
    );
};

export default Exhibitions;