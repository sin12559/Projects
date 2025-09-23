async function j(u){ const r=await fetch(u); if(!r.ok) throw new Error(u+': '+r.status); return r.json(); }
function setChip(ok){ const c=document.getElementById('ready-chip'); c.textContent=ok?'READY':'DEGRADED'; c.className='chip '+(ok?'ok':'bad'); }

// highlight active tab
(function activateTab(){
  const p = location.pathname === '/' ? '/' : location.pathname;
  document.querySelectorAll('.tab').forEach(a=>{
    const href = a.getAttribute('href');
    if ((p === '/' && href === '/') || (p !== '/' && href !== '/' && p.startsWith(href))) {
      a.classList.add('active');
    }
  });
})();

async function tick(){
  try{ const s=await j('/status'); document.getElementById('status').textContent=JSON.stringify(s,null,2); setChip(!!s.ready); }
  catch(e){ document.getElementById('status').textContent=String(e); setChip(false); }

  try{
    const ck=await j('/checks'); const ul=document.getElementById('checks'); ul.innerHTML='';
    const arr=ck.results||[]; 
    if(!arr.length){ ul.innerHTML='<li>(no targets set — export HEALTH_ENDPOINTS)</li>'; }
    arr.forEach(c=>{
      const li=document.createElement('li');
      li.textContent=(c.url||'')+(c.status?(' ['+c.status+']'):'')+(c.ms?(' '+c.ms+'ms'):'');
      ul.appendChild(li);
    });
  }catch(e){ document.getElementById('checks').innerHTML='<li>'+String(e)+'</li>'; }

  try{ const info=await j('/info'); document.getElementById('info').textContent=JSON.stringify(info,null,2); }catch{}
}
tick(); setInterval(tick,5000);
