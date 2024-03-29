package com.review.email.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.review.email.domain.EmailReview;

@RunWith(SpringJUnit4ClassRunner.class)
@RestClientTest({ EmailReviewService.class })
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class EmailReviewServiceImplTest {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Autowired
	private EmailReviewService emailReviewService;
	
	@Test
	public void saveShouldReturnEntity() throws Exception {
		EmailReview emailReview = this.emailReviewService.save(new EmailReview("123", null, "email@email.com", "email", "subject", "body", 0.0, false));
		
		assertThat(emailReview.getFromEmail()).isEqualTo("email@email.com");
		assertThat(emailReview.getMessageId()).isEqualTo("123");
	}
	
	@Test
	public void findByMessageIdShouldReturnEmailReview() throws Exception {
		this.emailReviewService.save(new EmailReview("123", null, "email@email.com", "email", "subject", "body", 0.0, false));
		EmailReview emailReview = this.emailReviewService.findByMessageId("123");
		
		assertThat(emailReview.getFromEmail()).isEqualTo("email@email.com");
		assertThat(emailReview.getMessageId()).isEqualTo("123");
	}
	
	@Test
	public void findByMessageIdShouldNotReturnEmailReview() throws Exception {
		this.emailReviewService.save(new EmailReview("123", null, "email@email.com", "email", "subject", "body", 0.0, false));
		EmailReview emailReview = this.emailReviewService.findByMessageId("xyz");
		assertThat(emailReview).isNull();
	}
	
	@Test
	public void findAllShouldReturnAListOfEmailReview() throws Exception {
		this.emailReviewService.save(new EmailReview("123", null, "email@email.com", "email", "subject", "body", 0.0, false));
		this.emailReviewService.save(new EmailReview("456", null, "email@email.com", "email", "subject", "body", 0.0, false));
		this.emailReviewService.save(new EmailReview("789", null, "email@email.com", "email", "subject", "body", 0.0, false));
		
		List<EmailReview> emailReviews = this.emailReviewService.findAll();
		
		assertThat(emailReviews.size()).isEqualTo(3);
	}
	
	@Test
	public void findAllByPageOrderByDateShouldReturnPage() throws Exception {
		this.emailReviewService.save(new EmailReview("123", new Timestamp(0), "email@email.com", "email", "subject", "body", 0.0, false));
		this.emailReviewService.save(new EmailReview("456", new Timestamp(1234), "email@email.com", "email", "subject", "body", 0.0, false));
		this.emailReviewService.save(new EmailReview("789", new Timestamp(5678), "email@email.com", "email", "subject", "body", 0.0, false));
		
		Page<EmailReview> emailReviewsDb = this.emailReviewService.findAllByPageOrderByDate(new Pageable() {

			@Override
			public int getPageNumber() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getPageSize() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getOffset() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Sort getSort() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Pageable next() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Pageable previousOrFirst() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Pageable first() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean hasPrevious() {
				// TODO Auto-generated method stub
				return false;
			}});
		
		assertThat(emailReviewsDb.getContent().size()).isEqualTo(3);
	}
	
	@Test
	public void findByPublished() throws Exception {
		this.emailReviewService.save(new EmailReview("123", new Timestamp(0), "email@email.com", "email", "subject", "body", 0.0, false));
		this.emailReviewService.save(new EmailReview("456", new Timestamp(1234), "email@email.com", "email", "subject", "body", 0.0, true));
		this.emailReviewService.save(new EmailReview("789", new Timestamp(5678), "email@email.com", "email", "subject", "body", 0.0, false));
		
		Page<EmailReview> emailReviews = this.emailReviewService.findByPublished(new Pageable() {

			@Override
			public int getPageNumber() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getPageSize() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getOffset() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Sort getSort() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Pageable next() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Pageable previousOrFirst() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Pageable first() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean hasPrevious() {
				// TODO Auto-generated method stub
				return false;
			}}, true);	
		assertThat(emailReviews.getContent().size()).isEqualTo(1);
	}

}
