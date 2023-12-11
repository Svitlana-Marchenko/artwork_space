import React from 'react';
import {NavLink, useNavigate} from "react-router-dom";
interface EmptyProps {
    message: string;
    link: string;
    dist: string;
}
const Empty:React.FC<EmptyProps> = ({message, link, dist}) => {
    return (
        <div className={'flex flex-col space-y-4 justify-center items-center mt-36'}>
            <p className={'text-3xl font-bold'}>{message}</p>
            <NavLink to={link} className="underline underline-offset-2 text-gray-400">
                Click here to return to the {dist}
            </NavLink>
        </div>
    );
};

export default Empty;