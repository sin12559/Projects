using ServiDesk.Core.Models;
using Xunit;
public class TicketTests {
  [Fact]
  public void Defaults() {
    var t = new Ticket { Title = "Printer down" };
    Assert.Equal("Open", t.Status);
    Assert.False(t.SlaBreached);
  }
}
