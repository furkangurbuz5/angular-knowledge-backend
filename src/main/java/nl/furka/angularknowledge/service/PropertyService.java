package nl.furka.angularknowledge.service;

import nl.furka.angularknowledge.dto.CreatePropertyRequest;
import nl.furka.angularknowledge.model.Ingredient;
import nl.furka.angularknowledge.model.Property;
import nl.furka.angularknowledge.model.Unit;
import nl.furka.angularknowledge.model.filter.PropertyFilter;
import nl.furka.angularknowledge.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;

    PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public List<Property> getAllProperties(PropertyFilter filter) {
        return propertyRepository.getAllProperties(filter)
                .stream()
                .map((propertyResponse) -> new Property(
                        propertyResponse.id(),
                        propertyResponse.name(),
                        Unit.fromId(propertyResponse.unitId())
                )).toList();
    }

    public List<Property> getAllProperties() {
        return propertyRepository.getAllProperties()
                .stream()
                .map((propertyResponse) -> new Property(
                        propertyResponse.id(),
                        propertyResponse.name(),
                        Unit.fromId(propertyResponse.unitId())
                )).toList();
    }

    public Property getPropertyById(Integer id) {
        var propertyResponse = propertyRepository.getPropertyById(id);

        return new Property(
                propertyResponse.id(),
                propertyResponse.name(),
                Unit.fromId(propertyResponse.unitId())
        );
    }

    public Property addProperty(CreatePropertyRequest property) {
        var propertyResponse = propertyRepository.addProperty(property);

        return new Property(
                propertyResponse.id(),
                propertyResponse.name(),
                Unit.fromId(propertyResponse.unitId())
        );
    }

    public Property deletePropertyById(Integer id) {
        var propertyResponse = propertyRepository.deletePropertyById(id);

        return new Property(
                propertyResponse.id(),
                propertyResponse.name(),
                Unit.fromId(propertyResponse.unitId())
        );
    }
}
