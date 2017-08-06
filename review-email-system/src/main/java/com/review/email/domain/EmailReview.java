package com.review.email.domain;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.util.Assert;

@Entity
@Table(name = "EmailReview")
public class EmailReview {
	@Id
	private String messageId;
	private Timestamp arrivalDate;
	private String fromEmail;
	private String fromDisplayName;
	private String subject;
	private String body;	
	private Double score;
	private boolean published;
	
	public EmailReview() {}
	
	public EmailReview(String messageId, Timestamp arrivalDate, String fromEmail, String fromDisplayName, String subject,
			String body, Double score, boolean published) {
		super();
		
		Assert.hasLength(messageId, "messageId must not be empty");
		Assert.hasLength(fromEmail, "from must not be empty");
		
		this.messageId = messageId;
		this.arrivalDate = arrivalDate;
		this.fromEmail = fromEmail;
		this.fromDisplayName = fromDisplayName;
		this.subject = subject;
		this.body = body;
		this.score = score;
		this.published = published;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Timestamp getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Timestamp arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String from) {
		this.fromEmail = from;
	}

	public String getFromDisplayName() {
		return fromDisplayName;
	}

	public void setFromDisplayName(String fromDisplayName) {
		this.fromDisplayName = fromDisplayName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	@Override
	public String toString() {
		return "EmailReview [messageId=" + messageId + ", arrivalDate=" + arrivalDate + ", fromEmail=" + fromEmail
				+ ", fromDisplayName=" + fromDisplayName + ", subject=" + subject + ", body=" + body + ", score="
				+ score + ", published=" + published + "]";
	}
}
