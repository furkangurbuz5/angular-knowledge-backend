package nl.furka.angularknowledge.model;

public record PropertyWithValue(
        Integer id,
        String name,
        Unit unit,
        Integer value
) {
}
