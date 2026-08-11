package nl.furka.angularknowledge.controller;

import nl.furka.angularknowledge.dto.CreatePropertyRequest;
import nl.furka.angularknowledge.model.Property;
import nl.furka.angularknowledge.model.filter.PropertyFilter;
import nl.furka.angularknowledge.service.PropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class PropertyController {
    private final PropertyService propertyService;
    private final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping("properties")
    public ResponseEntity<Property> addProperty(@RequestBody CreatePropertyRequest property) {
        logger.info("adding property {}", property);
        return ResponseEntity.ok(propertyService.addProperty(property));
    }

    @GetMapping("properties")
    public ResponseEntity<List<Property>> getProperties(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer unitId
    ) {
        PropertyFilter filter = new PropertyFilter(id, name, unitId);
        logger.info(filter.toString());
        return ResponseEntity.ok(propertyService.getAllProperties(filter));
    }

    @GetMapping("properties/{id}")
    public ResponseEntity<Property> getPropertyById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }

    @DeleteMapping("properties/{id}")
    public ResponseEntity<Property> deletePropertyById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(propertyService.deletePropertyById(id));
    }
}
