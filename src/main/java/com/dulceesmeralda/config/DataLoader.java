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
                // Datos de ejemplo (placeholder). Reemplazar por el catalogo real del cliente.
                Categoria postres = categoria(categoriaRepo, "Postres");
                Categoria pasteles = categoria(categoriaRepo, "Pasteles");
                Categoria panaderia = categoria(categoriaRepo, "Panaderia");
                Categoria temporada = categoria(categoriaRepo, "Temporada");

                productoRepo.saveAll(List.of(
                        producto("Cheesecake de fresa", "Porcion individual con salsa de fresa natural", "2800", postres, true),
                        producto("Brownie con nuez", "Brownie de chocolate semiamargo", "2200", postres, false),
                        producto("Tres leches", "Bizcocho humedo con crema y canela", "2500", postres, true),
                        producto("Pastel de chocolate", "Por encargo, minimo 8 porciones", null, pasteles, true),
                        producto("Pastel red velvet", "Relleno de queso crema, por encargo", null, pasteles, false),
                        producto("Cupcakes decorados", "Caja de 6, decoracion a eleccion", null, pasteles, false),
                        producto("Croissant de mantequilla", "Hojaldre horneado del dia", "1500", panaderia, false),
                        producto("Pan de banano", "Con nueces, unidad o molde", "1600", panaderia, false),
                        producto("Rosca navidena", "Disponible en diciembre, por encargo", null, temporada, true)
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
        p.setPrecio(precio == null ? null : new BigDecimal(precio));
        p.setBajoPedido(precio == null);
        p.setCategoria(categoria);
        p.setDisponible(true);
        p.setDestacado(destacado);
        return p;
    }
}
