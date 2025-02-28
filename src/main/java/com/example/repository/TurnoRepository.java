package com.example.repository;

import com.example.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TurnoRepository extends JpaRepository<Turno, Long> {
    List<Turno> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin); // Busca turnos en un rango de fechas
    List<Turno> findByFechaHoraBefore(LocalDateTime fechaHora);
    List<Turno> findByFechaHoraAfter(LocalDateTime fechaHora);

    @Query("SELECT t FROM Turno t WHERE t.fechaHora <= :fechaHora ORDER BY t.fechaHora DESC")
    List<Turno> findTurnoActual(@Param("fechaHora") LocalDateTime fechaHora);
}