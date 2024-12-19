CREATE TABLE travels (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    duration VARCHAR(50),
    author_id INTEGER,
    FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE SET NULL
);
CREATE TABLE users(
    id SERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    email varchar(255) NOT NULL,
    password VARCHAR(128) NOT NULL,
    date_of_birth DATE NOT NULL
);
CREATE TABLE images(
    id serial PRIMARY KEY,
    travel_id int NOT NULL ,
    image_url varchar(255),
    FOREIGN KEY (travel_id) REFERENCES travels(id)
);
CREATE TABLE locations (
    id serial  PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE ,
    country VARCHAR(64) NOT NULL
);
CREATE TABLE travel_location (
    travel_id int,
    location_id int,
    PRIMARY KEY (travel_id, location_id),
    FOREIGN KEY (travel_id) REFERENCES travels(id),
    FOREIGN KEY (location_id) REFERENCES locations(id)
);