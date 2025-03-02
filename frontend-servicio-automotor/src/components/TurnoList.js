import React, { useState, useEffect } from "react";
import axios from "axios";
import { formatearFecha, formatearHora } from "../util";
import TurnoForm from "./TurnoForm"; // Importa el formulario de agendar turno
import Modal from "./Modal"; // Importa el componente Modal
import "./TurnoList.css"; // Importamos el archivo CSS para estilos

const TurnosList = ({ apiEndpoint, titulo, limite }) => {
  const [turnos, setTurnos] = useState([]);
  const [modalVisible, setModalVisible] = useState(false); // Para el modal de detalles del turno
  const [modalData, setModalData] = useState(null); // Datos del turno para el modal de detalles
  const [modalAgendarVisible, setModalAgendarVisible] = useState(false); // Para el modal de agendar turno

  useEffect(() => {
    const cargarTurnos = async () => {
      try {
        const response = await axios.get(apiEndpoint); // Usamos la URL que se pasa como prop
        const turnosData = response.data;

        const turnosConDetalles = await Promise.all(
          turnosData.map(async (turno) => {
            try {
              // Petición para obtener el cliente por email
              const clienteResponse = await axios.get(
                `http://localhost:8080/clientes?email=${turno.emailCliente}`
              );

              // Petición para obtener el nombre del servicio por ID
              const servicioResponse = await axios.get(
                `http://localhost:8080/servicios/${turno.tipoServicio}`
              );

              return {
                ...turno,
                nombreCliente: clienteResponse.data[0].nombre || "Desconocido",
                nombreServicio: servicioResponse.data.nombre || "Sin especificar",
              };
            } catch (error) {
              console.error("Error obteniendo datos:", error);
              return {
                ...turno,
                nombreCliente: "Desconocido",
                nombreServicio: "Sin especificar",
              };
            }
          })
        );

        setTurnos(limite ? turnosConDetalles.slice(0, limite) : turnosConDetalles);
      } catch (error) {
        console.error("Error cargando turnos:", error);
      }
    };

    cargarTurnos();
  }, [apiEndpoint, limite]);

  const abrirModalDetalles = (turno) => {
    setModalData(turno);
    setModalVisible(true);
  };

  const cerrarModalDetalles = () => {
    setModalVisible(false);
    setModalData(null);
  };

  const manejarClicFueraModal = (e) => {
    if (e.target === e.currentTarget) {
      cerrarModalDetalles();
    }
  };

  const handleTurnoRegistrado = (nuevoTurno) => {
    setTurnos([nuevoTurno, ...turnos]); // Agrega el nuevo turno al principio de la lista
    setModalAgendarVisible(false); // Cierra el modal de agendar turno
  };

  return (
    <div className="turnos-container">
  <h2>{titulo}</h2>
  <div className="header-container">
    <button onClick={() => setModalAgendarVisible(true)} className="agendar-btn">Agendar Turno</button>
  </div>
      <div className="turnos-list">
        {turnos.map((turno) => (
          <div key={turno.id} className="turno-card" onClick={() => abrirModalDetalles(turno)}>
            <div className="turno-header">
              <h3>{turno.nombreServicio}</h3>
            </div>
            <div className="turno-body">
              <p><strong>Fecha:</strong> {formatearFecha(turno.fechaHora)}</p>
              <p><strong>Hora:</strong> {formatearHora(turno.fechaHora)}</p>
              <p><strong>Cliente:</strong> {turno.nombreCliente}</p>
              <p><strong>Patente:</strong> {turno.patenteVehiculo}</p>
              <p><strong>Precio:</strong> ${modalData?.precio}</p>
            </div>
          </div>
        ))}
      </div>

      {/* Modal para detalles del turno */}
      {modalVisible && (
        <div className="modal-overlay" onClick={manejarClicFueraModal}>
          <div className="modal-content">
            <button className="close-button" onClick={cerrarModalDetalles}>X</button>
            <h3>{modalData.nombreServicio}</h3>
            <p><strong>Fecha:</strong> {formatearFecha(modalData.fechaHora)}</p>
            <p><strong>Hora:</strong> {formatearHora(modalData.fechaHora)}</p>
            <p><strong>Cliente:</strong> {modalData.nombreCliente}</p>
            <p><strong>Patente:</strong> {modalData.patenteVehiculo}</p>
            <p><strong>Precio:</strong> ${modalData.precio}</p>
          </div>
        </div>
      )}

      {/* Modal para agendar turno */}
      {modalAgendarVisible && (
        <Modal onClose={() => setModalAgendarVisible(false)}>
          <TurnoForm onTurnoRegistrado={handleTurnoRegistrado} />
        </Modal>
      )}
    </div>
  );
};

export default TurnosList;