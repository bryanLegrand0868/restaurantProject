package com.example.project.table;

import java.io.Serializable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrador")

public class Admin implements Serializable{

    private static final long serialVersionUID = 4L;

    @Id
    @Column(name="idadministrador")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional=false)
    private Integer idAdministrador;

    @Column(name="Usuario")
    private String usuario;

    @Column(name="Contrasenia")
    private String contrasenia;

    public Integer getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(Integer idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
