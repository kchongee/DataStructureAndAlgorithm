drop table if exists roomCatalog;

create table roomCatalog(
	roomID    int not null,
    productID int not null
    primary key(roomID, productID)
);