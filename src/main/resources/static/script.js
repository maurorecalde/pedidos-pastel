const apiUrlProductos = "http://localhost:8080/api/productos"; // Cambia si usas otro puerto o endpoint
const apiUrlClientes = "http://localhost:8080/api/clientes";
const apiUrlPedidos = "http://localhost:8080/api/pedidos";

// Función para cargar productos
function cargarProductos() {
    fetch(apiUrlProductos)
        .then(response => response.json())
        .then(productos => {
            const productosDiv = document.getElementById("productos");
            productosDiv.innerHTML = "";

            productos.forEach(producto => {
                const productoHTML = `
                    <div class="col-md-4">
                        <div class="card h-100">
                            <div class="card-body">
                                <h5 class="card-title">${producto.nombre}</h5>
                                <p class="card-text">Tipo: ${producto.tipo}</p>
                                <p class="card-text">Tamaño: ${producto.tamano}</p>
                                <p class="card-text">Precio: $${producto.precioBase}</p>
                                <p class="card-text">Disponible: ${producto.disponibilidad ? "Sí" : "No"}</p>
                            </div>
                        </div>
                    </div>`;
                productosDiv.innerHTML += productoHTML;
            });
        });
}

// Función para cargar clientes
function cargarClientes() {
    fetch(apiUrlClientes)
        .then(response => response.json())
        .then(clientes => {
            const clienteSelect = document.getElementById("clienteSelect");
            clienteSelect.innerHTML = '<option selected disabled>Selecciona un cliente</option>';
            clientes.forEach(cliente => {
                const option = document.createElement("option");
                option.value = cliente.id;
                option.textContent = `${cliente.nombre} (${cliente.email})`;
                clienteSelect.appendChild(option);
            });
        });
}

// Función para cargar productos en el formulario de pedidos
function cargarProductosParaPedido() {
    fetch(apiUrlProductos)
        .then(response => response.json())
        .then(productos => {
            const productoSelect = document.getElementById("productoSelect");
            productoSelect.innerHTML = '<option selected disabled>Selecciona un producto</option>';
            productos.forEach(producto => {
                const option = document.createElement("option");
                option.value = producto.id;
                option.textContent = producto.nombre;
                productoSelect.appendChild(option);
            });
        });
}

// Función para agregar un nuevo producto
document.getElementById("formAgregarProducto").addEventListener("submit", function(event) {
    event.preventDefault();
    const nuevoProducto = {
        nombre: document.getElementById("nombre").value,
        tipo: document.getElementById("tipo").value,
        tamano: document.getElementById("tamano").value,
        precioBase: parseFloat(document.getElementById("precio").value),
        disponibilidad: document.getElementById("disponibilidad").checked,
    };

    fetch(apiUrlProductos, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(nuevoProducto)
    })
    .then(response => response.ok ? alert("Producto agregado") : alert("Error al agregar producto"))
    .catch(error => console.error("Error:", error));
});

// Función para agregar un cliente
document.getElementById("formAgregarCliente").addEventListener("submit", function(event) {
    event.preventDefault();
    const nuevoCliente = {
        nombre: document.getElementById("nombreCliente").value,
        email: document.getElementById("emailCliente").value,
        telefono: document.getElementById("telefonoCliente").value,
        direccion: document.getElementById("direccionCliente").value,
    };

    fetch(apiUrlClientes, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(nuevoCliente)
    })
    .then(response => response.ok ? alert("Cliente agregado") : alert("Error al agregar cliente"))
    .catch(error => console.error("Error:", error));
});

// Función para agregar un pedido
document.getElementById("formAgregarPedido").addEventListener("submit", function(event) {
    event.preventDefault();

    const nuevoPedido = {
        clienteId: document.getElementById("clienteSelect").value,
        productoId: document.getElementById("productoSelect").value,
    };

    fetch(apiUrlPedidos, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(nuevoPedido)
    })
    .then(response => response.ok ? alert("Pedido agregado correctamente") : alert("Error al agregar el pedido"))
    .catch(error => console.error("Error al agregar pedido:", error));
});

// Cargar los datos iniciales cuando se carga la página
window.onload = function() {
    cargarProductos();
    cargarClientes();
    cargarProductosParaPedido();
};
