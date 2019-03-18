package com.company.hm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.company.hm.entity.ReceipeEntity;
import com.company.hm.model.Ingredients;
import com.company.hm.model.Receipe;
import com.company.hm.repository.IngredientRepository;
import com.company.hm.repository.ReceipeRepository;
import com.company.hm.service.ReceipeServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)

@SpringBootTest

public class ServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(ServiceTest.class);

	@InjectMocks
	private ReceipeServiceImpl receipeService;
	@Mock
	ReceipeRepository receipeRepository;
	@Mock
	IngredientRepository ingredientRepository;

	

	List<ReceipeEntity> receipeMOList;
	Receipe _receipeVO_withValidRequest;
	ReceipeEntity receipeMo;
	Receipe recipeVO;

	@Before
	public void setUp() {
		//receipeService= new ReceipeServiceImpl();
		MockitoAnnotations.initMocks(this);
		

				receipeMOList = Arrays.asList(
						new ReceipeEntity(1, "Vermecelli with Clams & Corn",
								"http://www.recipezaar.com/Crock-Pot- Caramelized-Onions-191625",
								"http://img.recipepuppy.com/ 338845.jpg", Arrays.asList("butter", "onions"), true),
						
						new ReceipeEntity(2, "Pulled Chicken Sandwiches (Crock Pot",
								"http://www.recipezaar.com/Pulled-Chicken- Sandwiches-Crock-Pot-242547",
								"http://img.recipepuppy.com/ 107122.jpg", Arrays.asList("onions", "chiken"), true));
	}

	@Test
	public void testCreateReceipe() throws Exception {

		ReceipeEntity mo = new ReceipeEntity(1, "Vermecelli with Clams & Corn",
				"http://www.recipezaar.com/Crock-Pot- Caramelized-Onions-191625",
				"http://img.recipepuppy.com/ 338845.jpg", Arrays.asList("butter", "onions"), true);

		Receipe vo = new Receipe("Vermecelli with Clams & Corn",
				"http://www.recipezaar.com/Crock-Pot- Caramelized-Onions-191625",
				"http://img.recipepuppy.com/ 338845.jpg", new String[] { "butter", "onions" });
		when(receipeService.insertReceipes(vo)).thenReturn(mo);
		when(receipeRepository.save(mo)).thenReturn(mo);
		ReceipeEntity receipeMO = receipeService.insertReceipes(vo);
		System.out.println(receipeMO);
		System.out.println(mo);
		assertNotNull(receipeMO);
		//assertEquals(mo, receipeMO);
		verify(receipeService, times(1)).insertReceipes(vo);
	}

	@Test
	public void testExistsReceipe() throws Exception {
		when(receipeService.existsByTitle("Vermecelli with Clams & Corn")).thenReturn(true);

		assertTrue(receipeService.existsByTitle("Vermecelli with Clams & Corn"));
	}

	@Test
	public void testGetAllReceipe() throws Exception {

		ReceipeEntity mo1=	new ReceipeEntity(1, "Vermecelli with Clams & Corn",
				"http://www.recipezaar.com/Crock-Pot- Caramelized-Onions-191625",
				"http://img.recipepuppy.com/ 338845.jpg", Arrays.asList("butter", "onions"), true);
		
		ReceipeEntity mo2=new ReceipeEntity(2, "Pulled Chicken Sandwiches (Crock Pot",
				"http://www.recipezaar.com/Pulled-Chicken- Sandwiches-Crock-Pot-242547",
				"http://img.recipepuppy.com/ 107122.jpg", Arrays.asList("onions", "chiken"), true);
		List<ReceipeEntity> receipeMOs= new ArrayList<>();
		receipeMOs.add(mo1);
		receipeMOs.add(mo2);
		when(receipeService.getAllReceipes()).thenReturn(receipeMOs);

		List<ReceipeEntity> receipeMOReturnList = receipeService.getAllReceipes();

		assertEquals(receipeMOs, receipeMOReturnList);
		verify(receipeService, times(1)).getAllReceipes();
		assertEquals("Vermecelli with Clams & Corn", receipeService.getAllReceipes().get(0).getTitle());
	}
	@Test
	public void testGetReecipesOfIngredients() throws Exception {
 //List<String> ingredients= Arrays.asList("onion","chiken");
		ReceipeEntity mo1=	new ReceipeEntity(1, "Vermecelli with Clams & Corn",
				"http://www.recipezaar.com/Crock-Pot- Caramelized-Onions-191625",
				"http://img.recipepuppy.com/ 338845.jpg", Arrays.asList("butter", "onions"), true);
		
		ReceipeEntity mo2=new ReceipeEntity(2, "Pulled Chicken Sandwiches (Crock Pot",
				"http://www.recipezaar.com/Pulled-Chicken- Sandwiches-Crock-Pot-242547",
				"http://img.recipepuppy.com/ 107122.jpg", Arrays.asList("onions", "chiken"), true);
		List<ReceipeEntity> receipeMOs= new ArrayList<>();
		receipeMOs.add(mo1);
		receipeMOs.add(mo2);
		Ingredients ingredientsVO = new Ingredients();
	ingredientsVO.setIngredients(new String[] { "onions"});
		

		when(receipeService.getReceipesOfIngredients(ingredientsVO)).thenReturn(receipeMOList);

		List<ReceipeEntity> receipeMOReturnList = receipeService.getReceipesOfIngredients(ingredientsVO);

		assertEquals(receipeMOs, receipeMOReturnList);
		verify(receipeService, times(1)).getReceipesOfIngredients(ingredientsVO);
		assertEquals("Vermecelli with Clams & Corn",
				receipeService.getReceipesOfIngredients(ingredientsVO).get(0).getTitle());

	}

}
