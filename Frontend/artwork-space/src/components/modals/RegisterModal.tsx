import React, { useCallback, useState } from "react";
import {
    FieldValues,
    SubmitHandler,
    useForm
} from "react-hook-form";
import Input from "../input/Input";
import {Modal} from "./Modal";
import {User} from "../../mockup/mockup_users";
import Select from "../input/Select";

interface RegisterModalProps {
    isOpen:boolean;
    toggle: () => void;
}

export const RegisterModal:React.FC<RegisterModalProps> = ({isOpen, toggle}) => {

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

    const onSubmit: SubmitHandler<FieldValues> = (data) => {
        const userData: User = {
            id: 0,
            username: data.name,
            firstName: '',
            lastName: '',
            email: data.email,
            role: 'user',
            password: data.password
        };
        // // Set isLoading to true to indicate that the form is being submitted
        // setIsLoading(true);
        //
        // // Send a POST request to the /api/register endpoint with the form data
        // axios.post('/api/register', data)
        //     .then(() => {
        //         // Display a success message if registration is successful
        //         toast.success('Registered!');
        //
        //         // Close the register modal and open the login modal
        //         registerModal.onClose();
        //         loginModal.onOpen();
        //     })
        //     .catch((error) => {
        //         // Display an error message if there's an error
        //         toast.error(error);
        //     })
        //     .finally(() => {
        //         // Set isLoading back to false after the request is completed
        //         setIsLoading(false);
        //     })
    }

    const bodyContent = (
        <div className="flex flex-col gap-4">
            <div className={'text-center'}>
                <div className="text-2xl font-bold">
                    Welcome to ExhibitPro
                </div>
                <div className="font-light text-neutral-500 mt-2">
                    Create an account!
                </div>
            </div>
            <div className={"flex flex-row space-x-4"}>
                <Input
                    id="email"
                    label="Email"
                    placeholder="Email"
                    type='email'
                    register={register}
                    errors={errors}
                    required
                    setValue={()=>{}}
                />
                <Input
                    id="username"
                    label="Username"
                    placeholder="Username"
                    register={register}
                    errors={errors}
                    required
                    setValue={()=>{}}
                />
            </div>
                <Input
                    id="name"
                    label="Name"
                    placeholder="Name"
                    register={register}
                    errors={errors}
                    required
                    setValue={()=>{}}
                />
                <Input
                    id="lastName"
                    label="Last name"
                    placeholder="Last name"
                    register={register}
                    errors={errors}
                    required
                    setValue={()=>{}}
                />
            <div className={"flex flex-row space-x-4 items-center"}>
                <Select
                    id="role"
                    label="Role"
                    placeholder="Role"
                    register={register}
                    errors={errors}
                    required
                    setValue={()=>{}}
                    options={["Artist", "Curator", "Collectioneer"]}
                />
                <Input
                    id="password"
                    label="Password"
                    type="password"
                    placeholder="Password"
                    register={register}
                    errors={errors}
                    required
                    setValue={()=>{}}
                />
            </div>
        </div>
    )

    return(
        <Modal
            isOpen={isOpen}
            title="Register"
            actionLabel="Sign up"
            onSubmit={handleSubmit(onSubmit)}
            body={bodyContent}
            toggleModal={toggle}
        />
    )

}