package nl.furka.angularknowledge.service;

import nl.furka.angularknowledge.dto.AddIngredientToPersonRequest;
import nl.furka.angularknowledge.dto.CreatePersonRequest;
import nl.furka.angularknowledge.dto.PersonIngredientsResponse;
import nl.furka.angularknowledge.model.*;
import nl.furka.angularknowledge.model.filter.IngredientFilter;
import nl.furka.angularknowledge.repository.PersonRepository;
import nl.furka.angularknowledge.dto.UpdatePersonRequest;
import nl.furka.angularknowledge.model.filter.PersonFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PropertyService propertyService;
    private final IngredientService ingredientService;

    PersonService(PersonRepository personRepository, PropertyService propertyService, IngredientService ingredientService) {
        this.personRepository = personRepository;
        this.propertyService = propertyService;
        this.ingredientService = ingredientService;
    }

    public List<Person> getAllPersons(PersonFilter filter) {
        return personRepository.getAllPersons(filter);
    }

    public Person getPersonById(Integer id) {
        return personRepository.getPersonById(id);
    }

    public Person addPerson(CreatePersonRequest person) {
        return personRepository.addPerson(person);
    }

    public Person deletePersonById(Integer id) {
        return personRepository.deletePersonById(id);
    }

    public Person updatePerson(Integer id, UpdatePersonRequest person) {
        return personRepository.updatePerson(id, person);
    }

    public List<PersonIngredientsResponse> getPersonIngredients(Integer personId) {
        return personRepository.getPersonIngredientsById(personId);
    }

    public PersonIngredientsResponse addIngredientToPerson(AddIngredientToPersonRequest request) {
        return personRepository.addIngredientToPerson(request);
    }

    @Transactional
    public PersonWithIngredients getPersonWithIngredients(Integer personId) {
        Person person = personRepository.getPersonById(personId);
        List<Ingredient> personIngredients = ingredientService.getIngredientsByPersonId(personId);

        List<IngredientWithProperties> ingredientWithProperties = personIngredients
                .stream()
                .map(ingredient -> {
                    var propertiesByIngredient =
                            propertyService.getPropertiesByIngredientId(ingredient.id());
                    return new IngredientWithProperties(
                            ingredient,
                            propertiesByIngredient
                    );
                }).toList();
        return new PersonWithIngredients(
                person,
                ingredientWithProperties
        );
    }
}





















