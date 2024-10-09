package com.example.project.table;


import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "pedido_platillos")
public class PedidoPlatillo implements Serializable {
    private static final long serialVersionUID = 6L;

    @Id
    @Column(name="idpedido_platillos")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional=false)
    private Integer idpedidoPlatillo;

    @Column(name="cantidad")
    private Integer cantidad;

    @Column(name="Subtotal")
    private Integer subtotal;

    @Column(name="Pedido_idpedido")
    private Integer pedidoIdpedido;

    @Column(name="platillo_Idplatillo")
    private Integer platilloIdplatillo;

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

    public Integer getPedidoIdpedido() {
        return pedidoIdpedido;
    }

    public void setPedidoIdpedido(Integer pedidoIdpedido) {
        this.pedidoIdpedido = pedidoIdpedido;
    }

    public Integer getplatilloIdplatillo() {
        return platilloIdplatillo;
    }

    public void setplatilloIdplatillo(Integer platilloIdplatillo) {
        this.platilloIdplatillo = platilloIdplatillo;
    }
}
