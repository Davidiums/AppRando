package com.david.apprando.controller;

import com.david.apprando.dao.ImagesDao;
import com.david.apprando.model.Images;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin
public class ImageController {

    final
    ImagesDao imagesDao;

    public ImageController(ImagesDao imageDao) {
        this.imagesDao = imageDao;
    }

    //test de sauvegarde d'images
    @PostMapping("/image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.getSize() > 1000000){
            return ResponseEntity.badRequest().body("L'image est trop volumineuse !");
        }
        Images image = new Images();
        image.setNom(file.getOriginalFilename());
        if(file.getContentType().equals("image/jpeg")){
            image.setType("jpg");
        } else if(file.getContentType().equals("image/png")){
            image.setType("png");
        } else {
            return ResponseEntity.badRequest().body("Le format de l'image n'est pas supporté !");
        }

        Path directory = Paths.get("../imagesApprando/");
        Files.createDirectories(directory);
        String filePath = directory.toString()+"/" + file.getOriginalFilename();
        System.out.println(filePath);
        image.setChemin(filePath);
        byte[] bytes = file.getBytes();
        Path path = Paths.get(filePath);
        Files.write(path, bytes);
        imagesDao.save(image);
        return ResponseEntity.ok().body("Image enregistrée avec succès !");
    }

}
