#!/usr/bin/env bash
set -euo pipefail
# hit the running dev server
curl -fsS http://localhost:${PORT:-5173}/healthz >/dev/null
curl -fsS http://localhost:${PORT:-5173}/readyz >/dev/null
curl -fsS http://localhost:${PORT:-5173}/health >/dev/null
echo "smoke ok"
