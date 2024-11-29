package com.pasteleria.mipasteleria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pasteleria.mipasteleria.model.pedido;
import com.pasteleria.mipasteleria.model.cliente;
import com.pasteleria.mipasteleria.repository.pedidoRepository;
import com.pasteleria.mipasteleria.repository.clienteRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
public class pedidoController {

    @Autowired
    private pedidoRepository pedidoRepository;

    @Autowired
    private clienteRepository clienteRepository;  // Se necesita para obtener el cliente

    @GetMapping
    public List<pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<pedido> obtenerPedidoPorId(@PathVariable Long id) {
        Optional<pedido> pedido = pedidoRepository.findById(id);
        return pedido.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<pedido> crearPedido(@RequestBody pedido pedido) {
        return ResponseEntity.ok(pedidoRepository.save(pedido));
    }

    @PutMapping("/{id}")
    public ResponseEntity<pedido> actualizarPedido(@PathVariable Long id, @RequestBody pedido pedidoDetalles) {
        return pedidoRepository.findById(id).map(pedido -> {
            pedido.setFechaPedido(pedidoDetalles.getFechaPedido());
            pedido.setEstado(pedidoDetalles.getEstado());
            pedido.setCliente(pedidoDetalles.getCliente());
            pedido.setProductoLista(pedidoDetalles.getProductoLista());
            pedido.calcularMontoTotal(); // Recalcula el monto total
            return ResponseEntity.ok(pedidoRepository.save(pedido));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Nuevo endpoint para obtener pedidos por cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<pedido>> obtenerPedidosPorCliente(@PathVariable Long clienteId) {
        Optional<cliente> clienteOptional = clienteRepository.findById(clienteId);
        
        if (clienteOptional.isPresent()) {
            cliente cliente = clienteOptional.get();
            List<pedido> pedidos = pedidoRepository.findAll().stream()
                    .filter(p -> p.getCliente().equals(cliente))
                    .toList();  // Filtramos los pedidos por cliente
            return ResponseEntity.ok(pedidos);
        }
        
        return ResponseEntity.notFound().build();
    }
}
