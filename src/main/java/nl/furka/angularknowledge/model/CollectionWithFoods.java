package nl.furka.angularknowledge.model;

import java.util.List;

public record CollectionWithFoods(
        Collection collection,
        List<IngredientWithUnit> foods
) {
}
