package com.blaqboxdev.unsplash.Controllers;


import com.blaqboxdev.unsplash.Exceptions.CollectionNotFoundException;
import com.blaqboxdev.unsplash.Exceptions.ImageNotFoundException;
import com.blaqboxdev.unsplash.Models.Entities.Collection;
import com.blaqboxdev.unsplash.Models.Requests.CollectionRequest;
import com.blaqboxdev.unsplash.Services.CollectionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/collections")
public class CollectionsController {

    @Autowired
    final private CollectionsService collectionsService;

    @GetMapping()
    public ResponseEntity<?> getAllCollections(){
        List<Collection> collections = collectionsService.getAllCollections();
        return ResponseEntity.ok(collections);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCollectionById(@PathVariable String id){
        try {
            return ResponseEntity.ok(collectionsService.getCollectionById(id));
        } catch (CollectionNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/including/{id}")
    public ResponseEntity<?> getAllCollectionsWithImageId(@PathVariable String id) {
        try {
            return ResponseEntity.ok(collectionsService.getCollectionsByImageId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createCollection(@RequestBody CollectionRequest collectionRequest){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(collectionsService.createCollection(collectionRequest));
        } catch (RuntimeException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{collectionId}/images/{imgId}")
    public ResponseEntity<?> addImageToCollection(@PathVariable String collectionId, @PathVariable String imgId){
        try {
            return ResponseEntity.ok().body(collectionsService.addImageToCollection(collectionId, imgId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCollection(@PathVariable String id, CollectionRequest collection){
        Collection newCollection = Collection.builder()
                ._id(id)
                .author(collection.author()).title(collection.title()).build();
        try {
            return ResponseEntity.accepted().body(collectionsService.updateCollection(newCollection));
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCollection(@PathVariable String id){
        try{
            collectionsService.deleteCollection(id);
            return ResponseEntity.ok().build();
        } catch (CollectionNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/images/{imageId}")
    public ResponseEntity<?> removeImageFromCollection(@PathVariable String id, @PathVariable String imageId){
        try {

            return ResponseEntity.ok(collectionsService.removeImageFromCollection(id, imageId));
        } catch (CollectionNotFoundException | ImageNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
