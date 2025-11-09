package com.example.recipeDB.controllers;


import com.example.recipeDB.enums.Ingredient;
import com.example.recipeDB.enums.Tag;
import com.example.recipeDB.models.Recipe;
import com.example.recipeDB.repository.RecipeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
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
        @RequestParam List<Tag> tags,
        @RequestParam String imageUrl,
        @RequestParam List<Ingredient> ingredients
    ) {
        List<String> recipeOwners = List.of("Jackson", "Mark", "Alex", "Diego");
        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setDescription(description);
        recipe.setPrepTime(prepTime);
        recipe.setCookTime(cookTime);
        recipe.setServings(servings);
        recipe.setDifficulty(difficulty);
        recipe.setUpvotes(0);
        recipe.setSteps(steps);
        recipe.setTag(tags);
        recipe.setImageUrl(imageUrl);
        recipe.setIngredients(ingredients);
        int r = (int) (Math.random() * recipeOwners.size());
        recipe.setRecipeOwner(recipeOwners.get(r));
        recipeRepository.save(recipe);
        return "Recipe created";
    }

    @GetMapping("/all")
    public Iterable<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

//    @DeleteMapping("/clear")
//    public String clearRecipes() {
//        recipeRepository.deleteAll();
//        return "All recipes cleared";
//    }

    @GetMapping("/tag/{tag}")
    public List<Recipe> getRecipesByTag(@PathVariable Tag tag) {
        return recipeRepository.findByTag(tag);
    }
}
