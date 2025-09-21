(function(){
  // fallback if an image path is wrong
  const fall = '/img/placeholder/car.png';
  document.querySelectorAll('img').forEach(img=>{
    img.addEventListener('error', ()=>{ if (!img.src.includes('placeholder')) img.src = fall; });
  });

  // search/sort on /cars
  const grid = document.getElementById('carsGrid');
  if (!grid) return;
  const cards = Array.from(grid.querySelectorAll('.car-card'));
  const searchInput = document.getElementById('searchInput');
  const searchBtn   = document.getElementById('searchBtn');
  const resetBtn    = document.getElementById('resetBtn');
  const sortBtns    = Array.from(document.querySelectorAll('.sort-btn'));

  const norm = (s)=> (s||'').toString().toLowerCase().trim();

  function doSearch(){
    const q = norm(searchInput?.value);
    cards.forEach(card=>{
      const hay = norm(card.dataset.make + ' ' + card.dataset.model);
      card.style.display = (!q || hay.includes(q)) ? '' : 'none';
    });
  }
  function doReset(){ if (searchInput) searchInput.value=''; cards.forEach(c=>c.style.display=''); }
  function doSort(key,dir){
    const factor = dir==='desc' ? -1 : 1;
    const visible = cards.filter(c=>c.style.display!=='none');
    visible.sort((a,b)=>{
      const av = Number(a.dataset[key])||0, bv=Number(b.dataset[key])||0;
      return av===bv ? 0 : (av<bv?-1:1)*factor;
    });
    const frag = document.createDocumentFragment();
    visible.forEach(c=>frag.appendChild(c));
    grid.appendChild(frag);
  }

  searchBtn?.addEventListener('click', doSearch);
  searchInput?.addEventListener('keydown', e=>{ if (e.key==='Enter'){ e.preventDefault(); doSearch(); }});
  resetBtn?.addEventListener('click', doReset);
  sortBtns.forEach(btn => btn.addEventListener('click', ()=>doSort(btn.dataset.key, btn.dataset.dir)));
})();
