package nl.furka.angularknowledge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CollectionIngredientsResponse(
        Integer id,
        @JsonProperty("collection_id")
        Integer collectionId,
        @JsonProperty("ingredient_id")
        Integer ingredientId,
        Integer quantity
) {
}
