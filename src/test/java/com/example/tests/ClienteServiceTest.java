package com.example.tests;

import com.example.model.Cliente;
import com.example.repository.ClienteRepository;
import com.example.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    public void testRegistrarCliente() {
        Cliente cliente = new Cliente("cliente@example.com", "Juan Perez","3329610463");
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = clienteService.registrarCliente("cliente@example.com", "Juan Perez","1134228203");

        assertNotNull(resultado);
        assertEquals("cliente@example.com", resultado.getEmail());
        assertEquals("Juan Perez", resultado.getNombre());
    }

    @Test
    public void testIncrementarContadorServicios() {
        Cliente cliente = new Cliente("cliente@example.com", "Juan Perez","1149817602");
        when(clienteRepository.findByEmail("cliente@example.com")).thenReturn(Optional.of(cliente));

        clienteService.incrementarContadorServicios("cliente@example.com");

        assertEquals(1, cliente.getContadorServicios());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    public void testObtenerClientePorEmail_ClienteExiste() {
        Cliente cliente = new Cliente("cliente@example.com", "Juan Perez","3329480668");
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