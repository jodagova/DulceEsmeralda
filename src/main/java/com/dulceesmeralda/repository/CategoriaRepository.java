package com.dulceesmeralda.repository;

import com.dulceesmeralda.domain.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findByActivoTrue();

    boolean existsByDescripcionIgnoreCase(String descripcion);
}
