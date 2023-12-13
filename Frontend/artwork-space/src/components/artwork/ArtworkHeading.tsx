import React from 'react';
import UserLink from "../UserLink";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { faStar as solidStar } from '@fortawesome/free-solid-svg-icons';
interface ArtworkHeadingProps {
    id: number;
    username: string;
    firstName: string;
    lastName: string;
    title: string;
    showAverageRating:boolean
    averageRating: number;
}
const ArtworkHeading:React.FC<ArtworkHeadingProps> = ({id, username, firstName, lastName, title,showAverageRating, averageRating}) => {
    return (
        <div className="flex flex-col items-start">
            <UserLink id={id} username={username} />
            <p className="text-gray-400 mb-1">Painting by {firstName} {lastName}</p>
            <h2 className="text-3xl font-bold">{title.toUpperCase()}</h2>
            {showAverageRating && (
            <div className="flex items-center gap-2">
                <span className="text-lg font-semibold">{averageRating.toFixed(1)}</span>
                <FontAwesomeIcon icon={solidStar} className="text-yellow-500" />
            </div>
                )}
        </div>
    );
};

export default ArtworkHeading;
