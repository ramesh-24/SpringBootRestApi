package com.company.hm.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.hm.entity.IngredientsEntity;
import com.company.hm.entity.ReceipeEntity;
import com.company.hm.model.Ingredients;
import com.company.hm.model.Receipe;
import com.company.hm.repository.IngredientRepository;
import com.company.hm.repository.ReceipeRepository;



@Service
public class ReceipeServiceImpl implements ReceipeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReceipeServiceImpl.class);

	@Autowired
	ReceipeRepository receipeRepository;

	@Autowired
	IngredientRepository ingredientRepository;


	
	public ReceipeEntity insertReceipes(Receipe receipeVO) {
		LOGGER.info("InsertReceipes ::: {}", receipeVO);

		List<ReceipeEntity> receipeMOList = new ArrayList<ReceipeEntity>();
		ReceipeEntity receipeMO = null;

	
			receipeMO= new ReceipeEntity();
			receipeMO.setHref(receipeVO.getHref());
			receipeMO.setTitle(receipeVO.getTitle());
			receipeMO.setThumbnail(receipeVO.getThumbnail());
			receipeMO.setIngredients(Arrays.asList(receipeVO.getIngredients()));
			
			
			
			IngredientsEntity ingredientsMO = null;

			for (String ingredientName : receipeVO.getIngredients()) {
				ingredientsMO= new IngredientsEntity();
				ingredientsMO.setIngredient(ingredientName);
				
				receipeMO.getIngredientsCollection().add(ingredientsMO);
			}

			if (!existsByTitle(receipeVO.getTitle())) {
				receipeMO.setStatus(true);
				receipeMOList.add(receipeMO);
				receipeRepository.save(receipeMO);
			}else
			    receipeMOList.add(receipeMO);
		
		return receipeMO;
	}

	/**
	 * Method to check the title of receipe exists.
	 * 
	 *  
	 * @param receipeTitle, title of receipe   
	 * 
	 * @return boolean, return the boolean value whether the title exists or not.
	 */
	public boolean existsByTitle(String receipeTitle) {
		LOGGER.info("Find By Title ::: {}", receipeTitle);

		return receipeRepository.existsByTitle(receipeTitle);

	}

	/**
	 * Method to get all the receipes.
	 * 
	 *  
	 * @param    
	 * 
	 * @return List<ReceipeMO>, List of receipe objects
	 */
	public List<ReceipeEntity> getAllReceipes(){
		LOGGER.info("GetAllReceipes ::: ");
		
		List<ReceipeEntity> receipes = new ArrayList<>();		
		receipeRepository.findAll().forEach(receipes::add);
		
		LOGGER.debug("Retrieve Receipes return object:: {}", receipes);		
		
		return receipes;
	}

	/**
	 * Method to get receips based on ingredients.
	 * Receipes of the corresponding ingredients are fetched from the receipe table
	 *  
	 * @param ingredientVO   
	 * 
	 * @return List<ReceipeMO>, List of receipe objects
	 */
	public List<ReceipeEntity> getReceipesOfIngredients(Ingredients ingredientVO) {
		LOGGER.info("GetReceipesOfIngredients ::: {}",
				ingredientVO.getIngredients().length);

		List<ReceipeEntity> receipeMOList = new ArrayList<ReceipeEntity>();

		for (String ingredientName : ingredientVO.getIngredients()) {
			LOGGER.debug("Ingredient Name {}", ingredientName);

			List<IngredientsEntity> ingredientsMO = ingredientRepository.findByIngredient(ingredientName);			
			for (IngredientsEntity ingredient : ingredientsMO) {
				receipeMOList.add(ingredient.getReceipesCollection().get(0));
			}
		}
		
		LOGGER.debug("Retrieve Receipes by Ingredients return object:: {}", receipeMOList);

		return receipeMOList;
	}

}
