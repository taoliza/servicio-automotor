import React from "react";
import TurnosList from "./TurnoList";
import "./Home.css"

const Home = () => {
  return (
    <div>
      <h1>Bienvenido</h1>
      <TurnosList
        apiEndpoint="http://localhost:8080/turnos/proximos"
        titulo="Próximos Turnos"
        limite={3} // Limitar a 3 turnos
      />
    </div>
  );
};

export default Home;