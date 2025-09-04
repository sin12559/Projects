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
public class BoardsController : ControllerBase
{
    private readonly AppDbContext _db;
    public BoardsController(AppDbContext db) => _db = db;

    int GetUserId()
    {
        var sub = User.FindFirstValue("sub") ?? User.FindFirstValue(ClaimTypes.NameIdentifier);
        return int.Parse(sub!);
    }

    // GET /api/boards  (list only your boards)
    [HttpGet]
    public async Task<IActionResult> Get()
    {
        var uid = GetUserId();
        var boards = await _db.Boards.AsNoTracking()
            .Where(b => b.OwnerId == uid)
            .Select(b => new { b.Id, b.Title })
            .ToListAsync();
        return Ok(boards);
    }

    public record CreateDto(string Title);

    // POST /api/boards  (create board for you)
    [HttpPost]
    public async Task<IActionResult> Create([FromBody] CreateDto dto)
    {
        if (string.IsNullOrWhiteSpace(dto.Title)) return BadRequest("Title required");
        var uid = GetUserId();
        var board = new Board { Title = dto.Title, OwnerId = uid };
        _db.Boards.Add(board);
        await _db.SaveChangesAsync();
        return Ok(new { board.Id, board.Title });
    }

    // GET /api/boards/{id}  (details + columns + cards)
    [HttpGet("{id:int}")]
    public async Task<IActionResult> GetOne(int id)
    {
        var uid = GetUserId();
        var board = await _db.Boards
            .AsNoTracking()
            .Include(b => b.Columns.OrderBy(c => c.Order))
                .ThenInclude(c => c.Cards.OrderBy(k => k.Order))
            .Where(b => b.OwnerId == uid && b.Id == id)
            .Select(b => new {
                b.Id,
                b.Title,
                Columns = b.Columns.Select(c => new {
                    c.Id, c.Title, c.Order,
                    Cards = c.Cards.Select(k => new { k.Id, k.Title, k.Description, k.Order })
                })
            })
            .FirstOrDefaultAsync();

        return board is null ? NotFound() : Ok(board);
    }

    public record CreateColumnDto(string Title, int Order);

    // POST /api/boards/{id}/columns  (add column to your board)
    [HttpPost("{id:int}/columns")]
    public async Task<IActionResult> AddColumn(int id, [FromBody] CreateColumnDto dto)
    {
        if (string.IsNullOrWhiteSpace(dto.Title)) return BadRequest("Title required");
        var uid = GetUserId();

        var board = await _db.Boards.FirstOrDefaultAsync(b => b.Id == id && b.OwnerId == uid);
        if (board is null) return NotFound();

        var col = new Column { Title = dto.Title, Order = dto.Order, BoardId = board.Id };
        _db.Columns.Add(col);
        await _db.SaveChangesAsync();
        return Ok(new { col.Id, col.Title, col.Order });
    }
}
