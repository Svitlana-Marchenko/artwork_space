import React, {useEffect, useState} from 'react';
import {Artwork, NewArtwork} from "../../types/artworkTypes";
import {FieldValues, SubmitHandler, useForm} from "react-hook-form";
import {User} from "../../types/usersTypes";
import {useNavigate, useParams} from "react-router-dom";
import ArtworkService from "../../API/ArtworkService";
import toast from "react-hot-toast";
import Input from "../../components/input/Input";
import {
    dateValidation, endDateValidation, fileSizeValidation, imageFileValidation,
    lettersOnlyValidation,
    maxLengthValidation, minValueValidation,
    requiredValidation
} from "../../utils/validationUtils";
import {Button} from "../../components/Button";
import ArtworksList from "../../components/lists/ArtworksList";
import ExhibitionService from "../../API/ExhibitionService";

const ArtworkForm = () => {
    const {id} = useParams();
    const navigate = useNavigate();
    const [artwork, setArtwork] = useState<Artwork>();
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User | null = storedUserString ? JSON.parse(storedUserString) : null;
    const {
        register,
        handleSubmit,
        reset,
        setValue,
        formState: {
            errors,
        },
    } = useForm<FieldValues>({
        defaultValues: {
            title: '',
            technique: '',
            description:'',
            width: 0,
            height: 0,
            file: File,
            user: {
                id:0,
                username: '',
                firstName:  '',
                lastName: '',
                email:  '',
                role:  '',
                password:  '',
            }
        },
    });

    useEffect(()=>{
        if(id) {
            ArtworkService.getArtworkById(id)
                .then((data)=>{
                    setArtwork(data);
                })
                .catch(() => {
                    toast.error('Failed to edit artwork.');
                    navigate(-1)
                })
        }
    }, []);
    useEffect(()=>{
        if (artwork) {
            setValue('title', artwork.title);
            setValue('description', artwork.description);
            setValue('technique', artwork.technique);
            setValue('height', artwork.height);
            setValue('width', artwork.width);
        }
    }, [artwork]);
    const onSubmit: SubmitHandler<FieldValues> = (data) => {
        if (currentUser) {
            const artworkData: NewArtwork = {
                title: data.title,
                technique: data.technique,
                description: data.description,
                width: data.width,
                height: data.height,
                user: currentUser,
            };
            if (artwork&&id){
                ArtworkService.editArtworkById(id, artworkData)
                    .then((data) => {
                            toast.success("Artwork was edited")
                            reset();
                            navigate(-1)
                            console.log(data)
                        }
                    )
                    .catch((error) => {
                        toast.error("Failed to edit artwork")
                        console.error('Error in editing artwork:', error);
                    });
            }else{
                ArtworkService.addArtwork(artworkData, data.file[0])
                    .then((data) => {
                            toast.success("Artwork was created")
                            reset();
                            navigate(-1)
                            console.log(data)
                        }
                    )
                    .catch((error) => {
                        toast.error("Failed to create artwork")
                        console.error('Error in creating artwork:', error);
                    });
            }
        }
    }

    return (
        <div className='mx-32'>
            <div className='flex flex-row my-8'>
                {
                    artwork&&(
                        <img src={artwork.imageURL} alt={artwork.title} className="w-1/2 max-w-3xl h-[600px] object-cover"/>
                    )
                }
                <div className={'ml-4 w-1/2 space-y-4'}>
                <div className='flex flex-row justify-between'>
                </div>
                    <div className='w-1/2 flex flex-col space-y-4'>
                        <Input
                            id="title"
                            label="Title"
                            placeholder="Title"
                            register={register}
                            errors={errors}
                            validationOptions={{
                                ...lettersOnlyValidation,
                                ...requiredValidation,
                                ...maxLengthValidation(50),
                            }}
                        />
                        <Input
                            id="technique"
                            label="Technique"
                            placeholder="Technique"
                            register={register}
                            errors={errors}
                            validationOptions={{
                                ...lettersOnlyValidation,
                                ...requiredValidation,
                                ...maxLengthValidation(100),
                            }}
                        />
                    </div>
                <div className='flex flex-row space-x-4 w-1/2'>
                    <Input
                        id="height"
                        label="Height"
                        placeholder="Height"
                        type='number'
                        register={register}
                        errors={errors}
                        validationOptions={{
                            ...requiredValidation,
                            ...minValueValidation(1),
                        }}
                    />
                    <Input
                        id="width"
                        label="Width"
                        placeholder="Width"
                        type='number'
                        register={register}
                        errors={errors}
                        validationOptions={{
                            ...requiredValidation,
                            ...minValueValidation(1),
                        }}
                    />
                </div>
                    {
                        !id && (
                            <Input
                                id={'file'}
                                label={'File'}
                                placeholder={'File'}
                                type={'file'}
                                register={register}
                                errors={errors}
                                validationOptions={{
                                    ...requiredValidation,
                                    ...imageFileValidation,
                                    ...fileSizeValidation(10)
                                }}/>
                        )
                    }
                <Input
                    id="description"
                    label="Description"
                    placeholder="Description"
                    register={register}
                    errors={errors}
                    validationOptions={{
                        ...requiredValidation,
                        ...maxLengthValidation(2000),}}
                    isTextArea
                    required
                />
                    <Button
                        label={id ? 'Save artwork' :'Create artwork'}
                        onClick={handleSubmit(onSubmit)}
                    />
            </div>
            </div>
        </div>
    );
};

export default ArtworkForm;