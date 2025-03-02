import React, { useState, useEffect } from "react";
import axios from "axios";

const Configuracion = () => {
  const [servicios, setServicios] = useState([]);
  const [precioEditado, setPrecioEditado] = useState({});
  const [error, setError] = useState(null);

  useEffect(() => {
    axios
      .get("http://localhost:8080/servicios")
      .then((response) => setServicios(response.data))
      .catch((err) => setError("Error al cargar los servicios"));
  }, []);

  const handlePrecioChange = (id, nuevoPrecio) => {
    setPrecioEditado({ ...precioEditado, [id]: nuevoPrecio });
  };

  const handleGuardar = (id) => {
    axios
      .put(`http://localhost:8080/servicios/${id}`, {
        precio: precioEditado[id],
      })
      .then((response) => {
        alert("Precio actualizado exitosamente");
      })
      .catch((err) => {
        setError("Error al actualizar el precio");
      });
  };

  return (
    <div>
      <h2>Configuración - Servicios</h2>
      {error && <p>{error}</p>}
      <table>
        <thead>
          <tr>
            <th>Servicio</th>
            <th>Precio</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {servicios.map((servicio) => (
            <tr key={servicio.id}>
              <td>{servicio.nombre}</td>
              <td>
                <input
                  type="number"
                  value={precioEditado[servicio.id] || servicio.precio}
                  onChange={(e) =>
                    handlePrecioChange(servicio.id, e.target.value)
                  }
                />
              </td>
              <td>
                <button onClick={() => handleGuardar(servicio.id)}>
                  Guardar
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Configuracion;
