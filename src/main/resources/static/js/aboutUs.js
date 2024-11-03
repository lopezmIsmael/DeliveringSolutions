
// Seleccionar el contenedor y las secciones
const contenedor = document.getElementById('contenedor');
const secciones = document.querySelectorAll('.seccion');
const navLinks = document.querySelectorAll('.nav-link');
let indiceActual = 0;

// Función para avanzar a la siguiente pestaña
function avanzar() {
  if (indiceActual < secciones.length - 1) {
    indiceActual++;
    secciones[indiceActual].scrollIntoView({ behavior: 'smooth' });
    actualizarPestañaActiva();
  }
}

// Función para retroceder a la pestaña anterior
function retroceder() {
  if (indiceActual > 0) {
    indiceActual--;
    secciones[indiceActual].scrollIntoView({ behavior: 'smooth' });
    actualizarPestañaActiva();
  }
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

// Actualizar la pestaña activa al cargar la página
actualizarPestañaActiva();

// Función para volver al inicio
function volverAlInicio() {
  window.location.href = '/';
}
