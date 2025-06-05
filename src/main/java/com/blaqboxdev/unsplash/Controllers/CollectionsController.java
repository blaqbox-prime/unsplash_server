package com.blaqboxdev.unsplash.Controllers;


import com.blaqboxdev.unsplash.Models.Entities.Collection;
import com.blaqboxdev.unsplash.Models.Requests.CollectionRequest;
import com.blaqboxdev.unsplash.Services.CollectionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/collections")
public class CollectionsController {

    @Autowired
    final private CollectionsService collectionsService;

    @GetMapping()
    public ResponseEntity<?> getAllCollections(){
        List<Collection> collections = collectionsService.getAllCollections();
        return ResponseEntity.ok(collections);
    }

    @PostMapping()
    public ResponseEntity<?> createCollection(@RequestBody CollectionRequest collectionRequest){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(collectionsService.createCollection(collectionRequest));
        } catch (RuntimeException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{collectionId}/add")
    public ResponseEntity<?> addImageToCollection(@PathVariable String collectionId, @RequestParam String imgId){
        try {
            return ResponseEntity.ok().body(collectionsService.addImageToCollection(collectionId, imgId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCollection(@PathVariable String id, CollectionRequest collection){
        Collection newCollection = Collection.builder()._id(id).author(collection.author()).title(collection.title()).build();
        try {
            return ResponseEntity.accepted().body(collectionsService.updateCollection(newCollection));
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
