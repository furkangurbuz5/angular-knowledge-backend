package nl.furka.angularknowledge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record IngredientPropertiesResponse(
        Integer id,
        @JsonProperty("ingredient_id")
        Integer ingredientId,
        @JsonProperty("property_id")
        Integer propertyId,
        Integer value
) {
}
