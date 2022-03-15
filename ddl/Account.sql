DROP TABLE Account;

CREATE TABLE Account(
    accountID varchar(5) NOT NULL,
    userName varchar(20) NOT NULL,
    userPwd varchar(20) NOT NULL,
    name varchar(50) NOT NULL,
    address varchar(100) NOT NULL,
    email varchar(50) NOT NULL,
    isSeller integer NOT NULL,
    primary key (accountID)
);

INSERT INTO Account VALUES("A1", "nathan", "nathan", 
"Nathan", "123 Magnolia Street", "nathan41@gmail.com", 0);
INSERT INTO Account VALUES("A2", "ringo", "ringo", 
"Ringo Starr", "123 McCarthy Street", "beatles1@gmail.com", 1);
INSERT INTO Account VALUES("A3", "lennon", "lennon", 
"John Lennon", "123 McMurdo Street", "beatles2@gmail.com", 0);