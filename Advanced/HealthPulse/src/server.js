import express from "express";
import helmet from "helmet";
import pino from "pino";
import client from "prom-client";

const PORT = process.env.PORT || 5173;
const app = express();
const log = pino();

// security + JSON
app.use(helmet());
app.use(express.json());

// ---- Prometheus metrics ----
const register = new client.Registry();
client.collectDefaultMetrics({ register });

// custom counter example
const httpCounter = new client.Counter({
  name: "http_requests_total",
  help: "Total HTTP requests",
  labelNames: ["method", "path", "status"]
});
register.registerMetric(httpCounter);

// metrics endpoint
app.get("/metrics", async (_req, res) => {
  try {
    res.set("Content-Type", register.contentType);
    res.end(await register.metrics());
  } catch (err) {
    res.status(500).end(String(err));
  }
});

// simple liveness
app.get("/healthz", (req, res) => {
  httpCounter.inc({ method: req.method, path: "/healthz", status: 200 });
  res.status(200).send("OK");
});

// simple readiness (if you later add DB checks, put them here)
app.get("/readyz", (req, res) => {
  httpCounter.inc({ method: req.method, path: "/readyz", status: 200 });
  res.status(200).json({ ok: true });
});

// richer health
app.get("/health", async (req, res) => {
  const checks = [];

  // uptime
  checks.push({ name: "uptime", ok: true, msg: `${process.uptime().toFixed(1)}s` });

  // memory
  const rssMb = (process.memoryUsage().rss / 1024 / 1024).toFixed(1);
  checks.push({ name: "rss_mb", ok: true, msg: rssMb });

  // outbound HTTP
  try {
    const r = await fetch("https://example.com");
    checks.push({ name: "internet", ok: r.ok, msg: `HTTP ${r.status}` });
  } catch (e) {
    checks.push({ name: "internet", ok: false, msg: String(e) });
  }

  const ok = checks.every(c => c.ok);
  httpCounter.inc({ method: req.method, path: "/health", status: ok ? 200 : 503 });
  res.status(ok ? 200 : 503).json({ ok, checks, ts: new Date().toISOString() });
});

app.listen(PORT, () => {
  log.info(`HealthPulse listening on http://localhost:${PORT}`);
});
