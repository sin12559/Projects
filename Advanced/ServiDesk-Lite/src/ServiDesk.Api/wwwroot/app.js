/* Clean stable UI with subtle animations + working CRUD + solid filters */
const API_BASE = ""; // same origin
const Pri = { Low:0, Medium:1, High:2, Critical:3 };
const PriByVal = Object.fromEntries(Object.entries(Pri).map(([k,v])=>[v,k]));
let dt, chart;
const kpiPrev = { open: 0, progress: 0, resolved: 0, sla: 0 };

document.addEventListener("DOMContentLoaded", () => {
  initTable();
  bindUI();
  refresh();
});

function bindUI() {
  qs("#btn-new").addEventListener("click", openCreate);
  qs("#btn-save").addEventListener("click", saveTicket);

  const statusSel = qs("#filter-status");
  const prioSel   = qs("#filter-priority");
  statusSel.addEventListener("change", () => {
    const v = statusSel.value;
    dt.column(3).search(v ? `^${escapeRegex(v)}$` : "", true, false).draw();
  });
  prioSel.addEventListener("change", () => {
    const v = prioSel.value;
    dt.column(2).search(v ? `^${escapeRegex(v)}$` : "", true, false).draw();
  });

  document.addEventListener("pointerdown", e => {
    const el = e.target.closest(".btn"); if (!el) return;
    const r = el.getBoundingClientRect();
    el.style.setProperty("--x", (e.clientX - r.left) + "px");
    el.style.setProperty("--y", (e.clientY - r.top) + "px");
  });
}

function initTable() {
  dt = new DataTable("#tickets", {
    columns: [
      { data: "id" },
      { data: "title" },
      { data: "priorityLabel" },
      { data: "status" },
      { data: "assignee" },
      { data: "createdUtc", render: v => new Date(v).toISOString().replace('T',' ').slice(0,19) },
      { data: null, orderable:false, searchable:false, render: row => `
        <div class="btn-group btn-group-sm" role="group">
          <button class="btn btn-outline-secondary" onclick='editTicket(${row.id})' title="Edit"><i class="bi bi-pencil-square"></i></button>
          <button class="btn btn-outline-danger" onclick='deleteTicket(${row.id})' title="Delete"><i class="bi bi-trash3"></i></button>
        </div>` }
    ],
    order: [[0, "desc"]],
    pageLength: 10,
    responsive: true,
    searching: true,
    destroy: true
  });

  $('#tickets').on('draw.dt', function(){
    const rows = document.querySelectorAll('#tickets tbody tr');
    rows.forEach((tr, i) => {
      tr.classList.remove('row-in');
      tr.style.animationDelay = (i * 50) + 'ms';
      void tr.offsetWidth;
      tr.classList.add('row-in');
    });
  });
}

async function refresh(){
  try {
    const raw = await apiGet("/api/tickets");
    const tickets = raw.map(t => ({ ...t, priorityLabel: PriByVal[t.priority] ?? t.priority }));
    dt.clear().rows.add(tickets).draw();
    updateKpis(tickets);
    renderChart(tickets);
  } catch (e) { toast("Failed to load: " + sanitize(e.message), "danger"); console.error(e); }
}

function updateKpis(t){
  const by = t.reduce((a,x)=>((a[x.status]=1+(a[x.status]||0)),a),{});
  const now = {
    open: by.Open||0,
    progress: by.InProgress||0,
    resolved: by.Resolved||0,        // << only Resolved now
    sla: t.filter(x=>x.slaBreached).length
  };
  countUp(qs("#kpi-open"),     kpiPrev.open,     now.open);
  countUp(qs("#kpi-progress"), kpiPrev.progress, now.progress);
  countUp(qs("#kpi-resolved"), kpiPrev.resolved, now.resolved);
  countUp(qs("#kpi-sla"),      kpiPrev.sla,      now.sla);
  Object.assign(kpiPrev, now);
}

function renderChart(tickets){
  const ctx = qs("#chart-status");
  const by = tickets.reduce((a,x)=>((a[x.status]=1+(a[x.status]||0)),a),{});
  const labels = ["Open","InProgress","Resolved","Closed"];
  const data = labels.map(k => by[k]||0);
  if (chart) chart.destroy();
  chart = new Chart(ctx, { type: "doughnut", data: { labels, datasets: [{ data }] }, options: { plugins:{ legend:{ position:"bottom" } }, animation: { duration: 400 } } });
}

