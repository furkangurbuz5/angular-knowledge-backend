package nl.furka.angularknowledge.dto;

public record IngredientPropertiesResponse(
        Integer id,
        Integer ingredientId,
        Integer propertyId,
        Integer value
) {
}
