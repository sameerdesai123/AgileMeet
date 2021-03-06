package com.agilemeet.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class SendMail {
	public static void sendMail(String emailId, String con) throws IOException{
			Logger logger = LoggerFactory.getLogger(SendMail.class);
			Email from = new Email("sameerdesaicsed2@vjit.ac.in");
		    String subject = "Notification task assignment";
		    Email to = new Email(emailId);
		    Content content = new Content("text/plain", con);
		    Mail mail = new Mail(from, subject, to, content);
		    SendGrid sg = new SendGrid("");
		    Request request = new Request();
		    try {
		      request.setMethod(Method.POST);
		      request.setEndpoint("mail/send");
		      request.setBody(mail.build());
		      Response response = sg.api(request);
		      System.out.println(response.getStatusCode());
		      System.out.println(response.getBody());
		      System.out.println(response.getHeaders());
		      logger.info("SENT MAIL : \n" + emailId + "\n" + con);
		    } catch (IOException ex) {
		    	ex.printStackTrace();
		    }
	}
}
