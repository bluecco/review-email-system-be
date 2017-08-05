package com.review.email.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.review.email.domain.EmailReview;
import com.review.email.service.EmailReviewService;
import com.review.email.web.azure.AzureDocument;
import com.review.email.web.azure.AzureDocuments;
import com.review.email.web.azure.AzureSentimentDocuments;

@RestController
@EnableSpringDataWebSupport
public class EmailReviewController {
	
	@Autowired
	final EmailReviewService emailReviewService;
	
	@Value("${az.api.key}")
    private String apiKey;
	
	@Value("${az.sentiment.endpoint}")
	private String sentimentEndpoint;
	
	public EmailReviewController(EmailReviewService emailReviewService) {
		this.emailReviewService = emailReviewService;
	}
	
	@RequestMapping(value = "/reviews", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public EmailReview addCustomer(@RequestBody EmailReview emailReview) {
		EmailReview er = emailReviewService.save(emailReview);
		return er;
	}

	@RequestMapping(value = "/reviews", method=RequestMethod.GET)
	@ResponseBody
	public Page<EmailReview> getAdminEmails(
			@RequestParam(value="size", required = true, defaultValue = "20") Integer size,
	        @RequestParam(value="page", required = true, defaultValue = "0") int page,
	        @RequestParam(value="sort", required = false, defaultValue = "arrivalDate") String sort,
	        @RequestParam(value="direction", required = false, defaultValue = "asc") String sortDirection) {
		
		Sort sorting = new Sort(sortDirection, sort);
		PageRequest pg = new PageRequest(page, size, sorting);
		Page<EmailReview> emails = emailReviewService.findAllByPageOrderByDate(pg);
		return emails;
	}
	
	@RequestMapping(value = "/reviews/{messageId}/publish", method=RequestMethod.PUT)
	@ResponseBody
	public EmailReview publishReview(@PathVariable("messageId") String messageId) {
		EmailReview emailReview = emailReviewService.findByMessageId(messageId);
		
		if (emailReview == null) {
			throw new ResourceNotFoundException("Email not found");
		}
		
		emailReview.setPublished(!emailReview.isPublished());
		emailReviewService.save(emailReview);	
		return emailReview;
	}
	
	@RequestMapping(value = "/reviews/{messageId}/analyze", method=RequestMethod.PUT)
	@ResponseBody
	public EmailReview analyzeReview(@PathVariable("messageId") String messageId) {
		return null;
	}
	
	@RequestMapping(value = "/reviews/published", method=RequestMethod.GET)
	@ResponseBody
	public Page<EmailReview> getPublishedReviews(
			@RequestParam(value="size", required = true, defaultValue = "20") Integer size,
	        @RequestParam(value="page", required = true, defaultValue = "0") int page,
	        @RequestParam(value="sort", required = false, defaultValue = "arrivalDate") String sort,
	        @RequestParam(value="direction", required = false, defaultValue = "desc") String sortDirection) {
		
		Sort sorting = new Sort(sortDirection, sort);
		PageRequest pg = new PageRequest(page, size, sorting);
		return emailReviewService.findByPublished(pg, true);
	}
	
}
