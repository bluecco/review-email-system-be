package com.review.email.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Null;

import org.springframework.util.Assert;

@Entity
@Table(name = "EmailReview")
public class EmailReview {
	@Id
	private String messageId;
	private Timestamp arrivalDate;
	private String from;
	private String fromDisplayName;
	private String subject;
	private String body;	
	@Null
	private Double score;
	private boolean published;
	
	public EmailReview(String messageId, Timestamp arrivalDate, String from, String fromDisplayName, String subject,
			String body, Double score, boolean published) {
		super();
		
		Assert.hasLength(messageId, "messageId must not be empty");
		Assert.hasLength(from, "from must not be empty");
		
		this.messageId = messageId;
		this.arrivalDate = arrivalDate;
		this.from = from;
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

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
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
		return "EmailReview [messageId=" + messageId + ", arrivalDate=" + arrivalDate + ", from=" + from
				+ ", fromDisplayName=" + fromDisplayName + ", subject=" + subject + ", body=" + body + ", score="
				+ score + ", published=" + published + "]";
	}
}
