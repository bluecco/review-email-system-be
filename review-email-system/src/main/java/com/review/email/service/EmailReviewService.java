package com.review.email.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.review.email.domain.EmailReview;

public interface EmailReviewService {

	List<EmailReview> findAll();
	
	Page<EmailReview> findAllByPageOrderByDate(Pageable pageable);
	
	Page<EmailReview> findByPublished(Pageable pageable, Boolean published);
	
	EmailReview save(EmailReview entity);

	EmailReview findByMessageId(String messageId);
	
}
