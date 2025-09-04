using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Kanban.Api.Data;
using Kanban.Api.Models;
using System.Security.Claims;

namespace Kanban.Api.Controllers;

[ApiController]
[Route("api/[controller]")]
[Authorize]
public class ColumnsController : ControllerBase
{
    private readonly AppDbContext _db;
    public ColumnsController(AppDbContext db) => _db = db;

    int GetUserId()
    {
        var sub = User.FindFirstValue("sub") ?? User.FindFirstValue(ClaimTypes.NameIdentifier);
        return int.Parse(sub!);
    }

    public record CreateCardDto(string Title, string? Description, int Order);

    // POST /api/columns/{columnId}/cards
    [HttpPost("{columnId:int}/cards")]
    public async Task<IActionResult> AddCard(int columnId, [FromBody] CreateCardDto dto)
    {
        if (string.IsNullOrWhiteSpace(dto.Title)) return BadRequest("Title required");
        var uid = GetUserId();

        var column = await _db.Columns
            .Include(c => c.Board)
            .FirstOrDefaultAsync(c => c.Id == columnId && c.Board.OwnerId == uid);

        if (column is null) return NotFound();

        var card = new Card
        {
            Title = dto.Title,
            Description = dto.Description,
            Order = dto.Order,
            ColumnId = column.Id
        };

        _db.Cards.Add(card);
        await _db.SaveChangesAsync();
        return Ok(new { card.Id, card.Title, card.Description, card.Order, columnId = column.Id });
    }
}
