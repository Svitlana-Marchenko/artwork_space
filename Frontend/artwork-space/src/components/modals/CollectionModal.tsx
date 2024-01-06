import React, { useState } from 'react';
import { useForm, SubmitHandler, FieldValues } from 'react-hook-form';
import { User } from '../../types/usersTypes';
import { Modal } from './Modal';
import {Collection, NewCollection} from '../../types/collectionTypes';
import CollectionService from '../../API/CollectionService';
import toast from 'react-hot-toast';
import Input from '../input/Input';
import { useNavigate } from 'react-router-dom';
import {maxLengthValidation, requiredValidation} from '../../utils/validationUtils';
import {NewAuction} from "../../types/auctionsTypes";

interface CollectionModalProps {
    isOpen: boolean;
    toggle: () => void;
}

const CollectionModal: React.FC<CollectionModalProps> = ({ isOpen, toggle}) => {
    const navigate = useNavigate();
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User= storedUserString ? JSON.parse(storedUserString) : null;

    const [newCollection, setNewCollection] = useState<NewCollection>();
    const {
        register,
        reset,
        handleSubmit,
        formState: {
            errors,
        },
    } = useForm<FieldValues>({
        defaultValues: {
            title: '',
        },
    });

    const onSubmit: SubmitHandler<FieldValues> = (data) => {
        console.log("Button pressed 2");
        setNewCollection({
            owner: currentUser,
           title: data.title,
            artworks:[]
        });

        if (newCollection) {
            CollectionService.createCollection(newCollection).then(data => {
                toast.success('Created collection');
                reset();
                // Toggle the modal to hide it
                console.log("Button pressed");
                toggle();
                // Optionally, navigate to /artworks after successfully creating a collection
                window.location.reload();
            })
                .catch((error) => {
                    toast.error("Failed to create collection")
                    console.error('Error in creating collection:', error);
                });
        }
    };

    const bodyContent = (
        <div className="flex flex-col gap-4">
            <div className={'text-center'}>
                <div className="font-light text-neutral-500 mt-2">Fill in the details below</div>
            </div>
            <Input
                id="title"
                label="Collection Title"
                placeholder="Collection title"
                type="text"
                register={register}
                errors={errors}
                style={{color: 'black' }}
                validationOptions={{
                    ...requiredValidation,
                    ...maxLengthValidation(50), // Example: Setting a max length of 50 characters
                }}
            />
        </div>
    );

    return (
        <Modal
            isOpen={isOpen}
            title="Create Collection"
            actionLabel="Create"
            style={{color: 'black' }}
            onSubmit={handleSubmit(onSubmit)}
            toggleModal={toggle}
            body={bodyContent}
        />
    );
};

export default CollectionModal;
