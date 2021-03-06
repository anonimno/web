DROP TABLE HOURRANGE;
DROP TABLE ACTIVITYTAGS;
DROP TABLE TAG;
DROP TABLE BOOKINGINSTRUCTORS;
DROP TABLE BOOKINGCLIENTS;
DROP TABLE BOOKING;
DROP TYPE BOOKINGSTATE;
DROP TABLE ACTIVITYINSTRUCTORS;
DROP TABLE ACTIVITY;
DROP TABLE USERACCOUNT;
DROP TYPE USERTYPE;

CREATE TYPE USERTYPE AS ENUM('client', 'instructor');

CREATE TABLE USERACCOUNT (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	username VARCHAR(20) NOT NULL,
	password VARCHAR(60) NOT NULL,
	email VARCHAR(60) NOT NULL,
	nif VARCHAR(20) NOT NULL,
	firstName VARCHAR(30) NOT NULL,
	lastName VARCHAR(30) NOT NULL,
	phoneNumber VARCHAR(12) NOT NULL,
	address VARCHAR(50),
	gender BOOLEAN,
	pictureUrl TEXT,
	userType USERTYPE NOT NULL
);

CREATE TABLE ACTIVITY (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	description TEXT,
	difficultyLevel SMALLINT NOT NULL CHECK (difficultyLevel >= 0 AND difficultyLevel <= 5),
	pricePerPerson NUMERIC(9,2) NOT NULL, 
	minPersons SMALLINT NOT NULL CHECK (minPersons >= 0), 
	maxPersons SMALLINT NOT NULL CHECK (minPersons <= 2000), 
	recommendedAge SMALLINT NOT NULL, 
	rating NUMERIC(3, 2) NULL
);

CREATE TABLE ACTIVITYINSTRUCTORS (
	idActivity BIGINT NOT NULL REFERENCES ACTIVITY ON DELETE CASCADE ON UPDATE CASCADE,
	idInstructor BIGINT NOT NULL REFERENCES USERACCOUNT ON DELETE CASCADE ON UPDATE CASCADE,
	PRIMARY KEY (idActivity, idInstructor)
);

CREATE TYPE BOOKINGSTATE AS ENUM('pending', 'confirmed', 'rejected');

CREATE TABLE BOOKING (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	idActivity BIGINT NULL REFERENCES ACTIVITY ON DELETE SET NULL ON UPDATE CASCADE,
	creationDate TIMESTAMP NOT NULL, 
	state BOOKINGSTATE NOT NULL, 
	numAttendees SMALLINT NOT NULL CHECK (numAttendees >= 0 AND numAttendees <= 2000)
);

CREATE TABLE BOOKINGCLIENTS (
	idBooking BIGINT NOT NULL REFERENCES BOOKING ON DELETE CASCADE ON UPDATE CASCADE,
	idClient BIGINT NOT NULL REFERENCES USERACCOUNT ON DELETE CASCADE ON UPDATE CASCADE,
	review TEXT NULL,
	rating NUMERIC(3, 2) NOT NULL,
	PRIMARY KEY (idBooking, idClient)
);

CREATE TABLE BOOKINGINSTRUCTORS (
	idBooking BIGINT NOT NULL REFERENCES BOOKING ON DELETE CASCADE ON UPDATE CASCADE,
	idInstructor BIGINT NOT NULL REFERENCES USERACCOUNT ON DELETE CASCADE ON UPDATE CASCADE,
	PRIMARY KEY (idBooking, idInstructor)
);

CREATE TABLE TAG (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(20) NOT NULL
);

CREATE TABLE ACTIVITYTAGS (
	idActivity BIGINT NOT NULL REFERENCES ACTIVITY ON DELETE CASCADE ON UPDATE CASCADE,
	idTag BIGINT NOT NULL REFERENCES TAG ON DELETE CASCADE ON UPDATE CASCADE,
	PRIMARY KEY (idActivity, idTag)
);

CREATE TABLE HOURRANGE (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  idActivity BIGINT NOT NULL REFERENCES ACTIVITY ON DELETE SET NULL ON UPDATE CASCADE,
  startTime TIME NOT NULL,
  endTime TIME NOT NULL,
  days VARCHAR(14) NULL
);
