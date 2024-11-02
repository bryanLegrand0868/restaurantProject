package com.example.project.table;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {
    private static final long serialVersionUID = 5L;

    @Id
    @Column(name="idpedido")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional=false)
    private Integer idpedido;

    @Column(name="Cliente")
    private String cliente;

    @Column(name="Fecha_hora_pedido")
    private Date fechaHoraPedido;

    @Column(name="Precio_total")
    private Integer precioTotal;

    @Column(name="Estado_pedido")
    private String estadoPedido;

    @Column(name="Fecha_hora_actualizada")
    private Date fechaHoraActualizada;

    @Column(name="usuarioIdusuario")
    private Integer usuarioIdusuario;

    @JsonManagedReference
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PedidoPlatillo> pedidoPlatilloList = new ArrayList<>();


    public List<PedidoPlatillo> getPedidoPlatilloList() {
        return pedidoPlatilloList;
    }

    public void setPedidoPlatilloList(List<PedidoPlatillo> pedidoPlatilloList) {
        this.pedidoPlatilloList = pedidoPlatilloList;
    }

    public Date getFechaHoraActualizada() {
        return fechaHoraActualizada;
    }

    public void setFechaHoraActualizada(Date fechaHoraActualizada) {
        this.fechaHoraActualizada = fechaHoraActualizada;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public Integer getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Integer precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Date getFechaHoraPedido() {
        return fechaHoraPedido;
    }

    public void setFechaHoraPedido(Date fechaHoraPedido) {
        this.fechaHoraPedido = fechaHoraPedido;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Integer getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(Integer idpedido) {
        this.idpedido = idpedido;
    }

    public Integer getUsuarioIdusuario() {
        return usuarioIdusuario;
    }

    public void setUsuarioIdusuario(Integer usuarioIdusuario) {
        this.usuarioIdusuario = usuarioIdusuario;
    }
}

