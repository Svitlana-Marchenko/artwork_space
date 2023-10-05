package com.system.artworkspace.artwork;

import com.system.artworkspace.user.User;
import jakarta.persistence.*;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double rate;
    @ManyToOne
    private User curator;
    @Column(length = 2000)
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rating(double rate, User curator, String comment) {
        this.rate = rate;
        this.curator = curator;
        this.comment = comment;
    }

    public Rating() {

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
