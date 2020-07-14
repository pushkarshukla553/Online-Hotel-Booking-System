import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ShowReciept extends HttpServlet{
	
	public void service(HttpServletRequest req, HttpServletResponse res)throws IOException,ServletException{
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		
		int bid = 0;
		Cookie ck[] = req.getCookies();
		int i=0;
		while(ck[i]!=null){
			if(ck[i].getName().equals("bid")){
				bid = Integer.parseInt(ck[i].getValue());
			}
		}
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con =  DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","password");
			
			PreparedStatement ps = con.prepareStatement("Select b.c_email,b.cust_name,bd.checkin,bd.checkout,bd.guest_freq,bd.room_req,bd.roomtype from bookings b,book_data bd where b.book_id=bd.book_id and bookings.bid = ?");
			ps.setInt(1,bid);
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			String cname = rs.getString(2);
			String cemail = rs.getString(1);
			String in_date = rs.getString(3);
			String out_date = rs.getString(4);
			int guests = rs.getInt(5);
			int room_req = rs.getInt(6);
			int type = rs.getInt(7);
			
			ps = con.prepareStatement("Select room_price from rooms where type = ?");
			ps.setInt(1,type);
			rs = ps.executeQuery();
			
			rs.next();
			int rate = rs.getInt(1);
			
			pw.println("Customer name : "+cname);
			pw.println("Customer email : "+cemail);
			pw.println("Check in date : "+in_date);
			pw.println("Check out date : "+out_date);
			pw.println("Guest Frequency : "+guests);
			pw.println("Room Frequency : "+room_req);
			pw.println("Amount to be paid : "+(room_req*rate));
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}