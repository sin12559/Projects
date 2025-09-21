document.addEventListener('click', function(e){
  const b = e.target.closest('.hm-btn'); if(!b) return;
  const r = document.createElement('span'); r.className = 'hm-ripple';
  const rect = b.getBoundingClientRect();
  r.style.left = (e.clientX - rect.left) + 'px';
  r.style.top  = (e.clientY - rect.top) + 'px';
  r.style.width = r.style.height = Math.max(rect.width, rect.height) + 'px';
  b.appendChild(r); setTimeout(()=> r.remove(), 650);
}, {passive:true});
