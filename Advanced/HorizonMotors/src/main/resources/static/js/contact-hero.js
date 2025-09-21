(function(){
  if (!/\/contact(?:$|[?#])/.test(location.pathname)) return;
  document.addEventListener('DOMContentLoaded', function(){
    const sections = Array.from(document.querySelectorAll('section'));
    // Prefer section containing h1/h2 with that text
    let target = sections.find(sec=>{
      const t = (sec.querySelector('h1,h2')?.textContent || '').trim().toLowerCase();
      return t.includes('talk to a specialist');
    }) || sections[0]; // fallback to first section if not found
    if (target && !target.classList.contains('specialist-hero')){
      target.classList.add('specialist-hero');
    }
  });
})();
