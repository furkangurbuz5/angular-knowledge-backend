package nl.furka.angularknowledge.controller;

import nl.furka.angularknowledge.dto.AddFoodToCollectionRequest;
import nl.furka.angularknowledge.dto.CreateCollectionRequest;
import nl.furka.angularknowledge.dto.DeleteFoodFromCollectionRequest;
import nl.furka.angularknowledge.model.Collection;
import nl.furka.angularknowledge.model.CollectionWithFoods;
import nl.furka.angularknowledge.model.CollectionWithFoodsAndProperties;
import nl.furka.angularknowledge.model.CollectionWithProperties;
import nl.furka.angularknowledge.service.CollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class CollectionController {
    private static final Logger log = LoggerFactory.getLogger(CollectionController.class);
    private final CollectionService collectionService;

    CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping("collections")
    public ResponseEntity<List<Collection>> getAllCollections() {
        return ResponseEntity.ok(collectionService.getAllCollections());
    }

    @GetMapping("collections/{id}")
    public ResponseEntity<Collection> getCollectionById(@PathVariable Integer id) {
        return ResponseEntity.ok(collectionService.getCollectionById(id));
    }

    @GetMapping("collections/{id}/properties")
    public ResponseEntity<CollectionWithProperties> getCollectionPropertiesById(@PathVariable Integer id) {
        return ResponseEntity.ok(collectionService.getCollectionWithProperties(id));
    }

    @PostMapping("collections")
    public ResponseEntity<Collection> addCollection(
            @RequestBody @Validated CreateCollectionRequest request
    ) {
        return ResponseEntity.ok(collectionService.addCollection(request));
    }

    @GetMapping("collections/{id}/foods")
    public ResponseEntity<CollectionWithFoodsAndProperties> getCollectionWithFoods(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(collectionService.getCollectionWithFoodsAndProperties(id));
    }

    @PostMapping("collections/{id}/foods")
    public ResponseEntity<Collection> addFoodToCollection(
            @PathVariable Integer id,
            @RequestBody @Validated AddFoodToCollectionRequest request
    ) {
        return ResponseEntity.ok(collectionService.addFoodToCollection(id, request));
    }

    @DeleteMapping("collections/{id}")
    public ResponseEntity<Void> deleteCollection(@PathVariable Integer id) {
        collectionService.deleteCollectionById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("collections/{id}/foods")
    public ResponseEntity<Void> deleteFoodFromCollection(
            @PathVariable Integer id,
            @RequestBody @Validated DeleteFoodFromCollectionRequest request
    ) {
        collectionService.deleteFoodFromCollection(id, request);
        return ResponseEntity.noContent().build();
    }
}
