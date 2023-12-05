import React, { useCallback, useState } from "react";
import {
    FieldValues,
    SubmitHandler,
    useForm
} from "react-hook-form";
import {Modal} from "./Modal";
import Input from "../input/Input";
import AuctionBid from "../input/AuctionInput";

interface PlaceBidModalProps {
    auctionId:number;
    minVal:number
    isOpen:boolean;
    toggle: () => void;
}

export const PlaceBidModal:React.FC<PlaceBidModalProps> = ({auctionId, minVal, isOpen, toggle}) => {

    const [isLoading, setIsLoading] = useState(false);

    const {
        register,
        handleSubmit,
        formState: {
            errors,
        },
    } = useForm<FieldValues>({
        defaultValues: {
            bid: '',
        },
    });

    // Define the form submission handler
    const onSubmit: SubmitHandler<FieldValues> = (data) => {

    }

    //todo edit email + server checking
    //todo implement functionality

    const bodyContent = (
        <div className="flex flex-col gap-4">
            <div className={'text-center'}>
                <div className="font-light text-neutral-500 mt-2">
                    Place bid for this incredible artwork!
                </div>
            </div>

            <AuctionBid minValue={minVal}></AuctionBid>

        </div>
    )

    return(
        <Modal
            isOpen={isOpen}
            title="Place Bid"
            actionLabel="Place"
            onSubmit={handleSubmit(onSubmit)}
            body={bodyContent}
            toggleModal={toggle}
        />
    )

}