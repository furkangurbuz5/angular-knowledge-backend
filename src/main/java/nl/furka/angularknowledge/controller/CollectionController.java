package nl.furka.angularknowledge.controller;

import nl.furka.angularknowledge.model.Collection;
import nl.furka.angularknowledge.service.CollectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class CollectionController {
    private CollectionService collectionService;

    CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping("collections")
    public ResponseEntity<List<Collection>> getAllCollections(){
        return ResponseEntity.ok(collectionService.getAllCollections());
    }
}
