import React, { useCallback, useState } from "react";
import {
    FieldValues,
    SubmitHandler,
    useForm
} from "react-hook-form";
import Input from "../Input";
import {Modal} from "./Modal";

interface RegisterModalProps {
    isOpen:boolean;
    toggle: () => void;
}

export const RegisterModal:React.FC<RegisterModalProps> = ({isOpen, toggle}) => {

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
                id="name"
                label="Name"
                placeholder="Name"
                register={register}
                errors={errors}
                required
                setValue={()=>{}}
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
    )
// JSX content for the modal footer
    const footerContent = (
        <div className="flex flex-col gap-4 mt-3">
            <hr />
            <div
                className="
          text-neutral-500
          text-center
          mt-4
          font-light
        "
            >
                <p>Already have an account?
                    <span
                        onClick={()=>{}}
                        className="
              text-neutral-800
              cursor-pointer
              hover:underline
            "
                    > Log in</span>
                </p>
            </div>
        </div>
    )

    return(
        <Modal
            isOpen={isOpen}
            title="Register"
            actionLabel="Continue"
            onSubmit={handleSubmit(onSubmit)}
            body={bodyContent}
            footer={footerContent}
            toggleModal={toggle}
        />
    )

}