package nl.furka.angularknowledge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreatePropertyRequest(
        String name,
        @JsonProperty("unit_id")
        Integer unitId
) {
}
