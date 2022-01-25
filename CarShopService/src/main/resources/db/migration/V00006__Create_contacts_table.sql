CREATE TABLE Contacts
(
    contactId       serial PRIMARY KEY,
    number          character varying,
    advertisementId serial,
    FOREIGN KEY (advertisementId)
        REFERENCES Advertisements (advertisementId)
);