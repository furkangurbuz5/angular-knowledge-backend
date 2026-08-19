package nl.furka.angularknowledge.model;

public record IngredientWithQuantity(
        Integer id,
        String name,
        Integer servingSize,
        Unit unit,
        Integer quantity
) {
}
