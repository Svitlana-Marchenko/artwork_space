import React from 'react';
import {NavLink} from "react-router-dom";

interface LogoProps {
    name: string
}
const Logo:React.FC<LogoProps> = ({name}) => {
    return (
        <NavLink to={"/"}>
            <h1 className="text-3xl font-bold">
                {name}
            </h1>
        </NavLink>
    );
};

export default Logo;
