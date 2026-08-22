package nl.furka.angularknowledge.model;

import nl.furka.angularknowledge.dto.CollectionPropertiesResponse;

import java.util.List;

public record CollectionWithFoodsAndProperties(
        Collection collection,
        List<IngredientWithQuantity> ingredients,
        List<CollectionProperties> collectionProperties
) {
}
