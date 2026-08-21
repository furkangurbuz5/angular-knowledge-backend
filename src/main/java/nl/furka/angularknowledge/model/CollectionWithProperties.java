package nl.furka.angularknowledge.model;

import java.util.List;

public record CollectionWithProperties(
        Collection collection,
        List<PropertyWithValue> properties
) {
}
