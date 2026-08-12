package nl.furka.angularknowledge.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public enum Unit {
    ML(1),
    G(2),
    AMOUNT(3),
    KCAL(4),
    MG(5);

    private final Integer id;

    Unit(Integer id) {
        this.id = id;
    }

    @JsonValue
    public String getNameLowerCased() {
        return name().toLowerCase();
    }

    public Integer getId() {
        return id;
    }

    public static Unit fromId(Integer id) {
        for (Unit u : values()) {
            if (Objects.equals(u.id, id)) return u;
        }
        throw new IllegalArgumentException("Unknown Unit id: " + id);
    }
}
