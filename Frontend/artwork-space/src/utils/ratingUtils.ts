import {Rating} from "../types/artworkTypes";
import {User} from "../types/usersTypes";

export const calculateAverageRating = (ratings:Rating[]) => {
    if (!ratings || ratings.length === 0) {
        return 0;
    }

    const totalRating = ratings.reduce((sum, rating) => sum + rating.rate, 0);
    return totalRating / ratings.length;
};
export const hasUserRatedArtwork = (ratings:Rating[], currentUser:User) => {
    // Check if there is any rating by the current user
    return ratings.some(rating => rating.user.id === currentUser.id);
};