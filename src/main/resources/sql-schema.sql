create database if not exists ims;
create table if not exists ims.customers(customer_id int primary key auto_increment, first_name varchar(40), surname varchar(40));
create table if not exists ims.items(item_id int primary key auto_increment, item_name varchar(50), price decimal(7,2));
create table if not exists ims.item_orders(item_ordercode int not null, item_id int, foreign key (item_id) references items(item_id), qty int);
create table if not exists ims.orders(order_id int primary key auto_increment, customer_id int, foreign key(customer_id) references customers(customer_id), item_ordercode int, foreign key (item_ordercode) references item_orders(item_ordercode), total decimal(7,2));