package nl.furka.angularknowledge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PersonWithIngredients(
        Person person,
        @JsonProperty("ingredients_with_properties")
        List<IngredientWithProperties> ingredientsWithProperties
) {
}
