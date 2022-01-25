CREATE TABLE Photos
(
    photoId serial PRIMARY KEY,
    path    character varying,
    carId   serial,
    FOREIGN KEY (carId)
        REFERENCES Cars (carId)
);