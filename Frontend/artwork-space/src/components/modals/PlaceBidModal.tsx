import React, {useCallback, useEffect, useState} from "react";
import {
    FieldValues,
    SubmitHandler,
    useForm
} from "react-hook-form";
import {Modal} from "./Modal";
import Input from "../input/Input";
import {Auction} from "../../types/auctionsTypes";
import {Password} from "../../types/usersTypes";
import UserService from "../../API/UserService";
import toast from "react-hot-toast";
import AuctionService from "../../API/AuctionService";
import {useNavigate} from "react-router-dom";
import {minValueValidation, requiredValidation} from "../../utils/validationUtils";

interface PlaceBidModalProps {
    auction:Auction;
    isOpen:boolean;
    toggle: () => void;
}

export const PlaceBidModal:React.FC<PlaceBidModalProps> = ({auction, isOpen, toggle}) => {
const navigate = useNavigate();
    const {
        register,
        handleSubmit,
        reset,
        setValue,
        formState: {
            errors,
        },
    } = useForm<FieldValues>({
        defaultValues: {
            newPrice: '',
        },
    });

    useEffect(()=>{
        if (auction) {
            setValue('newPrice', auction.currentBid+auction.bid);
        }
    }, [auction]);

    const onSubmit: SubmitHandler<FieldValues> = (data) => {
        AuctionService.placeBid(auction.id, data.newPrice)
            .then(() => {
                    toast.success("The bid was placed successfully")
                    reset();
                    toggle();
                    window.location.reload()
                }
            )
            .catch((error) => {
                toast.error("Failed to place your bid")
                console.error('Error in placing your bid:', error);
            });
    }

    const bodyContent = (
        <div className="flex flex-col gap-4">
            <div className={'text-center'}>
                <div className="text-2xl font-bold">
                    Bid settings
                </div>
                <div className="font-light text-neutral-500 mt-2">
                    {`Current price: ${auction.currentBid} Bid: ${auction.bid}`}
                </div>
            </div>

            <Input
                id="newPrice"
                label="New price"
                placeholder="New price"
                type="number"
                register={register}
                errors={errors}
                validationOptions={{
                    ...requiredValidation,
                    ...minValueValidation(auction.currentBid+auction.bid)
                }}
            />
        </div>
    )

    return(
        <Modal
            isOpen={isOpen}
            title="Place Bid"
            actionLabel="Submit"
            onSubmit={handleSubmit(onSubmit)}
            body={bodyContent}
            toggleModal={toggle}
        />
    )

}