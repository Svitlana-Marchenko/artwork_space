import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faStar as solidStar } from '@fortawesome/free-solid-svg-icons';
import { faStar as regularStar } from '@fortawesome/free-regular-svg-icons';
import {User} from "../../mockup/mockup_users";
import {FieldValues, SubmitHandler} from "react-hook-form";
import {NewRating} from "../../mockup/mockup_artworks";
import ArtworkService from "../../API/ArtworkService";
import toast from "react-hot-toast";
import {Button} from "../Button";
import {useNavigate} from "react-router-dom";

interface RatingModalProps {
    currentUser: User;
    currentArtworkId: number;
}


const RatingModal: React.FC<RatingModalProps> = ({ currentUser,currentArtworkId }) => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({ rate: 0, comment: '' });
    const onSubmit: SubmitHandler<FieldValues> = (data) => {
        console.log(data)
        const ratingData:NewRating = {
            rate: data.rate,
            user: currentUser,
            comment: data.comment,
        };
        console.log(ratingData)
        ArtworkService.addRating(currentArtworkId, ratingData)
            .then(() => {
                    toast.success("The rating was added successfully")
                navigate(`/artwork/${currentArtworkId}`);
                }
            )
            .catch(() => {
                toast.error("Failed to add the rating")
            });
    }
    const renderStars = (rating: number) => {
        const totalStars = 10;

        return (
            <>
                {[...Array(rating)].map((_, index) => (
                    <FontAwesomeIcon icon={solidStar} key={index} className="text-yellow-500 cursor-pointer" onClick={() => handleStarClick(index + 1)} />
                ))}
                {[...Array(totalStars - rating)].map((_, index) => (
                    <FontAwesomeIcon icon={regularStar} key={rating + index} className="text-gray-300 cursor-pointer" onClick={() => handleStarClick(rating + index + 1)} />
                ))}
            </>
        );
    };

    const handleStarClick = (clickedRating: number) => {
        const newRating = clickedRating === formData.rate ? 0 : clickedRating;
        setFormData({ ...formData, rate: newRating });
    };

    const handleCommentChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        setFormData({ ...formData, comment: e.target.value });
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        onSubmit(formData);
    };

    return (
        <div className="flex justify-center">
            <form style={{ width: '66.666%' }} onSubmit={handleSubmit} className="mt-4 text-center">
                <h4 className="text-lg font-semibold mb-2" style={{ textAlign: 'left' }}>Add Your Review</h4>
                <div className="mb-2" style={{ textAlign: 'left' }}>{renderStars(formData.rate)}</div>
                <textarea
                    value={formData.comment}
                    onChange={handleCommentChange}
                    placeholder="Add your comments..."
                    rows={4}
                    className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:border-blue-500"
                />
                <div style={{ width: '15%'}} className="flex justify-start mt-2">
                    <Button label={"Submit review"} onClick={handleSubmit}/>
                </div>
            </form>
        </div>

    );
};

export default RatingModal;
