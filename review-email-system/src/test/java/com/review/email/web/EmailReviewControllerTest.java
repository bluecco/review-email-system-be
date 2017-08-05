package com.review.email.web;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.review.email.domain.EmailReview;
import com.review.email.service.EmailReviewService;

public class EmailReviewControllerTest {

	private MockMvc mockMvc;

	@Mock
	private EmailReviewService emailReviewService;
	
	private EmailReview er;

	@Before
    public void setUp() {
    		er = new EmailReview("123", new Date(1234), "email@email.com", "email", "subject", "body", 0.0, true);
    		emailReviewService = mock(EmailReviewService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new EmailReviewController(emailReviewService)).build();
    }

	@Test
	public void shouldSaveEmailReview() throws Exception {

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(this.er);
		
		when(emailReviewService.save(any(EmailReview.class))).thenReturn(er);

		mockMvc.perform(post("/reviews").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.messageId", is("123")));
	}
	
	@Test
	public void shouldRetrieveEmailReviewPage() throws Exception {
		
		ArrayList<EmailReview> emailReviews = new ArrayList<EmailReview>();
		emailReviews.add(new EmailReview("123", new Date(1234), "email@email.com", "email", "subject", "body", 0.0, true));
		emailReviews.add(new EmailReview("456", new Date(1234), "email2@email.com", "email2", "subject", "body", 0.0, false));
		emailReviews.add(new EmailReview("789", new Date(1234), "email3@email.com", "email3", "subject", "body", 0.0, false));
		
		Page<EmailReview> page = new PageImpl<>(emailReviews);
		when(emailReviewService.findAllByPageOrderByDate(any(Pageable.class))).thenReturn(page);

		mockMvc.perform(get("/reviews")).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.totalElements", is(3)));
	}
	
	@Test
	public void shouldRetrievePublishedEmailReviewPage() throws Exception {
		
		ArrayList<EmailReview> emailReviews = new ArrayList<EmailReview>();
		emailReviews.add(new EmailReview("123", new Date(1234), "email@email.com", "email", "subject", "body", 0.0, true));
		
		Page<EmailReview> page = new PageImpl<>(emailReviews);
		when(emailReviewService.findByPublished(any(Pageable.class), any(boolean.class))).thenReturn(page);

		mockMvc.perform(get("/reviews/published")).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.totalElements", is(1)));
	}
	
	

}