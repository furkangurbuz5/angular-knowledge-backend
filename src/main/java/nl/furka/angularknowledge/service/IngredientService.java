package nl.furka.angularknowledge.service;

import nl.furka.angularknowledge.dto.*;
import nl.furka.angularknowledge.model.*;
import nl.furka.angularknowledge.model.filter.IngredientFilter;
import nl.furka.angularknowledge.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public IngredientWithUnit addIngredient(CreateIngredientRequest ingredient) {
        var ingredientResponse = ingredientRepository.addIngredient(ingredient);

        return new IngredientWithUnit(
                ingredientResponse.id(),
                ingredientResponse.name(),
                ingredientResponse.servingSize(),
                Unit.fromId(ingredientResponse.unitId())
        );
    }

    public List<IngredientWithUnit> getIngredients(IngredientFilter filter) {
        return ingredientRepository.getIngredients(filter)
                .stream()
                .map((ingredientResponse) -> new IngredientWithUnit(
                        ingredientResponse.id(),
                        ingredientResponse.name(),
                        ingredientResponse.servingSize(),
                        Unit.fromId(ingredientResponse.unitId())
                )).toList();
    }

    public List<IngredientWithUnit> getIngredients() {
        return ingredientRepository.getIngredients()
                .stream()
                .map((ingredientResponse) -> new IngredientWithUnit(
                        ingredientResponse.id(),
                        ingredientResponse.name(),
                        ingredientResponse.servingSize(),
                        Unit.fromId(ingredientResponse.unitId())
                )).toList();
    }

    public IngredientWithUnit getIngredientById(Integer ingredientId) {
        var ingredientResponse = ingredientRepository.getIngredientById(ingredientId);

        return new IngredientWithUnit(
                ingredientResponse.id(),
                ingredientResponse.name(),
                ingredientResponse.servingSize(),
                Unit.fromId(ingredientResponse.unitId())
        );
    }

    public IngredientWithUnit deleteIngredientById(Integer ingredientId) {
        var ingredientResponse = ingredientRepository.deleteIngredientById(ingredientId);

        return new IngredientWithUnit(
                ingredientResponse.id(),
                ingredientResponse.name(),
                ingredientResponse.servingSize(),
                Unit.fromId(ingredientResponse.unitId())
        );
    }

    public List<IngredientWithUnit> getIngredientsByPersonId(Integer personId) {
        return ingredientRepository.getIngredientsByPersonId(personId)
                .stream()
                .map((ingredientResponse) -> new IngredientWithUnit(
                        ingredientResponse.id(),
                        ingredientResponse.name(),
                        ingredientResponse.servingSize(),
                        Unit.fromId(ingredientResponse.unitId())
                )).toList();
    }

    public IngredientPropertiesResponse addPropertyToIngredient(AddPropertyToIngredientRequest request) {
        return ingredientRepository.addPropertyToIngredient(request);
    }

    public List<IngredientWithUnit> getIngredientsByCollectionId(Integer collectionId) {
        return ingredientRepository.getIngredientsByCollectionId(collectionId)
                .stream()
                .map((ingredientResponse) -> new IngredientWithUnit(
                        ingredientResponse.id(),
                        ingredientResponse.name(),
                        ingredientResponse.servingSize(),
                        Unit.fromId(ingredientResponse.unitId())
                )).toList();
    }
}
