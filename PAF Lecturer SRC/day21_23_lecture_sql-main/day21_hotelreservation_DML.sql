use hotelreservation;

/*
insert into customer (first_name, last_name) values ('James', 'Bond');
insert into customer (first_name, last_name) values ('Swee Kiat', 'Ng');
insert into customer (first_name, last_name) values ('Lawrence', 'Wong');
insert into customer (first_name, last_name) values ('Chan Meng', 'Khoong');
insert into customer (first_name, last_name) values ('Chan Kiat', 'Wong');
insert into customer (first_name, last_name) values ('Desmond', 'Koh');
insert into customer (first_name, last_name) values ('Pei Ling', 'Ting');
insert into customer (first_name, last_name) values ('Pritam', 'Singh');
insert into customer (first_name, last_name) values ('Edwin', 'Tong');

insert into room (room_type, price) values ('standard', 100);
insert into room (room_type, price) values ('deluxe', 250);
insert into room (room_type, price) values ('president', 350);
insert into room (room_type, price) values ('suite', 400);
*/

create table customer2 (
	id int not null auto_increment,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    primary key (id)
);

insert into customer2 (first_name, last_name) values ('James', 'Bond');
insert into customer2 (first_name, last_name) values ('Swee Kiat', 'Ng');
insert into customer2 (first_name, last_name) values ('Lawrence', 'Wong');
insert into customer2 (first_name, last_name) values ('Chan Meng', 'Khoong');
insert into customer2 (first_name, last_name) values ('Chan Kiat', 'Wong');
insert into customer2 (first_name, last_name) values ('Desmond', 'Koh');
insert into customer2 (first_name, last_name) values ('Pei Ling', 'Ting');
insert into customer2 (first_name, last_name) values ('Pritam', 'Singh');
insert into customer2 (first_name, last_name) values ('Edwin', 'Tong');

select * from customer2;

update customer2 
set first_name = 'Hsien Loong',
last_name = 'Lee' 
where id = 9;

delete from customer2 where id = 6;
delete from customer2;


select * from customer;

select * from room;

/*
insert into reservation (customer_id, room_id, start_date, end_date, total_cost) 
values (1, 1, '2023-02-01', '2023-02-02', 100);
insert into reservation (customer_id, room_id, start_date, end_date, total_cost) 
values (2, 2, '2023-02-01', '2023-02-03', 200);
insert into reservation (customer_id, room_id, start_date, end_date, total_cost) 
values (3, 3, '2023-02-04', '2023-02-06', 300);
insert into reservation (customer_id, room_id, start_date, end_date, total_cost) 
values (4, 4, '2023-02-05', '2023-02-08', 400);
insert into reservation (customer_id, room_id, start_date, end_date, total_cost) 
values (5, 1, '2023-02-05', '2023-02-08', 100);
insert into reservation (customer_id, room_id, start_date, end_date, total_cost) 
values (1, 2, '2023-02-06', '2023-02-08', 200);
insert into reservation (customer_id, room_id, start_date, end_date, total_cost) 
values (2, 3, '2023-02-07', '2023-02-09', 400);
insert into reservation (customer_id, room_id, start_date, end_date, total_cost) 
values (3, 4, '2023-02-07', '2023-02-09', 800);
insert into reservation (customer_id, room_id, start_date, end_date, total_cost) 
values (4, 1, '2023-02-16', '2023-02-20', 500);
insert into reservation (customer_id, room_id, start_date, end_date, total_cost) 
values (5, 2, '2023-02-16', '2023-02-20', 800);
*/
/*
insert into reservation (customer_id, room_id, start_date, end_date, total_cost) 
values (1, 1, '2023-03-01', '2023-03-02', 100);
insert into reservation (customer_id, room_id, start_date, end_date, total_cost) 
values (2, 2, '2023-03-01', '2023-03-03', 200);
insert into reservation (customer_id, room_id, start_date, end_date, total_cost) 
values (3, 3, '2023-03-04', '2023-03-06', 300);
insert into reservation (customer_id, room_id, start_date, end_date, total_cost) 
values (4, 4, '2023-03-05', '2023-03-08', 400);
insert into reservation (customer_id, room_id, start_date, end_date, total_cost) 
values (5, 1, '2023-03-05', '2023-03-08', 100);
insert into reservation (customer_id, room_id, start_date, end_date, total_cost) 
values (1, 2, '2023-03-06', '2023-03-08', 200);
insert into reservation (customer_id, room_id, start_date, end_date, total_cost) 
values (2, 3, '2023-03-07', '2023-03-09', 400);
insert into reservation (customer_id, room_id, start_date, end_date, total_cost) 
values (3, 4, '2023-03-07', '2023-03-09', 800);
insert into reservation (customer_id, room_id, start_date, end_date, total_cost) 
values (4, 1, '2023-03-16', '2023-03-20', 500);
insert into reservation (customer_id, room_id, start_date, end_date, total_cost) 
values (5, 2, '2023-03-16', '2023-03-20', 800);
*/
/*

*/
select * from room;

