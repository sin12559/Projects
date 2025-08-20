import request from "supertest";
import app from "../src/server.js";

describe("HealthPulse endpoints", () => {
  it("GET /healthz should return ok:true", async () => {
    const res = await request(app).get("/healthz");
    expect(res.status).toBe(200);
    expect(res.body).toEqual({ ok: true });
  });

  it("GET /readyz should return ready:true", async () => {
    const res = await request(app).get("/readyz");
    expect(res.status).toBe(200);
    expect(res.body).toEqual({ ready: true });
  });

  it("GET /metrics should return Prometheus metrics", async () => {
    const res = await request(app).get("/metrics");
    expect(res.status).toBe(200);
    expect(res.text).toContain("process_cpu_seconds_total");
  });
});
