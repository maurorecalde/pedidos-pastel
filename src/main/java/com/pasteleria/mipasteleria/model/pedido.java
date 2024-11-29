package com.pasteleria.mipasteleria.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fechaPedido;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private cliente cliente;

    @ManyToMany
    @JoinTable(
        name = "pedido_producto",
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<producto> productoLista = new ArrayList<>();

    private BigDecimal montoTotal;

    // Constructores
    public pedido() {}

    public pedido(String fechaPedido, String estado, cliente cliente) {
        this.fechaPedido = fechaPedido;
        this.estado = estado;
        this.cliente = cliente;
    }

    // Métodos
    public void agregarProducto(producto producto) {
        this.productoLista.add(producto);
    }

    public void removerProducto(producto producto) {
        this.productoLista.remove(producto);
    }

    public void calcularMontoTotal() {
        this.montoTotal = this.productoLista.stream()
            .map(producto::calcularPrecioFinal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public List<producto> getProductoLista() {
        return productoLista;
    }

    public void setProductoLista(List<producto> productoLista) {
        this.productoLista = productoLista;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }
}