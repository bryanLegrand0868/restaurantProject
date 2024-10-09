package com.example.project.table;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "platillo")
public class Platillo implements Serializable {
    private static final long serialVersionUID = 7L;

    @Id
    @Column(name="idplatillo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional=false)
    private Integer idplatillo;

    @Column(name="Foto")
    @Lob
    private String foto;

    @Column(name="Precio")
    private Integer precio;

    @Column(name="Descripcion")
    private String descripcion;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "platillo",
            orphanRemoval = true)
    @JsonIgnoreProperties("platillo")
    private List<IngredientesPlatillo> ingredientesList = new ArrayList<>();

    public Integer getIdplatillo() {
        return idplatillo;
    }

    public void setIdplatillo(Integer idplatillo) {
        this.idplatillo = idplatillo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void addIngrediente(IngredientesPlatillo ingrediente) {
        ingredientesList.add(ingrediente);
        ingrediente.setPlatillo(this);
    }

    public void removeIngrediente(IngredientesPlatillo ingrediente) {
        ingredientesList.remove(ingrediente);
        ingrediente.setPlatillo(null);
    }

    @JsonIgnore
    public List<IngredientesPlatillo> getIngredientesList() {
        return ingredientesList;
    }

    @JsonProperty
    public void setIngredientesList(List<IngredientesPlatillo> ingredientesList) {
        this.ingredientesList.clear();
        if (ingredientesList != null) {
            for (IngredientesPlatillo ingrediente : ingredientesList) {
                addIngrediente(ingrediente);
            }
        }
    }
}
