package com.example.controller;

import com.example.model.Cliente;
import com.example.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Cliente> registrarCliente(
            @RequestParam String email,
            @RequestParam String nombre) {
        Cliente cliente = clienteService.registrarCliente(email, nombre);
        return ResponseEntity.ok(cliente);
    }


}