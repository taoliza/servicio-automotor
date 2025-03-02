import React from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter } from "react-router-dom"; // Importa BrowserRouter
import App from "./App"; // Importa tu componente principal

const root = createRoot(document.getElementById("root"));

root.render(
  <React.StrictMode>
    <BrowserRouter> {/* Envuelve tu aplicación en BrowserRouter */}
      <App />
    </BrowserRouter>
  </React.StrictMode>
);