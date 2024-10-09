package com.example.project.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.example.project.DTO.NewsDTO;
import com.example.project.repository.NewsRepository;
import com.example.project.table.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/news")
@CrossOrigin

public class NewsService {
    @Autowired
    NewsRepository nr;

    @GetMapping(path="/buscar")
    public List<NewsDTO> buscar(){
        List<News> news = nr.findAll();
        return news.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private NewsDTO convertToDTO(News news) {
        NewsDTO dto = new NewsDTO();
        dto.setIdnews(news.getIdnews());
        dto.setTitulo(news.getTitulo());
        dto.setDescripcion(news.getDescripcion());
        dto.setFechaPublicacion(news.getFechaPublicacion());
        if (news.getImagen() != null) {
            dto.setImagen(Base64.getEncoder().encodeToString(news.getImagen()));
        }
        return dto;
    }

    @GetMapping("/buscarOrdenadoPorFecha")
    public List<News> findAllOrderByFechaDesc() {
        return nr.findAllByOrderByFechaPublicacionDesc();
    }

    @PostMapping(path="/guardar", consumes = { "multipart/form-data" })
    public News guardar(@RequestParam("titulo") String titulo,
                        @RequestParam("descripcion") String descripcion,
                        @RequestParam("fechaPublicacion") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaPublicacion,
                        @RequestParam(value = "imagen", required = false) MultipartFile imagen) throws IOException {

        News news = new News();
        news.setTitulo(titulo);
        news.setDescripcion(descripcion);
        news.setFechaPublicacion(fechaPublicacion);

        if (imagen != null && !imagen.isEmpty()) {
            news.setImagen(imagen.getBytes());
            System.out.println("Imagen guardada, tamaño: " + imagen.getSize() + " bytes");
        } else {
            System.out.println("No se ha recibido ninguna imagen.");
        }

        return nr.save(news);
    }

    @DeleteMapping(path="/eliminar/{idNews}")
    public void eliminar(@PathVariable int idNews) {
        nr.deleteById(idNews);
    }

    @PutMapping(path = "/actualizar/{idNews}")
    public ResponseEntity<NewsDTO> actualizar(
            @PathVariable int idNews,
            @RequestParam("titulo") String titulo,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("fechaPublicacion") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaPublicacion,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) throws IOException {

        News existingNews = nr.findById(idNews).orElse(null);
        if (existingNews != null) {
            existingNews.setTitulo(titulo);
            existingNews.setDescripcion(descripcion);
            existingNews.setFechaPublicacion(fechaPublicacion);

            if (imagen != null && !imagen.isEmpty()) {
                existingNews.setImagen(imagen.getBytes());
            }

            News updatedNews = nr.save(existingNews);
            return ResponseEntity.ok(convertToDTO(updatedNews));
        }
        return ResponseEntity.notFound().build();
    }
}
