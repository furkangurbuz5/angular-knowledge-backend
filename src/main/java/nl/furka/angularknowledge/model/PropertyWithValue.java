package nl.furka.angularknowledge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PropertyWithValue(
        String name,
        Unit unit,
        Integer value
) {
}
