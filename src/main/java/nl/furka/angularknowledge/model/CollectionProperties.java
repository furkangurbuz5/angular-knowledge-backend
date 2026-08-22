package nl.furka.angularknowledge.model;

public record CollectionProperties(
        Integer propertyId,
        String propertyName,
        Unit unit,
        Integer propertyAmount
) {
}
