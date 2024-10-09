package com.example.project.repository;

import com.example.project.table.PedidoPlatillo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pedidoPlatilloRepository")
public interface PedidoPlatilloRepository extends JpaRepository<PedidoPlatillo, Integer> {
}
