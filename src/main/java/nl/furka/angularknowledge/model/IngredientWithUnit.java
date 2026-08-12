package nl.furka.angularknowledge.model;

public record IngredientWithUnit(
        Integer id,
        String name,
        Integer servingSize,
        Unit unit
) {
}
