using Microsoft.EntityFrameworkCore;
using ServiDesk.Core.Models;
namespace ServiDesk.Infrastructure;
public class AppDb : DbContext {
  public AppDb(DbContextOptions<AppDb> options) : base(options) {}
  public DbSet<Ticket> Tickets => Set<Ticket>();
}
