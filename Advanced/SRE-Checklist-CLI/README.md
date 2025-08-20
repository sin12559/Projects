# SRE-Checklist-CLI

A lightweight **CLI** to run SRE/DevOps **troubleshooting checklists** written in YAML and output a pass/fail summary (text or JSON). Great for quick node health checks, smoke tests, and runbooks.

---

## ✨ Features
- ✅ Run **prebuilt or custom YAML** checklists
- 🔧 Built-in checks: **shell command**, **HTTP GET**, **file exists**, **disk usage**
- 🧪 **Exit code** ⇒ 0 on success, non-zero on failure (CI-friendly)
- 📤 Output **text** (human) or **JSON** (machine-readable)

---

## 🛠 Requirements
- Python 3.10+ (recommended)  
- macOS/Linux/WSL work fine

---

## 🚀 Quick Start

### 1) Create & activate venv
```bash
python3 -m venv Advanced/SRE-Checklist-CLI/.venv
source Advanced/SRE-Checklist-CLI/.venv/bin/activate

