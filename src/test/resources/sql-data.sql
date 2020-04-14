insert into ims.customers(first_name, surname) values('chris', 'perrins');
insert into ims.customers(first_name, surname) values('rhys', 'thompson');
insert into ims.customers(first_name, surname) values('nic', 'johnson');
insert into ims.customers(first_name, surname) values('jordon', 'harrison');

insert into ims.items(item_name, price) values('H is for Hawk', 4.99);
insert into ims.items(item_name, price) values('Angels and Demons', 7.99);
insert into ims.items(item_name, price) values('A Tale of Two Cities', 4.99);
insert into ims.items(item_name, price) values('Never Gonna: Rick Rolled', 4.99);

insert into ims.item_orders(order_id, item_id, qty) values(1, 4, 2);
insert into ims.item_orders(order_id, item_id, qty) values(1, 1);

insert into ims.orders(customer_id, total) values(1, 14.97);