function eliminarItem(button) {
    const itemId = button.getAttribute('data-id');
    if (confirm('¿Estás seguro de que deseas eliminar este item?')) {
        fetch(`/cartas/eliminarItem/${itemId}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                button.closest('li').remove();
            } else {
                alert('Error al eliminar el item');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error al eliminar el item');
        });
    }
}
