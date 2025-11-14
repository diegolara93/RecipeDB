package com.example.recipeDB.helper;

import com.example.recipeDB.dto.RecipeDTO;
import com.example.recipeDB.models.Recipe;

public class Utils {
    public static RecipeDTO mapToRecipeDTO(Recipe recipe) {
        return new RecipeDTO(
                recipe.getRecipeID(),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getPrepTime(),
                recipe.getCookTime(),
                recipe.getServings(),
                recipe.getDifficulty(),
                recipe.getUpvotes(),
                recipe.getSteps(),
                recipe.getImageUrl(),
                recipe.getTags(),
                recipe.getIngredients(),
                recipe.getOwner().getUsername(),
                recipe.getComments().stream().map(c -> new com.example.recipeDB.dto.CommentDTO(
                        c.getId(),
                        c.getRecipe().getRecipeID(),
                        c.getText(),
                        c.getAuthor().getUsername()
                )).toList()
        );
    }
}
