package com.dulceesmeralda.config;

import com.dulceesmeralda.domain.Categoria;
import com.dulceesmeralda.domain.Producto;
import com.dulceesmeralda.domain.Rol;
import com.dulceesmeralda.domain.Usuario;
import com.dulceesmeralda.repository.CategoriaRepository;
import com.dulceesmeralda.repository.ProductoRepository;
import com.dulceesmeralda.repository.RolRepository;
import com.dulceesmeralda.repository.UsuarioRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Carga datos iniciales (roles, usuario administrador y un menu de ejemplo) la
 * primera vez que arranca la aplicacion. Util para la demo al cliente.
 *
 * Las credenciales del administrador se toman de las propiedades:
 *   app.admin.username  (por defecto: admin)
 *   app.admin.password  (por defecto: cambiar123)
 */
@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner seed(RolRepository rolRepo,
            UsuarioRepository usuarioRepo,
            CategoriaRepository categoriaRepo,
            ProductoRepository productoRepo,
            PasswordEncoder passwordEncoder,
            org.springframework.core.env.Environment env) {
        return args -> {

            Rol admin = rolRepo.findByRol("ADMIN").orElseGet(() -> nuevoRol(rolRepo, "ADMIN"));
            rolRepo.findByRol("CLIENTE").orElseGet(() -> nuevoRol(rolRepo, "CLIENTE"));

            String adminUser = env.getProperty("app.admin.username", "admin");
            if (usuarioRepo.findByUsername(adminUser).isEmpty()) {
                Usuario u = new Usuario();
                u.setUsername(adminUser);
                u.setPassword(passwordEncoder.encode(env.getProperty("app.admin.password", "cambiar123")));
                u.setNombre("Administrador");
                u.setApellidos("Dulce Esmeralda");
                u.setCorreo("admin@dulceesmeralda.local");
                u.setActivo(true);
                u.setRoles(Set.of(admin));
                usuarioRepo.save(u);
            }

            if (categoriaRepo.count() == 0) {
                Categoria cafe = categoria(categoriaRepo, "Cafe");
                Categoria postres = categoria(categoriaRepo, "Postres");
                Categoria panaderia = categoria(categoriaRepo, "Panaderia");
                Categoria bebidasFrias = categoria(categoriaRepo, "Bebidas frias");

                productoRepo.saveAll(List.of(
                        producto("Espresso", "Shot doble de cafe de origen", "1200", cafe, true),
                        producto("Capuchino", "Espresso con leche vaporizada y espuma", "1900", cafe, true),
                        producto("Latte de vainilla", "Cafe con leche y jarabe de vainilla", "2100", cafe, false),
                        producto("Cheesecake de fresa", "Porcion con salsa de fresa natural", "2800", postres, true),
                        producto("Brownie con nuez", "Brownie tibio de chocolate", "2200", postres, false),
                        producto("Croissant de mantequilla", "Horneado del dia", "1500", panaderia, false),
                        producto("Pan de banano", "Rebanada con nueces", "1600", panaderia, false),
                        producto("Frappe de moka", "Bebida fria batida con hielo", "2600", bebidasFrias, true),
                        producto("Limonada de hierbabuena", "Limonada natural de la casa", "1800", bebidasFrias, false)
                ));
            }
        };
    }

    private static Rol nuevoRol(RolRepository repo, String nombre) {
        Rol r = new Rol();
        r.setRol(nombre);
        return repo.save(r);
    }

    private static Categoria categoria(CategoriaRepository repo, String descripcion) {
        Categoria c = new Categoria();
        c.setDescripcion(descripcion);
        c.setActivo(true);
        return repo.save(c);
    }

    private static Producto producto(String nombre, String descripcion, String precio,
            Categoria categoria, boolean destacado) {
        Producto p = new Producto();
        p.setNombre(nombre);
        p.setDescripcion(descripcion);
        p.setPrecio(new BigDecimal(precio));
        p.setCategoria(categoria);
        p.setDisponible(true);
        p.setDestacado(destacado);
        return p;
    }
}
