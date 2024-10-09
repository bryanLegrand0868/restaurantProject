package com.example.project.repository;

import com.example.project.table.Platillo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("platilloRepository")
public interface PlatilloRepository extends JpaRepository<Platillo, Integer> {
    List<Platillo> findByDescripcionContaining(String descripcion);
    List<Platillo> findAllByPrecioBetween(Integer precioMin, Integer precioMax);
}
