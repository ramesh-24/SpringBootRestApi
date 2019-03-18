package com.company.hm.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.hm.entity.ReceipeEntity;
import com.company.hm.exception.InternalServerException;
import com.company.hm.model.Ingredients;
import com.company.hm.model.Receipe;
import com.company.hm.service.ReceipeService;



@RestController
@RequestMapping("/api/receipes")
public class ReceipeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReceipeController.class);

	@Autowired
	private ReceipeService receipeService;

	
	@PostMapping("/create")

	public ResponseEntity<ReceipeEntity> insertReceipes(@RequestBody Receipe receipes) throws IOException {
		LOGGER.info("Creating receipe resource :::");
		System.out.println("console print");

		if (receipes == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		ReceipeEntity receipeMOReturnList;
		try {
			receipeMOReturnList = receipeService.insertReceipes(receipes);
		} catch (Exception e) {
			LOGGER.trace("Server Exception occurs");
			throw new InternalServerException("Server Exception");
		}

		LOGGER.debug("Size of ReceipeList {}", receipeMOReturnList);

		return new ResponseEntity<ReceipeEntity>(receipeMOReturnList, HttpStatus.CREATED);

	}

	
	@GetMapping("/")
	public ResponseEntity<List<ReceipeEntity>> getAllReceipes() throws IOException {
		
		List<ReceipeEntity> receipeList;
		try {
			receipeList = receipeService.getAllReceipes();
		} catch (Exception e) {
			LOGGER.trace("Server Exception occurs at ");
			throw new InternalServerException("Server Exception");
		}

		LOGGER.debug("Size of ReceipeList {}", receipeList);

		if (receipeList.isEmpty() || receipeList.size() <= 0)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(receipeList, HttpStatus.OK);

	}

	
	@PostMapping("/retrieve")
	public ResponseEntity<List<ReceipeEntity>> getReceipesOfIngredients(@RequestBody Ingredients ingredients) {

		List<ReceipeEntity> receipeMOList = null;

		if (ingredients.getIngredients().length == 0)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		try {
			receipeMOList = receipeService.getReceipesOfIngredients(ingredients);
		} catch (Exception e) {
			LOGGER.trace("Server Exception occurs");
			throw new InternalServerException("Server Exception");
		}

		LOGGER.debug("Size of rceipeMO {}", receipeMOList);

		if (receipeMOList == null || receipeMOList.size() == 0)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(receipeMOList, HttpStatus.OK);
	}

	

}
