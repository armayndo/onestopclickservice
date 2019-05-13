package com.osc.server.util;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;

import com.osc.config.EmailProperties;
import com.osc.server.model.User;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@Slf4j
public class EmailUtil {
	
	private User user;
	
	@Autowired
	private EmailProperties emailProperties;

	
	public boolean sendEmail (String email, String token, String username) {
		log.info("Inside sendEmail1");
		Map<String, String> data = new HashMap<String, String>();
		data.put("email", email);
		data.put("token", token);
		data.put("username", username);
		
		try {
			if(sendEmail(data)) {
				return true;
			}else {
				return false;
			}
		} catch (MessagingException | IOException e) {	
			log.error(e.getMessage(), e);
		}
		
		return false;
	}
	
	public boolean sendEmail(User user, String token) {
		
		Map<String, String> data = new HashMap<String, String>();
		data.put("email", user.getEmail());
		data.put("token", token);
		
		try {
			if(sendEmail(data)) {
				return true;
			}else {
				return false;
			}
		} catch (MessagingException | IOException e) {	
			log.error(e.getMessage(), e);
		}
		
		return false;
	}
	
	public boolean sendEmail(Map<String, String> data) throws AddressException, MessagingException, IOException {
		
		log.info("Inside sendEmail2");
		String token = data.get("token");
		String email = data.get("email");
		String username = data.get("username");
		
		try {
			log.info("Token-Inside sendEmail2: "+token);
			log.info("email-Inside sendEmail2: "+email);
			/*log.info("Email From: "+emailProperties.getEmail());
			log.info("Password From: "+emailProperties.getPassword());*/
			
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			      protected PasswordAuthentication getPasswordAuthentication() {
			    	  return new PasswordAuthentication("cdcbootcamp@gmail.com", "CdcJavaBootcamp");
			          //return new PasswordAuthentication(emailProperties.getEmail(), emailProperties.getPassword());
			      }
			});
			
			Message message = new MimeMessage(session);
			/*message.setFrom(new InternetAddress(emailProperties.getEmail(), false));*/
			message.setFrom(new InternetAddress("admin@onestopclick.com", false));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("[OneStopClick-Admin] Your password reset request");
			message.setContent("[OneStopClick-Admin] Your password reset request", "text/html");
			message.setSentDate(new Date());

			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent("Please klik the following link to reset your password, <br/> <a href = \"http://localhost:3000/reset?token=" +token+"&username="+username+"\">Reset Password</a>", "text/html");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			   
			message.setContent(multipart);
			Transport.send(message); 
		}catch(Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
		
		return true;
	}
}
