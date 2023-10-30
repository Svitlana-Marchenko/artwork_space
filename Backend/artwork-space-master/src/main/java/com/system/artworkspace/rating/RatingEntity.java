package com.system.artworkspace.rating;

import com.system.artworkspace.user.UserEntity;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class RatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double rate;
    @ManyToOne
    private UserEntity curator;
    @Column(length = 2000)
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RatingEntity(double rate, UserEntity curator, String comment) {
        this.rate = rate;
        this.curator = curator;
        this.comment = comment;
    }

    public RatingEntity() {

    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public UserEntity getCurator() {
        return curator;
    }

    public void setCurator(UserEntity curator) {
        this.curator = curator;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingEntity that = (RatingEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
