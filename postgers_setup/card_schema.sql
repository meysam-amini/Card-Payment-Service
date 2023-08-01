CREATE TABLE Card (
id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
cardnumber VARCHAR(255) NOT NULL,
fullname VARCHAR(255) NOT NULL,
cvv2 INTEGER,
password1 INTEGER,
password2 INTEGER,
expiredate VARCHAR(255),
balance INTEGER,
version INTEGER DEFAULT(0)
);