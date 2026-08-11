package nl.furka.angularknowledge.model;

public record Ingredient(
        Integer id,
        String name,
        Integer servingSize,
        Integer unitId
) {
}
