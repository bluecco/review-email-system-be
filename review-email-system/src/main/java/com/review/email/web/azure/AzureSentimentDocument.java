package com.review.email.web.azure;

public class AzureSentimentDocument {

	private double score;
	private String id;
	
	public AzureSentimentDocument(double score, String id) {
		super();
		this.score = score;
		this.id = id;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
