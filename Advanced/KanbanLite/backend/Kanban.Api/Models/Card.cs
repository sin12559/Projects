namespace Kanban.Api.Models;

public class Card
{
    public int Id { get; set; }
    public string Title { get; set; } = default!;
    public string? Description { get; set; }
    public int Order { get; set; }
    public int ColumnId { get; set; }
    public Column Column { get; set; } = default!;
}
