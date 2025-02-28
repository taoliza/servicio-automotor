package com.example.tests;

import com.example.model.Cliente;
import com.example.repository.ClienteRepository;
import com.example.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    public void testRegistrarCliente() {
        Cliente cliente = new Cliente("cliente@example.com", "Juan Perez");
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = clienteService.registrarCliente("cliente@example.com", "Juan Perez");

        assertNotNull(resultado);
        assertEquals("cliente@example.com", resultado.getEmail());
        assertEquals("Juan Perez", resultado.getNombre());
    }

    @Test
    public void testIncrementarContadorServicios() {
        Cliente cliente = new Cliente("cliente@example.com", "Juan Perez");
        when(clienteRepository.findByEmail("cliente@example.com")).thenReturn(Optional.of(cliente));

        clienteService.incrementarContadorServicios("cliente@example.com");

        assertEquals(1, cliente.getContadorServicios());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    public void testObtenerClientePorEmail_ClienteExiste() {
        Cliente cliente = new Cliente("cliente@example.com", "Juan Perez");
        when(clienteRepository.findByEmail("cliente@example.com")).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = clienteService.obtenerClientePorEmail("cliente@example.com");

        assertTrue(resultado.isPresent());
        assertEquals("cliente@example.com", resultado.get().getEmail());
        assertEquals("Juan Perez", resultado.get().getNombre());
    }

    @Test
    public void testObtenerClientePorEmail_ClienteNoExiste() {
        when(clienteRepository.findByEmail("cliente@example.com")).thenReturn(Optional.empty());

        Optional<Cliente> resultado = clienteService.obtenerClientePorEmail("cliente@example.com");

        assertTrue(resultado.isEmpty());
    }
}