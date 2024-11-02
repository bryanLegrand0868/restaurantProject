package com.example.project.repository;


import com.example.project.table.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Repository("pedidoRepository")
public interface PedidoRepository extends JpaRepository<Pedido, Serializable> {
    List<Pedido> findByEstadoPedidoAndFechaHoraPedidoAfter(String estadoPedido, Date fechaHoraActualizada);
    List<Pedido> findByUsuarioIdusuarioAndEstadoPedido(Integer usuarioIdusuario, String estadoPedido);
    List<Pedido> findByUsuarioIdusuario(Integer usuarioId);
}
