package com.review.email.web.azure;

import java.util.ArrayList;

public class AzureSentimentDocuments {
	
	private ArrayList<AzureSentimentDocument> documents;
	
	public AzureSentimentDocuments() {}
	
	public AzureSentimentDocuments(ArrayList<AzureSentimentDocument> documents) {
		super();
		this.documents = documents;
	}

	public ArrayList<AzureSentimentDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(ArrayList<AzureSentimentDocument> documents) {
		this.documents = documents;
	}
	
	
}
