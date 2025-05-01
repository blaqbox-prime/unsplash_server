package com.blaqboxdev.unsplash.Controllers;

import com.blaqboxdev.unsplash.Models.Image;
import com.blaqboxdev.unsplash.Repositories.PhotoRepo;
import com.blaqboxdev.unsplash.Repositories.PhotoRepositoryCustom;
import com.blaqboxdev.unsplash.Repositories.SearchRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@CrossOrigin(origins = "http://localhost:3000/", maxAge = 36000)
@RestController
@RequestMapping("/api")
public class PhotoController {

    @Autowired
    PhotoRepo repo;
    @Autowired
    PhotoRepositoryCustom pcrepo;

    @Autowired
    SearchRepo srepo;
    @GetMapping("/photos")
    public List<Image> allPhotos(){
        return repo.findAll();
    }

    @GetMapping("/photos-sorted")
    public List<Image> allPhotosDesc(){
        return pcrepo.findAllSorted();
    }

    //@CrossOrigin
    @PostMapping("/add-photo")
    public Image addPhoto(@RequestBody Image image){
        return repo.save(image);
    }

    @GetMapping("/photos/{text}")
    public List<Image> search(@PathVariable String text){
        return srepo.findByText(text);
    }

}