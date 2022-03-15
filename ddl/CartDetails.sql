create table CartDetails(
	cartID      int not null,
    productID   varchar(50) not null,
    productQty  int not null,
    primary key(cartID, productID)
);