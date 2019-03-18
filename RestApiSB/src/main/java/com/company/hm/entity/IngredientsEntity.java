package com.company.hm.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity

@Table(name = "ingredients")

public class IngredientsEntity {

	@Id
	@NotNull(message = "id is required")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull(message = "ingredient is required")

	private String ingredient;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "ingredientsCollection")

	private final List<ReceipeEntity> receipesCollection = new ArrayList<ReceipeEntity>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}

	public List<ReceipeEntity> getReceipesCollection() {
		return receipesCollection;
	}

	public IngredientsEntity(@NotNull(message = "id is required") long id,
			@NotNull(message = "ingredient is required") String ingredient) {
		
		this.id = id;
		this.ingredient = ingredient;
	}

	public IngredientsEntity() {
		
		// TODO Auto-generated constructor stub
	}
	

}
