package com.example.project.repository;


import com.example.project.table.IngredientesPlatillo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ingredientesPlatilloRepository")
public interface IngredientesPlatilloRepository extends JpaRepository<IngredientesPlatillo, Integer> {
}
