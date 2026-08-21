package nl.furka.angularknowledge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CollectionPropertiesResponse(
        Integer propertyId,
        Integer collectionServingAmount,
        Integer servingSize,
        Integer unitId,
        Integer servingAmount
) {
}
