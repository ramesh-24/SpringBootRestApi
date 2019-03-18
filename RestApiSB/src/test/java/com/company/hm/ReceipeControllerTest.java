package com.company.hm;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.company.hm.commons.CommonUtil;
import com.company.hm.controller.ReceipeController;
import com.company.hm.entity.ReceipeEntity;
import com.company.hm.exception.InternalServerException;
import com.company.hm.model.Ingredients;
import com.company.hm.model.Receipe;
import com.company.hm.service.ReceipeService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RestApiSbApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReceipeControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(ReceipeControllerTest.class);

	@InjectMocks
	private ReceipeController receipeController;

	@Mock
	private ReceipeService receipeService;

	private MockMvc mockMvc;

	List<ReceipeEntity> receipeMO;

	

	Receipe _receipeVO_withInvalidRequest;

	ReceipeEntity _receipeMO_withInvalidRequest;

	Receipe _receipeVO_withValidRequest;

	String arryOfIngredients;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(receipeController).build();
		
		receipeMO = Arrays.asList(
				new ReceipeEntity(604, "Vermecelli with Clams & Corn",
						"http://www.eatingwell.com/recipes/ spaghetti_clams_corn.html",
						"http://img.recipepuppy.com/ 698569.jpg", Arrays.asList("Tomatoe", "Masala"),true),
				
				new ReceipeEntity(606, "Casserole Recipe", "http://cookeatshare.com/recipes/broccolicasserole- 59082",
						"http://img.recipepuppy.com/ 780513.jpg",
						Arrays.asList("onions", "sausage", "Minced Chicken"),true));

		

		_receipeVO_withInvalidRequest = new Receipe("Vermecelli with Clams & Corn",
				"http://www.eatingwell.com/recipes/ spaghetti_clams_corn.html",
				"http://img.recipepuppy.com/ 698569.jpg", new String[] { "Tomatoe", "Masala" });

		_receipeMO_withInvalidRequest = new ReceipeEntity(604, "Vermecelli with Clams & Corn",
				"http://www.eatingwell.com/recipes/ spaghetti_clams_corn.html",
				"http://img.recipepuppy.com/ 698569.jpg", Arrays.asList("Vermicelli", "Milk", "Cashews", "Raisens"),true);

		_receipeVO_withValidRequest = new Receipe("Vermecelli with Clams & Corn",
				"http://www.eatingwell.com/recipes/ spaghetti_clams_corn.html",
				"http://img.recipepuppy.com/ 698569.jpg", new String[] { "Tomatoe", "Masala" });

		arryOfIngredients = "{\"ingredients\":[\"onion\",\"Brocolli\",\"Potato\"]}";
	}

	@Test
	public void testRetrieveReceipe() throws Exception {
		when(receipeService.getAllReceipes()).thenReturn(receipeMO);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/receipes/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id", is(604)))
				.andExpect(jsonPath("$[0].title", is("Vermecelli with Clams & Corn")));
		verify(receipeService, times(1)).getAllReceipes();
		verifyNoMoreInteractions(receipeService);
	}

	@Test
	public void testCreateReceipe() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/receipes/create").contentType(MediaType.APPLICATION_JSON)
				.content(CommonUtil.convertObjectToJsonBytes(_receipeVO_withValidRequest)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

	}

	@Test(expected = Exception.class)
	public void testCreateReceipeThrowsException() throws Exception {
		doThrow(new InternalServerException()).when(receipeService).insertReceipes(any(Receipe.class));
		receipeService.insertReceipes(_receipeVO_withValidRequest);
	}

	@Test
	public void testCreateReceipeBehaviourSimulation() throws Exception {
		Receipe _receipeVO = new Receipe();

		receipeService.insertReceipes(_receipeVO);
		verify(receipeService).insertReceipes(_receipeVO);

		doAnswer(new Answer() {
			@Override
			public List<Receipe> answer(InvocationOnMock invocation) throws Throwable {
				Receipe args = (Receipe) (invocation.getArgument(1));
				args = new Receipe("Vermecelli with Clams & Corn",
						"http://www.eatingwell.com/recipes/ spaghetti_clams_corn.html",
						"http://img.recipepuppy.com/ 698569.jpg", new String[] { "Tomatoe", "Masala" });
				return Arrays.asList(args);
			}
		}).when(receipeService).insertReceipes(_receipeVO);

		receipeService.insertReceipes(_receipeVO);
		assertTrue("Vermecelli with Clams & Corn".equals(_receipeVO.getTitle()));
	}

	@Test
	public void testFindByTitle() throws Exception {
		when(receipeService.existsByTitle("Vermecelli with Clams & Corn")).thenReturn(true);
		assertTrue(receipeService.existsByTitle("Vermecelli with Clams & Corn"));
	}

	@Test
	public void testReceipesOfIngredients() throws Exception {
		when(receipeService.getReceipesOfIngredients(any(Ingredients.class))).thenReturn(receipeMO);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/receipes/retrieve").contentType(MediaType.APPLICATION_JSON)
				.content(arryOfIngredients).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].title", is("Vermecelli with Clams & Corn")));
	}

	@Test
	public void shouldGetReceipeWhenValidRequest() throws Exception {
		when(receipeService.existsByTitle(_receipeVO_withValidRequest.getTitle())).thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/receipes/find")
				.content(CommonUtil.convertObjectToJsonBytes(_receipeVO_withValidRequest))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))	
		        .andExpect(MockMvcResultMatchers.status().isAccepted())		        
				.andExpect(MockMvcResultMatchers.content().string("New Record"));
	}

	@Test
	public void shouldGetReceipeWhenInValidRequest() throws Exception {
		when(receipeService.existsByTitle(_receipeMO_withInvalidRequest.getTitle())).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/receipes/find")
				.content(CommonUtil.convertObjectToJsonBytes(_receipeVO_withInvalidRequest)).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.status().isConflict())
				.andExpect(MockMvcResultMatchers.content().string("Conflict"));
	}

	

}
