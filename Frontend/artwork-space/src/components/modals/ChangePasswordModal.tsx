import React from "react";
import {
    FieldValues,
    SubmitHandler,
    useForm
} from "react-hook-form";
import {Modal} from "./Modal";
import Input from "../input/Input";
import UserService from "../../API/UserService";
import toast from "react-hot-toast";
import {Password, User} from "../../types/usersTypes";
import {passwordValidation, repeatPasswordValidation, requiredValidation} from "../../utils/validationUtils";
interface ChangePasswordModalProps {
    isOpen:boolean;
    toggle: () => void;
}
export const ChangePasswordModal:React.FC<ChangePasswordModalProps> = ({isOpen, toggle}) => {
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User= storedUserString ? JSON.parse(storedUserString) : null;
    const {
        register,
        reset,
        handleSubmit,
        formState: {
            errors,
        },
    } = useForm<FieldValues>({
        defaultValues: {
            oldPassword: '',
            newPassword: '',
            newRepeatPassword: ''
        },
    });

    const onSubmit: SubmitHandler<FieldValues> = (data) => {
        const passwordData:Password = {
            id: currentUser.id,
            oldPassword: data.oldPassword,
            newPassword: data.newPassword
        };
        UserService.changePassword(passwordData)
            .then((data) => {
                    toast.success("The password was changed successfully")
                    reset();
                    toggle();
                }
            )
            .catch((error) => {
                toast.error("Failed to change your password")
                console.error('Error in creating your profile:', error);
            });
    }
//todo validation for password length 8
    const bodyContent = (
        <div className="flex flex-col gap-4">
            <div className={'text-center'}>
                <div className="text-2xl font-bold">
                    Password settings
                </div>
                <div className="font-light text-neutral-500 mt-2">
                    Change it right here
                </div>
            </div>
            <Input
                id="oldPassword"
                label="Previous password"
                placeholder="Previous password"
                type="password"
                register={register}
                errors={errors}
                validationOptions={{
                    ...requiredValidation,
                }}
                
            />
            <Input
                id="newPassword"
                label="New password"
                placeholder="New password"
                type="password"
                register={register}
                errors={errors}
                validationOptions={{
                    ...passwordValidation,
                    ...requiredValidation,
                }}
            />
            <Input
                id="newRepeatPassword"
                label="Repeat new password"
                placeholder="Repeat new password"
                type="password"
                register={register}
                errors={errors}
                validationOptions={{
                    ...repeatPasswordValidation,
                    ...requiredValidation,
                }}
            />
        </div>
    )

    return(
        <Modal
            isOpen={isOpen}
            title="Change password"
            actionLabel="Submit"
            onSubmit={handleSubmit(onSubmit)}
            toggleModal={toggle}
            body={bodyContent}
        />
    )

}