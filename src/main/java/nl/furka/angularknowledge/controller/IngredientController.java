package nl.furka.angularknowledge.controller;

import nl.furka.angularknowledge.dto.AddPropertyToIngredientRequest;
import nl.furka.angularknowledge.dto.CreateIngredientRequest;
import nl.furka.angularknowledge.dto.IngredientPropertiesResponse;
import nl.furka.angularknowledge.model.IngredientWithUnit;
import nl.furka.angularknowledge.model.filter.IngredientFilter;
import nl.furka.angularknowledge.service.IngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class IngredientController {
    private final IngredientService ingredientService;
    private final Logger logger = LoggerFactory.getLogger(IngredientController.class);

    IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping("ingredients")
    public ResponseEntity<IngredientWithUnit> addIngredient(@RequestBody CreateIngredientRequest ingredient) {
        return ResponseEntity.ok(ingredientService.addIngredient(ingredient));
    }

    @GetMapping("ingredients")
    public ResponseEntity<List<IngredientWithUnit>> getIngredients(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer servingSize,
            @RequestParam(required = false) Integer unitId
    ) {
        IngredientFilter filter = new IngredientFilter(id, name, servingSize, unitId);
        logger.info(filter.toString());
        return ResponseEntity.ok(ingredientService.getIngredients(filter));
    }

    @GetMapping("ingredients/{id}")
    public ResponseEntity<IngredientWithUnit> getIngredientById(@PathVariable Integer id) {
        return ResponseEntity.ok(ingredientService.getIngredientById(id));
    }

    @GetMapping("ingredients/person/{id}")
    public ResponseEntity<List<IngredientWithUnit>> getIngredientsByPersonId(@PathVariable Integer id) {
        return ResponseEntity.ok(ingredientService.getIngredientsByPersonId(id));
    }

    @DeleteMapping("ingredients/{id}")
    public ResponseEntity<IngredientWithUnit> deleteIngredientById(@PathVariable Integer id) {
        return ResponseEntity.ok(ingredientService.deleteIngredientById(id));
    }

    @PostMapping("ingredients/property")
    public ResponseEntity<IngredientPropertiesResponse> addPropertyToIngredient(
            @RequestBody AddPropertyToIngredientRequest request
    ) {
        return ResponseEntity.ok(ingredientService.addPropertyToIngredient(request));
    }
}
