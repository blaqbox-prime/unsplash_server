package com.blaqboxdev.unsplash.Controllers;

import com.blaqboxdev.unsplash.Models.Entities.Image;
import com.blaqboxdev.unsplash.Repositories.PhotoRepositoryCustom;
import com.blaqboxdev.unsplash.Repositories.SearchRepo;
import com.blaqboxdev.unsplash.Services.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Tag(name = "Images API")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/images")
public class ImagesController {

    @Autowired
    private final ImageService imageService;

    @Autowired
    private final PhotoRepositoryCustom customImageRepository;
    @Autowired
    private final SearchRepo searchImageRepository;

    @GetMapping()
    public ResponseEntity<?> allPhotos(){
        return ResponseEntity.ok().body(imageService.getAllImages());
    }

    @GetMapping("/latest")
    public List<Image> allPhotosDesc(){
        return customImageRepository.findAllSorted();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getImage(@PathVariable String id){
        log.info("Trying to get: %s".formatted(id));
        try {
            return ResponseEntity.ok().body(imageService.getImageById(id));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @SneakyThrows
    @PostMapping(value = "/", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addPhoto(@RequestPart("details") Image details,
                         @RequestPart("userId") String userId,
                         @RequestPart("image") MultipartFile image){

        Image savedImage = imageService.addPhoto(details, userId, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedImage);

    }

    @GetMapping("/search")
    public List<Image> search(@RequestParam String query){
        System.out.println(query);
        return searchImageRepository.findByText(Arrays.stream(query.split(" ")).toList());
    }

    @Operation(summary = "Get a list of images uploaded by a specific user", description = "Returns a list of images as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "403", description = "Bad Request - The username was not found or is invalid")
    })
    @GetMapping("/profile/{username}")
    public ResponseEntity<?> getImagesByUsername(@PathVariable @NotBlank String username){
        try {
            return ResponseEntity.ok(imageService.getImagesByUsername(username));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}