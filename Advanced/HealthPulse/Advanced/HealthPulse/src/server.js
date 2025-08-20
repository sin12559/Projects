import express from "express";
import helmet from "helmet";
import client from "prom-client";
import pino from "pino";

const app = express();
const logger = pino();

// Middleware
app.use(helmet());
app.use(express.json());

// Metrics
const collectDefaultMetrics = client.collectDefaultMetrics;
collectDefaultMetrics();

app.get("/healthz", (req, res) => {
  res.json({ ok: true });
});

app.get("/readyz", (req, res) => {
  res.json({ ready: true });
});

app.get("/metrics", async (req, res) => {
  res.set("Content-Type", client.register.contentType);
  res.end(await client.register.metrics());
});

const port = process.env.PORT || 8080;

// Only start server if not testing
if (process.env.NODE_ENV !== "test") {
  app.listen(port, () => {
    logger.info(`HealthPulse listening on http://localhost:${port}`);
  });
}

export default app;
