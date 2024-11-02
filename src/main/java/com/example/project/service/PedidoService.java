package com.example.project.service;

import com.example.project.repository.*;
import com.example.project.table.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    PlatilloRepository platillorepo;

    @Autowired
    IngredientesPlatilloRepository ipr;

    @Autowired
    IngredienteRepository ingredienteRepository;

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

    @Transactional
    public Pedido guardar(@RequestBody Pedido pedido) {
        try {
            // Verificar si el usuario existe y obtener su información
            Usuario usuario = ur.findById(pedido.getUsuarioIdusuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el ID: " + pedido.getUsuarioIdusuario()));

            System.out.println("Usuario encontrado: " + usuario.getNombre());
            System.out.println("Procesando pedido para el usuario ID: " + pedido.getUsuarioIdusuario());

            int precioTotal = 0;

            // Verificación de stock antes de reducir
            for (PedidoPlatillo pedidoPlatillo : pedido.getPedidoPlatilloList()) {
                Integer platilloId = pedidoPlatillo.getPlatilloId();
                if (platilloId == null) {
                    throw new RuntimeException("El ID del platillo no puede ser nulo");
                }

                // Verificar que el platillo existe
                Platillo platillo = platillorepo.findById(platilloId)
                        .orElseThrow(() -> new RuntimeException("Platillo no encontrado con el ID: " + platilloId));

                System.out.println("Platillo encontrado: " + platillo.getDescripcion());

                // Verificación de stock de cada ingrediente
                for (IngredientesPlatillo ingredientePlatillo : platillo.getIngredientesList()) {
                    Ingrediente ingrediente = ingredientePlatillo.getIngrediente();
                    int cantidadNecesaria = ingredientePlatillo.getCantidadIngredientes() * pedidoPlatillo.getCantidad();

                    System.out.println("Ingrediente: " + ingrediente.getNombre() + ", Stock Actual: " + ingrediente.getStock() + ", Cantidad Necesaria: " + cantidadNecesaria);

                    // Verifica si hay suficiente stock
                    if (ingrediente.getStock() < cantidadNecesaria) {
                        throw new RuntimeException("Stock insuficiente para el ingrediente: " + ingrediente.getNombre());
                    }
                }
            }

            // Si la verificación de stock fue exitosa, ahora procedemos a reducir el stock
            for (PedidoPlatillo pedidoPlatillo : pedido.getPedidoPlatilloList()) {
                Platillo platillo = platillorepo.findById(pedidoPlatillo.getPlatilloId())
                        .orElseThrow(() -> new RuntimeException("Platillo no encontrado con el ID: " + pedidoPlatillo.getPlatilloId()));

                // Reducción de stock de cada ingrediente
                for (IngredientesPlatillo ingredientePlatillo : platillo.getIngredientesList()) {
                    Ingrediente ingrediente = ingredientePlatillo.getIngrediente();
                    int cantidadNecesaria = ingredientePlatillo.getCantidadIngredientes() * pedidoPlatillo.getCantidad();

                    System.out.println("Reduciendo stock de " + ingrediente.getNombre() + " en " + cantidadNecesaria);

                    // Actualizar el stock y guardar
                    ingrediente.setStock(ingrediente.getStock() - cantidadNecesaria);

                    // Forzamos la actualización en la base de datos
                    ingredienteRepository.saveAndFlush(ingrediente);
                    System.out.println("Stock de " + ingrediente.getNombre() + " después de la reducción y guardado: " + ingrediente.getStock());
                }
            }

            // Asignar el precio total y la fecha de creación al pedido
            pedido.setPrecioTotal(precioTotal);
            pedido.setFechaHoraPedido(new Date());

            // Guardar el pedido completo en la base de datos
            return pr.save(pedido);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar el pedido: " + e.getMessage());
        }
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

    @GetMapping("/usuario/{usuarioId}/pedidos")
    public List<Pedido> obtenerPedidosPorUsuario(@PathVariable Integer usuarioId) {
        return pr.findByUsuarioIdusuario(usuarioId);
    }


    @PutMapping(path = "/cancelar/{idpedido}")
    public Pedido cancelarPedido(@PathVariable int idpedido) {
        Pedido pedido = pr.findById(idpedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con el ID: " + idpedido));

        // Verificar que el pedido no esté entregado
        if ("Orden entregada".equals(pedido.getEstadoPedido())) {
            throw new RuntimeException("No se puede cancelar un pedido ya entregado.");
        }

        // Cambiar el estado a "Cancelado"
        pedido.setEstadoPedido("Cancelado");
        pedido.setFechaHoraActualizada(new Date());

        return pr.save(pedido); // Guardar los cambios en la base de datos
    }


    private void actualizarInventario(Pedido pedido) {
        for (PedidoPlatillo pedidoPlatillo : pedido.getPedidoPlatilloList()) {
            Platillo platillo = platillorepo.findById(pedidoPlatillo.getPlatilloId())
                    .orElseThrow(() -> new RuntimeException("Platillo no encontrado con el ID: " + pedidoPlatillo.getPlatilloId()));

            for (IngredientesPlatillo ingredientePlatillo : platillo.getIngredientesList()) {
                Ingrediente ingrediente = ingredientePlatillo.getIngrediente();
                int cantidadNecesaria = ingredientePlatillo.getCantidadIngredientes() * pedidoPlatillo.getCantidad();

                // Verificar si hay suficiente stock
                if (ingrediente.getStock() < cantidadNecesaria) {
                    throw new RuntimeException("Stock insuficiente para el ingrediente: " + ingrediente.getNombre());
                }

                // Actualizar el stock
                ingrediente.setStock(ingrediente.getStock() - cantidadNecesaria);
                System.out.println("Stock de " + ingrediente.getNombre() + " después de guardar: " + ingrediente.getStock());
                ingredienteRepository.save(ingrediente);

            }
        }
    }
}