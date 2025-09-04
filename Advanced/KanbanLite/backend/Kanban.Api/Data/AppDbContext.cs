using Kanban.Api.Models;
using Microsoft.EntityFrameworkCore;

namespace Kanban.Api.Data;

public class AppDbContext(DbContextOptions<AppDbContext> opts) : DbContext(opts)
{
    public DbSet<User> Users => Set<User>();
    public DbSet<Board> Boards => Set<Board>();
    public DbSet<Column> Columns => Set<Column>();
    public DbSet<Card> Cards => Set<Card>();

    protected override void OnModelCreating(ModelBuilder b)
    {
        b.Entity<User>().HasIndex(u => u.Email).IsUnique();

        b.Entity<Board>()
            .HasOne(x => x.Owner).WithMany(u => u.Boards)
            .HasForeignKey(x => x.OwnerId).OnDelete(DeleteBehavior.Cascade);

        b.Entity<Column>()
            .HasOne(c => c.Board).WithMany(bd => bd.Columns)
            .HasForeignKey(c => c.BoardId).OnDelete(DeleteBehavior.Cascade);

        b.Entity<Card>()
            .HasOne(c => c.Column).WithMany(col => col.Cards)
            .HasForeignKey(c => c.ColumnId).OnDelete(DeleteBehavior.Cascade);
    }
}
