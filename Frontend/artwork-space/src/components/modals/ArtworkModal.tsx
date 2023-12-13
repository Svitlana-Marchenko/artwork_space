import React from 'react';
import {Artwork} from "../../types/artworkTypes";
import {FieldValues, SubmitHandler, useForm} from "react-hook-form";
import {User} from "../../types/usersTypes";

interface ArtworkModalProps {
    isOpen:boolean;
    toggle: () => void;
    artwork?: Artwork
}
const ArtworkModal:React.FC<ArtworkModalProps> = ({isOpen, toggle, artwork}) => {
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User | null = storedUserString ? JSON.parse(storedUserString) : null;
    const {
        register,
        handleSubmit,
        reset,
        formState: {
            errors,
        },
    } = useForm<FieldValues>({
        defaultValues: {
            title: '',
            technique: '',
            width: 0,
            height: 0,
            imageURL: '',
            fileSize: 0,
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

    const onSubmit: SubmitHandler<FieldValues> = (data) => {
        // if (currentUser) {
        //     const artworkData: NewArtwork = {
        //         title: '',
        //         technique: '',
        //         width: 0,
        //         height: 0,
        //         imageURL: '',
        //         user: currentUser,
        //         ratings:[]
        //     };
        // }
        // UserService.createUser(userData)
        //     .then((data) => {
        //             toast.success("Successful registration")
        //             reset();
        //             toggle();
        //             const currentUser = JSON.stringify(data);
        //             localStorage.setItem("currentUser", currentUser);
        //             console.log(data)
        //         }
        //     )
        //     .catch((error) => {
        //         toast.error("Failed to create your profile")
        //         console.error('Error in creating your profile:', error);
        //     });
    }

    return (
        <div>

        </div>
    );
};

export default ArtworkModal;