import {Artwork} from "../../mockup/mockup_artworks";
import {FieldValues, SubmitHandler, useForm} from "react-hook-form";
import Input from "../../components/input/Input";
import {Button} from "../../components/Button";
import {User, users} from "../../mockup/mockup_users";
import React, {useState} from "react";

export const NewArtwork = () => {
    const currentUser: User = users[0];
    const [imageFile, setImageFile] = useState<File | null>(null);

    const handleAddArtwork = async (data: any) => {
        // const artwork: Artwork = {
        //     id: 0,
        //     title: data.title,
        //     technique: data.technique,
        //     imageURL: '',
        //     width: Number(data.width),
        //     height: Number(data.height),
        //     artist: [currentUser],
        // };
        // console.log(artwork)
        // const formData = new FormData();
        // formData.append('title', data.title);
        // formData.append('technique', data.technique);
        // formData.append('width', String(artwork.width));
        // formData.append('height', String(artwork.height));
        // formData.append('image', imageFile!);
        // formData.append('artist', JSON.stringify(currentUser));
        // try {
        //     const response = await fetch('your-backend-endpoint', {
        //         method: 'POST',
        //         body: formData,
        //     });
        //
        //     if (response.ok) {
        //         const imageData = await response.json();
        //         artwork.imageURL = imageData.url;
        //         console.log('Artwork added successfully:', artwork);
        //     } else {
        //         console.error('Error adding artwork:', response.statusText);
        //     }
        // } catch (error) {
        //     console.error('Error adding artwork:', error.message);
        // }
    };
    const {
        register,
        handleSubmit,
        setValue,
        formState: { errors },
    } = useForm<FieldValues>()

    const onSubmit: SubmitHandler<any> = (data) => {
        handleAddArtwork(data);
        console.log(data)
    };

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files && e.target.files[0];
        setImageFile(file);
    };

    return (
        <div className='mx-32'>
            <form onSubmit={handleSubmit(onSubmit)}>
                <Input
                    id={'title'}
                    label={'Title'}
                    setValue={setValue}
                    placeholder={'Title'}
                    register={register}
                    errors={errors}
                    required
                />
                <Input
                    id={'technique'}
                    label={'Technique'}
                    setValue={setValue}
                    placeholder={'Technique'}
                    register={register}
                    errors={errors}
                    required
                />
                <Input
                    id={'width'}
                    label={'Width'}
                    placeholder={'Width'}
                    type={'number'}
                    setValue={setValue}
                    register={register}
                    errors={errors}
                    required
                />
                <Input
                    id={'height'}
                    label={'Height'}
                    placeholder={'Height'}
                    setValue={setValue}
                    type={'number'}
                    register={register}
                    errors={errors}
                    required
                />
                <Input
                    id={'artwork'}
                    label={'Artwork'}
                    placeholder={'Artwork'}
                    setValue={setValue}
                    type={'file'}
                    register={register}
                    errors={errors}
                    required/>
                <Button label="Add Artwork" onClick={handleSubmit(onSubmit)} />
            </form>
        </div>
    )
}
