package com.system.artworkspace.artwork;

import com.system.artworkspace.collection.Collection;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ArtworkServiceImpl implements ArtworkService{

    private final ArtworkRepository repository;

    @Autowired
    public ArtworkServiceImpl(ArtworkRepository repository) {
        this.repository = repository;
    }

    @Override
    public Artwork addArtwork(Artwork artwork) {
        repository.save(artwork);
        return artwork;
    }

    @Override
    public void deleteArtwork(Long artworkId) {
repository.deleteById(artworkId);
    }

    @Override
    public Artwork findArtworkById(Long id) {
        Optional<Artwork> artwork = repository.findById(id);
        return artwork.orElse(null);
    }

    @Override
    public void updateTitle(Artwork artwork, String title) {
        Optional<Artwork> optionalArtwork = repository.findById(artwork.getId());

        if (optionalArtwork.isPresent()) {
            Artwork existingArtwork= optionalArtwork.get();
            existingArtwork.setTitle(title);
            repository.save(existingArtwork);
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + artwork.getId());
        }
    }

    @Override
    public void updateDescription(Artwork artwork, String description) {
        Optional<Artwork> optionalArtwork = repository.findById(artwork.getId());

        if (optionalArtwork.isPresent()) {
            Artwork existingArtwork= optionalArtwork.get();
            existingArtwork.setDescription(description);
            repository.save(existingArtwork);
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + artwork.getId());
        }
    }

    @Override
    public void updateTechnique(Artwork artwork, String technique) {
        Optional<Artwork> optionalArtwork = repository.findById(artwork.getId());

        if (optionalArtwork.isPresent()) {
            Artwork existingArtwork= optionalArtwork.get();
            existingArtwork.setTechnique(technique);
            repository.save(existingArtwork);
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + artwork.getId());
        }
    }

    @Override
    public void updateWidth(Artwork artwork, double width) {
        Optional<Artwork> optionalArtwork = repository.findById(artwork.getId());

        if (optionalArtwork.isPresent()) {
            Artwork existingArtwork= optionalArtwork.get();
            existingArtwork.setWidth(width);
            repository.save(existingArtwork);
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + artwork.getId());
        }
    }

    @Override
    public void updateHeight(Artwork artwork, double height) {
        Optional<Artwork> optionalArtwork = repository.findById(artwork.getId());

        if (optionalArtwork.isPresent()) {
            Artwork existingArtwork= optionalArtwork.get();
            existingArtwork.setHeight(height);
            repository.save(existingArtwork);
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + artwork.getId());
        }
    }

    @Override
    public void updateImgUrl(Artwork artwork, String url) {
        Optional<Artwork> optionalArtwork = repository.findById(artwork.getId());

        if (optionalArtwork.isPresent()) {
            Artwork existingArtwork= optionalArtwork.get();
            existingArtwork.setImageURL(url);
            repository.save(existingArtwork);
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + artwork.getId());
        }
    }

    @Override
    public void updateImgSize(Artwork artwork, double size) {
        Optional<Artwork> optionalArtwork = repository.findById(artwork.getId());

        if (optionalArtwork.isPresent()) {
            Artwork existingArtwork= optionalArtwork.get();
            existingArtwork.setImageSize(size);
            repository.save(existingArtwork);
        } else {
            throw new EntityNotFoundException("Artwork not found with ID: " + artwork.getId());
        }
    }

    public List<Artwork> getArtworksByTitle(String title) {
        return repository.findByTitle(title);
    }

}
