import React, { useCallback, useState } from "react";
import {
    FieldValues,
    SubmitHandler,
    useForm
} from "react-hook-form";
import Input from "../input/Input";
import {Modal} from "./Modal";
import {NewUser, User, UserRole} from "../../mockup/mockup_users";
import Select from "../input/Select";
import UserService from "../../API/UserService";
import toast from "react-hot-toast";
import {LoginModal} from "./LoginModal";
import {
    emailValidation,
    lettersOnlyValidation,
    maxLengthValidation, passwordValidation,
    requiredValidation
} from "../../utils/validationUtils";

interface RegisterModalProps {
    isOpen:boolean;
    toggle: () => void;
}

export const RegisterModal:React.FC<RegisterModalProps> = ({isOpen, toggle}) => {
    const [isOpenLogin, setIsOpenLogin] = useState(false);
    const toggleOpenLogin = useCallback(() => {
        setIsOpenLogin((value) => !value);
    }, []);
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
                firstName: '',
                lastName: '',
                email: '',
                role: '',
                password: ''
            },
        });

    const onSubmit: SubmitHandler<FieldValues> = (data) => {
        const userData: NewUser = {
            username: data.username,
            firstName: data.firstName,
            lastName: data.lastName,
            email: data.email,
            role: data.role.toUpperCase(),
            password: data.password
        };
        UserService.createUser(userData)
            .then((data) => {
                toast.success("Successful registration")
                reset();
                toggle();
                toggleOpenLogin();
                }
            )
            .catch((error) => {
                toast.error("Failed to create your profile")
                console.error('Error in creating your profile:', error);
            });
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
                    validationOptions={{
                        ...requiredValidation,
                        ...emailValidation
                    }}
                />
                <Input
                    id="username"
                    label="Username"
                    placeholder="Username"
                    register={register}
                    errors={errors}
                    validationOptions={{
                        ...requiredValidation,
                    }}
                />
            </div>
                <Input
                    id="firstName"
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
                    id="lastName"
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
            <div className={"flex flex-row space-x-4 items-center"}>
                <Select
                    id="role"
                    label="Role"
                    placeholder="Role"
                    register={register}
                    errors={errors}
                    required
                    options={Object.values(UserRole)}
                />
                <Input
                    id="password"
                    label="Password"
                    type="password"
                    placeholder="Password"
                    register={register}
                    errors={errors}
                    validationOptions={{
                        ...requiredValidation,
                        ...passwordValidation
                    }}
                />
            </div>
        </div>
    )

    return(
        <>
            <Modal
                isOpen={isOpen}
                title="Register"
                actionLabel="Sign up"
                onSubmit={handleSubmit(onSubmit)}
                body={bodyContent}
                toggleModal={toggle}
            />
            <LoginModal isOpen={isOpenLogin} toggle={toggleOpenLogin}/>
        </>
    )

}