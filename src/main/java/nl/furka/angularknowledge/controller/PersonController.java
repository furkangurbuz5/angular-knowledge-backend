package nl.furka.angularknowledge.controller;

import nl.furka.angularknowledge.dto.CreatePersonRequest;
import nl.furka.angularknowledge.dto.PersonIngredientsResponse;
import nl.furka.angularknowledge.dto.UpdatePersonRequest;
import nl.furka.angularknowledge.model.Ingredient;
import nl.furka.angularknowledge.model.Person;
import nl.furka.angularknowledge.model.filter.PersonFilter;
import nl.furka.angularknowledge.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class PersonController {
    private final PersonService personService;
    private final Logger logger = LoggerFactory.getLogger(PersonController.class);

    PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("persons")
    public ResponseEntity<List<Person>> getAllPersons(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String car,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String countryOfOrigin,
            @RequestParam(required = false) String bank
    ) {
        PersonFilter filter = new PersonFilter(id, firstName, lastName, email, car, city, countryOfOrigin, bank);
        logger.info(filter.toString());

        return ResponseEntity.ok(personService.getAllPersons(filter));
    }

    @GetMapping("persons/{id}")
    public ResponseEntity<Person> getPersonById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(personService.getPersonById(id));
    }

    @PostMapping("persons")
    public ResponseEntity<Person> addPerson(
            @RequestBody CreatePersonRequest person
    ) {
        return ResponseEntity.ok(personService.addPerson(person));
    }

    @PatchMapping("persons/{id}")
    public ResponseEntity<Person> updatePerson(
            @PathVariable Integer id,
            @RequestBody UpdatePersonRequest person
    ) {
        return ResponseEntity.ok(personService.updatePerson(id, person));
    }

    @DeleteMapping("persons/{id}")
    public ResponseEntity<Person> deletePersonById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(personService.deletePersonById(id));
    }

    @GetMapping("persons/ingredients/{id}")
    public ResponseEntity<List<PersonIngredientsResponse>> getIngredientsByPersonId(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
                personService.getPersonIngredients(id)
        );
    }

}
