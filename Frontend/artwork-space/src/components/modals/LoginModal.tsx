import React, { useCallback, useState } from "react";
import {
    FieldValues,
    SubmitHandler,
    useForm
} from "react-hook-form";

import {Modal} from "./Modal";
import Input from "../input/Input";
interface LoginModalProps {
    isOpen:boolean;
    toggle: () => void;
}
export const LoginModal:React.FC<LoginModalProps> = ({isOpen, toggle}) => {

    const {
        register,
        handleSubmit,
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

        // signIn('credentials', {
        //     ...data,
        //     redirect: false,
        // })
        //     .then((callback) => {
        //         setIsLoading(false);
        //
        //         if (callback?.ok) {
        //             toast.success('Logged in');
        //             router.refresh();
        //             loginModal.onClose();
        //         }
        //
        //         if (callback?.error) {
        //             toast.error(callback.error);
        //         }
        //     });
    }

    // const onToggle = useCallback(() => {
    //     loginModal.onToggle();
    //     registerModal.onOpen();
    // }, [loginModal, registerModal]);


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
            actionLabel="Continue"
            onSubmit={handleSubmit(onSubmit)}
            toggleModal={toggle}
            body={bodyContent}
        />
    )

}