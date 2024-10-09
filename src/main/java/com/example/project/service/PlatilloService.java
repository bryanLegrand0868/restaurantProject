package com.example.project.service;


import com.example.project.repository.IngredientesPlatilloRepository;
import com.example.project.repository.PedidoPlatilloRepository;
import com.example.project.repository.PlatilloRepository;
import com.example.project.table.IngredientesPlatillo;
import com.example.project.table.Platillo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/platillo")
@CrossOrigin
public class PlatilloService {

    @Autowired
    PedidoPlatilloRepository pedidoPlatilloRepo2;

    @Autowired
    IngredientesPlatilloRepository ipr;

    @Autowired
    PlatilloRepository platillorepo;

    @GetMapping(path="/buscar")
    public List<Platillo> buscar(){
        return platillorepo.findAll();
    }

    @GetMapping(path="/buscar/{idplatillo}")
    public Platillo buscarPorId(@PathVariable int idplatillo) {
        return platillorepo.findById(idplatillo)
                .orElseThrow(() -> new RuntimeException("Platillo no encontrado con el ID: " + idplatillo));
    }

    @GetMapping(path = "/buscarPorNombre/{nombre}")
    public List<Platillo> buscarPorNombre(@PathVariable String nombre) {
        return platillorepo.findByDescripcionContaining(nombre);
    }

    @GetMapping("/buscarPorPrecio")
    public List<Platillo> buscarPorRangoDePrecios(@RequestParam Integer precioMin, @RequestParam Integer precioMax) {
        return platillorepo.findAllByPrecioBetween(precioMin, precioMax);
    }

    @Transactional
    @PostMapping(path="/guardar")
    public Platillo guardar(@RequestBody Platillo platillo) {
        List<IngredientesPlatillo> ingredientes = platillo.getIngredientesList();
        platillo.setIngredientesList(new ArrayList<>()); // Clear the list to avoid duplication

        Platillo savedPlatillo = platillorepo.save(platillo);

        if (ingredientes != null) {
            for (IngredientesPlatillo ingrediente : ingredientes) {
                if (ingrediente.getIngrediente() != null && ingrediente.getIngrediente().getIdIngredientes() != null) {
                    // Verificar si el ingrediente tiene un ID válido
                    if (ipr.findById(ingrediente.getIngrediente().getIdIngredientes()).isEmpty()) {
                        throw new RuntimeException("Ingrediente con ID " + ingrediente.getIngrediente().getIdIngredientes() + " no existe.");
                    }
                } else {
                    throw new RuntimeException("El ingrediente no tiene un ID válido.");
                }
                ingrediente.setPlatillo(savedPlatillo);
                ipr.save(ingrediente);
            }
        }

        return savedPlatillo;
    }


    @DeleteMapping(path="/eliminar/{idplatillo}")
    public void eliminar(@PathVariable int idplatillo) {
        platillorepo.deleteById(idplatillo);
    }

    @Transactional
    @PutMapping(path = "/actualizar/{idplatillo}")
    public Platillo actualizar(@PathVariable int idplatillo, @RequestBody Platillo platillo) {
        Platillo platilloExistente = platillorepo.findById(idplatillo)
                .orElseThrow(() -> new RuntimeException("Platillo no encontrado con el ID: " + idplatillo));

        if (platillo.getDescripcion() != null) {
            platilloExistente.setDescripcion(platillo.getDescripcion());
        }

        if (platillo.getFoto() != null) {
            platilloExistente.setFoto(platillo.getFoto());
        }

        if (platillo.getPrecio() != null) {
            platilloExistente.setPrecio(platillo.getPrecio());
        }

        // Validamos y actualizamos los ingredientes
        if (platillo.getIngredientesList() != null && !platillo.getIngredientesList().isEmpty()) {
            platilloExistente.getIngredientesList().clear();  // Limpiamos la lista existente
            for (IngredientesPlatillo ingrediente : platillo.getIngredientesList()) {
                if (ingrediente.getIngrediente() != null && ingrediente.getIngrediente().getIdIngredientes() != null) {
                    ingrediente.setPlatillo(platilloExistente);  // Relacionamos con el platillo existente
                    platilloExistente.addIngrediente(ingrediente);
                } else {
                    throw new RuntimeException("El ingrediente no tiene un ID válido.");
                }
            }
        }

        return platillorepo.save(platilloExistente);  // Guardamos los cambios
    }

}
