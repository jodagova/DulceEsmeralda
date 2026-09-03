package com.dulceesmeralda.service;

import com.dulceesmeralda.domain.Rol;
import com.dulceesmeralda.domain.Usuario;
import com.dulceesmeralda.repository.RolRepository;
import com.dulceesmeralda.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<Usuario> getUsuarios(boolean soloActivos) {
        return soloActivos ? usuarioRepository.findByActivoTrue() : usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> getUsuario(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    /**
     * Registra un nuevo cliente con rol CLIENTE y la contrasena encriptada.
     */
    @Transactional
    public Usuario registrar(Usuario usuario) {
        if (usuarioRepository.existsByUsernameOrCorreo(usuario.getUsername(), usuario.getCorreo())) {
            throw new DataIntegrityViolationException("El usuario o correo ya esta registrado.");
        }
        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contrasena es obligatoria.");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setActivo(true);

        Rol cliente = rolRepository.findByRol("CLIENTE")
                .orElseThrow(() -> new IllegalStateException("Falta el rol CLIENTE."));
        usuario.getRoles().add(cliente);

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void cambiarPassword(String username, String nuevaPassword) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        usuarioRepository.save(usuario);
    }
}
