package com.pasteleria.mipasteleria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface clienteRepository extends JpaRepository<com.pasteleria.mipasteleria.model.cliente, Long> {
    // Puedes agregar consultas personalizadas aquí, si es necesario
}
