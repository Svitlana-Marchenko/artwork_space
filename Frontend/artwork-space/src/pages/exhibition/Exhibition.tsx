import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import {Exhibition as ExhibitionType} from "../../mockup/mockup_exhibitions";
import ArtworksList from "../../components/lists/ArtworksList";
import {Button} from "../../components/Button";
import ExhibitionService from "../../API/ExhibitionService";

const Exhibition = () => {
    const { id } = useParams();
    //const {curator, title, description, artworks, startDate, endDate} = exhibitions[convertToInt(id)];

    const [exhibition, setExhibition] = useState<ExhibitionType>();

    const currentUser = {
         role: "artist",
        // role: "curator",
        // role: "collectioneer",
    }

    useEffect(() => {
        if (id) {
            ExhibitionService.getExhibitionById(id)
                .then((data) => setExhibition(data))
                .catch((error) =>
                    console.error('Error fetching auction by ID:', error)
                );
        }
    }, [id]);

    //todo normal page for error
    if (!exhibition) {
        return <div>Loading...</div>;
    }


    return (
        <div className="mx-32">
            <p className={"mb-2"}>Collected by {exhibition.curator.firstName} {exhibition.curator.lastName}</p>
            <div className={"flex flex-row justify-between align-top"}>
                <article>
                    <h2 className={"text-3xl font-bold"}>{exhibition.title.toUpperCase()}</h2>
                    <p className={"text-3xl font-bold text-gray-400"}>{new Date(exhibition.startDate).toDateString()} - {new Date(exhibition.endDate).toDateString()}</p>
                </article>
                {
                    currentUser.role === "curator"
                    ?
                        <div className={"w-1/4"}>
                            <Button label={"Edit"} onClick={()=>{}}/>
                        </div>
                        :
                        null
                }
            </div>
            <p className={"mt-6 mb-12"}>{exhibition.description}</p>
            <ArtworksList artworks={exhibition.artworks}/>
        </div>
    );
};
export default Exhibition;
