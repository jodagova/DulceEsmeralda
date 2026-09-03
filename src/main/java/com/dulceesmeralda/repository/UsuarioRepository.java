package com.dulceesmeralda.repository;

import com.dulceesmeralda.domain.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUsernameAndActivoTrue(String username);

    Optional<Usuario> findByUsername(String username);

    List<Usuario> findByActivoTrue();

    boolean existsByUsernameOrCorreo(String username, String correo);
}
