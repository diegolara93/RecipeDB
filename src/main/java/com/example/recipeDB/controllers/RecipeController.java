package com.example.recipeDB.controllers;


import com.example.recipeDB.dto.RecipeDTO;
import com.example.recipeDB.enums.Ingredient;
import com.example.recipeDB.enums.Tag;
import com.example.recipeDB.models.Recipe;
import com.example.recipeDB.models.User;
import com.example.recipeDB.repository.RecipeRepository;
import com.example.recipeDB.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    public RecipeController(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
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
        @RequestParam List<Ingredient> ingredients,
        @RequestParam int userID
    ) {
        List<String> recipeOwners = List.of("Jackson", "Mark", "Alex", "Diego");
        Recipe recipe = new Recipe();
        User user = userRepository.findById(userID).orElse(null);

        recipe.setTitle(title);
        recipe.setDescription(description);
        recipe.setPrepTime(prepTime);
        recipe.setCookTime(cookTime);
        recipe.setServings(servings);
        recipe.setDifficulty(difficulty);
        recipe.setUpvotes(0);
        recipe.setSteps(steps);
        recipe.setTags(tags);
        recipe.setImageUrl(imageUrl);
        recipe.setIngredients(ingredients);
        int r = (int) (Math.random() * recipeOwners.size());
        recipe.setOwner(user);
        recipeRepository.save(recipe);
        return "Recipe created";
    }

    @GetMapping("/all")
    public List<RecipeDTO> all() {
        return recipeRepository.findAll().stream()
                .map(r -> new RecipeDTO(
                        r.getRecipeID(),
                        r.getTitle(),
                        r.getDescription(),
                        r.getPrepTime(),
                        r.getCookTime(),
                        r.getServings(),
                        r.getDifficulty(),
                        r.getUpvotes(),
                        r.getSteps(),
                        r.getImageUrl(),
                        r.getTags(),
                        r.getIngredients(),
                        r.getOwner() != null ? r.getOwner().getUsername() : null
                ))
                .toList();
    }

    @GetMapping("/u/{userID}")
    public List<Recipe> getRecipesByUser(@PathVariable int userID) {
        User user = userRepository.findById(userID).orElse(null);
        assert user != null;
        return user.getRecipes();
    }
//    @DeleteMapping("/clear")
//    public String clearRecipes() {
//        recipeRepository.deleteAll();
//        return "All recipes cleared";
//    }

    @GetMapping("/tags")
    public List<Recipe> getByTags(@RequestParam List<Tag> tags) {
        if (tags == null || tags.isEmpty()) return List.of();
        return recipeRepository.findDistinctByTagsIn(tags);
    }
}
