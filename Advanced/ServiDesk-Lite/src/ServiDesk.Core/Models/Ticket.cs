namespace ServiDesk.Core.Models
{
    public enum Priority { Low, Medium, High, Critical }

    public class Ticket
    {
        public int Id { get; set; }
        public string Title { get; set; } = string.Empty;
        public string Description { get; set; } = string.Empty;

        // fields the API uses
        public Priority Priority { get; set; } = Priority.Medium;
        public string Category { get; set; } = "General";
        public string Status { get; set; } = "Open"; // Open, InProgress, Resolved, Closed
        public string Assignee { get; set; } = "Unassigned";

        public System.DateTime CreatedUtc { get; set; } = System.DateTime.UtcNow;
        public System.DateTime? ResolvedUtc { get; set; }
        public bool SlaBreached { get; set; } = false;
    }
}
