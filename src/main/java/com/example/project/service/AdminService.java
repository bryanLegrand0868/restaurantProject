package com.example.project.service;

import java.util.List;
import java.util.Optional;

import com.example.project.repository.AdminRepository;
import com.example.project.table.Admin;
import com.example.project.table.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/admin")
@CrossOrigin

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

}
