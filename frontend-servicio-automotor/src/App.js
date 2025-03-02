import React, { useState } from "react";
import Navbar from "./components/Navbar";
import Home from "./components/Home";
import TurnosList from "./components/TurnoList";
import ClienteList from "./components/ClienteList"; 
import Config from "./components/Config"

const App = () => {
  const [vistaActual, setVistaActual] = useState("home");

  const handleSelect = (seleccion) => {
    setVistaActual(seleccion);
  };

  return (
    <div>
      <Navbar onSelect={handleSelect} />

      {vistaActual === "home" && <Home />}

      {vistaActual === "agenda" && (
        <TurnosList
          apiEndpoint="http://localhost:8080/turnos/proximos"
          titulo="Agenda de Turnos"
        />
      )}

      {vistaActual === "historial" && (
        <TurnosList
          apiEndpoint="http://localhost:8080/turnos/historial"
          titulo="Historial de Servicios"
        />
      )}

      {vistaActual === "clientes" && <ClienteList />} {}

      {vistaActual === "configuracion" && <Config />  }

          {}

    
    </div>
  );
};

export default App;