
export const calculateAverageRating = (ratings) => {
    if (!ratings || ratings.length === 0) {
        return 0;
    }

    const totalRating = ratings.reduce((sum, rating) => sum + rating.rate, 0);
    return totalRating / ratings.length;
};
