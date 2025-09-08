venv: ; python3 -m venv .venv
install: venv ; . .venv/bin/activate && pip install -U pip && pip install -r requirements.txt
test: ; . .venv/bin/activate && pytest -q
run: ; . .venv/bin/activate && python src/cli.py
