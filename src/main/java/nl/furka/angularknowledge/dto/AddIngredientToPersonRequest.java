package nl.furka.angularknowledge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddIngredientToPersonRequest(
        @JsonProperty("ingredient_id")
        Integer ingredientId,
        @JsonProperty("person_id")
        Integer personId
) {
}
