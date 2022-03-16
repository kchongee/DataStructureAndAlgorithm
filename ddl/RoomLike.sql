drop table roomlike;
create table roomLike(
	roomID    varchar(50) not null,
    accountID varchar(50) not null,
    value     varchar(50) not null,
    likeTime  time(0)     not null,
    primary key(roomID, accountID)
)