package com.system.artworkspace.exhibition;

import com.system.artworkspace.artwork.ArtworkEntity;
import com.system.artworkspace.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ExhibitionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity curator;

    private String title;

    @Column(length = 2000)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "exhibition_artwork",
            joinColumns = @JoinColumn(name = "exhibition_id"),
            inverseJoinColumns = @JoinColumn(name = "artwork_id")
    )
    private List<ArtworkEntity> artworks;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    public ExhibitionEntity(UserEntity curator, String title, String description, List<ArtworkEntity> artworks, Date startDate, Date endDate) {
        this.curator = curator;
        this.title = title;
        this.description = description;
        this.artworks = artworks;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
