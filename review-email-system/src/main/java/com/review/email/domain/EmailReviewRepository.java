package com.review.email.domain;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmailReviewRepository extends PagingAndSortingRepository<EmailReview, String> {

}
