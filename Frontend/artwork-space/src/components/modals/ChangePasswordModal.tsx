import React, { useCallback, useState } from "react";
import {
    FieldValues,
    SubmitHandler,
    useForm
} from "react-hook-form";

import {Modal} from "./Modal";
import toast from "react-hot-toast";
import axios from "axios";
import Input from "../input/Input";
interface ChangePasswordModalProps {
    isOpen:boolean;
    toggle: () => void;
}
export const ChangePasswordModal:React.FC<ChangePasswordModalProps> = ({isOpen, toggle}) => {

    const {
        register,
        handleSubmit,
        formState: {
            errors,
        },
    } = useForm<FieldValues>({
        defaultValues: {
            prevPassword: '',
            newPassword: '',
            newRepeatPassword: ''
        },
    });

    const onSubmit: SubmitHandler<FieldValues> = (data) => {
//todo implement changing password
    }

    const bodyContent = (
        <div className="flex flex-col gap-4">
            <div className={'text-center'}>
                <div className="font-light text-neutral-500 mt-2">
                    Use these fields to change password!
                </div>
            </div>
            <Input
                id="prevPassword"
                label="Previous password"
                placeholder="Previous password"
                type="password"
                register={register}
                errors={errors}
                required
                
            />

            <Input
                id="newPassword"
                label="New password"
                placeholder="New password"
                type="password"
                register={register}
                errors={errors}
                required
                
            />

            <Input
                id="newRepeatPassword"
                label="Repeat new password"
                placeholder="Repeat new password"
                type="password"
                register={register}
                errors={errors}
                required
                
            />
        </div>
    )

    const footerContent = (
        <></>
    )

    return(
        <Modal
            isOpen={isOpen}
            title="Change password"
            actionLabel="Change"
            onSubmit={handleSubmit(onSubmit)}
            toggleModal={toggle}
            body={bodyContent}
        />
    )

}