namespace Kanban.Api.Dtos;

public record RegisterDto(string Name, string Email, string Password);
public record LoginDto(string Email, string Password);
public record AuthResult(string Token, string Name, string Email);
