(function(){
  // reveal
  const els=[...document.querySelectorAll('.fade-up')];
  const io=new IntersectionObserver(entries=>{
    entries.forEach(e=>{ if(e.isIntersecting){ e.target.classList.add('in'); io.unobserve(e.target);} });
  },{threshold:.12});
  els.forEach(el=>io.observe(el));

  // calculator
  const form=document.getElementById('calc');
  if(form){
    form.addEventListener('submit',e=>{
      e.preventDefault();
      const P=parseFloat(document.getElementById('price').value||0);
      const r=parseFloat(document.getElementById('rate').value||0)/100/12;
      const n=parseInt(document.getElementById('months').value||0,10);
      if(P>0 && n>0){
        const pmt = r===0 ? (P/n) : (P*r)/(1-Math.pow(1+r,-n));
        document.getElementById('pmt').textContent = pmt.toLocaleString('en-US',{style:'currency',currency:'USD'});
        document.getElementById('result').classList.remove('hidden');
        document.getElementById('result').classList.add('in');
      }
    });
  }
})();
