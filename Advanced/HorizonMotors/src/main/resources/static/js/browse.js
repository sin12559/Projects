document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('browseForm');
  if (form) {
    form.addEventListener('submit', () => {
      // could add loading state or pushState here
    });
  }
  // click handlers for future “Save”/“View” buttons
  document.querySelectorAll('.card .btn.ghost').forEach(btn=>{
    btn.addEventListener('click', ()=> alert('Saved to favorites (demo).'));
  });
  document.querySelectorAll('.card .btn:not(.ghost)').forEach(btn=>{
    btn.addEventListener('click', ()=> alert('Open details (demo).'));
  });
});
