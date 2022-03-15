-- can replace withYour table script here but pkey field name must same
drop table Account;
create table Account(
	accountID    varchar(50) not null,
    accountType  varchar(50) not null,
    username     varchar(50) not null,
    primary key(accountID)
);


-- MUST PUT BELOW AccountTable If applicable
drop table Comment;
create table Comment(
    commentSeq   INT auto_increment,
	accountID    varchar(50) not null,
    commentDate  varchar(50) not null,
	commentTime  time(0)   not null,
    roomID       varchar(50) not null,
    content      longtext    not null,
    isOrder      binary      not null,
    primary key(commentSeq, accountID, commentDate, commentTime)
);


insert into RoomStatus values('RoomA', "active");
insert into RoomStatus values('RoomB', "ended");

-- Account Table Data

insert into Account (accountID, accountType, username) values ('A31036', 'buyer', 'lcranham0');
insert into Account (accountID, accountType, username) values ('A52561', 'seller', 'bwhyke1');
insert into Account (accountID, accountType, username) values ('A90570', 'seller', 'arozzier2');
insert into Account (accountID, accountType, username) values ('A40187', 'seller', 'sseagar3');
insert into Account (accountID, accountType, username) values ('A52688', 'buyer', 'ngeator4');
insert into Account (accountID, accountType, username) values ('A53296', 'buyer', 'eyanshonok5');
insert into Account (accountID, accountType, username) values ('A17201', 'seller', 'ebernini6');
insert into Account (accountID, accountType, username) values ('A83028', 'buyer', 'lbillingham7');
insert into Account (accountID, accountType, username) values ('A16195', 'seller', 'smablestone8');
insert into Account (accountID, accountType, username) values ('A63052', 'seller', 'fhamfleet9');
insert into Account (accountID, accountType, username) values ('A72506', 'buyer', 'gparmbya');
insert into Account (accountID, accountType, username) values ('A91056', 'buyer', 'abthamb');
insert into Account (accountID, accountType, username) values ('A51783', 'seller', 'mparncuttc');
insert into Account (accountID, accountType, username) values ('A19122', 'seller', 'cbastowd');
insert into Account (accountID, accountType, username) values ('A48489', 'buyer', 'hmewse');
insert into Account (accountID, accountType, username) values ('A69264', 'buyer', 'afranceschellif');
insert into Account (accountID, accountType, username) values ('A27283', 'buyer', 'rhenmarshg');
insert into Account (accountID, accountType, username) values ('A50323', 'buyer', 'dmallyah');
insert into Account (accountID, accountType, username) values ('A06820', 'buyer', 'ewinspearei');
insert into Account (accountID, accountType, username) values ('A22842', 'seller', 'ldreppj');
insert into Account (accountID, accountType, username) values ('A07374', 'buyer', 'vhaguek');
insert into Account (accountID, accountType, username) values ('A89421', 'buyer', 'sbleasel');
insert into Account (accountID, accountType, username) values ('A08591', 'buyer', 'rpetelm');
insert into Account (accountID, accountType, username) values ('A74638', 'seller', 'atrevettn');
insert into Account (accountID, accountType, username) values ('A15607', 'seller', 'mconnichieo');
insert into Account (accountID, accountType, username) values ('A56860', 'seller', 'radnamp');
insert into Account (accountID, accountType, username) values ('A79820', 'buyer', 'fbiswellq');
insert into Account (accountID, accountType, username) values ('A83266', 'buyer', 'gpimmocker');
insert into Account (accountID, accountType, username) values ('A48771', 'buyer', 'vwithefords');
insert into Account (accountID, accountType, username) values ('A06853', 'buyer', 'lworsnupt');





