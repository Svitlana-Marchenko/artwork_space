import React from 'react';
import {NavLink} from "react-router-dom";
import {AiOutlineArrowRight} from "react-icons/ai";

interface CustomLinkProps {
   title: string;
   url: string;
}
const CustomLink:React.FC<CustomLinkProps> = ({title,url}) => {
    return (
        <div className={"flex flex-row gap-2 items-center"}>
            <NavLink to={`/${url}`} className="text-[#a62c2a] font-bold hover:border-b-2 hover:border-[#a62c2a]">
                {title}
            </NavLink>
            <AiOutlineArrowRight className={"text-[#a62c2a]"}/>
        </div>

    );
};

export default CustomLink;
