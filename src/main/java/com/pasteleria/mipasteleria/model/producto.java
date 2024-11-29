package com.pasteleria.mipasteleria.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String tipo;
    private boolean disponibilidad;
    private String tamano;

    private BigDecimal precioBase;

    // Constructores
    public producto() {}

    public producto(String nombre, String tipo, boolean disponibilidad, String tamano, BigDecimal precioBase) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.disponibilidad = disponibilidad;
        this.tamano = tamano;
        this.precioBase = precioBase;
    }

    // Métodos
    public BigDecimal calcularPrecioFinal() {
        // Lógica de precios por ejemplo
        BigDecimal ajuste = tamano.equalsIgnoreCase("Grande") ? BigDecimal.valueOf(1.2) : BigDecimal.ONE;
        return precioBase.multiply(ajuste);
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public BigDecimal getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(BigDecimal precioBase) {
        this.precioBase = precioBase;
    }
}