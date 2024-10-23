package com.system.artworkspace.artwork;

import com.system.artworkspace.rating.RatingEntity;
import com.system.artworkspace.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class ArtworkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String title;

    @Column(length = 2000)
    private String description;

    private String technique;

    private double width;

    private double height;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private String imageURL;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<RatingEntity> ratings;

    public ArtworkEntity(UserEntity artist, String title, String description, String technique, double width, double height, String imageURL, List<RatingEntity> ratings) {
        this.user = artist;
        this.title=title;
        this.width=width;
        this.height=height;
        this.imageURL=imageURL;
        this.technique=technique;
        this.description=description;
        this.ratings=ratings;
    }


//    public ArtworkEntity(Long id) {
//        this.id=id;
//    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ArtworkEntity artwork = (ArtworkEntity) obj;
        return Objects.equals(id, artwork.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}