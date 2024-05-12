package com.fastshop.net.service.impl;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;

import com.fastshop.net.model.MailInfo;
import com.fastshop.net.service._MailService;

@Service
public class _MailServiceImpl implements _MailService{
	@Autowired
	JavaMailSender sender;
	List<MailInfo> list = new ArrayList<>();

	@Override
	public void send(MailInfo mail) throws MessagingException{
		// Tạo message
		MimeMessage message = sender.createMimeMessage();
		// Sử dụng Helper để thiết lập các thông tin cần thiết cho message
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		helper.setFrom(mail.getFrom());
		helper.setTo(mail.getTo());
		helper.setSubject(mail.getSubject());
		helper.setText(mail.getBody(), true);
		helper.setReplyTo(mail.getFrom());
		String[] cc = mail.getCc();
		if(cc != null && cc.length > 0) {
			helper.setCc(cc);
		}
		String[] bcc = mail.getBcc();
		if(bcc != null && bcc.length > 0) {
			helper.setBcc(bcc);
		}
		String[] attachments = mail.getAttachments();
		if(attachments != null && attachments.length > 0) {
			for(String path: attachments) {
				File file = new File(path);
				helper.addAttachment(file.getName(), file);
			}
		}
		// Gửi message đến SMTP server
		sender.send(message);
	}
	
	@Override
	public void send(String to, String subject, String body)
			throws MessagingException {
		this.send(new MailInfo(to,subject,body));
	}

	@Override
	public void queue(MailInfo mail) {
		list.add(mail);
	}

	@Override
	public void queue(String to, String subject, String body) {
		queue(new MailInfo(to, subject, body));
	}
	
	@Override
	@Scheduled(fixedDelay = 5000)
	public void run() {
		while (!list.isEmpty()) {
			// giu lai gia tri can xoa truoc khi xoa khoi list
			MailInfo mailInfo = list.remove(0);
			try {
				this.send(mailInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}

