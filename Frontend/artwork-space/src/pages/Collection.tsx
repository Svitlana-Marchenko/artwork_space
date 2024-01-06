import React, {useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import toast from "react-hot-toast";
import { User } from '../types/usersTypes';
import CollectionService from "../API/CollectionService";
import {Collection as CollectionType} from "../types/collectionTypes";
import Empty from "../empty";
import {Button} from "../components/Button";
import ArtworksList from "../components/lists/ArtworksList";



const Collection = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User = storedUserString ? JSON.parse(storedUserString) : null;
    const [collection, setCollection] = useState<CollectionType>();
    useEffect(() => {
        if (id) {
            CollectionService.getCollectionById(id)
                .then((data) => setCollection(data))
                .catch((error) =>
                    console.error('Error fetching collection by ID:', error)
                );
        }
    }, [id]);

    function handleDelete() {
        if (id) {
            CollectionService.deleteCollectionById(id)
                .then(() => {
                    navigate(-1)
                    toast.success('Collection was deleted');
                })
                .catch((error) => {
                    toast.error('Failed to delete collection.');
                    console.log(error)
                });
        }
    }

    if (!collection) {
        return <Empty message={"Theres no such collection"} link={`/artworks`} dist={'all the artworks'}/>;
    }

    return (
        <div className="mx-32">
            <p className={"mb-2"}>Collected by
                <span
                    className={'underline underline-offset-2 cursor-pointer ml-1'}
                    onClick={()=>{navigate(`/profile/${collection.owner.id}`)}}
                >
                   {collection.owner.firstName} {collection.owner.lastName}
                </span>
            </p>
            <div className={"flex flex-row justify-between align-top"}>
                <article>
                    <h2 className={"text-3xl font-bold"}>{collection.title.toUpperCase()}</h2>
                </article>
                <div className={"flex flex-row space-x-4 w-1/4 h-1/2"}>
                    <>
                        <Button label={"Delete"} onClick={handleDelete} outline/>
                    </>
                </div>
            </div>
            <ArtworksList artworks={collection.artworks} collections={[collection]} chosenCollection={collection}/>
        </div>
    );
};
export default Collection;
