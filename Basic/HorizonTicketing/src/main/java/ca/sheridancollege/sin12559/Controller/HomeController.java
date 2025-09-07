package ca.sheridancollege.sin12559.Controller;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.sin12559.Database.HorizonDBMS;
import ca.sheridancollege.sin12559.beans.Horizon;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class HomeController {
	
	@GetMapping("/")
	public String Root() {
		return "root.html";
	}
	
	@GetMapping("/Purchase Ticket")
	public String PurchaseTicket(){
		return "Purchase.html";
	}
	
	@PostMapping("/Response")
	public String Response(@RequestParam String name, 
            				@RequestParam int Age,
            				@RequestParam String Address,
            				@RequestParam String SEX,
            				@RequestParam String cardnumber,
            				@RequestParam String expiry,
            				@RequestParam int CVV)
{

		Horizon horizons = new Horizon(name, Age, Address, cardnumber, SEX, expiry, CVV);
		HorizonDBMS.horizondata.add(horizons);
		return "root.html"; 
	}
	
	@GetMapping("/view")
    public void viewticket(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>View Tickets</title></head>");
        out.println("<body>");
        out.println("<style>table{border: solid black;} header{text-align: center; background-color: black; line-height: 125px; font-size: xx-large; color: white;} html{background-color:orangered}</style>");

        out.println("<header>Ticket</header>");
        out.println("<table>");
        out.println("<tr><th>Name</th><th>Age</th><th>Address</th><th>Sex</th></tr>");

        for (Horizon horizondata : HorizonDBMS.horizondata) {
            out.println("<tr>");
            out.println("<td>" + horizondata.getName() + "</td>");
            out.println("<td>" + horizondata.getAge() + "</td>");
            out.println("<td>" + horizondata.getAddress() + "</td>");
            out.println("<td>" + horizondata.getSEX() + "</td>");
            
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("<a href='/'>back to home page</a>");
        out.println("</body>");
        out.println("</html>");

        out.close();
    }
}
