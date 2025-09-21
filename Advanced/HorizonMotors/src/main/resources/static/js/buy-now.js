/* Buy Now simplifier: works anywhere, routes to /contact (with ?carId when available) */
(function () {
  function byText(el, words) {
    if (!el) return false;
    var t = (el.textContent || '').trim().toLowerCase();
    return words.some(w => t === w || t.includes(w));
  }
  function findCarId(fromEl) {
    if (!fromEl) return "";
    var id = (fromEl.getAttribute && fromEl.getAttribute('data-id')) || "";
    if (id) return id;
    var card = fromEl.closest('.card,[data-card],[data-vehicle-id]');
    if (card) id = card.getAttribute('data-vehicle-id') || card.getAttribute('data-id') || "";
    return id || "";
  }
  document.addEventListener('click', function (e) {
    var t = e.target;
    var buy = t.closest('[data-role="buy"], .btn.buy-now, .buy-now') || (byText(t, ['buy now', 'ask']) ? t : null);
    if (!buy) return;
    if (location.pathname === '/contact') return;
    e.preventDefault();
    var id = findCarId(buy);
    var url = '/contact' + (id ? ('?carId=' + encodeURIComponent(id)) : '');
    location.assign(url);
  }, { passive: false });
})();
