function toggleFavorite(button) {
    const restaurantId = button.getAttribute('data-id');
    const isFavorited = button.classList.contains('favorited');

    fetch(`/clientes/toggleFavorito/${restaurantId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-Requested-With': 'XMLHttpRequest'
        },
        body: JSON.stringify({ favorited: !isFavorited })
    })
        .then(response => {
            if (response.ok) {
                button.classList.toggle('favorited');
                button.querySelector('.favorite-icon').textContent = isFavorited ? '☆' : '★';

                // Si estamos en la vista de favoritos y el restaurante ha sido desmarcado,
                // eliminamos el elemento del DOM
                if (document.querySelector('.container').getAttribute('data-vista-favoritos') === 'true' && isFavorited) {
                    button.closest('.restaurant-card').remove();
                }
            } else {
                console.error('Error al actualizar el estado de favorito');
            }
        })
        .catch(error => {
            console.error('Error en la solicitud:', error);
        });
}
