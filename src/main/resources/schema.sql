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

CREATE TABLE IF NOT EXISTS person
(
    id                INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name        TEXT NOT NULL,
    last_name         TEXT NOT NULL,
    email             TEXT NOT NULL UNIQUE,
    car               TEXT,
    city              TEXT,
    country_of_origin TEXT,
    bank              TEXT
);

CREATE TABLE IF NOT EXISTS person_ingredients
(
    id            INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    person_id     INTEGER NOT NULL,
    ingredient_id INTEGER NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person (id),
    FOREIGN KEY (ingredient_id) REFERENCES ingredients (id),
    UNIQUE (person_id, ingredient_id)
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
    FOREIGN KEY (ingredient_id) REFERENCES ingredients (id)
);