package com.example.project.service;

import com.example.project.repository.PedidoPlatilloRepository;
import com.example.project.repository.PedidoRepository;
import com.example.project.repository.UsuarioRepository;
import com.example.project.table.Pedido;
import com.example.project.table.PedidoPlatillo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/pedido")
@CrossOrigin

public class PedidoService {

    @Autowired
    UsuarioRepository ur;

    @Autowired
    PedidoPlatilloRepository ppl;

    @Autowired
    PedidoRepository pr;

    @GetMapping(path="/buscar")
    public List<Pedido> buscar(){
        return pr.findAll();
    }

    @GetMapping(path ="/buscar/{idpedido}")
    public Pedido buscarPorId(@PathVariable int idpedido) {
        return pr.findById(idpedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con el ID: " + idpedido));
    }


    @GetMapping(path="/buscarPorEstadoYFecha/{estado}/{fecha}")
    public List<Pedido> findByEstadoAndFechaAfter(
            @PathVariable String estado,
            @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date fecha) {
        return pr.findByEstadoPedidoAndFechaHoraPedidoAfter(estado, fecha);
    }

    @GetMapping(path="/buscarPorUsuarioYEstado/{usuarioId}/{estado}")
    public List<Pedido> findByUsuarioIdAndEstado(
            @PathVariable Integer usuarioId,
            @PathVariable String estado) {
        return pr.findByUsuarioIdusuarioAndEstadoPedido(usuarioId, estado);
    }

    @PostMapping(path="/guardar")
    public Pedido guardar(@RequestBody Pedido pedido) {
        return pr.save(pedido);
    }

    @DeleteMapping(path="/eliminar/{idpedido}")
    public void eliminar(@PathVariable int idpedido) {
        pr.deleteById(idpedido);
    }

    @PutMapping(path = "/cambiarEstado/{idpedido}")
    public Pedido cambiarEstadoPedido(@PathVariable int idpedido, @RequestBody String nuevoEstado) {
        Pedido pedido = pr.findById(idpedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con el ID: " + idpedido));

        pedido.setEstadoPedido(nuevoEstado);
        pedido.setFechaHoraActualizada(new Date());

        if (nuevoEstado.equals("Orden entregada")){
            actualizarInventario(pedido);
        }
        return pr.save(pedido);
    }

    private void actualizarInventario(Pedido pedido){
        for (PedidoPlatillo pedidoPlatillo : pedido.getPedidoPlatilloList()){
            Integer platilloId = pedidoPlatillo.getPedidoIdpedido();
            Integer cantidad = pedidoPlatillo.getCantidad();
        }
    }

}
