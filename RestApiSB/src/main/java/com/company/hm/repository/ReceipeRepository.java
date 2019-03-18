package com.company.hm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.company.hm.entity.ReceipeEntity;


/**
 * Entry point for this project.
 *
 * @author Gerald AJ
 */
@Repository
public interface ReceipeRepository extends CrudRepository<ReceipeEntity, Long> {

	boolean existsByTitle(String receipeTitle);
}
