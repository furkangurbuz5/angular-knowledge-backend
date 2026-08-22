package nl.furka.angularknowledge.dto;

public record CollectionPropertiesResponse(
        Integer propertyId,
        String propertyName,
        Integer unitId,
        Integer propertyAmount
) {
}
