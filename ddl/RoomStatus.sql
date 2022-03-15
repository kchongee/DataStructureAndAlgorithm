drop table RoomStatus;
create table RoomStatus(
	roomID  varchar(50) not null,
    status  varchar(50) not null,
    primary key(roomID)
);

insert into RoomStatus values('RoomA', "active");
insert into RoomStatus values('RoomB', "ended");






