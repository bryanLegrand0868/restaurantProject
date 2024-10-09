package com.example.project.table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "ingredientes_platillo")
public class IngredientesPlatillo implements Serializable {
    private static final long serialVersionUID = 8L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idingredientesPlatillo")
    private Integer idingredientesPlatillo;

    @Column(name = "cantidad_ingredientes")
    private Integer cantidadIngredientes;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "platilloIdplatillo", referencedColumnName = "idplatillo")
    private Platillo platillo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ingredientesIdingredientes", referencedColumnName = "idingredientes", nullable = false)
    private Ingrediente ingrediente;

    public Integer getIdingredientesPlatillo() {
        return idingredientesPlatillo;
    }

    public void setIdingredientesPlatillo(Integer idingredientesPlatillo) {
        this.idingredientesPlatillo = idingredientesPlatillo;
    }

    public Integer getCantidadIngredientes() {
        return cantidadIngredientes;
    }

    public void setCantidadIngredientes(Integer cantidadIngredientes) {
        this.cantidadIngredientes = cantidadIngredientes;
    }

    public Platillo getPlatillo() {
        return platillo;
    }

    public void setPlatillo(Platillo platillo) {
        this.platillo = platillo;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }
}

