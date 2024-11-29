package com.pasteleria.mipasteleria.repository;


import com.pasteleria.mipasteleria.model.pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface pedidoRepository extends JpaRepository<pedido, Long> {
    // Puedes agregar consultas personalizadas aquí, si es necesario
}