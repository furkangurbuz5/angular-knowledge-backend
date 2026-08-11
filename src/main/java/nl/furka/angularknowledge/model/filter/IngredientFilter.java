package nl.furka.angularknowledge.model.filter;

import org.springframework.web.bind.annotation.RequestParam;

public record IngredientFilter(
        @RequestParam(required = false) Integer id,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Integer servingSize,
        @RequestParam(required = false) Integer unitId
) implements Filter {
}
