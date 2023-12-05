import React, { useCallback, useState } from "react";
import {
    FieldValues,
    SubmitHandler,
    useForm
} from "react-hook-form";

import {Modal} from "./Modal";
import Input from "../input/Input";
import {LoginProps, NewUser} from "../../mockup/mockup_users";
import UserService from "../../API/UserService";
import toast from "react-hot-toast";
interface LoginModalProps {
    isOpen:boolean;
    toggle: () => void;
}

export const LoginModal:React.FC<LoginModalProps> = ({isOpen, toggle}) => {

    const {
        register,
        handleSubmit,
        reset,
        formState: {
            errors,
        },
    } = useForm<FieldValues>({
        defaultValues: {
            email: '',
            password: ''
        },
    });

    const onSubmit: SubmitHandler<FieldValues> = (data) => {
        const userData: LoginProps = {
            email: data.email,
            password: data.password
        };
        // UserService.authorize(userData)
        //     .then((data) => {
        //             toast.success("Successful login")
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


    const bodyContent = (
        <div className="flex flex-col gap-4">
            <div className={'text-center'}>
                <div className="text-2xl font-bold">
                    Welcome back
                </div>
                <div className="font-light text-neutral-500 mt-2">
                    Login to your account!
                </div>
            </div>
            <Input
                id="email"
                label="Email"
                placeholder="Email"
                register={register}
                errors={errors}
                required
            />

            <Input
                id="password"
                label="Password"
                placeholder="Password"
                type="password"
                register={register}
                errors={errors}
                required
            />
        </div>
    )

    return(
        <Modal
            isOpen={isOpen}
            title="Login"
            actionLabel="Login"
            onSubmit={handleSubmit(onSubmit)}
            toggleModal={toggle}
            body={bodyContent}
        />
    )

}