import React, { useState } from "react";
import axios from "axios";
import "./ClienteForm.css"; // Importa los estilos del formulario

const ClienteForm = ({ onClienteRegistrado, onCancel }) => {
  const [email, setEmail] = useState("");
  const [nombre, setNombre] = useState("");
  const [numeroTelefono, setNumeroTelefono] = useState("");
  const [errors, setErrors] = useState([]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post("http://localhost:8080/clientes", null, {
        params: { email, nombre, numeroTelefono },
      });
      onClienteRegistrado(response.data);
      setEmail("");
      setNombre("");
      setNumeroTelefono("");
      setErrors([]);
    } catch (error) {
      if (error.response && error.response.status === 400) {
        setErrors(error.response.data.errors); // Asignar la lista de errores
      } else {
        alert('Ocurrió un error inesperado. Inténtalo de nuevo.');
      }
    }
  };

  return (
    <form onSubmit={handleSubmit} className="cliente-form">
      <div>
        <label>Email:</label>
        <input
          type="email"
          value={email}
          onChange={(e) => {
            setEmail(e.target.value);
            setErrors([]); // Limpiar errores al cambiar el valor
          }}
          required
        />
        {errors
          .filter((error) => error.toLowerCase().includes("email")) // Filtrar errores relacionados con el email
          .map((error, index) => (
            <p key={index} className="error-message">{error}</p> // Mostrar cada error en una línea separada
          ))}
      </div>
      <div>
        <label>Nombre:</label>
        <input
          type="text"
          value={nombre}
          onChange={(e) => {
            setNombre(e.target.value);
            setErrors([]); // Limpiar errores al cambiar el valor
          }}
          required
        />
        {errors
          .filter((error) => error.toLowerCase().includes("nombre")) // Filtrar errores relacionados con el nombre
          .map((error, index) => (
            <p key={index} className="error-message">{error}</p> // Mostrar cada error en una línea separada
          ))}
      </div>
      <div>
        <label>Teléfono:</label>
        <input
          type="text"
          value={numeroTelefono}
          onChange={(e) => {
            setNumeroTelefono(e.target.value);
            setErrors([]); // Limpiar errores al cambiar el valor
          }}
          required
        />
        {errors
          .filter((error) => error.toLowerCase().includes("teléfono")) // Filtrar errores relacionados con el teléfono
          .map((error, index) => (
            <p key={index} className="error-message">{error}</p> // Mostrar cada error en una línea separada
          ))}
      </div>
      <div className="form-buttons">
        <button type="submit">Registrar Cliente</button>
        <button type="button" onClick={onCancel}>Cancelar</button>
      </div>
    </form>
  );
};

export default ClienteForm;