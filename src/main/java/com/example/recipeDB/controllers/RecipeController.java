package com.example.recipeDB.controllers;


import com.example.recipeDB.dto.RecipeDTO;
import com.example.recipeDB.enums.Ingredient;
import com.example.recipeDB.enums.Tag;
import com.example.recipeDB.models.Recipe;
import com.example.recipeDB.models.User;
import com.example.recipeDB.repository.RecipeRepository;
import com.example.recipeDB.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @PreAuthorize("isAuthenticated()")
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
        Authentication auth
    ) {
        Recipe recipe = new Recipe();
        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow( () -> new IllegalStateException("User not found"));

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

    @PreAuthorize("@recipeSecurityService.isOwner(#recipeID, authentication)")
    @PutMapping("/r/{recipeID}/edit")
    public ResponseEntity<Recipe> editRecipe(
            @PathVariable int recipeID,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Integer prepTime,
            @RequestParam(required = false) Integer cookTime,
            @RequestParam(required = false) Integer servings,
            @RequestParam(required = false) Integer difficulty,
            @RequestParam(required = false) String steps,
            @RequestParam(required = false) List<Tag> tags,
            @RequestParam(required = false) String imageUrl,
            @RequestParam(required = false) List<Ingredient> ingredients
    ) {
        Recipe recipe = recipeRepository.findById(recipeID).orElseThrow(
                () -> new IllegalStateException("Recipe with ID " + recipeID + " does not exist.")
        );
        if(title != null) recipe.setTitle(title);
        if(description != null) recipe.setDescription(description);
        if(prepTime != null) recipe.setPrepTime(prepTime);
        if(cookTime != null) recipe.setCookTime(cookTime);
        if(servings != null) recipe.setServings(servings);
        if(difficulty != null) recipe.setDifficulty(difficulty);
        if(steps != null) recipe.setSteps(steps);
        if(tags != null) recipe.setTags(tags);
        if(imageUrl != null) recipe.setImageUrl(imageUrl);
        if(ingredients != null) recipe.setIngredients(ingredients);

        recipeRepository.save(recipe);
        return ResponseEntity.ok(recipe);
    }

    @PreAuthorize("@recipeSecurityService.isOwner(#recipeID, authentication)")
    @DeleteMapping("/r/{recipeID}/delete")
    public ResponseEntity<?> deleteRecipe(
            @PathVariable int recipeID
    ) {
        Recipe recipe = recipeRepository.findById(recipeID).orElse(null);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        recipeRepository.delete(recipe);
        return ResponseEntity.ok("Deleted recipe with ID " + recipeID);
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
