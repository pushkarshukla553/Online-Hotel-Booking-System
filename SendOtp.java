import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SendOtp extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void service(HttpServletRequest req, HttpServletResponse res)throws IOException,ServletException{
		res.setContentType("text/html");
		
		Tools tool = new Tools();
		
		try {
			String otp = tool.generateOTP();
			System.out.println("\n\n"+otp+"\n\n");
			//tool.sendEmail(cemail, otp);
			System.out.println(otp);
			Cookie cookie = new Cookie("otp",otp);
			cookie.setMaxAge(300);
			res.addCookie(cookie);
			RequestDispatcher dis = req.getRequestDispatcher("/Authenciation.html");
			dis.forward(req,res);
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
