package com.dulceesmeralda.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * Producto de reposteria: postre, pastel, temporada, etc.
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

    /**
     * Precio de referencia. Puede quedar vacio cuando el producto es solo por
     * pedido y se cotiza segun tamano o decoracion ({@link #bajoPedido}).
     */
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que cero")
    @Column(precision = 12, scale = 2)
    private BigDecimal precio;

    /** Se elabora por encargo; el precio final se cotiza por DM. */
    private boolean bajoPedido = true;

    @Column(length = 1024)
    private String rutaImagen;

    /** Visible en el catalogo. */
    private boolean disponible = true;

    /** Aparece destacado en la portada. */
    private boolean destacado = false;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
}
