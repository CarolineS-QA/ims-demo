create database if not exists ims;
drop table if exists ims.customers;
drop table if exists ims.items;
drop table if exists ims.item_orders;
drop table if exists ims.orders;
create table if not exists ims.customers(customer_id int primary key auto_increment, first_name varchar(40), surname varchar(40));
create table if not exists ims.items(item_id int primary key auto_increment, item_name varchar(50), price decimal(7,2));
create table if not exists ims.item_orders(order_id int, foreign key (order_id) references orders(order_id), item_id int, foreign key (item_id) references items(item_id), qty int not null default 1);
create table if not exists ims.orders(order_id int primary key auto_increment, customer_id int, foreign key(customer_id) references customers(customer_id),  total decimal(7,2));

