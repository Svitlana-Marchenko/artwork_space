import React from 'react';
import { Rating } from '../../mockup/mockup_artworks';
import UserLink from '../UserLink';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faStar as solidStar } from '@fortawesome/free-solid-svg-icons';
import { faStar as regularStar } from '@fortawesome/free-regular-svg-icons';

interface ArtworkRatingsProps {
    ratings: Rating[];
}

const ArtworkRatings: React.FC<ArtworkRatingsProps> = ({ ratings }) => {
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

    return (
        <div style={{ width: '66.666%' }} className="mt-4 p-4 border border-gray-300 rounded mx-auto">
            <h3 className="text-lg font-semibold mb-2">Ratings</h3>
            {ratings.length === 0 ? (
                <ul>
                    <li className="mb-4">
                        <p className="text-gray-600">No ratings yet.</p>
                    </li>
                </ul>
            ) : (
                <ul>
                    {ratings.map((rating, index) => (
                        <li
                            key={rating.id}
                            className={`mb-4 ${index !== ratings.length - 1 ? 'border-b border-gray-300 pb-4' : ''}`}
                        >
                            <p className="text-gray-600">
                                Rated by{' '}
                                <UserLink id={rating.user.id} username={rating.user.username} />
                            </p>
                            <p className="text-lg">
                                {renderStars(rating.rate)} ({rating.rate}/10)
                            </p>
                            <p className="text-gray-700">{rating.comment}</p>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default ArtworkRatings;
