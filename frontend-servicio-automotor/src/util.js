
export function formatearFecha(fecha) {
    const diasDeLaSemana = ["Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"];
    const mesesDelAño = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];
    
    const fechaObj = new Date(fecha);
    
    const diaSemana = diasDeLaSemana[fechaObj.getDay()]; // Día de la semana
    const dia = fechaObj.getDate(); // Día del mes
    const mes = fechaObj.getMonth() + 1; // Mes (sumar 1 para corregir el desfase)
    const anio = fechaObj.getFullYear(); // Año
    
    // Devolver la fecha formateada
    return `${diaSemana} ${dia}/${mes}/${anio}`;
}

  // Función para formatear la hora
  export function formatearHora(fecha) {
    const fechaObj = new Date(fecha);
    
    const horas = String(fechaObj.getHours()).padStart(2, '0'); // Obtener horas con 2 dígitos
    const minutos = String(fechaObj.getMinutes()).padStart(2, '0'); // Obtener minutos con 2 dígitos
    
    // Devolver la hora formateada
    return `${horas}:${minutos} hs.`;
  }
  