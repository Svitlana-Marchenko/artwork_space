import React from 'react';
import {NavLink} from "react-router-dom";

interface ArtistLinkProps {
    id: number;
    username: string;
}
const ArtistLink:React.FC<ArtistLinkProps> = ({id, username}) => {
    return (
        <NavLink to={`/artist/${id}`} className="underline underline-offset-1 text-sm">
            @{username}
        </NavLink>
    );
};

export default ArtistLink;
