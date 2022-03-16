drop table if exists room;
create table room(
	roomID     varchar(50) not null,
    roomTitle  long        not null,
    isOpen     boolean     not null,
    primary key(roomID)
)