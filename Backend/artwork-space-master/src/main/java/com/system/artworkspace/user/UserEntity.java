package com.system.artworkspace.user;

import com.system.artworkspace.artwork.ArtworkEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "\"user\"")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "user_id")
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    //@ManyToOne
    //private RoleEntity role;
    @Enumerated(EnumType.STRING)
    private Role role;
//    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, orphanRemoval = true)
//    private List<ArtworkEntity> artworkEntities;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_artwork",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "artwork_id")
    )
    private List<ArtworkEntity> collection;

    public UserEntity(String username, String firstName, String lastName, String email, String password, Role role, List <ArtworkEntity> collection)  {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.collection=collection;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserEntity() {
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ArtworkEntity> getCollection() {
        return collection;
    }

    public void setCollection(List<ArtworkEntity> collection) {
        this.collection = collection;
    }
}
