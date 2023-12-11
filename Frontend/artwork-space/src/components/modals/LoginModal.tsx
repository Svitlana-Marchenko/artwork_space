import React, { useCallback, useState } from "react";
import {
    FieldValues,
    SubmitHandler,
    useForm
} from "react-hook-form";
import { jwtDecode } from "jwt-decode";
import {Modal} from "./Modal";
import Input from "../input/Input";
import UserService from "../../API/UserService";
import toast from "react-hot-toast";
import {MyToken, User} from "../../mockup/mockup_users";
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
            username: '',
            password: ''
        },
    });

    const onSubmit: SubmitHandler<FieldValues> = async (data) => {
        let token: string | undefined = undefined;
        UserService.signIn(data.username, data.password)
            .then(async (data) => {
                    toast.success("Successful authorization")
                    reset();
                    toggle();
                    token = data;
                    const userId: number = jwtDecode<MyToken>(data).id;
                    const userData = await UserService.getUserById(userId);
                    localStorage.setItem("authToken", data);
                    localStorage.setItem("currentUser", JSON.stringify(userData));
                    console.log("User data fetched and saved to local storage:", userData);
                }
            )
            .catch((error) => {
                toast.error("Failed to log in to your profile")
                console.error('Error in authorization:', error);
            });

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
                id="username"
                label="Username"
                placeholder="Username"
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