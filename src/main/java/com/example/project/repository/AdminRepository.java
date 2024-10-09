package com.example.project.repository;

import com.example.project.table.Admin;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository("adminRepository")

public interface AdminRepository extends JpaRepository<Admin, Integer>{
    Optional<Admin> findByUsuarioAndContrasenia(String usuario, String contrasenia);
}
