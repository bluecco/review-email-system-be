package com.review.email.web;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.review.email.domain.EmailReview;
import com.review.email.service.EmailReviewServiceImpl;
import com.review.email.web.azure.AzureSentimentDocument;
import com.review.email.web.azure.AzureSentimentDocuments;

public class EmailReviewControllerTest {

	private MockMvc mockMvc;

	@Mock
	private EmailReviewServiceImpl emailReviewService;

	@Mock
	private RestTemplate restTemplate;

	private EmailReview er;

	@Before
	public void setUp() {
		er = new EmailReview("123", new Timestamp(1234), "email@email.com", "email", "subject", "body", 0.0, true);
		emailReviewService = mock(EmailReviewServiceImpl.class);
		restTemplate = mock(RestTemplate.class);
		mockMvc = MockMvcBuilders.standaloneSetup(new EmailReviewController(emailReviewService, restTemplate)).build();
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
		emailReviews
				.add(new EmailReview("123", new Timestamp(1234), "email@email.com", "email", "subject", "body", 0.0, true));
		emailReviews.add(
				new EmailReview("456", new Timestamp(1234), "email2@email.com", "email2", "subject", "body", 0.0, false));
		emailReviews.add(
				new EmailReview("789", new Timestamp(1234), "email3@email.com", "email3", "subject", "body", 0.0, false));

		Page<EmailReview> page = new PageImpl<>(emailReviews);
		when(emailReviewService.findAllByPageOrderByDate(any(Pageable.class))).thenReturn(page);

		mockMvc.perform(get("/reviews")).andExpect(status().isOk())
				.andExpect(jsonPath("$.totalElements", is(3)));
	}

	@Test
	public void shouldRetrievePublishedEmailReviewPage() throws Exception {

		ArrayList<EmailReview> emailReviews = new ArrayList<EmailReview>();
		emailReviews
				.add(new EmailReview("123", new Timestamp(1234), "email@email.com", "email", "subject", "body", 0.0, true));

		Page<EmailReview> page = new PageImpl<>(emailReviews);
		when(emailReviewService.findByPublished(any(Pageable.class), any(boolean.class))).thenReturn(page);

		mockMvc.perform(get("/reviews/published")).andExpect(status().isOk())
				.andExpect(jsonPath("$.totalElements", is(1)));
	}

	@Test
	public void shouldPublishEmailReview() throws Exception {
		EmailReview toUpdate = new EmailReview("123", new Timestamp(1234), "email@email.com", "email", "subject", "body",
				0.0, false);
		when(emailReviewService.findByMessageId(any(String.class))).thenReturn(toUpdate);

		mockMvc.perform(put("/reviews/123/publish"))
				.andExpect(jsonPath("$.messageId", is("123")))
				.andExpect(jsonPath("$.published", is(true)));
	}

	@Test
	public void shouldGetSentimentScore() throws Exception {

		AzureSentimentDocument asd = new AzureSentimentDocument(0.22, "123");
		ArrayList<AzureSentimentDocument> documents = new ArrayList<AzureSentimentDocument>();
		documents.add(asd);
		
		AzureSentimentDocuments asds = new AzureSentimentDocuments(documents);
		ResponseEntity<AzureSentimentDocuments> responseEntity = new ResponseEntity<AzureSentimentDocuments>(asds, new HttpHeaders(), HttpStatus.OK);
	
		ReflectionTestUtils.setField(emailReviewService, "sentimentEndpoint", "url.com");
		when(emailReviewService.findByMessageId(any(String.class))).thenReturn(er);
		when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(AzureSentimentDocuments.class)))
				.thenReturn(responseEntity);
		
		mockMvc.perform(put("/reviews/123/analyze")).andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.messageId", is("123")))
		.andExpect(jsonPath("$.score", is(22.0)));
		

	}

}
