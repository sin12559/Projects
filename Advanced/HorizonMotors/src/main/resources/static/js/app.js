document.addEventListener("DOMContentLoaded",()=>{
  const here = location.pathname.replace(/\/+$/,'') || '/';
  document.querySelectorAll('.nav-links a').forEach(a=>{
    const href = (a.getAttribute('href')||'').replace(/\/+$/,'') || '/';
    if((here === '/' && href === '/') || (here !== '/' && here.startsWith(href))) {
      a.classList.add('active');
      a.style.opacity = "1";
      a.style.fontWeight = "700";
    }
  });
});
/* LuxRayPlusRipple */
document.addEventListener('click', function(e){
  const t = e.target.closest('.lux-btn'); if(!t) return;
  const r = document.createElement('span'); r.className='lux-ripple';
  const rect = t.getBoundingClientRect();
  r.style.left = (e.clientX - rect.left)+'px';
  r.style.top  = (e.clientY - rect.top)+'px';
  r.style.width = r.style.height = Math.max(rect.width, rect.height)+'px';
  t.appendChild(r); setTimeout(()=> r.remove(), 650);
}, {passive:true});
