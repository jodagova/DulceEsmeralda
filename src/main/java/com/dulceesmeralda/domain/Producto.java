package com.dulceesmeralda.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * Producto del menu de la cafeteria (bebida, postre, reposteria, etc.).
 */
@Data
@Entity
@Table(name = "producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @NotBlank
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que cero")
    @Column(precision = 12, scale = 2)
    private BigDecimal precio;

    @Column(length = 1024)
    private String rutaImagen;

    /** Disponible para venta hoy. */
    private boolean disponible = true;

    /** Aparece destacado en la portada. */
    private boolean destacado = false;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
}
