CREATE TABLE Users
(
    userId                serial PRIMARY KEY,
    email                 character varying,
    name                  character varying,
    surname               character varying,
    password              character varying,
    role                  character varying,
    accountActivityStatus character varying
);