select * from reservation;

select resv.id resv_id, cust.id cust_id, cust.first_name, cust.last_name, 
resv.start_date, resv.end_date, resv.total_cost, room.price, room.room_type 
from customer cust
inner join reservation resv
on cust.id = resv.customer_id
inner join room 
on resv.room_id = room.id
where first_name like '%Swee Kiat%';

select distinct cust.first_name, cust.last_name
from customer cust
inner join reservation resv
on cust.id = resv.customer_id
inner join room 
on resv.room_id = room.id;


select start_date, avg(total_cost) from reservation
where start_date = '2023-02-01';


select *, length(first_name) from customer;

select * from reservation
where start_date between '2023-02-01' and '2023-02-28';

select 'Feb 2023' as Feb, room_id as Feb_Room_id, count(*) as Feb_cnt, 
'' as Mar, null as Mar_Room_id, null as Mar_cnt
from reservation
where start_date between '2023-02-01' and '2023-02-28'
group by room_id
union
select '' as Feb, null as Feb_Room_id, null as Feb_cnt,
'Mar 2023' as Mar, room_id as Mar_Room_id, count(*) as Mar_cnt 
from reservation
where start_date between '2023-03-01' and '2023-03-31'
group by room_id;

select * from customer
order by first_name desc, last_name asc;

insert into employee(first_name, last_name, salary) values ('Desmond', 'Koh', 5000);
insert into employee(first_name, last_name, salary) values ('Eddie', 'Lim', 6000);
insert into employee(first_name, last_name, salary) values ('James', 'Pang', 7000);

select * from employee;

insert into dependant(employee_id, fullname, relationship, birth_date) values (1, 'Koh Jing Xing', 'father', '2015-01-01');
insert into dependant(employee_id, fullname, relationship, birth_date) values (1, 'Koh Jing Jing', 'father', '2016-01-01');
insert into dependant(employee_id, fullname, relationship, birth_date) values (2, 'Lim Esther', 'father', '2015-03-15');
insert into dependant(employee_id, fullname, relationship, birth_date) values (3, 'Pang Cheng Hou', 'father', '2016-06-10');

select * from dependant;


select e.id emp_id, e.first_name, e.last_name, e.salary,
d.id dep_id, d.fullname, d.relationship, d.birth_date 
from employee e
inner join dependant d
on e.id = d.employee_id;

/*
create table rsvp (
	id int not null auto_increment,
    full_name varchar(150) not null,
    email varchar(15) not null,
    phone numeric(8),
    confirmation_date date,
    comments varchar(255),
    constraint pk_rsvp_id primary key (id)
);
*/

select * from rsvp;

select * from rsvp where full_name like '%fred%';

insert into rsvp (full_name, email, phone, confirmation_date, comments) 
values ('Fred Perry', 'fred@gmail.com', 91179999, '2023-02-01', 'fred rsvp to B event');

update rsvp 
set full_name = 'fred', email = 'fred@g.com', phone = 99999999, 
confirmation_date = '2023-02-01', comments = 'fred rsvp - event B'
where id = 1;

select count(*) as cnt from rsvp;


