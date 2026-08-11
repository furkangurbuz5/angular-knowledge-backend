package nl.furka.angularknowledge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;

public record CreatePersonRequest(
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        String email,
        @Nullable
        String car,
        String city,
        @JsonProperty("country_of_origin")
        String countryOfOrigin,
        String bank
) {
}
