package nl.furka.angularknowledge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;

public record Person(
        Integer id,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        String email,
        @Nullable
        String car,
        String city,
        String countryOfOrigin,
        String bank
) {
}
