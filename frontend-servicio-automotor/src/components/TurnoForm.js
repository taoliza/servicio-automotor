import React, { useState, useEffect } from "react";
import axios from "axios";
import { formatearFecha, formatearHora } from "../util";

const TurnoForm = ({ onTurnoRegistrado }) => {
  const [fechasDisponibles, setFechasDisponibles] = useState([]);
  const [fechaSeleccionada, setFechaSeleccionada] = useState("");
  const [horariosDisponibles, setHorariosDisponibles] = useState([]);
  const [horarioSeleccionado, setHorarioSeleccionado] = useState("");
  const [servicios, setServicios] = useState([]);
  const [servicioSeleccionado, setServicioSeleccionado] = useState("");
  const [patente, setPatente] = useState("");
  const [emailCliente, setEmailCliente] = useState("");
  const [errors, setErrors] = useState([]);

  useEffect(() => {
    const cargarFechasDisponibles = async () => {
      try {
        const response = await axios.get("http://localhost:8080/turnos/fechas-disponibles");
        setFechasDisponibles(response.data);
      } catch (error) {
        console.error("Error cargando fechas:", error);
      }
    };
    cargarFechasDisponibles();
  }, []);

  useEffect(() => {
    if (fechaSeleccionada) {
      axios
        .get(`http://localhost:8080/turnos/horarios-disponibles?fecha=${fechaSeleccionada}`)
        .then((response) => setHorariosDisponibles(response.data))
        .catch((error) => console.error("Error cargando horarios:", error));
    }
  }, [fechaSeleccionada]);

  useEffect(() => {
    axios
      .get("http://localhost:8080/servicios")
      .then((response) => setServicios(response.data))
      .catch((error) => console.error("Error cargando servicios:", error));
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const fechaHora = `${fechaSeleccionada}T${horarioSeleccionado}`;
      const response = await axios.post("http://localhost:8080/turnos", null, {
        params: {
          fechaHora: fechaHora,
          tipoServicio: servicioSeleccionado,
          patenteVehiculo: patente,
          emailCliente: emailCliente,
        },
      });

      alert("Turno registrado exitosamente!");
      onTurnoRegistrado(response.data); // Cierra el modal y actualiza la lista de turnos
    } catch (error) {
      if (error.response && error.response.status === 400) {
        setErrors(error.response.data.errors);
      } else {
        alert("Ocurrió un error inesperado. Inténtalo de nuevo.");
      }
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>Fecha:</label>
        <select
          value={fechaSeleccionada}
          onChange={(e) => {
            setFechaSeleccionada(e.target.value);
            setErrors([]); // Limpiar errores al cambiar el valor
          }}
          required
        >
          <option value="">Seleccione una fecha</option>
          {fechasDisponibles.map((fecha) => (
            <option key={fecha} value={fecha}>
              {fecha} {}
            </option>
          ))}
        </select>
        {errors
          .filter((error) => error.toLowerCase().includes("fecha")) // Filtrar errores relacionados con la fecha
          .map((error, index) => (
            <p key={index} className="error-message">
              {error} {/* Mostrar cada error en una línea separada */}
            </p>
          ))}
      </div>

      {fechaSeleccionada && (
        <div>
          <label>Horario:</label>
          <select
            value={horarioSeleccionado}
            onChange={(e) => {
              setHorarioSeleccionado(e.target.value);
              setErrors([]); // Limpiar errores al cambiar el valor
            }}
            required
          >
            <option value="">Seleccione un horario</option>
            {horariosDisponibles.map((horario) => (
              <option key={horario} value={horario}>
                {horario} {}
              </option>
            ))}
          </select>
          {errors
            .filter((error) => error.toLowerCase().includes("horario")) // Filtrar errores relacionados con el horario
            .map((error, index) => (
              <p key={index} className="error-message">
                {error}
              </p> // Mostrar cada error en una línea separada
            ))}
        </div>
      )}

      <div>
        <label>Servicio:</label>
        <select
          value={servicioSeleccionado}
          onChange={(e) => {
            setServicioSeleccionado(e.target.value);
            setErrors([]); // Limpiar errores al cambiar el valor
          }}
          required
        >
          <option value="">Seleccione un servicio</option>
          {servicios.map((servicio) => (
            <option key={servicio.id} value={servicio.id}>
              {servicio.nombre}
            </option>
          ))}
        </select>
        {errors
          .filter((error) => error.toLowerCase().includes("servicio")) // Filtrar errores relacionados con el servicio
          .map((error, index) => (
            <p key={index} className="error-message">
              {error}
            </p> // Mostrar cada error en una línea separada
          ))}
      </div>

      <div>
        <label>Patente del auto:</label>
        <input
          type="text"
          value={patente}
          onChange={(e) => {
            setPatente(e.target.value);
            setErrors([]); // Limpiar errores al cambiar el valor
          }}
          required
        />
        {errors
          .filter((error) => error.toLowerCase().includes("patente")) // Filtrar errores relacionados con la patente
          .map((error, index) => (
            <p key={index} className="error-message">
              {error}
            </p> // Mostrar cada error en una línea separada
          ))}
      </div>

      <div>
        <label>Email del cliente:</label>
        <input
          type="email"
          value={emailCliente}
          onChange={(e) => {
            setEmailCliente(e.target.value);
            setErrors([]); // Limpiar errores al cambiar el valor
          }}
          required
        />
        {errors
          .filter((error) => error.toLowerCase().includes("email")) // Filtrar errores relacionados con el email
          .map((error, index) => (
            <p key={index} className="error-message">
              {error}
            </p> // Mostrar cada error en una línea separada
          ))}
      </div>

      <button type="submit">Registrar Turno</button>
    </form>
  );
};

export default TurnoForm;
