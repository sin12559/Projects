(function(){
  function onScroll(){
    if(window.scrollY>8){document.body.classList.add('scrolled');}
    else{document.body.classList.remove('scrolled');}
  }
  window.addEventListener('load',function(){
    document.body.classList.add('loaded');
    onScroll();
  });
  window.addEventListener('scroll',onScroll,{passive:true});
})();
