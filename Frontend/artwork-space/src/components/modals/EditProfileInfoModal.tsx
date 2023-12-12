import React, {useEffect} from "react";
import {
    FieldValues,
    SubmitHandler,
    useForm
} from "react-hook-form";
import {Modal} from "./Modal";
import Input from "../input/Input";
import {EditUser, User} from "../../types/usersTypes";
import UserService from "../../API/UserService";
import toast from "react-hot-toast";
import {lettersOnlyValidation, maxLengthValidation, requiredValidation} from "../../utils/validationUtils";

interface EditProfileInfoModalProps {
    isOpen:boolean;
    toggle: () => void;
    currentUser: User;
}

export const EditProfileInfoModal:React.FC<EditProfileInfoModalProps> = ({isOpen, toggle, currentUser}) => {

    const {
        register,
        handleSubmit,
        setValue,
        reset,
        formState: {
            errors,
        },
    } = useForm<FieldValues>({
        defaultValues: {
            username: '',
            firstname: '',
            lastname: ''
        },
    });

    useEffect(() => {
        if (currentUser) {
            setValue('username', currentUser.username);
            setValue('firstname', currentUser.firstName);
            setValue('lastname', currentUser.lastName);
        }
    }, [currentUser, setValue]);

    const onSubmit: SubmitHandler<FieldValues> = (data) => {
        const userData:EditUser = {
            id: currentUser.id,
            username: data.username,
            firstName: data.firstname,
            lastName: data.lastname
        };
        UserService.changeProfile(userData)
            .then(() => {
                    toast.success("The profile was changed successfully")
                    reset();
                    toggle();
                    window.location.reload()
                }
            )
            .catch(() => {
                toast.error("Failed to change your profile")
            });
    }

    const bodyContent = (
        <div className="flex flex-col gap-4">
            <div className={'text-center'}>
                <div className="text-2xl font-bold">
                    Profile settings
                </div>
                <div className="font-light text-neutral-500 mt-2">
                    You can change your bio here
                </div>
            </div>
            <Input
                id="username"
                label="Username"
                placeholder="Username"
                register={register}
                errors={errors}
                validationOptions={{
                    ...requiredValidation,
                    ...maxLengthValidation(100)
                }}
            />

            <Input
                id="firstname"
                label="First name"
                placeholder="First name"
                register={register}
                errors={errors}
                validationOptions={{
                    ...requiredValidation,
                    ...maxLengthValidation(100),
                    ...lettersOnlyValidation
                }}
            />

            <Input
                id="lastname"
                label="Last name"
                placeholder="Last name"
                register={register}
                errors={errors}
                validationOptions={{
                    ...requiredValidation,
                    ...maxLengthValidation(100),
                    ...lettersOnlyValidation
                }}
            />

        </div>
    )

    return(
        <Modal
            isOpen={isOpen}
            title="Edit profile"
            actionLabel="Submit"
            onSubmit={handleSubmit(onSubmit)}
            body={bodyContent}
            toggleModal={toggle}
        />
    )

}
