package com.company.hm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.company.hm.entity.ReceipeEntity;
import com.company.hm.model.Ingredients;
import com.company.hm.model.Receipe;
import com.company.hm.service.ReceipeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RestApiSbApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReceipeServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(ReceipeServiceTest.class);

	@Mock
	private ReceipeService receipeService;

	private MockMvc mockMvc;

	List<ReceipeEntity> receipeMOList;
	Receipe _receipeVO_withValidRequest;
	ReceipeEntity reciepeEntity;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(receipeService).build();

		receipeMOList = Arrays.asList(
				new ReceipeEntity(604, "Vermecelli with Clams & Corn",
						"http://www.eatingwell.com/recipes/ spaghetti_clams_corn.html",
						"http://img.recipepuppy.com/ 698569.jpg", Arrays.asList("Tomatoe", "Masala"), true),
				new ReceipeEntity(606, "Casserole Recipe", "http://cookeatshare.com/recipes/broccolicasserole- 59082",
						"http://img.recipepuppy.com/ 780513.jpg", Arrays.asList("onions", "sausage", "Minced Chicken"),
						true));
		reciepeEntity = new ReceipeEntity(604, "Vermecelli with Clams & Corn",
				"http://www.eatingwell.com/recipes/ spaghetti_clams_corn.html",
				"http://img.recipepuppy.com/ 698569.jpg", Arrays.asList("Tomatoe", "Masala"), true);
		_receipeVO_withValidRequest = new Receipe("Vermecelli with Clams & Corn",
				"http://www.eatingwell.com/recipes/ spaghetti_clams_corn.html",
				"http://img.recipepuppy.com/ 698569.jpg", new String[] { "Tomatoe", "Masala" });
	}

	@Test
	public void testCreateReceipe() throws Exception {

		when(receipeService.insertReceipes(_receipeVO_withValidRequest)).thenReturn(reciepeEntity);

		ReceipeEntity receipeMOReturnList = receipeService.insertReceipes(_receipeVO_withValidRequest);

		assertEquals(reciepeEntity, receipeMOReturnList);
		verify(receipeService, times(1)).insertReceipes(_receipeVO_withValidRequest);
	}

	@Test
	public void testExistsReceipe() throws Exception {
		when(receipeService.existsByTitle("Vermecelli with Clams & Corn")).thenReturn(true);

		assertTrue(receipeService.existsByTitle("Vermecelli with Clams & Corn"));
	}

	@Test
	public void testGetAllReceipe() throws Exception {

		when(receipeService.getAllReceipes()).thenReturn(receipeMOList);

		List<ReceipeEntity> receipeMOReturnList = receipeService.getAllReceipes();
		assertEquals(receipeMOList, receipeMOReturnList);
		assertEquals(2, receipeMOReturnList.size());
		verify(receipeService, times(1)).getAllReceipes();
		assertEquals("Vermecelli with Clams & Corn", receipeService.getAllReceipes().get(0).getTitle());
	}

	@Test
	public void testGetReecipesOfIngredients() throws Exception {

		Ingredients ingredientsVO = new Ingredients();
		ingredientsVO.setIngredients(new String[] { "onions" });

		when(receipeService.getReceipesOfIngredients(ingredientsVO)).thenReturn(receipeMOList);

		List<ReceipeEntity> receipeMOReturnList = receipeService.getReceipesOfIngredients(ingredientsVO);

		assertEquals(2, receipeMOReturnList.size());
		assertEquals(receipeMOList, receipeMOReturnList);
		verify(receipeService, times(1)).getReceipesOfIngredients(ingredientsVO);
		assertEquals("Vermecelli with Clams & Corn",
				receipeService.getReceipesOfIngredients(ingredientsVO).get(0).getTitle());

	}

}
