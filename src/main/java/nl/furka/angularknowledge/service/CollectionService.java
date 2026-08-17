package nl.furka.angularknowledge.service;

import nl.furka.angularknowledge.dto.AddFoodToCollectionRequest;
import nl.furka.angularknowledge.dto.CreateCollectionRequest;
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

    public Collection addCollection(CreateCollectionRequest request) {
        var response = collectionRepository.addCollection(request);

        return new Collection(
                response.id(),
                response.name()
        );
    }

    public Collection addFoodToCollection(Integer id, AddFoodToCollectionRequest request) {
        var collectionIngredientsResponse = collectionRepository.addFoodToCollection(id, request);
        var collection = collectionRepository.getCollectionById(collectionIngredientsResponse.id());

        return new Collection(
                collection.id(),
                collection.name()
        );
    }

    public CollectionWithFoods getCollectionWithFoods(Integer collectionId) {
        var collection = collectionRepository.getCollectionById(collectionId);
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
