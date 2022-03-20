DROP TABLE Notification;

CREATE TABLE Notification(
notificationID varchar(5) NOT NULL,
accountID varchar(5) NOT NULL,
sellerName varchar(20) NOT NULL,
title varchar(50) NOT NULL,
message varchar(300) NOT NULL,
date varchar (10) NOT NULL,
isRead boolean NOT NULL,
primary key (notificationID)
);

INSERT INTO Notification VALUES("N1", "A1", "Jessy", 
"blah","blah Nathan", "13/05/2021",false);
INSERT INTO Notification VALUES("N2", "A2", "Ricky",
"blah", "blah Ringo", "25/02/2022", false);
INSERT INTO Notification VALUES("N3", "A3", "Richard",
"blah","blah Lennon", "30/01/2020",false);