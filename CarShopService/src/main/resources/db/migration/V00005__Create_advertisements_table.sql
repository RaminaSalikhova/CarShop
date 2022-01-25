CREATE TABLE Advertisements
(
    advertisementId serial PRIMARY KEY,
    carId           serial,
    userId          serial,
    costId          serial,
    creationDate    date,
    activityStatus  character varying,
    FOREIGN KEY (carId)
        REFERENCES Cars (carId),
    FOREIGN KEY (userId)
        REFERENCES Users (userId),
    FOREIGN KEY (costId)
        REFERENCES Costs (costId)
);