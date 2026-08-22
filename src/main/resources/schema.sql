CREATE TABLE IF NOT EXISTS properties
(
    id      INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name    TEXT    NOT NULL,
    unit_id INTEGER NOT NULL,
    UNIQUE (name, unit_id)
);


CREATE TABLE IF NOT EXISTS ingredients
(
    id           INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name         TEXT    NOT NULL,
    serving_size INTEGER NOT NULL,
    unit_id      INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS ingredient_properties
(
    id            INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ingredient_id INTEGER NOT NULL,
    property_id   INTEGER NOT NULL,
    value         INTEGER NOT NULL,
    FOREIGN KEY (ingredient_id) REFERENCES ingredients (id),
    FOREIGN KEY (property_id) REFERENCES properties (id),
    UNIQUE (ingredient_id, property_id)
);

CREATE TABLE IF NOT EXISTS collections
(
    id   INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name TEXT UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS collection_ingredients
(
    id            INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    collection_id INTEGER NOT NULL,
    ingredient_id INTEGER NOT NULL,
    quantity      INTEGER NOT NULL,
    FOREIGN KEY (collection_id) REFERENCES collections (id),
    FOREIGN KEY (ingredient_id) REFERENCES ingredients (id),
    UNIQUE(collection_id, ingredient_id)
);
