import React, {useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import {Exhibition as ExhibitionType} from "../../mockup/mockup_exhibitions";
import ArtworksList from "../../components/lists/ArtworksList";
import {Button} from "../../components/Button";
import ExhibitionService from "../../API/ExhibitionService";
import toast from "react-hot-toast";

const Exhibition = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    //const {curator, title, description, artworks, startDate, endDate} = exhibitions[convertToInt(id)];

    const [exhibition, setExhibition] = useState<ExhibitionType>();

    const currentUser = {
        // role: "artist",
         role: "curator",
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

    //todo change to custom window.confirm
    //todo add link to api folder
    function handleDelete() {
        if (window.confirm('Are you sure you want to delete this exhibition?')) {
            fetch(`http://localhost:8080/exhibitions/${id}`, {
                method: 'DELETE',
            })
                .then((response) => {
                    if (response.ok) {
                        navigate(-1)
                        toast.success('Exhibition was deleted successfully');
                    } else {
                        toast.error('Failed to delete exhibition.');
                    }
                })
                .catch((error) => {
                    console.error('Error while deleting exhibition:', error);
                });
        }
    }

    //todo normal page for error
    if (!exhibition) {
        return <div>Loading...</div>;
    }

    //todo відступ між кнопками Оля

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
                        <>
                        <div className={"w-1/4"}>
                            <>
                                <Button label={"Edit"} onClick={()=>{}}/>
                                <Button label={"Delete"} onClick={handleDelete} outline/>
                            </>
                        </div>

                        </>
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
