package com.review.email.web.azure;

import java.util.List;

public class AzureDocuments {
	private List<AzureDocument> documents;

	public AzureDocuments(List<AzureDocument> documents) {
		super();
		this.documents = documents;
	}

	public List<AzureDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(List<AzureDocument> documents) {
		this.documents = documents;
	}
	
	
}
