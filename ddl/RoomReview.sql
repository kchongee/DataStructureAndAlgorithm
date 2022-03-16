create table RoomReview(
	accountID varchar(50) not null,
    roomID    varchar(50) not null,
    star      int         not null,
    msg       long        not null,
    revTime   time(0)     not null,
    primary key(accountID, roomID)
)