package nl.furka.angularknowledge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PersonIngredientsResponse(
        Integer id,
        @JsonProperty("person_id")
        Integer personId,
        @JsonProperty("ingredient_id")
        Integer ingredientId
) {
}
