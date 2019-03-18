package com.company.hm.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;




public class Ingredients implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@NotNull(message = "ingredients is required")
	
	private String[] ingredients;

	public String[] getIngredients() {
		return ingredients;
	}

	public void setIngredients(String[] ingredients) {
		this.ingredients = ingredients;
	}
	

	
}
