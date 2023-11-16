import React from 'react';
import UserLink from "../UserLink";

interface ArtworkHeadingProps {
    id: number;
    username: string;
    firstName: string;
    lastName: string;
    title: string;
}
const ArtworkHeading:React.FC<ArtworkHeadingProps> = ({id, username, firstName, lastName, title}) => {
    return (
        <div>
            <UserLink id={id} username={username}/>
            <p className={"text-gray-400 mb-1"}>Painting by {firstName} {lastName}</p>
            <h2 className={"text-3xl font-bold"}>{title.toUpperCase()}</h2>
        </div>
    );
};

export default ArtworkHeading;
