import React from 'react';
import {Sales} from "../types/salesTypes";

const Sale:React.FC<Sales> = ({artwork, dateOfBuying, price, seller, buyer}) => {
    return (
    <div>
        <div className={'w-[300px] relative'}>
            <div
                className="
                absolute
                top-0
                left-0
                flex
                h-[300px]
                w-full
                p-4
                bg-black/30
                text-white"
            >
                <p className={"text-xl font-bold"}>{artwork.title.toUpperCase()}</p>
            </div>
            <img src={artwork.imageURL} alt={artwork.title} className={"w-full h-[300px] object-cover"}/>
            <p>The painting was purchased by <span className={'font-bold'}>{buyer.firstName} {buyer.lastName}</span> on <span className={'font-bold'}>{new Date(dateOfBuying).toDateString()}</span> for <span className={'font-bold'}>{price}$</span></p>
        </div>
    </div>
    );
};

export default Sale;