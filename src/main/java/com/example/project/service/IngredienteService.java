package com.example.project.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.project.DTO.IngredienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.project.repository.IngredienteRepository;
import com.example.project.table.Ingrediente;

@RestController
@RequestMapping("/ingredientes")
@CrossOrigin

public class IngredienteService {
	@Autowired
	IngredienteRepository ir;
	
	@GetMapping(path="/buscar")
	public List<Ingrediente> buscar(){
		return ir.findAll();
	}

	@GetMapping(path = "/buscar/{idingredientes}")
	public Ingrediente buscarPorId(@PathVariable int idingredientes){
		return ir.findById(idingredientes)
				.orElseThrow(() -> new RuntimeException("Ingrediente no encontrado con el ID: " + idingredientes));
	}

	@GetMapping(path="/buscarIngredientes/{nombre}")
	public List<IngredienteDTO> buscarPorNombre(@PathVariable String nombre) {
		List<Ingrediente> ingredientes = ir.findByNombreContaining(nombre);
		return ingredientes.stream()
				.map(ing -> new IngredienteDTO(ing.getNombre(), ing.getStock()))
				.collect(Collectors.toList());
	}

	@PostMapping(path="/guardar")
	public Ingrediente guardar(@RequestBody Ingrediente ingredientes) {
		return ir.save(ingredientes);
	}
	
	@DeleteMapping(path="/eliminar/{idingredientes}")
	public void eliminar(@PathVariable int idingredientes) {
		ir.deleteById(idingredientes);
	}

	@PutMapping(path="/actualizarStock/{id}/{cantidad}")
	public Ingrediente updateStock(
			@PathVariable int id,
			@PathVariable int cantidad) {
		Ingrediente ingrediente = ir.findById(id)
				.orElseThrow(() -> new RuntimeException("Ingrediente no encontrado con el ID: " + id));

		int nuevoStock = ingrediente.getStock() + cantidad;
		if (nuevoStock < 0) {
			throw new RuntimeException("El stock no puede ser negativo. Operación cancelada.");
		}

		ingrediente.setStock(nuevoStock);
		return ir.save(ingrediente);
	}
}
