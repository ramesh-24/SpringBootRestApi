package com.company.hm;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.company.hm.controller.ReceipeController;
import com.company.hm.entity.ReceipeEntity;
import com.company.hm.model.Receipe;
import com.company.hm.service.ReceipeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebMvcTest(ReceipeController.class)

public class ControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(ControllerTest.class);

	@InjectMocks
	private ReceipeController receipeController;

	@Mock
	private ReceipeService receipeService;
	
    @Autowired
	private MockMvc mockMvc;
    
	ReceipeEntity recMo;
	List<ReceipeEntity> receipeMO;

	Receipe[] receipeVOArray;

	Receipe _receipeVO_withInvalidRequest;

	ReceipeEntity _receipeMO_withInvalidRequest;

	Receipe _receipeVO_withValidRequest;

	String arryOfIngredients;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(receipeController).build();

		receipeMO = Arrays.asList(
				new ReceipeEntity(1, "Vermecelli with Clams & Corn",
						"http://www.recipezaar.com/Crock-Pot- Caramelized-Onions-191625",
						"http://img.recipepuppy.com/ 338845.jpg", Arrays.asList("butter", "onions"), true),
				
				new ReceipeEntity(2, "Pulled Chicken Sandwiches (Crock Pot",
						"http://www.recipezaar.com/Pulled-Chicken- Sandwiches-Crock-Pot-242547",
						"http://img.recipepuppy.com/ 107122.jpg", Arrays.asList("onions", "chiken"), true));

	}

	@Test
	public void retrieveAllReciepesWithStatusOKTest() throws Exception {
		when(receipeService.getAllReceipes()).thenReturn(receipeMO);
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/api/receipes/")
				.accept(MediaType.APPLICATION_JSON);
		
		   MvcResult result = this.mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().json(asJsonString(receipeMO)))
				.andReturn();
		

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/receipes/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

		verify(receipeService, times(1)).getAllReceipes();
		assertNotNull(result);
	}

	public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	@Test
	public void saveReciepeTest() throws Exception {
		
		Receipe vo = new Receipe("Vermecelli with Clams & Corn",
				"http://www.recipezaar.com/Crock-Pot- Caramelized-Onions-191625",
				"http://img.recipepuppy.com/ 338845.jpg", new String[] { "butter", "onions" });
		
		mockMvc.perform(post("/api/receipes/create")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(asJsonString(vo)))
		            .andExpect(status().isOk());
		
	}
	@Test
	public void retrieveReciepeBIngredientsTest() throws Exception {
	String[] ingredients	=new String[] { "onions"};
	
	mockMvc.perform(post("/api/receipes/retrieve")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(ingredients)))
            .andExpect(status().isOk());
	
	
	}
	
	
	
	
	
	
	
	
}
