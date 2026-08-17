package nl.furka.angularknowledge.service;

import nl.furka.angularknowledge.dto.AddFoodToCollectionRequest;
import nl.furka.angularknowledge.dto.CreateCollectionRequest;
import nl.furka.angularknowledge.dto.DeleteFoodFromCollectionRequest;
import nl.furka.angularknowledge.model.Collection;
import nl.furka.angularknowledge.model.CollectionWithFoods;
import nl.furka.angularknowledge.repository.CollectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Collection addCollection(CreateCollectionRequest request) {
        var response = collectionRepository.addCollection(request);

        return new Collection(
                response.id(),
                response.name()
        );
    }

    public Collection addFoodToCollection(Integer id, AddFoodToCollectionRequest request) {
        var collectionIngredientsResponse = collectionRepository.addFoodToCollection(id, request);
        var collection = getCollectionById(collectionIngredientsResponse.collectionId());

        return new Collection(
                collection.id(),
                collection.name()
        );
    }

    public CollectionWithFoods deleteFoodFromCollection(Integer collectionId, DeleteFoodFromCollectionRequest request) {
        var collectionIngredientsResponse = collectionRepository.deleteFoodFromCollection(collectionId, request);
        var collection = getCollectionById(collectionId);
        var collectionIngredients = ingredientService.getIngredientsByCollectionId(collectionId);

        return new CollectionWithFoods(
                new Collection(
                        collection.id(),
                        collection.name()
                ),
                collectionIngredients
        );
    }

    public CollectionWithFoods getCollectionWithFoods(Integer collectionId) {
        var collection = getCollectionById(collectionId);
        var collectionIngredients = ingredientService.getIngredientsByCollectionId(collectionId);

        return new CollectionWithFoods(
                new Collection(
                        collection.id(),
                        collection.name()
                ),
                collectionIngredients
        );
    }
}
