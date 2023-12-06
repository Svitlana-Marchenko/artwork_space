import React, {useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import ExhibitionService from "../../API/ExhibitionService";
import toast from "react-hot-toast";
import ArtworksList from "../../components/lists/ArtworksList";
import ArtworkService from "../../API/ArtworkService";
import {Artwork} from "../../mockup/mockup_artworks";
import {FieldValues, SubmitHandler, useForm} from "react-hook-form";
import {NewUser} from "../../mockup/mockup_users";
import UserService from "../../API/UserService";
import Input from "../../components/input/Input";
import {Button} from "../../components/Button";

/*
export type Exhibition = {
    title: string;
    description: string;
    artworks: Artwork[];
    startDate: Date;
    endDate: Date;
}
 */
const ExhibitionForm = () => {
    const {id} = useParams();
    const navigate = useNavigate();
    const [artworks, setArtworks] = useState<Artwork[]>();

    useEffect(()=>{
        ArtworkService.getAllArtworks()
            .then((data) => {
                setArtworks(data);
            })
    }, [])
    function handleCreateExhibition() {
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

    const {
        register,
        handleSubmit,
        reset,
        formState: {
            errors,
        },
    } = useForm<FieldValues>({
        defaultValues: {
            username: '',
            firstName: '',
            lastName: '',
            email: '',
            role: '',
            password: ''
        },
    });

    const onSubmit: SubmitHandler<FieldValues> = (data) => {
        const userData: NewUser = {
            username: data.username,
            firstName: data.firstName,
            lastName: data.lastName,
            email: data.email,
            role: data.role.toUpperCase(),
            password: data.password
        };
        UserService.createUser(userData)
            .then((data) => {
                    toast.success("Successful registration")
                    reset();
                    navigate(-1)
                    const currentUser = JSON.stringify(data);
                    localStorage.setItem("currentUser", currentUser);
                    console.log(data)
                }
            )
            .catch((error) => {
                toast.error("Failed to create your profile")
                console.error('Error in creating your profile:', error);
            });
    }
    //todo dont let to create exhibition if theres no artworks to add
    if(!artworks) {
        return <div>
            No artworks
        </div>;
    }
    //todo input for description should be bigger
    //todo edit page, save values in input

    //todo like on artworks should add to exhibition to painting change like
    // or do smth else for it and change artwork list structure
    return (
        <div className='mx-32'>
            <div className='flex flex-col space-y-4 my-8'>
                <div className='flex flex-row justify-between'>
                    <div className='w-1/2'>
                    <Input
                        id="title"
                        label="Title"
                        placeholder="Title"
                        register={register}
                        errors={errors}
                        required
                    />
                    </div>
                    <div className='w-1/4'>
                        <Button
                            label='Create exhibition'
                            onClick={handleSubmit(onSubmit)}
                        />
                    </div>
                </div>
                <div className='flex flex-row space-x-4 w-1/2'>
                    <Input
                        id="startDate"
                        label="Start date"
                        placeholder="Start date"
                        type='date'
                        register={register}
                        errors={errors}
                        required
                    />
                    <Input
                        id="endDate"
                        label="End date"
                        placeholder="End date"
                        type='date'
                        register={register}
                        errors={errors}
                        required
                    />
                </div>
                <Input
                    id="description"
                    label="Description"
                    placeholder="Description"
                    register={register}
                    errors={errors}
                    required
                />
            </div>
            <ArtworksList artworks={artworks}/>
        </div>
    );
};

export default ExhibitionForm;