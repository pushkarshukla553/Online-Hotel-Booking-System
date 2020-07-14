import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SingleBooking extends HttpServlet{
	
	private int type=2;
	public void service(HttpServletRequest req, HttpServletResponse res)throws IOException,ServletException{
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		
		String cname = req.getParameter("cname");
		String cemail = req.getParameter("cemail");
		String in_date = req.getParameter("Indate")+"/"+req.getParameter("Inmonth")+"/"+req.getParameter("Inyear");
		String out_date = req.getParameter("Outdate")+"/"+req.getParameter("Outmonth")+"/"+req.getParameter("Outyear");
		int guests = Integer.parseInt(req.getParameter("peoples"));
		int room_req = Integer.parseInt(req.getParameter("roomfreq"));
		
		Tools tool = new Tools();
		
		long bid = tool.getBookingId();
		
		
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con =  DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","password");
			
			PreparedStatement ps = con.prepareStatement("insert into bookings values(?,?,?,?,?)");
			ps.setString(1,cemail);
			ps.setLong(2,bid);
			ps.setString(3,cname);
			ps.setString(4,tool.getDate());
			ps.setString(5,tool.getTime());
			ps.executeUpdate();
			
			PreparedStatement prs = con.prepareStatement("insert into book_data values(?,?,?,?,?,?)");
			prs.setLong(1,bid);
			prs.setString(2,in_date);
			prs.setString(3,out_date);
			prs.setInt(4,guests);
			prs.setInt(5,room_req);
			prs.setInt(6,type);
			prs.executeUpdate();
			
			System.out.println("\n\n\nData Entered\n\n\n");
			
			Cookie cookie = new Cookie("Hotel",Long.toString(bid));
			cookie.setMaxAge(3600);
			res.addCookie(cookie);
			
			RequestDispatcher rd = req.getRequestDispatcher("Sendotp");
			rd.forward(req,res);
			
		}
		catch(Exception e){
			System.out.println(e);
			RequestDispatcher rd = req.getRequestDispatcher("/singleroom.html");
			rd.forward(req,res);
		}
	}
}