package com.example.project.controller;

import com.example.project.service.PlatilloService;
import com.example.project.table.Platillo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/platillo")
public class PlatilloController {

    @Autowired
    private PlatilloService platilloService;

    // Endpoint para crear un nuevo platillo con sus ingredientes
    @PostMapping("/crear")
    public ResponseEntity<Platillo> crearPlatillo(@RequestBody Platillo platillo) {
        Platillo nuevoPlatillo = platilloService.guardar(platillo);
        return ResponseEntity.ok(nuevoPlatillo);
    }

}


