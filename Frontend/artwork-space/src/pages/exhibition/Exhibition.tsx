import React from 'react';
import {useParams} from "react-router-dom";
import {exhibitions} from "../../mockup/mockup_exhibitions";
import {convertToInt} from "../../actions/functions";
import ArtworksList from "../../components/lists/ArtworksList";
import {Button} from "../../components/Button";

const Exhibition = () => {
    const { id } = useParams();
    const {curator, title, description, artworks, startDate, endDate} = exhibitions[convertToInt(id)];

    const currentUser = {
         role: "artist",
        // role: "curator",
        // role: "collectioneer",
    }

    return (
        <div className="mx-32">
            <p className={"mb-2"}>Collected by {curator.firstName} {curator.lastName}</p>
            <div className={"flex flex-row justify-between align-top"}>
                <article>
                    <h2 className={"text-3xl font-bold"}>{title.toUpperCase()}</h2>
                    <p className={"text-3xl font-bold text-gray-400"}>{startDate.toDateString().toUpperCase()} - {endDate.toDateString().toUpperCase()}</p>
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
            <p className={"mt-6 mb-12"}>{description}</p>
            <ArtworksList artworks={[]}/>
        </div>
    );
};
//todo add normal values into artworkList
export default Exhibition;
