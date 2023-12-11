import React, {useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import ExhibitionService from "../../API/ExhibitionService";
import toast from "react-hot-toast";
import ArtworksList from "../../components/lists/ArtworksList";
import ArtworkService from "../../API/ArtworkService";
import {Artwork} from "../../mockup/mockup_artworks";
import {FieldValues, SubmitHandler, useForm} from "react-hook-form";
import {User} from "../../mockup/mockup_users";
import Input from "../../components/input/Input";
import {Button} from "../../components/Button";
import {EditExhibition, Exhibition, NewExhibition} from "../../mockup/mockup_exhibitions";
import Empty from "../../empty";

const ExhibitionForm = () => {
    const {id} = useParams();
    const navigate = useNavigate();
    const [artworks, setArtworks] = useState<Artwork[]>();
    const [exhibition, setExhibition] = useState<Exhibition>();
    const [selectedArtworks, setSelectedArtworks] = useState<Artwork[]>([]);
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User | null = storedUserString ? JSON.parse(storedUserString) : null;
    const handleAddToExhibition = (artwork: Artwork) => {
        setSelectedArtworks((prevArtworks) => [...prevArtworks, artwork]);
    };

    useEffect(()=>{
        ArtworkService.getAllArtworks()
            .then((data) => {
                setArtworks(data);
            })
        if(id) {
            ExhibitionService.getExhibitionById(id)
                .then((data)=>{
                    setExhibition(data);
                })
                .catch(() => {
                    toast.error('Failed to edit exhibition.');
                    navigate(-1)
                })
        }
    }, [])
    useEffect(()=>{
        if (exhibition) {
            setValue('title', exhibition.title);
            setValue('description', exhibition.description);
            setValue('startDate', exhibition.startDate);
            setValue('endDate', exhibition.endDate);
            setSelectedArtworks(exhibition.artworks);
        }
    }, [exhibition]);
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
            description: '',
            startDate: new Date(),
            endDate: new Date()
        },
    });

    const onSubmit: SubmitHandler<FieldValues> = (data) => {
        if (currentUser && selectedArtworks) {

            const exhibitionData:NewExhibition = {
                curator: currentUser,
                title: data.title,
                description: data.description,
                artworks: selectedArtworks,
                startDate: new Date(data.startDate),
                endDate: new Date(data.endDate),
            };

            if (exhibition) {
                const editExhibitionData:EditExhibition = {
                    id: exhibition?.id,
                    title: data.title,
                    description: data.description,
                };
                ExhibitionService.editExhibition(editExhibitionData)
                    .then((data) => {
                            toast.success("Exhibition was edited")
                            reset();
                            navigate(-1)
                            console.log(data)
                        }
                    )
                    .catch((error) => {
                        toast.error("Failed to edit exhibition")
                        console.error('Error in editing exhibition:', error);
                    });
            }else {
                ExhibitionService.createExhibition(exhibitionData)
                    .then((data) => {
                            toast.success("Exhibition was created")
                            reset();
                            navigate(-1)
                            console.log(data)
                        }
                    )
                    .catch((error) => {
                        toast.error("Failed to create exhibition")
                        console.error('Error in creating exhibition:', error);
                    });
            }
        } else {
            toast.error("Exhibition has no artworks")
        }

    }

    if(!artworks) {
        return <Empty message={"Theres no artworks here"} link={`/exhibitions`} dist={'all exhibitions'}/>;
    }

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
                            label={id ? 'Save exhibition' :'Create exhibition'}
                            onClick={handleSubmit(onSubmit)}
                        />
                    </div>
                </div>
                {
                   !id && (
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
                    )
                }
                <Input
                    id="description"
                    label="Description"
                    placeholder="Description"
                    register={register}
                    errors={errors}
                    isTextArea
                    required
                />
            </div>
            {
                !exhibition&&(
                    <ArtworksList
                        artworks={artworks}
                        onAddToExhibition={handleAddToExhibition}
                    />
                )
            }
        </div>
    );
};

export default ExhibitionForm;