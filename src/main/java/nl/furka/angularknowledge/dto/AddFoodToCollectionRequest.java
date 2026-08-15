package nl.furka.angularknowledge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddFoodToCollectionRequest(
        @JsonProperty("ingredient_id")
        Integer ingredientId,
        Integer quantity
) {
}
