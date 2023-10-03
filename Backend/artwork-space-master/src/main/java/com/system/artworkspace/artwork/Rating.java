package com.system.artworkspace.artwork;

import com.system.artworkspace.user.User;

public class Rating {
    private String ratingId;
    private double rate;
    private User curator;
    private String comment;

    public Rating(String ratingId, double rate, User curator, String comment) {
        this.ratingId = ratingId;
        this.rate = rate;
        this.curator = curator;
        this.comment = comment;
    }

    public String getRatingId() {
        return ratingId;
    }

    public void setRatingId(String ratingId) {
        this.ratingId = ratingId;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public User getCurator() {
        return curator;
    }

    public void setCurator(User curator) {
        this.curator = curator;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
