package com.pasteleria.mipasteleria.controller;

import com.pasteleria.mipasteleria.model.producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class productoController {

    @Autowired
    private com.pasteleria.mipasteleria.repository.productoRepository productoRepository;

    @GetMapping
    public List<producto> listarProductos() {
        return productoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<producto> obtenerProductoPorId(@PathVariable Long id) {
        Optional<producto> producto = productoRepository.findById(id);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<producto> crearProducto(@RequestBody producto producto) {
        // Validación básica para 'tamaño'
        if (producto.getTamano() == null || producto.getTamano().isEmpty()) {
            return ResponseEntity.badRequest().body(null); // Error 400 si 'tamaño' está vacío
        }

        // Log para depuración
        System.out.println("Producto recibido: " + producto);

        // Guardar el producto en la base de datos
        producto nuevoProducto = productoRepository.save(producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<producto> actualizarProducto(@PathVariable Long id, @RequestBody producto productoDetalles) {
        return productoRepository.findById(id).map(producto -> {
            producto.setNombre(productoDetalles.getNombre());
            producto.setTipo(productoDetalles.getTipo());
            producto.setDisponibilidad(productoDetalles.isDisponibilidad());
            producto.setTamano(productoDetalles.getTamano());
            producto.setPrecioBase(productoDetalles.getPrecioBase());
            return ResponseEntity.ok(productoRepository.save(producto));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
