package com.blaqboxdev.unsplash.Controllers;

import com.blaqboxdev.unsplash.Models.Photo;
import com.blaqboxdev.unsplash.Repositories.PhotoRepo;
import com.blaqboxdev.unsplash.Repositories.PhotoRepositoryCustom;
import com.blaqboxdev.unsplash.Repositories.SearchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/", maxAge = 36000)
@RestController
public class PhotoController {

    @Autowired
    PhotoRepo repo;
    @Autowired
    PhotoRepositoryCustom pcrepo;

    @Autowired
    SearchRepo srepo;
    @GetMapping("/photos")
    public List<Photo> allPhotos(){
        return repo.findAll();
    }

    @GetMapping("/photos-sorted")
    public List<Photo> allPhotosDesc(){
        return pcrepo.findAllSorted();
    }

    //@CrossOrigin
    @PostMapping("/add-photo")
    public Photo addPhoto(@RequestBody Photo photo){
        return repo.save(photo);
    }

    @GetMapping("/photos/{text}")
    public List<Photo> search(@PathVariable String text){
        return srepo.findByText(text);
    }

}