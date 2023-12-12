import React, {useState} from 'react';
import {Password, User} from "../../types/usersTypes";
import {FieldValues, SubmitHandler, useForm} from "react-hook-form";
import UserService from "../../API/UserService";
import toast from "react-hot-toast";
import Input from "../input/Input";
import {Modal} from "./Modal";
import {NewExhibition} from "../../types/exhibitionsTypes";
import {Artwork} from "../../types/artworkTypes";
import useAuction from "../../hooks/useAuction";
import {NewAuction} from "../../types/auctionsTypes";
import AuctionService from "../../API/AuctionService";
import {useNavigate} from "react-router-dom";
import {dateValidation, maxLengthValidation, minValueValidation, requiredValidation} from "../../utils/validationUtils";

interface AuctionModalProps {
    isOpen:boolean;
    toggle: () => void;
    artwork: Artwork;
}
const AuctionModal:React.FC<AuctionModalProps> = ({isOpen, toggle, artwork}) => {
    const navigate = useNavigate();
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User= storedUserString ? JSON.parse(storedUserString) : null;
    const [newAuction, setNewAuction] = useState<NewAuction>();
    const {
        register,
        reset,
        handleSubmit,
        formState: {
            errors,
        },
    } = useForm<FieldValues>({
        defaultValues: {
            startingPrice: 0,
            step: 0,
            closingTime: new Date(),
        },
    });
    const onSubmit: SubmitHandler<FieldValues> = (data) => {
        setNewAuction({
            artwork: artwork,
            startingPrice: data.startingPrice,
            bid: data.step,
            closingTime: data.closingTime,
            user: currentUser,
            currentBid: data.startingPrice
        });

        if (newAuction) {
            AuctionService.createAuction(newAuction).then(data => {
                toast.success('Added to auction');
                reset();
                navigate(-1)
                console.log(data)
            })
                .catch((error) => {
                    toast.error("Failed to create auction")
                    console.error('Error in adding auction:', error);
                });
        }
    }
    const bodyContent = (
        <div className="flex flex-col gap-4">
            <div className={'text-center'}>
                <div className="text-2xl font-bold">
                    Auction parameters
                </div>
                <div className="font-light text-neutral-500 mt-2">
                    Change it right here
                </div>
            </div>
            <Input
                id="startingPrice"
                label="Starting price"
                placeholder="Starting price"
                type="number"
                register={register}
                errors={errors}
                validationOptions={{
                    ...requiredValidation,
                    ...minValueValidation(0),
                }}
            />
            <Input
                id="step"
                label="Step"
                placeholder="Step"
                type="number"
                register={register}
                errors={errors}
                validationOptions={{
                    ...requiredValidation,
                    ...minValueValidation(0),
                }}
            />
            <Input
                id="closingTime"
                label="Closing time"
                placeholder="Closing time"
                type="date"
                register={register}
                errors={errors}
                validationOptions={{
                    ...requiredValidation,
                    ...dateValidation
                }}
            />
        </div>
    )

    return(
        <Modal
            isOpen={isOpen}
            title="Create auction"
            actionLabel="Submit"
            onSubmit={handleSubmit(onSubmit)}
            toggleModal={toggle}
            body={bodyContent}
        />
    )
};

export default AuctionModal;