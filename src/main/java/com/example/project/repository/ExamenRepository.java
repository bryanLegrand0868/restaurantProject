package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.project.table.examen;

import java.util.List;


@Repository("examenRepository")
public interface ExamenRepository extends JpaRepository<examen, Integer> {
    List<examen> findAllByCarnetAndNoParcial(String carnet, Integer noParcial);
}
