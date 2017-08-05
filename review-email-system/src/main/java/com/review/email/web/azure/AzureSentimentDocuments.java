package com.review.email.web.azure;

import java.util.List;

public class AzureSentimentDocuments {
	private List<AzureSentimentDocument> documents;

	public AzureSentimentDocuments(List<AzureSentimentDocument> documents) {
		super();
		this.documents = documents;
	}

	public List<AzureSentimentDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(List<AzureSentimentDocument> documents) {
		this.documents = documents;
	}
	
	
}
