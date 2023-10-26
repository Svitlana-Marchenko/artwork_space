package com.system.artworkspace.artwork;

import com.system.artworkspace.user.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public interface ArtworkMapper {

   ArtworkMapper INSTANCE = Mappers.getMapper(ArtworkMapper.class);

    ArtworkDto artworkToArtworkDto(Artwork artwork);

    Artwork artworkDtoToArtwork(ArtworkDto artworkDto);

   // ArtworkEntity artworkToArtworkEntity(Artwork artwork);

    //Artwork artworkEntityToArtwork(ArtworkEntity artworkEntity);

    default ArtworkEntity artworkToArtworkEntity(Artwork artwork) {
        if (artwork == null) {
            return null;
        }

        ArtworkEntity artworkEntity = new ArtworkEntity();
        artworkEntity.setId(artwork.getId());
        artworkEntity.setTitle(artwork.getTitle());
        artworkEntity.setDescription(artwork.getDescription());
        artworkEntity.setTechnique(artwork.getTechnique());
        artworkEntity.setWidth(artwork.getWidth());
        artworkEntity.setHeight(artwork.getHeight());
        artworkEntity.setImageURL(artwork.getImageURL());
        artworkEntity.setImageSize(artwork.getImageSize());

        User defUser = new User(1L, "username", "firstName", "lastName", "email", "password", Role.ARTIST);
        artworkEntity.setArtist(UserMapper.INSTANCE.userToUserEntity(defUser));

        return artworkEntity;
    }

    default Artwork artworkEntityToArtwork(ArtworkEntity artworkEntity) {


        Artwork artwork = new Artwork();
        artwork.setId(artworkEntity.getId());
        artwork.setTitle(artworkEntity.getTitle());
        artwork.setDescription(artworkEntity.getDescription());
        artwork.setTechnique(artworkEntity.getTechnique());
        artwork.setWidth(artworkEntity.getWidth());
        artwork.setHeight(artworkEntity.getHeight());
        artwork.setImageURL(artworkEntity.getImageURL());
        artwork.setImageSize(artworkEntity.getImageSize());
        artwork.setArtistId(artworkEntity.getId());

        return artwork;
    }

}
