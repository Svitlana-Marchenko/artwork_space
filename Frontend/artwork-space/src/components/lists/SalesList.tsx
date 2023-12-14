import React, {useEffect, useState} from 'react';
import {User} from "../../types/usersTypes";
import {Sales} from "../../types/salesTypes";
import SalesService from "../../API/SalesService";
import Sale from "../Sale";

const SalesList = () => {
    const storedUserString = localStorage.getItem("currentUser");
    const currentUser: User = storedUserString ? JSON.parse(storedUserString) : null;
    const [sales, setSales] = useState<Sales[]>();
    useEffect(()=>{
        if (currentUser.role ==='COLLECTIONEER') {
            SalesService.getSalesByCollectioneerId(currentUser.id)
                .then((data) => {
                    setSales(data)
                })
                .catch((e) => {
                    console.error("Error in getting sales", e)
                })
        } else if (currentUser.role === 'ARTIST') {
            SalesService.getSalesByArtistId(currentUser.id)
                .then((data) => {
                    setSales(data)
                })
                .catch((e) => {
                    console.error("Error in getting sales", e)
                })
        }
    },[]);

    return (
        <div>
            <h4 className={'font-bold text-3xl'}>MESSAGES</h4>
            <div className={'my-4 flex flex-row flex-wrap space-x-4'}>
                {
                    sales?.map((sale) => {
                        return (
                            <Sale
                                id={sale.id}
                                artwork={sale.artwork}
                                price={sale.price}
                                dateOfBuying={sale.dateOfBuying}
                                seller={sale.seller}
                                buyer={sale.buyer}
                            />
                        )
                    })
                }
            </div>
        </div>
    );
};

export default SalesList;