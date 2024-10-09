package com.example.project.DTO;

import java.util.Date;

public class NewsDTO {
    private Integer idnews;
    private String titulo;
    private String descripcion;
    private String imagen; // La imagen en formato Base64
    private Date fechaPublicacion;

    public NewsDTO() {}

    public NewsDTO(Integer idnews, String titulo, String descripcion, String imagen, Date fechaPublicacion) {
        this.idnews = idnews;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.fechaPublicacion = fechaPublicacion;
    }
    public Integer getIdnews() {
        return idnews;
    }

    public void setIdnews(Integer idnews) {
        this.idnews = idnews;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
}
