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

	

}
