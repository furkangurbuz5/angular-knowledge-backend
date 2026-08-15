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

    CollectionService(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
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
        var response = collectionRepository.addFoodToCollection(id, request);
        return null; //TODO
    }
}
