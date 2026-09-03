package com.dulceesmeralda.service;

import com.dulceesmeralda.domain.Producto;
import com.dulceesmeralda.repository.ProductoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean soloDisponibles) {
        return soloDisponibles ? productoRepository.findByDisponibleTrue() : productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Producto> getDestacados() {
        return productoRepository.findByDestacadoTrueAndDisponibleTrue();
    }

    @Transactional(readOnly = true)
    public List<Producto> getPorCategoria(Integer idCategoria) {
        return productoRepository.findByCategoria_IdCategoriaAndDisponibleTrue(idCategoria);
    }

    @Transactional(readOnly = true)
    public List<Producto> buscar(String palabra) {
        return productoRepository.findByNombreContainingIgnoreCase(palabra);
    }

    @Transactional(readOnly = true)
    public Optional<Producto> getProducto(Integer idProducto) {
        return productoRepository.findById(idProducto);
    }

    @Transactional
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Transactional
    public void delete(Integer idProducto) {
        if (!productoRepository.existsById(idProducto)) {
            throw new IllegalArgumentException("El producto con ID " + idProducto + " no existe.");
        }
        try {
            productoRepository.deleteById(idProducto);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el producto. Tiene pedidos asociados.", e);
        }
    }
}
