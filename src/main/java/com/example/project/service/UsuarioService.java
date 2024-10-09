package com.example.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.repository.UsuarioRepository;
import com.example.project.table.Usuario;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/usuario")
@CrossOrigin

public class UsuarioService {
	
	@Autowired
	UsuarioRepository ur;
	
	@GetMapping(path="/buscar")
	public List<Usuario> buscar(){
		return ur.findAll();
	}
	
	@PostMapping(path="/guardar")
	public Usuario guardar(@RequestBody Usuario usuario) {
		return ur.save(usuario);
	}
	
	@DeleteMapping(path="/eliminar/{idusuario}")
	public void eliminar(@PathVariable int idusuario) {
		ur.deleteById(idusuario);
	}
	
	
}