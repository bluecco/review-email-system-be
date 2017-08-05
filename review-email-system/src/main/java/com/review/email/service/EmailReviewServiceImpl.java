package com.review.email.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.review.email.domain.EmailReview;
import com.review.email.domain.EmailReviewRepository;

@Service
@Transactional
public class EmailReviewServiceImpl implements EmailReviewService {
	
	@Autowired
	final EmailReviewRepository emailReviewRepository;
	
	public EmailReviewServiceImpl(EmailReviewRepository emailReviewRepository) {
		this.emailReviewRepository = emailReviewRepository;
	}
	
	@Override
	public EmailReview save(EmailReview entity) {
		emailReviewRepository.save(entity);
		return entity;
	}

	@Override
	public EmailReview findByMessageId(String messageId) {
		return emailReviewRepository.findByMessageId(messageId);
	}
	
	@Override
	public List<EmailReview> findAll() {
		return (List<EmailReview>) emailReviewRepository.findAll();
	}

	@Override
	public Page<EmailReview> findAllByPageOrderByDate(Pageable pageable) {
		return emailReviewRepository.findAll(pageable);
	}

	@Override
	public Page<EmailReview> findByPublished(Pageable pageable, Boolean published) {
		return emailReviewRepository.findByPublished(pageable, published);
	}

}
