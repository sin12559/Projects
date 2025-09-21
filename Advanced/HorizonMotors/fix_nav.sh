#!/usr/bin/env bash
set -euo pipefail

FRAG=src/main/resources/templates/fragments/navbar.html
STATIC=src/main/resources/static/img
mkdir -p backups "$STATIC"

# 1) Backup current navbar (if exists)
[ -f "$FRAG" ] && cp "$FRAG" "backups/navbar.html.$(date +%s).bak" || true

# 2) Ensure a logo exists
cat > "$STATIC/logo.svg" <<'SVG'
<svg width="64" height="64" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 64 64">
  <defs><linearGradient id="g" x1="0%" y1="0%" x2="100%" y2="0%">
    <stop offset="0%" stop-color="#c8a44d"/><stop offset="100%" stop-color="#f0d37a"/></linearGradient></defs>
  <rect rx="14" ry="14" x="2" y="2" width="60" height="60" fill="#0b0f14" stroke="#1b2330"/>
  <path d="M18 42 L26 22 L32 34 L38 22 L46 42" fill="none" stroke="url(#g)" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
SVG

# 3) Write navbar fragment
mkdir -p "$(dirname "$FRAG")"
cat > "$FRAG" <<'HTML'
<div th:fragment="navbar">
  <style>
    .lux-nav { display:flex;justify-content:space-between;align-items:center;
      padding:14px 28px;background:#0b0f14;border-bottom:1px solid rgba(255,255,255,.08);
      position:sticky;top:0;z-index:1000;font-family:-apple-system,BlinkMacSystemFont,'Segoe UI',Roboto,Helvetica,Arial,sans-serif; }
    .lux-nav .brand { display:flex;align-items:center;gap:12px;text-decoration:none; }
    .lux-nav .brand img { height:34px;width:auto;object-fit:contain; }
    .lux-nav .brand span { font-size:20px;font-weight:800;
      background:linear-gradient(90deg,#c8a44d,#f0d37a);-webkit-background-clip:text;background-clip:text;color:transparent; }
    .lux-nav ul { display:flex;list-style:none;gap:26px;margin:0;padding:0; }
    .lux-nav a { text-decoration:none;font-weight:600;font-size:15px;color:#e6edf3;transition:color .2s ease; }
    .lux-nav a:hover { color:#f0d37a; }
    .lux-nav .cta { padding:8px 16px;border-radius:999px;font-weight:800;font-size:15px;
      background:linear-gradient(90deg,#c8a44d,#f0d37a);color:#0b0f14!important;box-shadow:0 6px 16px rgba(240,211,122,.2); }
  </style>
  <nav class="lux-nav">
    <a th:href="@{/}" class="brand">
      <img src="/img/logo.svg" alt="Horizon Motors"/>
      <span>Horizon Motors</span>
    </a>
    <ul>
      <li><a th:href="@{/}">Home</a></li>
      <li><a th:href="@{/browse}">Browse</a></li>
      <li><a th:href="@{/featured}">Featured</a></li>
      <li><a th:href="@{/contact}">Contact</a></li>
      <li><a th:href="@{/purchase}" class="cta">Buy Now</a></li>
    </ul>
  </nav>
</div>
HTML

# 4) Fix fragment references in templates (use Perl instead of sed for macOS safety)
for f in src/main/resources/templates/*.html src/main/resources/templates/error/*.html; do
  [ -f "$f" ] || continue
  perl -pi -e 's~th:(replace|insert)=["'\'']fragments/navbar :: navbar["'\'']~th:$1="~{fragments/navbar :: navbar}"~g' "$f"
done

# 5) Restart app
./mvnw -q -DskipTests spring-boot:run
