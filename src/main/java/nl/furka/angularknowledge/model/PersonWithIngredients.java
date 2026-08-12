package nl.furka.angularknowledge.model;

import java.util.List;

public record PersonWithIngredients(
        Person person,
        List<IngredientWithProperties> ingredientsWithProperties
) {
}
