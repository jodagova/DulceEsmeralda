package com.dulceesmeralda.repository;

import com.dulceesmeralda.domain.Rol;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Integer> {

    Optional<Rol> findByRol(String rol);
}
