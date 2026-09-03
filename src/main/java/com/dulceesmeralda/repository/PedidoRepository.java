package com.dulceesmeralda.repository;

import com.dulceesmeralda.domain.Pedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByUsuario_IdUsuarioOrderByFechaDesc(Integer idUsuario);

    List<Pedido> findByEstadoOrderByFechaAsc(String estado);
}
