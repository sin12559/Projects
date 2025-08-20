# SRE-Checklist-CLI

A lightweight CLI to run SRE/DevOps troubleshooting checklists (YAML) and output a pass/fail report.

## Features
- Run **prebuilt or custom YAML** checklists
- Built-in checks: `shell`, `http`, `file`, `disk`
- **Exit code** signals success/failure (CI-friendly)
- Output formats: **text** (pretty) or **json** (machine-readable)

## Install & Run
```bash
python3 -m venv .venv && source .venv/bin/activate
pip install -r Advanced/SRE-Checklist-CLI/requirements.txt
python Advanced/SRE-Checklist-CLI/src/cli.py run Advanced/SRE-Checklist-CLI/configs/basic.yml --format text

checks:
  - name: "Human friendly name"
    type: shell|http|file|disk
    # shell:   command: "echo ok"
    # http:    url: "https://example.com"
    # file:    path: "/etc/hosts"
    # disk:    threshold: 90   # percent

md
mg
