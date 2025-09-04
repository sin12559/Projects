using System.Collections.Generic;

namespace Kanban.Api.Models;

public class Column
{
    public int Id { get; set; }
    public string Title { get; set; } = default!;
    public int Order { get; set; }
    public int BoardId { get; set; }
    public Board Board { get; set; } = default!;
    public ICollection<Card> Cards { get; set; } = new List<Card>();
}
