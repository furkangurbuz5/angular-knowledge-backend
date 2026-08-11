package nl.furka.angularknowledge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;

public record UpdatePersonRequest(
        @Nullable
        @JsonProperty("first_name")
        String firstName,
        @Nullable
        @JsonProperty("last_name")
        String lastName,
        @Nullable
        String email,
        @Nullable
        String car,
        @Nullable
        String city,
        @Nullable
        @JsonProperty("country_of_origin")
        String countryOfOrigin,
        @Nullable
        String bank
) {
}
