package nl.furka.angularknowledge.service;

import nl.furka.angularknowledge.dto.AddFoodToCollectionRequest;
import nl.furka.angularknowledge.dto.CollectionPropertiesResponse;
import nl.furka.angularknowledge.dto.CreateCollectionRequest;
import nl.furka.angularknowledge.dto.DeleteFoodFromCollectionRequest;
import nl.furka.angularknowledge.model.*;
import nl.furka.angularknowledge.repository.CollectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
@Transactional
public class CollectionService {
    private final CollectionRepository collectionRepository;
    private final IngredientService ingredientService;

    CollectionService(CollectionRepository collectionRepository, IngredientService ingredientService) {
        this.collectionRepository = collectionRepository;
        this.ingredientService = ingredientService;
    }

    public List<Collection> getAllCollections() {
        return collectionRepository.getAllCollections()
                .stream()
                .map((collectionResponse -> new Collection(
                        collectionResponse.id(),
                        collectionResponse.name()))
                ).toList();
    }

    public Collection getCollectionById(Integer collectionId) {
        var response = this.collectionRepository.getCollectionById(collectionId);

        return new Collection(response.id(), response.name());
    }

    public List<CollectionPropertiesResponse> getCollectionPropertiesById(Integer collectionId) {
        return collectionRepository.getCollectionProperties(collectionId);
    }

    public Collection addCollection(CreateCollectionRequest request) {
        var response = collectionRepository.addCollection(request);

        return new Collection(
                response.id(),
                response.name()
        );
    }

    public void deleteCollectionById(Integer collectionId) {
        collectionRepository.deleteCollectionById(collectionId);
    }

    public Collection addFoodToCollection(Integer id, AddFoodToCollectionRequest request) {
        var collectionIngredientsResponse = collectionRepository.addFoodToCollection(id, request);
        var collection = getCollectionById(collectionIngredientsResponse.collectionId());

        return new Collection(
                collection.id(),
                collection.name()
        );
    }

    public void deleteFoodFromCollection(Integer collectionId, DeleteFoodFromCollectionRequest request) {
        requireNonNull(request.ingredientId());
        collectionRepository.deleteFoodFromCollection(collectionId, request);
    }

    public CollectionWithFoods getCollectionWithFoods(Integer collectionId) {
        var collection = getCollectionById(collectionId);
        var ingredientsWithQuantities = ingredientService.getIngredientsByCollectionId(collectionId)
                .stream()
                .map((ingredient) -> new IngredientWithQuantity(
                        ingredient.id(),
                        ingredient.name(),
                        ingredient.servingSize(),
                        ingredient.unit(),
                        ingredient.quantity()
                )).toList();

        return new CollectionWithFoods(
                new Collection(
                        collection.id(),
                        collection.name()
                ),
                ingredientsWithQuantities
        );
    }

    public CollectionWithProperties getCollectionWithProperties(Integer collectionId) {
        var collection = getCollectionById(collectionId);
        var properties = collectionRepository.getCollectionProperties(collectionId);
        return new CollectionWithProperties(
                collection,
                properties
        );
    }

    public CollectionWithFoodsAndProperties getCollectionWithFoodsAndProperties(Integer collectionId) {
        var collection = getCollectionById(collectionId);
        var foods = ingredientService.getIngredientsByCollectionId(collectionId);
        var properties = collectionRepository.getCollectionProperties(collectionId)
                .stream()
                .map((i) -> new CollectionProperties(
                        i.propertyId(),
                        i.propertyName(),
                        Unit.fromId(i.unitId()),
                        i.propertyAmount()
                )).toList();

        return new CollectionWithFoodsAndProperties(
                collection,
                foods,
                properties
        );
    }
}
