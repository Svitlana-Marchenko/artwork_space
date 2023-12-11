import React from 'react';
import {useNavigate} from "react-router-dom";

const Error = () => {
    const navigate = useNavigate();
    return (
        <div className={'flex flex-col space-y-4 justify-center items-center mt-72'}>
            <p className={'text-3xl font-bold'}>Sorry something went wrong :(</p>
            <p className={"text-gray-400"}>
                <span
                    className={'underline underline-offset-2 cursor-pointer'}
                    onClick={()=>{navigate(`/`)}}
                >
                    Click here to return to the main page
                </span>
            </p>
        </div>
    );
};

export default Error;
