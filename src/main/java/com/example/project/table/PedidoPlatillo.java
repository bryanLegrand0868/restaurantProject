package com.example.project.table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "pedido_platillos")
public class PedidoPlatillo implements Serializable {
    private static final long serialVersionUID = 6L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idpedido_platillos")
    private Integer idpedidoPlatillo;

    @Column(name="cantidad")
    private Integer cantidad;

    @Column(name="Subtotal")
    private Integer subtotal;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pedido_idpedido", referencedColumnName = "idpedido")
    private Pedido pedido;

    @Column(name = "Platillo_Idplatillo")
    private Integer platilloId;

    // Getters y setters

    public Integer getIdpedidoPlatillo() {
        return idpedidoPlatillo;
    }

    public void setIdpedidoPlatillo(Integer idpedidoPlatillo) {
        this.idpedidoPlatillo = idpedidoPlatillo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Integer getPlatilloId() {
        return platilloId;
    }

    public void setPlatilloId(Integer platilloId) {
        this.platilloId = platilloId;
    }
}

