package com.example.controller;

import com.example.model.Cliente;
import com.example.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:3000")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Cliente> registrarCliente(
            @RequestParam String email,
            @RequestParam String nombre,
            @RequestParam String numeroTelefono) {
        Cliente cliente = clienteService.registrarCliente(email, nombre, numeroTelefono);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerClientes() {
        List<Cliente> clientes = clienteService.obtenerClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/email")
    public ResponseEntity<Cliente> obtenerClientePorEmail(@RequestParam String email) {
        Optional<Cliente> cliente = clienteService.obtenerClientePorEmail(email);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}