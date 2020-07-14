import java.util.*;
import java.text.*;
import javax.mail.*; 
import javax.mail.internet.*; 
import javax.activation.*; 
import javax.mail.Session; 
import javax.mail.Transport; 
public class Tools{
	
	public String getDate(){
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		return df.format(d);
	}
	
	public String getTime(){
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.format(d);
	}
	
	public long getBookingId(){
		long id = 0;
		for(int i = 1; i <= 10; i++){
			double r = Math.random() * 10;
			id = id*10 + (int)r;
		}
		return id;
	}
	
	public String generateOTP(){
		String otp = "";
		for(int i=1; i<=6; i++){
			double r = Math.random() * 10;
			otp = otp + Integer.toString((int)r);
		}
		return otp;
	}
	
	public void sendEmail(String reciever,String mes){
		String to = reciever;
		String subject = "Your otp";
		String msg = mes;
		final String from ="sjuyal066@gmail.com";
		final  String password ="tester66";

		try{
		Properties props = new Properties();  
		props.setProperty("mail.transport.protocol", "smtps");     
		props.setProperty("mail.host", "smtp.gmail.com");  
		props.put("mail.smtp.auth", "true");  
		props.put("mail.smtp.port", "465");  
		props.put("mail.debug", "true");  
		props.put("mail.smtp.socketFactory.port", "465");  
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
		props.put("mail.smtp.socketFactory.fallback", "false");  
		Session session = Session.getInstance(props,  
		new javax.mail.Authenticator() {
		   protected PasswordAuthentication getPasswordAuthentication() {  
		   return new PasswordAuthentication(from,password);  
	   }  
	   });  

	   //session.setDebug(true);  
	   Transport transport = session.getTransport();  
	   InternetAddress addressFrom = new InternetAddress(from);  

	   MimeMessage message = new MimeMessage(session);  
	   message.setSender(addressFrom);  
	   message.setSubject(subject);  
	   message.setContent(msg, "text/plain");  
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));  

		transport.connect();  
		transport.send(message);  
		transport.close();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	public static void main(String[]args)throws Exception{
		Tools t = new Tools();
		t.sendEmail("sjuyal066@gmail.com",t.generateOTP());
	}
}