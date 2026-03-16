document.addEventListener('DOMContentLoaded', () => {
    const panels = document.querySelectorAll('.wc-panel, .wc-hero, .wc-login');

    const observer = new IntersectionObserver((entries) => {
        entries.forEach((entry) => {
            if (!entry.isIntersecting) {
                return;
            }
            entry.target.style.transform = 'translateY(0px)';
            entry.target.style.opacity = '1';
            observer.unobserve(entry.target);
        });
    }, { threshold: 0.15 });

    panels.forEach((panel, index) => {
        panel.style.opacity = '0';
        panel.style.transform = 'translateY(16px)';
        panel.style.transition = `opacity 0.55s ease ${index * 0.05}s, transform 0.55s ease ${index * 0.05}s`;
        observer.observe(panel);
    });

    const hero = document.querySelector('.wc-hero');
    if (hero) {
        window.addEventListener('mousemove', (event) => {
            const x = (event.clientX / window.innerWidth - 0.5) * 6;
            const y = (event.clientY / window.innerHeight - 0.5) * 6;
            hero.style.transform = `translate(${x * 0.16}px, ${y * 0.16}px)`;
        });
    }
});
