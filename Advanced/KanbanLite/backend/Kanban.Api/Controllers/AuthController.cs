using Kanban.Api.Data;
using Kanban.Api.Dtos;
using Kanban.Api.Models;
using Kanban.Api.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace Kanban.Api.Controllers;

[ApiController]
[Route("api/auth")]
public class AuthController(AppDbContext db, JwtService jwt) : ControllerBase
{
    [HttpPost("register")]
    public async Task<ActionResult<AuthResult>> Register(RegisterDto dto)
    {
        if (await db.Users.AnyAsync(u => u.Email == dto.Email))
            return BadRequest("Email already exists");

        var user = new User
        {
            Name = dto.Name,
            Email = dto.Email,
            PasswordHash = BCrypt.Net.BCrypt.HashPassword(dto.Password)
        };
        db.Users.Add(user);
        await db.SaveChangesAsync();

        var token = jwt.Create(user);
        return new AuthResult(token, user.Name, user.Email);
    }

    [HttpPost("login")]
    public async Task<ActionResult<AuthResult>> Login(LoginDto dto)
    {
        var user = await db.Users.FirstOrDefaultAsync(x => x.Email == dto.Email);
        if (user is null || !BCrypt.Net.BCrypt.Verify(dto.Password, user.PasswordHash))
            return Unauthorized("Invalid credentials");

        var token = jwt.Create(user);
        return new AuthResult(token, user.Name, user.Email);
    }
}
