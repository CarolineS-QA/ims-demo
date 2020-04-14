create database if not exists ims;
create table if not exists ims.customers(customer_id int primary key auto_increment, first_name varchar(40) not null, surname varchar(40) not null);
create table if not exists ims.items(item_id int primary key auto_increment, item_name varchar(40), price double(7,2), stock int not null default 0);
create table if not exists ims.orders(order_id int primary key auto_increment, customer_id int, foreign key(customer_id) references customers(customer_id), total double(7,2));
create table if not exists ims.item_orders(order_id int, foreign key (order_id) references orders(order_id), item_id int, foreign key (item_id) references items(item_id), qty int not null default 1);