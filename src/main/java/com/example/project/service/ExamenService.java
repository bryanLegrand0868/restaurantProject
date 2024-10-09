package com.example.project.service;


import com.example.project.repository.ExamenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.project.table.examen;

import java.util.List;

@RestController
@RequestMapping("/examen")
@CrossOrigin
public class ExamenService {
    @Autowired
    ExamenRepository er;

    @GetMapping(path = "/buscar")
    public List<examen> buscar(){return er.findAll();}

    @GetMapping(path = "/buscar/{carnet}/{noParcial}")
    public List<examen> findAllByCarnetAndNoParcial(@PathVariable String carnet, @PathVariable Integer noParcial) {
        return er.findAllByCarnetAndNoParcial(carnet, noParcial);
    }

    @PostMapping(path = "/guardar")
    public examen guardar(@RequestBody examen exam){
        return er.save(exam);
    }

    @DeleteMapping(path = "/eliminar/{idexamen}")
    public void eliminar(@PathVariable int idexamen){er.deleteById(idexamen);}
}
