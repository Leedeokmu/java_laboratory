package com.freeefly;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class ImageController {
    private final ImageService imageService;

    private static final String BASE_PATH = "/images";
    private static final String FILENAME = "{filename:.+}";

    @GetMapping(value = BASE_PATH + "/view/" + FILENAME, produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    Mono<ResponseEntity<?>> oneImage(@PathVariable String filename) {
        return imageService.getOneImage(filename)
                .map(resource -> {
                    try {
                        return ResponseEntity.ok()
                                .contentLength(resource.contentLength())
                                .body(new InputStreamResource(
                                        resource.getInputStream()));
                    } catch (IOException e) {
                        return ResponseEntity.badRequest()
                                .body("Couldn't find " + filename +
                                        " => " + e.getMessage());
                    }
                });
    }

    @PostMapping(value = BASE_PATH)
    public Mono<String> createFile(@RequestPart(name = "file") Flux<FilePart> files) {
        return imageService.uploadImage(files)
                .then(Mono.just("redirect:/"));
    }

    @DeleteMapping(BASE_PATH + "/delete/" + FILENAME)
    public Mono<String> deleteFile(@PathVariable String filename) {
        return imageService.deleteImage(filename)
                .then(Mono.just("redirect:/"));
    }

    @GetMapping("/")
    public Mono<String> index(Model model){
        model.addAttribute("images", imageService.allImages());
        return Mono.just("index");
    }

}
