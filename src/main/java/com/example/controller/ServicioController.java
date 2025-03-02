package com.example.controller;

import com.example.model.Servicio;
import com.example.repository.ServicioRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/servicios")
@CrossOrigin(origins = "http://localhost:3000")
public class ServicioController {

    private final ServicioRepository servicioRepository;
    public ServicioController(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    @GetMapping
    public ResponseEntity<List<Servicio>> obtenerServicios() {
        List<Servicio> servicios = servicioRepository.findAll();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        return ResponseEntity.ok().headers(headers).body(servicios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> obtenerServicioPorId(@PathVariable Long id) {
        Optional<Servicio> servicio = servicioRepository.findById(id);
        return servicio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicio> actualizarServicio(@PathVariable Long id, @RequestBody Servicio servicioActualizado) {
        Optional<Servicio> servicioOptional = servicioRepository.findById(id);

        if (servicioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Servicio servicio = servicioOptional.get();
        servicio.setPrecio(servicioActualizado.getPrecio()); // Actualizar el precio

        servicioRepository.save(servicio);
        return ResponseEntity.ok(servicio);
    }

}