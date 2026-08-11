package nl.furka.angularknowledge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PropertyResponse(
        Integer id,
        String name,
        @JsonProperty("unit_id")
        Integer unitId
) {
}
