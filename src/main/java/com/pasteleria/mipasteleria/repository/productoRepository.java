package com.pasteleria.mipasteleria.repository;

import com.pasteleria.mipasteleria.model.producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface productoRepository extends JpaRepository<producto, Long> {
    // Puedes agregar consultas personalizadas aquí, si es necesario
}