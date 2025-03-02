import React from 'react';
import './Navbar.css';

const Navbar = ({ onSelect }) => {
  return (
    <nav className="navbar">
      <div className="logo">
        <img src="logo.png" alt="Logo" className="logo-img" />
      </div>
      <ul className="menu">
        <li>
          <a
            href="#"
            onClick={(e) => {
              e.preventDefault();
              onSelect("home"); // Navegar a la vista Home
            }}
          >
            Home
          </a>
        </li>
        <li>
          <a
            href="#"
            onClick={(e) => {
              e.preventDefault();
              onSelect("agenda");
            }}
          >
            Agenda de Turnos
          </a>
        </li>
        <li>
          <a
            href="#"
            onClick={(e) => {
              e.preventDefault();
              onSelect("historial");
            }}
          >
            Historial de Servicios
          </a>
        </li>
        <li>
          <a
            href="#"
            onClick={(e) => {
              e.preventDefault();
              onSelect("clientes");
            }}
          >
            Clientes
          </a>
        </li>
        <li>
          <a
            href="#"
            onClick={(e) => {
              e.preventDefault();
              onSelect("configuracion");
            }}
          >
            Configuración
          </a>
        </li>
      </ul>
    </nav>
  );
};

export default Navbar;