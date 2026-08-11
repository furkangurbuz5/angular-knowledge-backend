package nl.furka.angularknowledge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateIngredientRequest(
        String name,
        @JsonProperty("serving_size")
        Integer servingSize,
        @JsonProperty("unit_id")
        Integer unitId
) {
}