function openCreate(){
  qs("#ticketModalLabel").textContent = "New Ticket";
  ["#ticket-id","#ticket-title","#ticket-assignee","#ticket-desc"].forEach(s=>qs(s).value="");
  qs("#ticket-priority").value = "Medium";
  qs("#ticket-category").value = "General";
  qs("#ticket-status").value = "Open";
  bootstrap.Modal.getOrCreateInstance(qs("#ticketModal")).show();
}

async function editTicket(id){
  try {
    const t = await apiGet(`/api/tickets/${id}`);
    qs("#ticketModalLabel").textContent = `Edit Ticket #${id}`;
    qs("#ticket-id").value = t.id;
    qs("#ticket-title").value = t.title;
    qs("#ticket-assignee").value = t.assignee || "";
    qs("#ticket-desc").value = t.description;
    qs("#ticket-priority").value = PriByVal[t.priority] ?? "Medium";
    qs("#ticket-category").value = t.category;
    qs("#ticket-status").value = t.status;
    bootstrap.Modal.getOrCreateInstance(qs("#ticketModal")).show();
  } catch(e) { toast("Failed to load ticket: " + sanitize(e.message), "danger"); console.error(e); }
}

async function saveTicket(){
  const btn = qs("#btn-save");
  try {
    btn.disabled = true;
    const id = qs("#ticket-id").value;
    const payload = {
      id: id ? Number(id) : 0,
      title: qs("#ticket-title").value.trim(),
      description: qs("#ticket-desc").value.trim(),
      priority: Pri[qs("#ticket-priority").value],
      category: qs("#ticket-category").value.trim() || "General",
      status: qs("#ticket-status").value,
      assignee: qs("#ticket-assignee").value.trim() || "Unassigned"
    };
    if (!payload.title || !payload.description){ toast("Title and Description are required.", "warning"); return; }
    if (id){ await apiPut(`/api/tickets/${id}`, payload); toast("Ticket updated.", "success"); }
    else   { await apiPost("/api/tickets", payload);        toast("Ticket created.", "success"); }
    bootstrap.Modal.getOrCreateInstance(qs("#ticketModal")).hide();
    await refresh();
  } catch(e) { toast("Save failed: " + sanitize(e.message), "danger"); console.error(e); }
  finally { btn.disabled = false; }
}

async function deleteTicket(id){
  if (!confirm(`Delete ticket #${id}?`)) return;
  try { await apiDelete(`/api/tickets/${id}`); toast("Ticket deleted.", "success"); await refresh(); }
  catch(e){ toast("Delete failed: " + sanitize(e.message), "danger"); console.error(e); }
}

/* Animations: KPI count-up */
function countUp(el, from, to, ms=600){
  if (from === to) { el.textContent = to; return; }
  const start = performance.now();
  const step = now => {
    const t = Math.min(1, (now - start)/ms);
    const eased = 1 - Math.pow(1 - t, 3);
    el.textContent = Math.round(from + (to - from) * eased);
    if (t < 1) requestAnimationFrame(step);
  };
  requestAnimationFrame(step);
}

/* Toast helper */
function toast(msg, tone="primary"){
  let host = document.querySelector("#toasts");
  if(!host){ host = document.createElement("div"); host.id="toasts"; host.className="toast-container"; document.body.appendChild(host); }
  const el = document.createElement("div");
  el.className = `toast align-items-center text-bg-${tone} border-0`;
  el.setAttribute("role","alert");
  el.innerHTML = `<div class="d-flex"><div class="toast-body">${sanitize(msg)}</div><button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button></div>`;
  host.appendChild(el);
  const t = new bootstrap.Toast(el, { delay: 2500 }); t.show(); el.addEventListener("hidden.bs.toast", ()=> el.remove());
}

/* API helpers */
async function apiGet(p){ const r=await fetch(API_BASE+p); await ok(r); return r.json(); }
async function apiPost(p,b){ const r=await fetch(API_BASE+p,{method:"POST",headers:{ "Content-Type":"application/json"},body:JSON.stringify(b)}); await ok(r); return r.json(); }
async function apiPut(p,b){ const r=await fetch(API_BASE+p,{method:"PUT",headers:{ "Content-Type":"application/json"},body:JSON.stringify(b)}); await ok(r); return true; }
async function apiDelete(p){ const r=await fetch(API_BASE+p,{method:"DELETE"}); await ok(r); return true; }
async function ok(r){ if(!r.ok) throw new Error(await r.text()|| (r.status+" "+r.statusText)); }

/* Utils */
function qs(s){ return document.querySelector(s); }
function sanitize(s){ return (s||"").replace(/</g,"&lt;").replace(/>/g,"&gt;"); }
function escapeRegex(s){ return s.replace(/[.*+?^${}()|[\]\\]/g, '\\$&'); }
