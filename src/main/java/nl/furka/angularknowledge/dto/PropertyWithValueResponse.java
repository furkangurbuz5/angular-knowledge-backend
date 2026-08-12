package nl.furka.angularknowledge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PropertyWithValueResponse(
        Integer id,
        String name,
        @JsonProperty("unit_id")
        Integer unitId,
        Integer value
) {
}
