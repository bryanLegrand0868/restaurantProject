package com.example.project.service;

import java.util.List;
import java.util.Optional;

import com.example.project.repository.AdminRepository;
import com.example.project.table.Admin;
import com.example.project.table.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminService {

    @Autowired
    AdminRepository ar;

    @GetMapping(path = "/buscar")
    public List<Admin> buscar(){
        return ar.findAll();
    }

    @GetMapping(path ="/buscar/{idAdministrador}")
    public Admin buscarPorId(@PathVariable int idAdministrador) {
        return ar.findById(idAdministrador)
                .orElseThrow(() -> new RuntimeException("admin no encontrado con el ID: " + idAdministrador));
    }

    @PostMapping(path = "/login")
    public boolean login(@RequestBody Admin admin){
        Optional<Admin> administrador = ar.findByUsuarioAndContrasenia(admin.getUsuario(), admin.getContrasenia());
        return administrador.isPresent();
    }

    @PostMapping(path="/guardar")
    public Admin guardar(@RequestBody Admin administrador) {
        return ar.save(administrador);
    }

    @DeleteMapping(path="/eliminar/{idAdministrador}")
    public void eliminar(@PathVariable int idAdministrador) {
        ar.deleteById(idAdministrador);
    }

    @PutMapping(path="/cambiar-contrasenia/{idAdministrador}")
    public ResponseEntity<?> cambiarContrasenia(@PathVariable int idAdministrador, @RequestBody String nuevaContrasenia) {
        Admin administrador = ar.findById(idAdministrador)
                .orElseThrow(() -> new RuntimeException("Admin no encontrado con el ID: " + idAdministrador));

        administrador.setContrasenia(nuevaContrasenia);
        ar.save(administrador);
        return ResponseEntity.ok(administrador);
    }

}
