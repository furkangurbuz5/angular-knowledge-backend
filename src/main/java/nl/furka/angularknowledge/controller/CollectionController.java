package nl.furka.angularknowledge.controller;

import nl.furka.angularknowledge.dto.AddFoodToCollectionRequest;
import nl.furka.angularknowledge.dto.CreateCollectionRequest;
import nl.furka.angularknowledge.model.Collection;
import nl.furka.angularknowledge.service.CollectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class CollectionController {
    private final CollectionService collectionService;

    CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping("collections")
    public ResponseEntity<List<Collection>> getAllCollections() {
        return ResponseEntity.ok(collectionService.getAllCollections());
    }

    @PostMapping("collections")
    public ResponseEntity<Collection> addCollection(
            @RequestBody CreateCollectionRequest request
    ) {
        return ResponseEntity.ok(collectionService.addCollection(request));
    }

    @PostMapping("collections/{id}/foods")
    public ResponseEntity<Collection> addFoodToCollection(
            @PathVariable Integer id,
            @RequestBody AddFoodToCollectionRequest request
    ) {
        return ResponseEntity.ok(collectionService.addFoodToCollection(id, request));
    }
}
