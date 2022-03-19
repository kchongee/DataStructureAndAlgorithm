DROP TABLE Notification;

CREATE TABLE Notification(
notificationID varchar(5) NOT NULL,
accountID varchar(5) NOT NULL,
userName varchar(20) NOT NULL,
message varchar(300) NOT NULL,
date varchar (10) NOT NULL,
primary key (notificationID)
);

INSERT INTO Notification VALUES("N1", "A1", "nathan", 
"blah Nathan", "13/05/2021");
INSERT INTO Notification VALUES("N2", "A2", "ringo", 
"blah Ringo", "25/02/2022");
INSERT INTO Notification VALUES("N3", "A3", "lennon", 
"blah Lennon", "30/01/2020");