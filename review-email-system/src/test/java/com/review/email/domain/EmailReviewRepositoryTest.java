package com.review.email.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class EmailReviewRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private EmailReviewRepository repository;

	@Test
	public void findByMessageIdShouldReturnEmailReview() throws Exception {
		this.entityManager.persist(new EmailReview("123", null, "email@email.com", "email", "subject", "body", 0.0, false));
		EmailReview emailReview = this.repository.findByMessageId("123");
		assertThat(emailReview.getFromEmail()).isEqualTo("email@email.com");
		assertThat(emailReview.getMessageId()).isEqualTo("123");
	}

	@Test
	public void findByMessageIdWhenNoEmailReviewShouldReturnNull() throws Exception {
		this.entityManager.persist(new EmailReview("123", null, "email@email.com", "email", "subject", "body", 0.0, false));
		EmailReview emailReview = this.repository.findByMessageId("xyz");
		assertThat(emailReview).isNull();
	}
	
}
