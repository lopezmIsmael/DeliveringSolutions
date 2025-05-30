const cartItems = [];

function addToCart(event, form) {
    event.preventDefault(); 
    const idItemMenu = form.idItemMenu.value;
    const nombre = form.nombre.value;
    const precio = parseFloat(form.precio.value);
    cartItems.push({ idItemMenu, nombre, precio });
    updateCart();
    openCart();
}

function removeFromCart(index) {
    event.stopPropagation();
    cartItems.splice(index, 1);
    updateCart();
}

function updateCart() {
    const cartItemsContainer = document.getElementById('cart-items');
    const cartTotalContainer = document.getElementById('cart-total');
    cartItemsContainer.innerHTML = '';
    let total = 0;
    cartItems.forEach((item, index) => {
        const li = document.createElement('li');
        li.classList.add('cart-item');

        const itemName = document.createElement('span');
        itemName.classList.add('item-name');
        itemName.textContent = item.nombre;

        const itemPrice = document.createElement('span');
        itemPrice.classList.add('item-price');
        itemPrice.textContent = ` - ${item.precio.toLocaleString('es-ES', { minimumFractionDigits: 2, maximumFractionDigits: 2 }).replace('.', ',').replace(/(\d)(?=(\d{3})+(?!\d))/g, '1.')}€`;

        const removeBtn = document.createElement('button');
        removeBtn.textContent = 'Eliminar';
        removeBtn.classList.add('remove-btn');
        removeBtn.onclick = () => removeFromCart(index);

        li.appendChild(itemName);
        li.appendChild(itemPrice);
        li.appendChild(removeBtn);
        cartItemsContainer.appendChild(li);

        total += item.precio;
    });
    cartTotalContainer.textContent = `Total: ${total.toLocaleString('es-ES', { minimumFractionDigits: 2, maximumFractionDigits: 2 }).replace('.', ',').replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')}€`;
}

function openCart() {
    document.getElementById('cart').classList.add('open');
}

function closeCart() {
    document.getElementById('cart').classList.remove('open');
}

function toggleCart() {
    const cart = document.getElementById('cart');
    if (cart.classList.contains('open')) {
        closeCart();
    } else {
        openCart();
    }
}

function finalizePurchase() {
    const total = cartItems.reduce((acc, item) => acc + item.precio, 0);
    const cartData = JSON.stringify(cartItems);
    const form = document.createElement('form');
    form.action = '/pago/register';
    form.method = 'post';

    const input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'cartData';
    input.value = cartData;

    const restauranteId = document.querySelector('[data-restaurante-id]').getAttribute('data-restaurante-id');
    const inputRestaurante = document.createElement('input');
    inputRestaurante.type = 'hidden';
    inputRestaurante.name = 'restauranteId';
    inputRestaurante.value = restauranteId;

    form.appendChild(input);
    form.appendChild(inputRestaurante);
    document.body.appendChild(form);
    form.submit();
}

document.addEventListener('click', function (event) {
    const cart = document.getElementById('cart');
    const isClickInsideCart = cart.contains(event.target);
    const isCartOpen = cart.classList.contains('open');

    if (!isClickInsideCart && isCartOpen) {
        closeCart();
    }
});

document.getElementById('cart').addEventListener('click', function (event) {
    event.stopPropagation();
});