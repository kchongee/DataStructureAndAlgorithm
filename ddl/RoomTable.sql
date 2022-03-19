drop table if exists room;
create table room(
	roomID     int auto_increment not null,
    roomTitle  long        not null,
    isOpen     boolean     not null,
    accountID  varchar(50) not null,
    primary key(roomID)
);

insert into Room (roomTitle, isOpen, accountID) values ('Stamm-Hayes', true, 'A01');
insert into Room (roomTitle, isOpen, accountID) values ('Cassin, Kuhn and Corwin', true, 'A08');
insert into Room (roomTitle, isOpen, accountID) values ('Boehm, Hoeger and Padberg', true, 'A10');
insert into Room (roomTitle, isOpen, accountID) values ('Bednar, Hagenes and Bauch', true, 'A11');
insert into Room (roomTitle, isOpen, accountID) values ('Greenholt Group', true, 'A13');
insert into Room (roomTitle, isOpen, accountID) values ('Jenkins-Cronin', true, 'A15');
insert into Room (roomTitle, isOpen, accountID) values ('Kuphal-Metz', true, 'A17');
insert into Room (roomTitle, isOpen, accountID) values ('Green, Abshire and Gislason', true, 'A19');
insert into Room (roomTitle, isOpen, accountID) values ('Walter and Sons', true, 'A20');
insert into Room (roomTitle, isOpen, accountID) values ('Dibbert, Nitzsche and Quigley', true, 'A21');
insert into Room (roomTitle, isOpen, accountID) values ('Lueilwitz-Schamberger', true, 'A22');
insert into Room (roomTitle, isOpen, accountID) values ('McLaughlin-Hickle', true, 'A26');
insert into Room (roomTitle, isOpen, accountID) values ('Windler Inc', true, 'A29');
insert into Room (roomTitle, isOpen, accountID) values ('Farrell LLC', true, 'A30');
insert into Room (roomTitle, isOpen, accountID) values ('Kuhlman and Sons', true, 'A31');
