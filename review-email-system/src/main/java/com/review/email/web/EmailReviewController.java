package com.review.email.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.review.email.domain.EmailReview;
import com.review.email.service.EmailReviewService;

@RestController
public class EmailReviewController {
	
	@Autowired
	final EmailReviewService emailReviewService;
	
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
	public Page<EmailReview> getAdminEmails(@SortDefault(sort = "timestamp", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<EmailReview> emails = emailReviewService.findAllByPageOrderByDate(pageable);
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
	
	@RequestMapping(value = "/reviews/published", method=RequestMethod.GET)
	@ResponseBody
	public Page<EmailReview> getPublishedReviews(@SortDefault(sort = "timestamp", direction = Sort.Direction.DESC) Pageable pageable) {
		return emailReviewService.findByPublished(pageable, true);
	}
	
}
