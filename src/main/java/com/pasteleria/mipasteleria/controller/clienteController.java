package com.pasteleria.mipasteleria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pasteleria.mipasteleria.model.cliente;
import com.pasteleria.mipasteleria.model.pedido;
import com.pasteleria.mipasteleria.repository.clienteRepository;
import com.pasteleria.mipasteleria.repository.pedidoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class clienteController {

    @Autowired
    private clienteRepository clienteRepository;

    @Autowired
    private pedidoRepository pedidoRepository;

    // Obtener lista de clientes
    @GetMapping
    public List<cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    // Obtener cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<cliente> obtenerClientePorId(@PathVariable Long id) {
        Optional<cliente> cliente = clienteRepository.findById(id);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear nuevo cliente
    @PostMapping
    public cliente crearCliente(@RequestBody cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Actualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<cliente> actualizarCliente(@PathVariable Long id, @RequestBody cliente clienteDetalles) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNombre(clienteDetalles.getNombre());
            cliente.setTelefono(clienteDetalles.getTelefono());
            cliente.setDireccion(clienteDetalles.getDireccion());
            cliente.setEmail(clienteDetalles.getEmail());
            return ResponseEntity.ok(clienteRepository.save(cliente));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Agregar un pedido a un cliente
    @PostMapping("/{clienteId}/pedidos")
    public ResponseEntity<pedido> agregarPedidoACliente(@PathVariable Long clienteId, @RequestBody pedido pedido) {
        Optional<cliente> cliente = clienteRepository.findById(clienteId);
        if (cliente.isPresent()) {
            pedido.setCliente(cliente.get()); // Asociamos el pedido al cliente
            pedidoRepository.save(pedido); // Guardamos el pedido
            return ResponseEntity.ok(pedido);
        }
        return ResponseEntity.notFound().build();
    }

    // Obtener todos los pedidos de un cliente
    @GetMapping("/{clienteId}/pedidos")
    public ResponseEntity<List<pedido>> obtenerPedidosDeCliente(@PathVariable Long clienteId) {
        Optional<cliente> cliente = clienteRepository.findById(clienteId);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get().getPedidos());
        }
        return ResponseEntity.notFound().build();
    }

    // Actualizar pedido de un cliente
    @PutMapping("/{clienteId}/pedidos/{pedidoId}")
    public ResponseEntity<pedido> actualizarPedidoDeCliente(@PathVariable Long clienteId, @PathVariable Long pedidoId, @RequestBody pedido pedidoDetalles) {
        Optional<cliente> cliente = clienteRepository.findById(clienteId);
        if (cliente.isPresent()) {
            Optional<pedido> pedido = pedidoRepository.findById(pedidoId);
            if (pedido.isPresent() && pedido.get().getCliente().getId().equals(clienteId)) {
                pedido.get().setEstado(pedidoDetalles.getEstado());
                pedido.get().setProductoLista(pedidoDetalles.getProductoLista());
                pedido.get().calcularMontoTotal(); // Recalcular el monto total
                return ResponseEntity.ok(pedidoRepository.save(pedido.get()));
            }
        }
        return ResponseEntity.notFound().build();
    }
}
