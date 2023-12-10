import React, { useState } from 'react';
import { Rating } from '../../mockup/mockup_artworks';
import UserLink from '../UserLink';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faStar as solidStar } from '@fortawesome/free-solid-svg-icons';
import { faStar as regularStar } from '@fortawesome/free-regular-svg-icons';
import {User} from "../../mockup/mockup_users";

interface ArtworkRatingsProps {
    ratings: Rating[];
    showRatingForm: boolean;
    currentUser: User;
    currentArtworkId: number;
}

/*TODO
*  calculate average rating and show
*  add rating should work properly with controllers and db (after login)
*  add refresh the page when adding a rating
*  think about required rate/comment in the form*/
const ArtworkRatings: React.FC<ArtworkRatingsProps> = ({ ratings, showRatingForm, currentUser, currentArtworkId }) => {
    const [showAllRatings, setShowAllRatings] = useState(false);

    const renderStars = (rating: number) => {
        const totalStars = 10;
        const filledStars = Math.round((rating / totalStars) * 10);

        return (
            <>
                {[...Array(filledStars)].map((_, index) => (
                    <FontAwesomeIcon icon={solidStar} key={index} className="text-yellow-500" />
                ))}
                {[...Array(totalStars - filledStars)].map((_, index) => (
                    <FontAwesomeIcon icon={regularStar} key={filledStars + index} className="text-gray-300" />
                ))}
            </>
        );
    };


    const handleClickToggle = () => {
        setShowAllRatings(!showAllRatings);
    };

    const visibleRatings = showAllRatings ? ratings : ratings.slice(0, 3);

    return (
        <div style={{ width: '66.666%' }} className="mt-4 p-4 border border-gray-300 rounded mx-auto">
            <h3 className="text-lg font-semibold mb-2">Ratings</h3>
            {visibleRatings.length === 0 ? (
                <ul>
                    <li className="mb-4">
                        <p className="text-gray-600">No ratings yet.</p>
                    </li>
                </ul>
            ) : (
                <ul>
                    {visibleRatings.map((rating, index) => (
                        <li
                            key={rating.id}
                            className={`mb-4 ${index !== visibleRatings.length - 1 ? 'border-b border-gray-300 pb-4' : ''}`}
                        >
                            <p className="text-gray-600">
                                Rated by <UserLink id={rating.user.id} username={rating.user.username} />
                            </p>
                            <p className="text-lg">
                                {renderStars(rating.rate)} ({rating.rate}/10)
                            </p>
                            <p className="text-gray-700">{rating.comment}</p>
                        </li>
                    ))}
                    {ratings.length > 3 && (
                        <li className="mt-2">
                            <button
                                className="text-gray-500 text-base underline cursor-pointer hover:text-gray-700"
                                onClick={handleClickToggle}
                            >
                                {showAllRatings ? 'Show Less' : 'Show More'}
                            </button>
                        </li>
                    )}
                </ul>
            )}
        </div>
    );
};

export default ArtworkRatings;
