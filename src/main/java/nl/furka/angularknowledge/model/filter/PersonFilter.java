package nl.furka.angularknowledge.model.filter;

import org.springframework.web.bind.annotation.RequestParam;

public record PersonFilter(
        @RequestParam(required = false) Integer id,
        @RequestParam(required = false) String firstName,
        @RequestParam(required = false) String lastName,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String car,
        @RequestParam(required = false) String city,
        @RequestParam(required = false) String countryOfOrigin,
        @RequestParam(required = false) String bank
) implements Filter {
}