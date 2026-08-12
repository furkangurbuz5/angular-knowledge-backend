package nl.furka.angularknowledge.model;


import java.util.List;

public record IngredientWithProperties(
        IngredientWithUnit ingredient,
        List<PropertyWithValue> properties
) {
}
