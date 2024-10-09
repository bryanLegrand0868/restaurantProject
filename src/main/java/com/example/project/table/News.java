package com.example.project.table;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;


@Entity
@Table(name = "news")
public class News implements Serializable{
    private static final long serialVersionUID = 3L;

    @Id
    @Column(name="idnews")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional=false)
    private Integer idnews;

    @Column(name="titulo")
    private String titulo;

    @Column(name="descripcion")
    private String descripcion;

    @Lob
    @Column(name="imagen", columnDefinition = "LONGBLOB")
    private byte[] imagen;

    @Column(name="Fecha_publicacion")
    private Date fechaPublicacion;

    public Integer getIdnews() {
        return idnews;
    }

    public void setIdnews(Integer idnews) {
        this.idnews = idnews;
    }


    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
