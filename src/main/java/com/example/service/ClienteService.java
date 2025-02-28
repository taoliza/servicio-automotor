package com.example.service;

import com.example.model.Cliente;
import com.example.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente registrarCliente(String email, String nombre) {
        Cliente cliente = new Cliente(email, nombre);
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> obtenerClientePorEmail(String email) {
        return clienteRepository.findByEmail(email); // Busca un cliente por email
    }

    public boolean incrementarContadorServicios(String email) {
        Optional<Cliente> posibleCliente = obtenerClientePorEmail(email);
        if (posibleCliente.isEmpty())
            return false;
        Cliente cliente = posibleCliente.get();
        cliente.incrementarContadorServicios();
        clienteRepository.save(cliente);
        return true;
    }
}
