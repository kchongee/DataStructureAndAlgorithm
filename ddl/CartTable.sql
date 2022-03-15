-- drop table Cart;
create table Cart(
	cartID        int not null auto_increment,
	buyerID       varchar(50)   not null,
	sellerID      varchar(50)   not null,
	isCheckout    boolean     not null,
	commentDate   varchar(50),
    commentTime   time(0),
    paymentMethod varchar(50),
    primary key(cartID)
);