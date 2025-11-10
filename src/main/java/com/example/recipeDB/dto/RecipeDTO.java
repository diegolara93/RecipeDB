package com.example.recipeDB.dto;

import com.example.recipeDB.enums.Ingredient;
import com.example.recipeDB.enums.Tag;
import java.util.List;

public record RecipeDTO(
        Integer recipeID,
        String title,
        String description,
        Integer prepTime,
        Integer cookTime,
        Integer servings,
        Integer difficulty,
        Integer upvotes,
        String steps,
        String imageUrl,
        List<Tag> tags,
        List<Ingredient> ingredients,
        String ownerUsername
) {}
