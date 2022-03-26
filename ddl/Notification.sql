DROP TABLE if exists Notification;

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

INSERT INTO Notification VALUES("N1", "A02", "Jessy", 
"NOtification 1","body of Notification 1", "13/05/2020",false);
INSERT INTO Notification VALUES("N2", "A02", "Ricky",
 "NOtification 2", "body of Notification 2", "25/02/2021", false);
INSERT INTO Notification VALUES("N3", "A02", "Richard",
"NOtification 3","body of Notification 3", "30/01/2022",false);
INSERT INTO Notification VALUES("N4", "A03", "Jessy", 
"NOtification 4","body of Notification 4", "13/05/2020",false);
INSERT INTO Notification VALUES("N5", "A03", "Ricky",
 "NOtification 5", "body of Notification 5", "25/02/2021", false);
INSERT INTO Notification VALUES("N6", "A03", "Richard",
"NOtification 6","body of Notification 6", "30/01/2022",false);