import React, {useCallback, useEffect, useState} from "react";
import {
    FieldValues,
    SubmitHandler,
    useForm
} from "react-hook-form";
import {Modal} from "./Modal";
import Input from "../input/Input";
import AuctionBid from "../input/AuctionInput";
import {Auction} from "../../mockup/mockup_auctions";

interface PlaceBidModalProps {
    auction:Auction;
    isOpen:boolean;
    toggle: () => void;
}

export const PlaceBidModal:React.FC<PlaceBidModalProps> = ({auction, isOpen, toggle}) => {

    const [isLoading, setIsLoading] = useState(false);

    const {
        register,
        handleSubmit,
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

    }

    //todo edit email + server checking
    //todo implement functionality

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
                required
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