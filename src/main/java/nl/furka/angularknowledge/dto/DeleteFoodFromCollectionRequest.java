package nl.furka.angularknowledge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeleteFoodFromCollectionRequest(
        @JsonProperty("ingredient_id")
        Integer ingredientId
) {
}
