package com.example.recipeDB.controllers;


import com.example.recipeDB.enums.Tag;
import com.example.recipeDB.models.Recipe;
import com.example.recipeDB.repository.RecipeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeRepository recipeRepository;
    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @PostMapping("/create")
    public String createRecipe(
        @RequestParam String title,
        @RequestParam String description,
        @RequestParam Integer prepTime,
        @RequestParam Integer cookTime,
        @RequestParam Integer servings,
        @RequestParam Integer difficulty,
        @RequestParam String steps,
        @RequestParam Tag tag
    ) {
        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setDescription(description);
        recipe.setPrepTime(prepTime);
        recipe.setCookTime(cookTime);
        recipe.setServings(servings);
        recipe.setDifficulty(difficulty);
        recipe.setUpvotes(0);
        recipe.setSteps(steps);
        recipe.setTag(tag);
        recipeRepository.save(recipe);
        return "Recipe created";
    }

    @GetMapping("/all")
    public Iterable<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }
    @GetMapping("/tag/{tag}")
    public List<Recipe> getRecipesByTag(@PathVariable Tag tag) {
        return recipeRepository.findByTag(tag);
    }
}
