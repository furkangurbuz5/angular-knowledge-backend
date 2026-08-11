package nl.furka.angularknowledge.service;

import nl.furka.angularknowledge.dto.CreateIngredientRequest;
import nl.furka.angularknowledge.dto.IngredientPropertiesResponse;
import nl.furka.angularknowledge.model.Ingredient;
import nl.furka.angularknowledge.model.IngredientWithProperties;
import nl.furka.angularknowledge.model.Property;
import nl.furka.angularknowledge.model.filter.IngredientFilter;
import nl.furka.angularknowledge.repository.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final PropertyService propertyService;

    IngredientService(IngredientRepository ingredientRepository, PropertyService propertyService) {
        this.ingredientRepository = ingredientRepository;
        this.propertyService = propertyService;
    }

    public Ingredient addIngredient(CreateIngredientRequest ingredient) {
        return ingredientRepository.addIngredient(ingredient);
    }

    public List<Ingredient> getIngredients(IngredientFilter filter) {
        return ingredientRepository.getIngredients(filter);
    }

    public Ingredient getIngredientById(Integer ingredientId) {
        return ingredientRepository.getIngredientById(ingredientId);
    }

    public Ingredient deleteIngredientById(Integer ingredientId) {
        return ingredientRepository.deleteIngredientById(ingredientId);
    }

    public List<Ingredient> getIngredients() {
        return ingredientRepository.getIngredients();
    }

    @Transactional
    public IngredientWithProperties getIngredientWithProperties(Integer ingredientId) {
        var ingredient = getIngredientById(ingredientId);
        var ingredientProperties = ingredientRepository.getIngredientProperties();
        var ingredientPropertyIds = ingredientProperties
                .stream()
                .map(IngredientPropertiesResponse::propertyId)
                .toList();
        var properties = propertyService.getAllProperties()
                .stream()
                .filter((property) -> ingredientPropertyIds.contains(property.id())).toList();

        return new IngredientWithProperties(
                ingredient,
                properties
        );
    }
}
