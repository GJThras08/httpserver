document.querySelectorAll('.feature').forEach(element => {
    let direction = 1;
    setInterval(() => {
        element.style.transform = `translateY(${Math.sin(Date.now() / 500) * 5}px)`;
    }, 50);
});
