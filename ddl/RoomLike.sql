create table RoomLike(
	accountID varchar(5)  not null,
    roomID    varchar(5)  not null,
    status    varchar(10) not null,
    primary key(accountID, roomID)
)