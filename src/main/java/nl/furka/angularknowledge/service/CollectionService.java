package nl.furka.angularknowledge.service;

import nl.furka.angularknowledge.model.Collection;
import nl.furka.angularknowledge.repository.CollectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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
}
