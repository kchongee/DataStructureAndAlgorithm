drop table if exists Cart;
create table Cart(
	cartID        int not null auto_increment,
	buyerID       varchar(50)   not null,
	sellerID      varchar(50)   not null,
	isCheckout    boolean     not null,
	checkoutDate   varchar(50),
    checkoutTime   time(0),
    paymentMethod varchar(50),
    primary key(cartID)
);

INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A01','A50',false,NULL,NULL,NULL);
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A01','A17',false,NULL,NULL,NULL);
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A01','A19',false,NULL,NULL,NULL);
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A755','A50',false,'17/6/2021','21:37',NULL);
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A347','A50',false,'13/7/2021','15:44',NULL);
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A25','A50',false,'6/8/2021','15:45',NULL);
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A58','A50',false,'17/8/2021','14:11',NULL);
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A178','A50',false,'13/8/2021','16:57',NULL);
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A894','A50',false,'10/7/2021','19:53',NULL);
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A570','A50',false,'28/11/2021','9:26',NULL);
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A723','A50',false,'11/10/2021','17:04',NULL);
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A882','A50',false,'6/9/2021','2:32',NULL);
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A680','A50',false,'19/9/2021','16:47',NULL);
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A211','A50',false,'30/12/2021','21:02',NULL);
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A224','A50',false,'2/1/2022','3:14',NULL);
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A572','A50',false,'20/2/2022','11:49',NULL);
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A27','A50',false,'17/5/2021','7:07',NULL);
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A67','A50',false,'23/7/2021','5:26',NULL);
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A112','A01',true,'25/4/2021','4:16','e-wallet');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A792','A08',true,'10/8/2021','14:58','debit card');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A164','A10',true,'10/3/2022','20:02','e-wallet');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A189','A11',true,'27/7/2021','19:13','credit card');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A07','A13',true,'24/5/2021','23:25','debit card');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A231','A15',true,'4/3/2022','3:20','debit card');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A915','A17',true,'1/3/2022','16:07','debit card');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A843','A19',true,'19/4/2021','10:00','credit card');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A409','A20',true,'30/4/2021','3:52','credit card');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A320','A21',true,'24/11/2021','16:53','debit card');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A943','A22',true,'19/2/2022','13:08','e-wallet');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A755','A26',true,'28/2/2022','12:40','e-wallet');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A549','A29',true,'17/5/2021','19:23','credit card');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A883','A30',true,'9/2/2022','22:24','e-wallet');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A400','A31',true,'22/8/2021','11:17','credit card');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A470','A32',true,'11/7/2021','14:25','credit card');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A498','A34',true,'28/3/2021','15:17','debit card');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A393','A36',true,'12/4/2021','23:34','debit card');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A514','A37',true,'22/8/2021','0:13','debit card');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A790','A38',true,'25/9/2021','18:36','e-wallet');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A318','A40',true,'21/12/2021','22:47','debit card');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A177','A41',true,'7/4/2021','12:55','e-wallet');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A751','A43',true,'25/10/2021','12:22','e-wallet');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A400','A44',true,'17/2/2022','18:52','e-wallet');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A366','A48',true,'28/4/2021','22:32','e-wallet');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A1011','A49',true,'22/3/2021','1:14','e-wallet');
INSERT INTO Cart(buyerID,sellerID,isCheckout,checkoutDate,checkoutTime,paymentMethod) VALUES ('A453','A50',true,'1/7/2021','6:35','credit card');
