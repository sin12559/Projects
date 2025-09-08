# HealthPulse
Health/readiness/metrics microservice.

Run:
- `npm install`
- `npm test`
- `npm start`

Endpoints:
- `GET /healthz` → `{ ok: true }`
- `GET /readyz` → `{ ready: true }`
- `GET /metrics` → Prometheus metrics
