package com.example.service;

import com.example.exception.CustomValidationException;
import com.example.model.Cliente;
import com.example.repository.ClienteRepository;
import com.example.util.validator.ValidadorEmail;
import com.example.util.validator.ValidadorNombre;
import com.example.exception.ErrorResponse;
import com.example.util.validator.ValidadorTelefono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente registrarCliente(String email, String nombre, String numeroTelefono) {
        ErrorResponse errorResponse = new ErrorResponse();

        // Validar email
        if (!ValidadorEmail.validar(email)) {
            errorResponse.addError("El email no es válido");
        }

        // Validar si el email ya existe
        if (obtenerClientePorEmail(email).isPresent()) {
            errorResponse.addError("Ya existe un cliente con ese email");
        }

        // Validar nombre
        String errorNombre = ValidadorNombre.validar(nombre);
        if (errorNombre != null) {
            errorResponse.addError(errorNombre);
        }
        // Validar teléfono
        if (!ValidadorTelefono.validar(numeroTelefono)) {
            errorResponse.addError("El número de teléfono no es válido");
        }

        // Si hay errores, lanzar una excepción personalizada con la lista de errores
        if (!errorResponse.getErrors().isEmpty()) {
            throw new CustomValidationException(HttpStatus.BAD_REQUEST, errorResponse.getErrors());
        }

        // Crear y guardar el cliente
        Cliente cliente = new Cliente(email, nombre, numeroTelefono);
        return clienteRepository.save(cliente);
    }


    //Acá tuve un problema asi que lo decidi filtrar manualmente
    public Optional<Cliente> obtenerClientePorEmail(String email) {
        // Obtener todos los clientes del repositorio
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .filter(cliente -> email.equals(cliente.getEmail()))
                .findFirst();
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

    public List<Cliente> obtenerClientes() {
        return clienteRepository.findAll();
    }
}
