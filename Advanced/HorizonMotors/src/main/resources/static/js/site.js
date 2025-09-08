(() => {
  const prefersReduced = window.matchMedia('(prefers-reduced-motion: reduce)').matches;
  if (prefersReduced) return;

  const animate = (el) => {
    el.classList.add('reveal--in');
    el.classList.remove('reveal');
  };

  const io = new IntersectionObserver((entries, obs) => {
    for (const e of entries) {
      if (e.isIntersecting) { animate(e.target); obs.unobserve(e.target); }
    }
  }, { threshold: 0.12 });

  document.querySelectorAll('.reveal').forEach(el => io.observe(el));

  // Hover lift for cards
  document.addEventListener('pointerover', (e) => {
    const card = e.target.closest('.card');
    if (card) card.classList.add('hovered');
  });
  document.addEventListener('pointerout', (e) => {
    const card = e.target.closest('.card');
    if (card) card.classList.remove('hovered');
  });
})();
