import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Authentication extends HttpServlet{
	
	private int type=1;
	public void service(HttpServletRequest req, HttpServletResponse res)throws IOException,ServletException{
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		
		Cookie ck[] = req.getCookies();
		
		String otp = req.getParameter("otp");
		String old_otp="";
		int bid=0;
		int i=0;
		while(ck[i]!=null){
			if(ck[i].getName().equals("otp")){
				old_otp = ck[i].getValue();
			}
		}
		i=0;
		while(ck[i]!=null){
			if(ck[i].getName().equals("bid")){
				bid = Integer.parseInt(ck[i].getValue());
			}
		}
		
		if(otp.equals(old_otp)){
			RequestDispatcher rd = req.getRequestDispatcher("show");
			rd.include(req,res);
		}
		else
		{
			pw.println("Authentication failed!!!");
			pw.println("<br><a href=\"Home.html\">Go back</a>");
			
			try{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con =  DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","password");
				
				PreparedStatement ps = con.prepareStatement("delete from bookings where bid = ?");
				ps.setInt(1,bid);
				ps.executeUpdate();
				
				ps = con.prepareStatement("delete from book_data where bid = ?");
				ps.setInt(1,bid);
				ps.executeUpdate();
			}
			catch(Exception e){
				System.out.println(e);
			}
		}
	}
}