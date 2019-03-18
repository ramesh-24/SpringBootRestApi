package com.company.hm.service;

import java.util.List;

import com.company.hm.entity.ReceipeEntity;
import com.company.hm.model.Ingredients;
import com.company.hm.model.Receipe;



public interface ReceipeService {

	ReceipeEntity insertReceipes(Receipe receipeVO);

	List<ReceipeEntity> getAllReceipes();

	List<ReceipeEntity> getReceipesOfIngredients(Ingredients ingredientVO);

	boolean existsByTitle(String receipeTitle);
}
