import React, {useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import {Exhibition as ExhibitionType} from "../../types/exhibitionsTypes";
import ArtworksList from "../../components/lists/ArtworksList";
import {Button} from "../../components/Button";
import ExhibitionService from "../../API/ExhibitionService";
import toast from "react-hot-toast";
import {User} from "../../types/usersTypes";
import Empty from "../../empty";
import useMyExhibition from "../../hooks/useMyExhibitions";
import {Collection} from "../../types/collectionTypes";
import CollectionService from "../../API/CollectionService";

const Exhibition = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User = storedUserString ? JSON.parse(storedUserString) : null;
    const [exhibition, setExhibition] = useState<ExhibitionType>();
    const { isMyExhibition } = useMyExhibition({
        user: currentUser,
        exhibition
    });
    const [allCollections, setAllCollections] = useState<Collection[]>([]);


    useEffect(() => {
        if (id) {
            ExhibitionService.getExhibitionById(id)
                .then((data) => setExhibition(data))
                .catch((error) =>
                    console.error('Error fetching auction by ID:', error)
                );
            if(currentUser && (currentUser.role === "CURATOR" || currentUser.role === "COLLECTIONEER")) {
                CollectionService.getAllCollectionsByUser(currentUser.id)
                    .then(data => {setAllCollections(data);
                    })
                    .catch(error => console.error('Error getting collections:', error));
            }
        }
    }, [id]);

    function handleDelete() {
        if (id) {
            ExhibitionService.deleteExhibition(id)
                .then(() => {
                    navigate(-1)
                    toast.success('Exhibition was deleted');
                })
                .catch((error) => {
                    toast.error('Failed to delete exhibition.');
                    console.log(error)
                });
        }
    }

    if (!exhibition) {
        return <Empty message={"Theres no such exhibition"} link={`/exhibitions`} dist={'all the exhibitions'}/>;
    }

    return (
        <div className="mx-32">
            <p className={"mb-2"}>Collected by
                <span
                    className={'underline underline-offset-2 cursor-pointer ml-1'}
                    onClick={()=>{navigate(`/profile/${exhibition.curator.id}`)}}
                >
                   {exhibition.curator.firstName} {exhibition.curator.lastName}
                </span>
            </p>
            <div className={"flex flex-row justify-between align-top"}>
                <article>
                    <h2 className={"text-3xl font-bold"}>{exhibition.title.toUpperCase()}</h2>
                    <p className={"text-3xl font-bold text-gray-400"}>{new Date(exhibition.startDate).toDateString()} - {new Date(exhibition.endDate).toDateString()}</p>
                </article>
                {
                    currentUser && (
                        <>
                            {
                                currentUser.role === "CURATOR"
                                    ?
                                    isMyExhibition&&(<>
                                        <div className={"flex flex-row space-x-4 w-1/4 h-1/2"}>
                                            <>
                                                <Button label={"Edit"} onClick={()=>{navigate(`/edit-exhibition/${exhibition?.id}`)}}/>
                                                <Button label={"Delete"} onClick={handleDelete} outline/>
                                            </>
                                        </div>
                                    </>)
                                    :
                                    null
                            }
                        </>
                    )
                }
            </div>
            <p className={"mt-6 mb-12"}>{exhibition.description}</p>
            <ArtworksList artworks={exhibition.artworks} collections={allCollections}/>
        </div>
    );
};
export default Exhibition;
