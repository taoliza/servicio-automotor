import React, { useState, useEffect } from "react";
import axios from "axios";
import ClienteForm from "./ClienteForm"; // Importa el formulario de registro de clientes
import Modal from "./Modal"; // Importa el componente Modal
import "./ClienteList.css"; // Importa los estilos (si los tienes)

const ClienteList = () => {
  const [clientes, setClientes] = useState([]);
  const [modalVisible, setModalVisible] = useState(false); // Estado para controlar el modal

  // Obtener la lista de clientes al cargar el componente
  useEffect(() => {
    const fetchClientes = async () => {
      try {
        const response = await axios.get("http://localhost:8080/clientes");
        setClientes(response.data);
      } catch (error) {
        console.error("Error obteniendo clientes:", error);
      }
    };

    fetchClientes();
  }, []);

  // Función para manejar el registro de un nuevo cliente
  const handleClienteRegistrado = (nuevoCliente) => {
    setClientes([...clientes, nuevoCliente]); // Agrega el nuevo cliente a la lista
    setModalVisible(false); // Cierra el modal
  };

  return (
    <div className="clientes-container">
      <h2>Lista de Clientes</h2>
      <button onClick={() => setModalVisible(true)}>Registrar Cliente</button>
      <ul className="clientes-list">
        {clientes.map((cliente) => (
          <li key={cliente.id} className="cliente-card">
            <div className="cliente-header">
              <h3>{cliente.nombre}</h3>
              {cliente.contadorServicios > 5 && ( // Mostrar "Premium" si contadorServicios > 5
                <span className="premium-tag">Premium</span>
              )}
            </div>
            <div className="cliente-body">
              <p><strong>Email:</strong> {cliente.email}</p>
              <p><strong>Teléfono:</strong> {cliente.numeroTelefono}</p>
              <p><strong>Servicios realizados:</strong> {cliente.contadorServicios}</p>
            </div>
          </li>
        ))}
      </ul>

      {/* Modal para registrar cliente */}
      {modalVisible && (
        <Modal onClose={() => setModalVisible(false)}>
          <ClienteForm
            onClienteRegistrado={handleClienteRegistrado}
            onCancel={() => setModalVisible(false)} // Pasa la función para cerrar el modal
          />
        </Modal>
      )}
    </div>
  );
};

export default ClienteList;