package com.review.email.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
public class EmailReviewTest {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Autowired
	private TestEntityManager entityManager;
	
	
	@Test
	public void createWhenMessageIdIsNullShouldThrowException() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("messageId must not be empty");
		new EmailReview(null, null, "from", "from display", "subject", "body", 0.0, false);
	}

	@Test
	public void createWhenMessageIdIsEmptyShouldThrowException() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("messageId must not be empty");
		new EmailReview("", null, "from", "from display", "subject", "body", 0.0, false);
	}

	@Test
	public void createWhenFromIsNullShouldThrowException() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("from must not be empty");
		new EmailReview("message123", null, null, "from display", "subject", "body", 0.0, false);
	}
	
	@Test
	public void createWhenFromIdIsEmptyShouldThrowException() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("from must not be empty");
		new EmailReview("message123", null, "", "from display", "subject", "body", 0.0, false);
	}
	
}