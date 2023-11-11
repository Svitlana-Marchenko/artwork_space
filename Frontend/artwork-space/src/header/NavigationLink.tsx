import React from 'react';
import {NavLink} from "react-router-dom";

interface NavigationLinkProps {
    title: string;
    path: string;
}

const NavigationLink:React.FC<NavigationLinkProps> = ({title, path}) => {
    return (
        <NavLink to={path} className="font-bold hover:border-b-2 hover:border-black">
            {title}
        </NavLink>
    );
};

export default NavigationLink;
