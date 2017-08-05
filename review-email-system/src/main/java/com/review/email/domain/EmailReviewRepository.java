package com.review.email.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmailReviewRepository extends PagingAndSortingRepository<EmailReview, String> {
	
	EmailReview findByMessageId(String messageId);

	Page<EmailReview> findByPublished(Pageable pageable, Boolean published);
}
