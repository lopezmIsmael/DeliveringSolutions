// Seleccionar el contenedor y las secciones
const contenedor = document.getElementById('contenedor');
const secciones = document.querySelectorAll('.seccion');
const navLinks = document.querySelectorAll('.nav-link');
const puntos = document.querySelectorAll('.punto');
let indiceActual = 0;

// Función para avanzar a la siguiente pestaña
function avanzar() {
  if (indiceActual < secciones.length - 1) {
    indiceActual++;
  } else {
    indiceActual = 0; // Volver al inicio si es la última sección
  }
  secciones[indiceActual].scrollIntoView({ behavior: 'smooth' });
  actualizarPestañaActiva();
}

// Función para retroceder a la pestaña anterior
function retroceder() {
  if (indiceActual > 0) {
    indiceActual--;
  } else {
    indiceActual = secciones.length - 1; // Ir a la última sección si es la primera
  }
  secciones[indiceActual].scrollIntoView({ behavior: 'smooth' });
  actualizarPestañaActiva();
}

// Actualizar la pestaña activa en la barra de navegación
function actualizarPestañaActiva() {
  navLinks.forEach((link, index) => {
    if (index === indiceActual) {
      link.classList.add('active');
    } else {
      link.classList.remove('active');
    }
  });
  puntos.forEach((punto, index) => {
    if (index === indiceActual) {
      punto.classList.add('active');
    } else {
      punto.classList.remove('active');
    }
  });
}

// Detectar cuando se hace scroll manual para actualizar el índice actual
contenedor.addEventListener('scroll', () => {
  const scrollPosition = contenedor.scrollLeft;
  const sectionWidth = window.innerWidth;
  indiceActual = Math.round(scrollPosition / sectionWidth);
  actualizarPestañaActiva(); // Actualizar la pestaña activa en función de la sección visible
});

// Añadir eventos a los botones
document.querySelector('.avanzar').addEventListener('click', avanzar);
document.querySelector('.retroceder').addEventListener('click', retroceder);

// Añadir eventos a los puntos de navegación
puntos.forEach((punto, index) => {
  punto.addEventListener('click', () => {
    indiceActual = index;
    secciones[indiceActual].scrollIntoView({ behavior: 'smooth' });
    actualizarPestañaActiva();
  });
});

// Actualizar la pestaña activa al cargar la página
actualizarPestañaActiva();

// Función para volver al inicio
function volverAlInicio() {
  window.location.href = '/';
}

// Cambiar automáticamente de pestaña cada 5 segundos
setInterval(avanzar, 10000);
