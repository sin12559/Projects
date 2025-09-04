using System.Collections.Generic;

namespace Kanban.Api.Models;

public class User
{
    public int Id { get; set; }
    public string Email { get; set; } = default!;
    public string PasswordHash { get; set; } = default!;
    public string Name { get; set; } = default!;
    public ICollection<Board> Boards { get; set; } = new List<Board>();
}
