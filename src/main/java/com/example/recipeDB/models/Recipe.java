package com.example.recipeDB.models;


import com.example.recipeDB.enums.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

enum Ingredient {
    SUGAR,
    SALT,
    FLOUR,
    BUTTER,
    EGGS,
    MILK,
    CHICKEN,
    BEEF,
    RICE,
    PASTA,
    TOMATOES,
    ONIONS,
    GARLIC,
    PEPPER,
    CHEESE,
    LETTUCE,
    CARROTS,
    POTATOES,
    CUCUMBERS,
    OLIVE_OIL,
    VINEGAR,
    CHOCOLATE,
    HONEY,
    VANILLA,
    CINNAMON,
    BASIL,
    OREGANO,
    BREAD,
    SHRIMP,
    HALIBUT,
    SPINACH,
    SALMON,
    MUSHROOMS,
    PEAS
}

@Entity
@Getter
@Setter
@Table(
        name = "recipes"
)
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer recipeID;
    private String title;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private Integer difficulty;
    private Integer upvotes;
    private String steps;

    @Column(nullable = false)
    private Tag tag;

    @ElementCollection
    private List<Ingredient> ingredients;
}
