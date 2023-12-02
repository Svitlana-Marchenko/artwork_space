import React, { useCallback, useState } from "react";
import {
    FieldValues,
    SubmitHandler,
    useForm
} from "react-hook-form";

import {Modal} from "./Modal";
import Input from "../Input";
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
                setValue={()=>{}}
            />

            <Input
                id="password"
                label="Password"
                placeholder="Password"
                type="password"
                register={register}
                errors={errors}
                required
                setValue={()=>{}}
            />
        </div>
    )

    const footerContent = (
        <div className="flex flex-col gap-4 mt-3">
            <hr />
            <div className="
      text-neutral-500 text-center mt-4 font-light">
                <p>First time using ExhibitPro?
                    <span
                        onClick={()=>{}}
                        className="
              text-neutral-800
              cursor-pointer
              hover:underline
            "
                    > Create an account</span>
                </p>
            </div>
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
            footer={footerContent}
        />
    )

}