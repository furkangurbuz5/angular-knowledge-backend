package nl.furka.angularknowledge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record IngredientWithUnit(
        Integer id,
        String name,
        @JsonProperty("serving_size")
        Integer servingSize,
        Unit unit
) {
}
