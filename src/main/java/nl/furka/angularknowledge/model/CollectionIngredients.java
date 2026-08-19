package nl.furka.angularknowledge.model;

public record CollectionIngredients(
        Integer id,
        Integer collectionId,
        Integer ingredientId,
        Integer quantity
) {
}
