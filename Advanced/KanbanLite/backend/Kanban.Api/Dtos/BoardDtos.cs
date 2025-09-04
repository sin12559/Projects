namespace Kanban.Api.Dtos;

public record BoardCreateDto(string Title);
public record ColumnCreateDto(string Title, int Order);
public record CardCreateDto(string Title, string? Description, int Order);
