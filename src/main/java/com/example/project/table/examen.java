package com.example.project.table;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "examen")
public class examen implements Serializable {
    private static final long serialVersionUID = 9L;

    @Id
    @Column(name = "idexamen")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idexamen;

    @Column(name = "no_parcial")
    private Integer noParcial;

    @Column(name = "fecha_examen")
    private Date fechaExamen;

    @Column(name = "nota")
    private Integer nota;

    @Column(name = "carnet")
    private String carnet;

    public Integer getIdexamen() {
        return idexamen;
    }

    public void setIdexamen(Integer idexamen) {
        this.idexamen = idexamen;
    }

    public Integer getNoParcial() {
        return noParcial;
    }

    public void setNoParcial(Integer noParcial) {
        this.noParcial = noParcial;
    }

    public Date getFechaExamen() {
        return fechaExamen;
    }

    public void setFechaExamen(Date fechaExamen) {
        this.fechaExamen = fechaExamen;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }
}
