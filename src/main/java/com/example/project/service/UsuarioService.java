package com.example.project.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.project.repository.UsuarioRepository;
import com.example.project.table.Usuario;

@RestController
@RequestMapping("/usuario")
@CrossOrigin
public class UsuarioService {

	@Autowired
	UsuarioRepository ur;

	@GetMapping(path="/buscar")
	public List<Usuario> buscar() {
		return ur.findAll();
	}

	@GetMapping(path="/perfil/{correo}")
	public ResponseEntity<Usuario> obtenerPerfil(@PathVariable String correo) {
		Usuario usuario = ur.findByCorreoElectronico(correo);
		if (usuario != null) {
			return ResponseEntity.ok(usuario);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@PostMapping(path="/guardar")
	public Usuario guardar(@RequestBody Usuario usuario) {
		return ur.save(usuario);
	}

	@DeleteMapping(path="/eliminar/{idusuario}")
	public void eliminar(@PathVariable int idusuario) {
		ur.deleteById(idusuario);
	}

	@PostMapping(path="/login")
	public ResponseEntity<?> login(@RequestBody Usuario usuario) {
		if (usuario.getCorreoElectronico() == null || usuario.getContrasenia() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faltan datos de inicio de sesión");
		}

		Usuario usuarioExistente = ur.findByCorreoElectronico(usuario.getCorreoElectronico());
		if (usuarioExistente != null && usuarioExistente.getContrasenia().equals(usuario.getContrasenia())) {
			Map<String, Object> response = new HashMap<>();
			response.put("idusuario", usuarioExistente.getIdusuario());
			response.put("nombre", usuarioExistente.getNombre());
			response.put("apellido", usuarioExistente.getApellido());
			response.put("token", "some-generated-token");
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo electrónico o contraseña incorrectos");
		}
	}

	@PostMapping(path="/registrar")
	public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
		if (ur.findByCorreoElectronico(usuario.getCorreoElectronico()) != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo electrónico ya está registrado.");
		}
		usuario.setFechaRegistro(new Date());
		Usuario nuevoUsuario = ur.save(usuario);

		// Devuelve el ID del nuevo usuario en la respuesta junto con su nombre y apellido
		Map<String, Object> response = new HashMap<>();
		response.put("idusuario", nuevoUsuario.getIdusuario());
		response.put("nombre", nuevoUsuario.getNombre());
		response.put("apellido", nuevoUsuario.getApellido());
		response.put("token", "some-generated-token"); // Puedes incluir un token si tienes autenticación basada en tokens
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
