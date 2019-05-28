package com.freeefly;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final ResourceLoader resourceLoader;
    private final ImageRepository imageRepository;

    public static String UPLOAD_ROOT = "/upload-dir";

    @Bean
    CommandLineRunner setUp() throws IOException {
        return (args) -> {
            FileSystemUtils.deleteRecursively(new File(UPLOAD_ROOT));
            Files.createDirectory(Paths.get(UPLOAD_ROOT));
            FileCopyUtils.copy("Test file1", new FileWriter(UPLOAD_ROOT + "/test1.jpg"));
            FileCopyUtils.copy("Test file2", new FileWriter(UPLOAD_ROOT + "/test2.jpg"));
            FileCopyUtils.copy("Test file3", new FileWriter(UPLOAD_ROOT + "/test3.jpg"));
            FileCopyUtils.copy("Test file4", new FileWriter(UPLOAD_ROOT + "/test4.jpg"));
        };
    }

    public Flux<Image> allImages() {
//        try {
//            return Flux.fromIterable(
//                    Files.newDirectoryStream(Paths.get(UPLOAD_ROOT)))
//                    .map(path ->
//                            new Image(Integer.toString(path.hashCode()),
//                                    path.getFileName().toString()));
//        } catch (IOException e) {
//            return Flux.empty();
//        }
        return imageRepository.findAll();
    }
    public Mono<Resource> getOneImage(String filename) {
        return Mono.fromSupplier(() ->
                resourceLoader.getResource(
                        "file:" + UPLOAD_ROOT + "/" + filename));
    }
    public Mono<Void> uploadImage(Flux<FilePart> files){
        return files
            .log()
            .publishOn(Schedulers.parallel())
            .flatMap(file -> {

                Mono<Image> saveDatabaseImage = imageRepository.save(
                        new Image(UUID.randomUUID().toString(), file.filename()));

                Mono<Void> copyFile = file
                        .transferTo(Paths.get(UPLOAD_ROOT, file.filename()).toFile())
                        .then();

//                Mono<Void> copyFile = Mono.just(
//                        Paths.get(UPLOAD_ROOT, file.filename())
//                                .toFile())
//                        .log("Create image-picktarget")
//                        .map(destFile -> {
//                            try {
//                                destFile.createNewFile();
//                                return destFile;
//                            } catch (IOException e) {
//                                throw new RuntimeException(e);
//                            }
//                        })
//                        .log("Create image-newfile")
//                        .flatMap(file::transferTo)
////                        .flatMap(destFile -> file.transferTo(destFile))
//                        .log("Create image-copy");

                return Mono.when(saveDatabaseImage, copyFile);
            })
        .then();
    }

    public Mono<Void> deleteImage(String filename) {
        Mono<Void> deleteDatabaseImage = imageRepository.findByName(filename)
                .flatMap(imageRepository::delete);
        Mono<Void> deleteFile = Mono.fromRunnable(() -> {
            try {
                Files.deleteIfExists(Paths.get(UPLOAD_ROOT, filename));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return Mono.when(deleteDatabaseImage, deleteFile).then();
    }
}
