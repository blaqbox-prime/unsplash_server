package com.blaqboxdev.unsplash.Services;

import com.blaqboxdev.unsplash.Models.Entities.Collection;
import com.blaqboxdev.unsplash.Models.Requests.CollectionRequest;

import java.util.List;

public interface CollectionsService {
    Collection createCollection(CollectionRequest collection);

    Collection updateCollection(Collection collection);

    Collection updateCollection(CollectionRequest collection, String id);

    Collection getCollectionById(String id);

    List<Collection> getCollectionsByAuthor(String username);

    void deleteCollection(String id);

    void deleteCollection(Collection collection);

    List<Collection> getAllCollections();

    Collection addImageToCollection(String collectionId, String imgId);
}
