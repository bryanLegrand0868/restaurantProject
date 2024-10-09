package com.example.project.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.table.News;

import java.util.List;

@Repository("newsRepository")
public interface NewsRepository extends JpaRepository<News, Integer>{
    List<News> findAllByOrderByFechaPublicacionDesc();
}
