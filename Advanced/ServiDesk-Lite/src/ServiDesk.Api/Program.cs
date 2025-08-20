using Microsoft.EntityFrameworkCore;
using ServiDesk.Core.Models;
using ServiDesk.Infrastructure;

var builder = WebApplication.CreateBuilder(args);

// Put the DB beside the built binaries so path is always correct
var dbPath = System.IO.Path.Combine(AppContext.BaseDirectory, "servi.db");
builder.Services.AddDbContext<AppDb>(opt => opt.UseSqlite($"Data Source={dbPath}"));

builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Ensure DB & tables exist
using (var scope = app.Services.CreateScope())
{
    var db = scope.ServiceProvider.GetRequiredService<AppDb>();
    db.Database.EnsureCreated();
}

app.UseSwagger();
app.UseSwaggerUI();

app.MapGet("/api/tickets", async (AppDb db) =>
  await db.Tickets.OrderByDescending(t => t.Id).ToListAsync());

app.MapGet("/api/tickets/{id:int}", async (AppDb db, int id) =>
  await db.Tickets.FindAsync(id) is Ticket t ? Results.Ok(t) : Results.NotFound());

app.MapPost("/api/tickets", async (AppDb db, Ticket t) => {
  db.Tickets.Add(t); await db.SaveChangesAsync();
  return Results.Created($"/api/tickets/{t.Id}", t);
});

app.MapPut("/api/tickets/{id:int}", async (AppDb db, int id, Ticket u) => {
  var t = await db.Tickets.FindAsync(id); if (t is null) return Results.NotFound();
  t.Title = u.Title; t.Description = u.Description; t.Priority = u.Priority;
  t.Category = u.Category; t.Status = u.Status; t.Assignee = u.Assignee;
  if (t.Status is "Resolved" or "Closed") t.ResolvedUtc = DateTime.UtcNow;
  await db.SaveChangesAsync(); return Results.NoContent();
});

app.MapDelete("/api/tickets/{id:int}", async (AppDb db, int id) => {
  var t = await db.Tickets.FindAsync(id); if (t is null) return Results.NotFound();
  db.Tickets.Remove(t); await db.SaveChangesAsync(); return Results.NoContent();
});

app.Run();
