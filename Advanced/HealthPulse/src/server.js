import express from "express";
import helmet from "helmet";
import client from "prom-client";
import pino from "pino";
import path from "path";
import { fileURLToPath } from "url";
import fs from "fs/promises";

const __filename = fileURLToPath(import.meta.url);
const __dirname  = path.dirname(__filename);

const app = express();
const logger = pino({ level: process.env.LOG_LEVEL || "info" });
const startedAt = Date.now();

// middleware
app.use(helmet());
app.use(express.json());
app.set("json spaces", 2);
app.use(express.static(path.join(__dirname, "..", "public")));

// --- helpers ---
function procSummary() {
  const mem = process.memoryUsage();
  return {
    pid: process.pid,
    node: process.version,
    platform: process.platform,
    uptimeSec: Number(process.uptime().toFixed(2)),
    memory: {
      rssMB: +(mem.rss / 1048576).toFixed(2),
      heapUsedMB: +(mem.heapUsed / 1048576).toFixed(2),
      heapTotalMB: +(mem.heapTotal / 1048576).toFixed(2),
    },
    startedAtISO: new Date(startedAt).toISOString(),
  };
}
async function pkgInfo() {
  try {
    const txt = await fs.readFile(path.join(__dirname, "..", "package.json"), "utf8");
    const p = JSON.parse(txt);
    return { name: p.name, version: p.version };
  } catch {
    return { name: "healthpulse", version: "0.0.0" };
  }
}
function parseTargets() {
  const raw = process.env.HEALTH_ENDPOINTS || process.env.HEALTH_TARGETS || "";
  return raw.split(",").map(s => s.trim()).filter(Boolean);
}
async function checkUrl(url, timeoutMs = +(process.env.TIMEOUT_MS || 2500)) {
  const ctrl = new AbortController();
  const t = setTimeout(() => ctrl.abort(), timeoutMs);
  const started = Date.now();
  try {
    const r = await fetch(url, { signal: ctrl.signal });
    const ms = Date.now() - started;
    clearTimeout(t);
    return { url, ok: r.ok, status: r.status, ms };
  } catch (e) {
    const ms = Date.now() - started;
    clearTimeout(t);
    return { url, ok: false, error: String(e), ms };
  }
}
async function runChecks() {
  const targets = parseTargets();
  if (!targets.length) return [];
  const results = await Promise.all(targets.map(u => checkUrl(u)));
  return results;
}

// --- endpoints ---
app.get("/health", (_req, res) => {
  res.status(200).json({ status: "ok", ts: new Date().toISOString() });
});

app.get("/live", (_req, res) => {
  res.status(200).json({ status: "alive", ts: new Date().toISOString() });
});

app.get("/ready", async (_req, res) => {
  const checks = await runChecks();
  const anyFail = checks.some(c => !c.ok);
  const ready = !anyFail;
  res.status(ready ? 200 : 503).json({
    status: ready ? "ready" : "degraded",
    checks,
    ts: new Date().toISOString(),
  });
});

app.get("/status", async (_req, res) => {
  const info = procSummary();
  const pkg = await pkgInfo();
  const checks = await runChecks();
  const anyFail = checks.some(c => !c.ok);
  res.json({
    service: pkg.name || "healthpulse",
    version: pkg.version,
    healthy: true,
    ready: checks.length ? !anyFail : true,
    checksCount: checks.length,
    info,
  });
});

app.get("/uptime", (_req, res) => {
  res.json({ uptimeSec: Number(process.uptime().toFixed(2)), startedAtISO: new Date(startedAt).toISOString() });
});

app.get("/info", async (_req, res) => {
  res.json({ ...(await pkgInfo()), ...procSummary() });
});

app.get("/checks", async (_req, res) => {
  res.json({ targets: parseTargets(), results: await runChecks() });
});

app.get("/probe", async (req, res) => {
  const url = req.query.url;
  if (!url) return res.status(400).json({ error: "url query is required" });
  const out = await checkUrl(String(url));
  res.status(out.ok ? 200 : 503).json(out);
});

// metrics (safe)
try {
  if (!client.register.getSingleMetric("process_cpu_user_seconds_total")) {
    client.collectDefaultMetrics();
  }
  app.get("/metrics", async (_req, res) => {
    res.set("Content-Type", client.register.contentType);
    res.end(await client.register.metrics());
  });
} catch (e) {
  logger.warn({ err: String(e) }, "metrics setup skipped");
}

// dashboard root
app.get("/", (_req, res) => {
  res.sendFile(path.join(__dirname, "..", "public", "index.html"));
});

const PORT = process.env.PORT || 8080;
app.listen(PORT, () => logger.info(`HealthPulse listening on http://localhost:${PORT}`));

export default app;
