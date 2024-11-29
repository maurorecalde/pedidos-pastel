package com.pasteleria.mipasteleria.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String telefono;
    private String direccion;
    private String email;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<pedido> pedidos;

    // Constructores
    public cliente() {}

    public cliente(String nombre, String telefono, String direccion, String email) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
    }

    // Métodos
    public void pedirOrden(pedido pedido) {
        this.pedidos.add(pedido);
        pedido.setCliente(this);
    }

    public void cancelarOrden(pedido pedido) {
        this.pedidos.remove(pedido);
    }

    public String contactoInfo() {
        return "Nombre: " + this.nombre + ", Teléfono: " + this.telefono + ", Email: " + this.email;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
