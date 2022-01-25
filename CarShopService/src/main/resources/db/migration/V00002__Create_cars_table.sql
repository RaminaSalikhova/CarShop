CREATE TABLE Cars
(
    carId            serial PRIMARY KEY,
    mark             character varying,
    model            character varying,
    yearOfProduction date,
    state            character varying,
    mileage          float
);