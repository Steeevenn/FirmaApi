package com.firma.firma.controller;


import com.firma.firma.entity.Firma;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.firma.firma.repository.FirmaRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class firmaCanvas {
    private final FirmaRepository firmaRepository;

    private final Path rootLocation = Paths.get("firmas");  // Directorio donde se guardarán las firmas

    @Autowired
    public firmaCanvas(FirmaRepository firmaRepository) {
        this.firmaRepository = firmaRepository;
    }

    @PostMapping("/firmas")
    public  ResponseEntity<?> guardarFirma(@RequestBody FirmaRequest firmaRequest) {
        try {
            byte[] image = Base64.getDecoder().decode(firmaRequest.image);
            String fileName = "firma_ " + firmaRequest.getCliente_id() + ".png";
            Path path = rootLocation.resolve(fileName);

        if (!Files.exists(rootLocation)){
            try{
            Files.createDirectories(rootLocation);
            System.out.println("DIRECTORIO CREADO");}
            catch (IOException e){
                return new ResponseEntity<>("Error al crear el directorio" , HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

        Files.write(path,image);
            System.out.println("DRIRECTORIO CREADP");
        String url = "/firmas/"+ fileName;
        Firma firma = new Firma();
        firma.setCliente_id(firmaRequest.getCliente_id());
        firma.setImage(url);

        firmaRepository.save(firma);

            return ResponseEntity.ok(Map.of("message", "firma guardada", "fileUrl",url));
        }catch (IOException e) {
            System.out.println("Error al escribir el archivo: " + e.getMessage());
            return new ResponseEntity<>("Error al guardar la firma", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            System.out.println("Error al decodificar la imagen: " + e.getMessage());
            return new ResponseEntity<>("Error al decodificar la imagen", HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/firmas/{id}")
    public ResponseEntity<?> getFirma(Long id){
        Optional<Firma> firmaOptional = firmaRepository.findById(id);

        if (firmaOptional.isPresent()){
           Firma firma = firmaOptional.get();
           return ResponseEntity.ok(Map.of("firma", firma.getImage()));

        }else{
            return new ResponseEntity<>("Firma no encontraad", HttpStatus.NOT_FOUND);
        }
    }

        @Data
    public static class FirmaRequest {
        String image;
        int cliente_id;
    }
}
