function abrirFormulario() {
    document.getElementById('formularioDireccion').classList.add('mostrar');
    document.getElementById('overlay').classList.add('mostrar');
}

function cerrarFormulario() {
    document.getElementById('formularioDireccion').classList.remove('mostrar');
    document.getElementById('overlay').classList.remove('mostrar');
}

function registrarDireccion(event) {
    event.preventDefault();
    const form = document.getElementById('formRegistrarDireccion');
    const formData = new FormData(form);

    fetch('/direccion/registro', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            console.log('Response content-type:', response.headers.get('content-type'));
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.text(); 
        })
        .then(data => {
            console.log('Respuesta JSON:', data);
            location.reload(); 
        })
        .catch(error => {
            console.error('Error en el registro:', error);
            document.getElementById('error').textContent = error.message;
        });
}
