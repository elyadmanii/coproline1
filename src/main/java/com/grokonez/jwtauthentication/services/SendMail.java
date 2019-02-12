package com.grokonez.jwtauthentication.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.web.multipart.MultipartFile;

public class SendMail {

	private Properties mailServerProperties;
	private Session getMailSession;
	private MimeMessage generateMailMessage;
	private Transport transport;

	public SendMail() {
		try {
			mailServerProperties = System.getProperties();
			mailServerProperties.put("mail.smtp.port", "587");
			mailServerProperties.put("mail.smtp.host", "smtp-mail.outlook.com");
			mailServerProperties.put("mail.smtp.auth", "true");
			mailServerProperties.put("mail.smtp.starttls.enable", "true");
			getMailSession = Session.getDefaultInstance(mailServerProperties, null);
			transport = getMailSession.getTransport("smtp");
			transport.connect("smtp-mail.outlook.com", "elyadmani.abdelhafid@outlook.fr", "Elyadmani123");
		} catch (NoSuchProviderException ex) {
		} catch (MessagingException ex) {
		}
	}

	public Boolean envoi(String subject, String mailDestinatair, String bodyMail) {

		try {
			generateMailMessage = new MimeMessage(getMailSession);
			generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mailDestinatair));
			generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(mailDestinatair));
			generateMailMessage.setSubject(subject);
			generateMailMessage.setText(bodyMail);
			generateMailMessage.setFrom(new InternetAddress("elyadmani.abdelhafid@outlook.fr", "NoReply-JD"));
			transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
			transport.close();
			System.out.println("message delai envoye");
		} catch (MessagingException | UnsupportedEncodingException ex) {
			System.out.println("erreur message delai envoye");
			return false;
		}
		return true;
	}
	
	
	public Boolean envoi(String subject, String mailDestinatair, String bodyMail, List<BodyPart> files) {

		try {

			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(bodyMail, "text/html");

			// creates multi-part
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			
			for (Iterator iterator = files.iterator(); iterator.hasNext();) {
				BodyPart multipartFile = (BodyPart) iterator.next();
				multipart.addBodyPart(multipartFile);
				System.out.println("qdq");
			}

			/*if (filePath.length() > 0) {
				MimeBodyPart attachPart = new MimeBodyPart();

				try {
					attachPart.attachFile(filePath);
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				multipart.addBodyPart(attachPart);
			}*/

			generateMailMessage = new MimeMessage(getMailSession);
			generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mailDestinatair));
			generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(mailDestinatair));
			generateMailMessage.setSubject(subject);
			generateMailMessage.setText(bodyMail);
			generateMailMessage.setFrom(new InternetAddress("elyadmani.abdelhafid@outlook.fr", "NoReply-JD"));
			generateMailMessage.setContent(multipart);
			transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
			transport.close();
			System.out.println("message delai envoye Attachment");
		} catch (AddressException ex) {
			System.out.println("erreur message delai envoye");
			return false;
		} catch (MessagingException ex) {
			System.out.println("erreur message delai envoye");
			return false;
		} catch (UnsupportedEncodingException ex) {
			System.out.println("erreur message delai envoye");
			return false;
		}
		return true;
	}
	/*public Boolean envoi(String subject, String mailDestinatair, String bodyMail, String filePath) {

		try {

			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(bodyMail, "text/html");

			// creates multi-part
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			

			if (filePath.length() > 0) {
				MimeBodyPart attachPart = new MimeBodyPart();

				try {
					attachPart.attachFile(filePath);
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				multipart.addBodyPart(attachPart);
			}

			generateMailMessage = new MimeMessage(getMailSession);
			generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mailDestinatair));
			generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(mailDestinatair));
			generateMailMessage.setSubject(subject);
			generateMailMessage.setText(bodyMail);
			generateMailMessage.setFrom(new InternetAddress("elyadmani.abdelhafid@outlook.fr", "NoReply-JD"));
			generateMailMessage.setContent(multipart);
			transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
			transport.close();
			System.out.println("message delai envoye Attachment");
		} catch (AddressException ex) {
			System.out.println("erreur message delai envoye");
			return false;
		} catch (MessagingException ex) {
			System.out.println("erreur message delai envoye");
			return false;
		} catch (UnsupportedEncodingException ex) {
			System.out.println("erreur message delai envoye");
			return false;
		}
		return true;
	}*/

}
