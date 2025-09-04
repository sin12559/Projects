using System.Collections.Generic;

namespace Kanban.Api.Models;

public class Board
{
    public int Id { get; set; }
    public string Title { get; set; } = default!;
    public int OwnerId { get; set; }
    public User Owner { get; set; } = default!;
    public ICollection<Column> Columns { get; set; } = new List<Column>();
}
