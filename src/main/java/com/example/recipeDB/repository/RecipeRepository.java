package com.example.recipeDB.repository;

import com.example.recipeDB.enums.Tag;
import com.example.recipeDB.models.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
    List<Recipe> findByTag(Tag tag);
}
