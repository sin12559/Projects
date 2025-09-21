import re, sys, pathlib

p = pathlib.Path("src/main/resources/templates/featured.html")
html = p.read_text(encoding="utf-8")

# Prepare new data (6 cars). Order matters: applied to the first 6 .car-card blocks found.
cars = [
    {
        "img": "/images/featured/enzo.jpg",
        "h2": "Ferrari Enzo",
        "h3": "2003 • 400 units",
        "spec": "6.0L V12 · 651 hp · RWD",
        "blurb": "Legend of the 2000s; carbon monocoque with F1 DNA."
    },
    {
        "img": "/images/featured/veneno.jpg",
        "h2": "Lamborghini Veneno",
        "h3": "2013 • 4 coupes / 9 roadsters",
        "spec": "6.5L V12 · 740 hp · AWD",
        "blurb": "Wild aero statement built for Lambo’s 50th anniversary."
    },
    {
        "img": "/images/featured/p1gtr.jpg",
        "h2": "McLaren P1 GTR",
        "h3": "2015 • ~58 units",
        "spec": "3.8L TT V8 Hybrid · 986 hp · RWD",
        "blurb": "Track-only apex P1; ultra-limited client program."
    },
    {
        "img": "/images/featured/agera-rs.jpg",
        "h2": "Koenigsegg Agera RS",
        "h3": "2015 • 25 units",
        "spec": "5.0L TT V8 · 1160+ hp · RWD",
        "blurb": "Former top-speed record holder; highly collectible."
    },
    {
        "img": "/images/featured/918-weissach.jpg",
        "h2": "Porsche 918 Spyder (Weissach)",
        "h3": "2015 • 918 units",
        "spec": "4.6L V8 Hybrid · 887 hp · AWD",
        "blurb": "Lightweight pack: magnesium wheels, extra downforce."
    },
    {
        "img": "/images/featured/divo.jpg",
        "h2": "Bugatti Divo",
        "h3": "2020 • 40 units",
        "spec": "8.0L Quad-Turbo W16 · 1500 hp · AWD",
        "blurb": "Coachbuilt, handling-focused Chiron variant."
    },
]

# Find each existing card block WITHOUT changing layout/structure
card_pattern = re.compile(r'(<(?:article|div)\s+class="[^"]*\bcar-card\b[^"]*".*?</(?:article|div)>)', re.S)
cards_found = card_pattern.findall(html)

changed = 0
new_blocks = []
for idx, block in enumerate(cards_found):
    if idx >= len(cars):
        new_blocks.append(block)
        continue
    data = cars[idx]
    b = block

    # Swap img src
    b = re.sub(r'(<img[^>]+src=")[^"]*(")',
               r'\1' + re.escape(data["img"]) + r'\2', b)

    # Swap <h2>...</h2>
    b = re.sub(r'(<h2[^>]*>).*?(</h2>)',
               r'\1' + data["h2"] + r'\2', b, flags=re.S)

    # Swap <h3>...</h3>
    b = re.sub(r'(<h3[^>]*>).*?(</h3>)',
               r'\1' + data["h3"] + r'\2', b, flags=re.S)

    # Swap spec paragraph (class="spec")
    b = re.sub(r'(<p[^>]*class="[^"]*\bspec\b[^"]*"[^>]*>).*?(</p>)',
               r'\1' + data["spec"] + r'\2', b, flags=re.S)

    # Swap blurb paragraph (class="blurb")
    b = re.sub(r'(<p[^>]*class="[^"]*\bblurb\b[^"]*"[^>]*>).*?(</p>)',
               r'\1' + data["blurb"] + r'\2', b, flags=re.S)

    new_blocks.append(b)
    changed += 1

# Rebuild the HTML with replaced blocks in-place
def repl_iter(match_iter, replacements):
    # Generator that yields the new block each time card_pattern.sub calls the repl function
    for r in replacements:
        yield r

rep_iter = iter(new_blocks)
def repl(_):
    return next(rep_iter)

html_new = card_pattern.sub(repl, html, count=len(new_blocks))

if changed == 0:
    print("No .car-card blocks found; nothing changed.", file=sys.stderr)
else:
    p.write_text(html_new, encoding="utf-8")
    print(f"Updated {changed} card(s) without touching layout.")
