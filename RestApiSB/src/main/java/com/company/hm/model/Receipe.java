package com.company.hm.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;



/**
 * View bean class of receipe.
 *
 * @author Gerald AJ
 */

public class Receipe implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull(message = "title is required")

	private String title;

	@NotNull(message = "href is required")

	private String href;

	@NotNull(message = "thumbnail is required")

	private String thumbnail;

	@NotNull(message = "ingredients is required")

	private String[] ingredients;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String[] getIngredients() {
		return ingredients;
	}

	public void setIngredients(String[] ingredients) {
		this.ingredients = ingredients;
	}

	public Receipe(@NotNull(message = "title is required") String title,
			@NotNull(message = "href is required") String href,
			@NotNull(message = "thumbnail is required") String thumbnail,
			@NotNull(message = "ingredients is required") String[] ingredients) {
	
		this.title = title;
		this.href = href;
		this.thumbnail = thumbnail;
		this.ingredients = ingredients;
	}

	public Receipe() {
		
	}
	
	

}
