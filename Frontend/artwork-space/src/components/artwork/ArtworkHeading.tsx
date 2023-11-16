import React from 'react';
import ArtistLink from "../ArtistLink";

interface ArtworkHeadingProps {
    id: number;
    username: string;
    firstName: string;
    lastName: string;
    title: string;
}
const ArtworkHeading:React.FC<ArtworkHeadingProps> = ({id, username, firstName, lastName, title}) => {
    return (
        <>
            <ArtistLink id={id} username={username}/>
            <p className={"text-gray-400 mb-1"}>Painting by {firstName} {lastName}</p>
            <h2 className={"text-3xl font-bold"}>{title.toUpperCase()}</h2>
        </>
    );
};

export default ArtworkHeading;
