package com.dulceesmeralda.repository;

import com.dulceesmeralda.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByDisponibleTrue();

    List<Producto> findByDestacadoTrueAndDisponibleTrue();

    List<Producto> findByCategoria_IdCategoriaAndDisponibleTrue(Integer idCategoria);

    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
