package com.blaqboxdev.unsplash.Services.Implementations;

import com.blaqboxdev.unsplash.Exceptions.CollectionNotFoundException;
import com.blaqboxdev.unsplash.Exceptions.ImageNotFoundException;
import com.blaqboxdev.unsplash.Exceptions.ProfileNotFoundException;
import com.blaqboxdev.unsplash.Exceptions.UserNotFoundException;
import com.blaqboxdev.unsplash.Models.Entities.Collection;
import com.blaqboxdev.unsplash.Models.Entities.Image;
import com.blaqboxdev.unsplash.Models.Entities.Profile;
import com.blaqboxdev.unsplash.Models.Requests.CollectionRequest;
import com.blaqboxdev.unsplash.Repositories.CollectionRepository;
import com.blaqboxdev.unsplash.Repositories.ImageRepository;
import com.blaqboxdev.unsplash.Repositories.ProfileRepository;
import com.blaqboxdev.unsplash.Services.CollectionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CollectionServiceImplementation implements CollectionsService {

    @Autowired
    final private CollectionRepository collectionRepository;
    final private ProfileRepository profileRepository;
    final private ImageRepository imageRepository;

    @Override
    public Collection createCollection(CollectionRequest collection) {
        Profile profile = profileRepository.findByUsername(collection.author()).orElseThrow(() -> new ProfileNotFoundException("Profile with username does not exist"));
        Collection newCollection = Collection.builder()
                .title(collection.title())
                .author(collection.author())
                .build();

        return collectionRepository.save(newCollection);
    }

    @Override
    public Collection updateCollection(Collection collection) {
        Collection existing = collectionRepository.findById(collection.get_id()).orElseThrow(() -> new CollectionNotFoundException("Collection ID does not exist"));
        return collectionRepository.save(collection);
    }

    @Override
    public Collection updateCollection(CollectionRequest collection, String id) {
        Collection existing = collectionRepository.findById(id).orElseThrow(() -> new CollectionNotFoundException("Collection ID does not exist"));
        existing.setTitle(collection.title());
        existing.setAuthor(collection.author());
        return collectionRepository.save(existing);
    }

    @Override
    public Collection getCollectionById(String id) {
        System.out.println(collectionRepository.existsById(id));
        return collectionRepository.findBy_id(id).orElseThrow(() -> new CollectionNotFoundException("Collection ID not found."));
    }

    @Override
    public List<Collection> getCollectionsByAuthor(String username) {
//        validate user
        Profile profile = profileRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("profile with username " + username +" does not exist"));

        return collectionRepository.findAllCollectionsByAuthor(username).orElseThrow(() -> new CollectionNotFoundException("Failed to get collections"));
    }

    @Override
    public void deleteCollection(String id) {
        Collection collection = collectionRepository.findById(id).orElseThrow(() -> new CollectionNotFoundException("Collection ID not found"));
        collectionRepository.delete(collection);
    }

    @Override
    public void deleteCollection(Collection collection) {
        collectionRepository.delete(collection);
    }

    @Override
    public List<Collection> getAllCollections() {
        return collectionRepository.findAll();
    }

    @Override
    public boolean removeImageFromCollection(String collectionId, String imageId) {
        Collection collection = collectionRepository.findById(collectionId).orElseThrow(() -> new CollectionNotFoundException("Collection ID not found"));
        Image image = imageRepository.findById(imageId).orElseThrow(() -> new ImageNotFoundException("Image not found"));

        boolean isRemoved = collection.removeImage(image);
        if (isRemoved) {
            collectionRepository.save(collection);
        }
        return isRemoved;
    }

    @Override
    public Collection addImageToCollection(String collectionId, String imgId) {
        Collection collection = collectionRepository.findById(collectionId).orElseThrow(() -> new CollectionNotFoundException("Collection with ID " + collectionId + " not found."));
        Image image = imageRepository.findById(imgId).orElseThrow(() -> new ImageNotFoundException("Image with ID " + imgId + " not found"));
        collection.addImage(image);
        return collectionRepository.save(collection);
    }

    @Override
    public List<Collection> getCollectionsByImageId(String id) {
        return collectionRepository.findAllByImageId(id).orElseThrow(() -> new CollectionNotFoundException("Failed to get collections"));
    }
}
