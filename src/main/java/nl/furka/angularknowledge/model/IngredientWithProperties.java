package nl.furka.angularknowledge.model;


import java.util.List;

public record IngredientWithProperties(
        Ingredient ingredient,
        List<Property> properties
) {
}
