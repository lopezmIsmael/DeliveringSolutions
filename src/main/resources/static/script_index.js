window.onload = function() {
    var canvas = document.getElementById('animationCanvas');
    var ctx = canvas.getContext('2d');

    // Ajustar el tamaño del canvas
    canvas.width = canvas.parentElement.clientWidth;
    canvas.height = canvas.parentElement.clientHeight;

    var items = [];
    var numItems = 50;
    var emojis = ['🍔', '🍕', '🍟', '🌭', '🍿', '🍩', '🍪', '🍫', '🍰', '🍦', '🍧', '🍨', '🍮', '🍭', '🍬', '🍯', '🍊', '🍋', '🍌', '🍉', '🍇', '🍓', '🍈', '🍒', '🍑', '🍍', '🥭', '🥥', '🥝', '🍅', '🍆', '🥑', '🥦', '🥒', '🌶️', '🌽', '🥕', '🥔', '🍠', '🥐', '🍞', '🥖', '🥨', '🧀', '🥚', '🍳', '🥓', '🥩', '🍗', '🍖', '🌭', '🍤', '🍣', '🍱', '🍛', '🍜', '🍲', '🍥', '🥮', '🍢', '🍡', '🍧', '🍨', '🍦', '🍰', '🎂', '🍮', '🍭', '🍬', '🍫', '🍿', '🍩', '🍪'];

    // Crear elementos con propiedades aleatorias
    for (var i = 0; i < numItems; i++) {
        items.push({
            emoji: emojis[Math.floor(Math.random() * emojis.length)],
            x: Math.random() * canvas.width,
            y: Math.random() * canvas.height,
            speedX: Math.random() * 0.5 + 0.2, // Velocidad entre 0.2 y 0.7
            speedY: Math.random() * 0.5 + 0.2, // Velocidad entre 0.2 y 0.7
            fontSize: Math.random() * 30 + 20
        });
    }

    // Función de animación
    function animate() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        items.forEach(function(item) {
            item.x += item.speedX;
            item.y += item.speedY;

            // Reiniciar posición si sale del canvas
            if (item.x > canvas.width || item.y > canvas.height) {
                item.x = -50;
                item.y = -50;
            }

            ctx.font = item.fontSize + 'px Arial';
            ctx.fillText(item.emoji, item.x, item.y);
        });
        requestAnimationFrame(animate);
    }

    animate();
};
