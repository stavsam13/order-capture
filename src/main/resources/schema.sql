DROP TABLE IF EXISTS ORDERS;

CREATE TABLE PILOTES (
  order_Number INT AUTO_INCREMENT  PRIMARY KEY,
  pilotes INT,
  order_Total DOUBLE(2),
  order_Time DATETIME,
  clientId INT
);

CREATE TABLE ADDRESS (
  addressId INT AUTO_INCREMENT  PRIMARY KEY,
  streetName VARCHAR(250) ,
  streetNumber VARCHAR(250) ,
  city VARCHAR(250),
  postcode VARCHAR(250),
  country VARCHAR(250)
);

CREATE TABLE CLIENT (
  clientId INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250),
  last_name VARCHAR(250),
  email VARCHAR(250),
  telephone VARCHAR(250)
);