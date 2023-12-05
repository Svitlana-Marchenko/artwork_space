import React, { useCallback, useState } from "react";
import {
    FieldValues,
    SubmitHandler,
    useForm
} from "react-hook-form";
import {Modal} from "./Modal";
import Input from "../input/Input";

interface EditProfileInfoModalProps {
    isOpen:boolean;
    toggle: () => void;
}

export const EditProfileInfoModal:React.FC<EditProfileInfoModalProps> = ({isOpen, toggle}) => {

    const [isLoading, setIsLoading] = useState(false);

    const {
        register,
        handleSubmit,
        formState: {
            errors,
        },
    } = useForm<FieldValues>({
        defaultValues: {
            name: '',
            email: '',
            password: ''
        },
    });

    // Define the form submission handler
    const onSubmit: SubmitHandler<FieldValues> = (data) => {

    }

    //todo edit email + server checking
    //todo implement functionality

    const bodyContent = (
        <div className="flex flex-col gap-4">
            <div className={'text-center'}>
                <div className="font-light text-neutral-500 mt-2">
                    Use these fields to edit profile!
                </div>
            </div>

            <Input
                id="username"
                label="Username"
                placeholder="Username"
                register={register}
                errors={errors}
                required
                
            />

            <Input
                id="firstname"
                label="First name"
                placeholder="First name"
                register={register}
                errors={errors}
                required
                
            />

            <Input
                id="lastname"
                label="Last name"
                placeholder="Last name"
                register={register}
                errors={errors}
                required
                
            />

        </div>
    )

    return(
        <Modal
            isOpen={isOpen}
            title="Edit profile"
            actionLabel="Edit"
            onSubmit={handleSubmit(onSubmit)}
            body={bodyContent}
            toggleModal={toggle}
        />
    )

}