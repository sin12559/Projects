(function () {
  // Redirect any "Compare" click unless we're already on /browse
  document.addEventListener('click', function (e) {
    var el = e.target && e.target.closest('.btn.compare, [data-role="compare"], button.compare');
    if (!el) return;
    if (location.pathname === '/browse') return;
    e.preventDefault();
    var from = encodeURIComponent(location.pathname + location.search);
    location.assign('/browse?from=' + from + '&compare=true');
  }, { passive: false });
})();
