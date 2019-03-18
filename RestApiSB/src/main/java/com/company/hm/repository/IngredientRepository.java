package com.company.hm.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.company.hm.entity.IngredientsEntity;


/**
 * Entry point for this project.
 *
 * @author Gerald AJ
 */
@Repository
public interface IngredientRepository extends CrudRepository<IngredientsEntity, Long> {

	List<IngredientsEntity> findByIngredient(String ingredient);

}
