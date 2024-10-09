package com.example.project.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.table.Ingrediente;

import java.util.List;

@Repository("ingredienteRepository")

public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer>{
    List<Ingrediente> findByNombreContaining(String nombre);
}
