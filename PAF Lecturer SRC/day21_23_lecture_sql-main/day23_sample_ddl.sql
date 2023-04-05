use leisure;

create table country(
	id int not null auto_increment,
	name varchar(50) not null,
	constraint country_pk primary key (id)
);

create table city(
	id smallint(5) not null,
    city_name varchar(50) not null,
    country_id int not null,
    last_updated timestamp,
    constraint city_pk primary key (id),
    constraint city_country_fk foreign key (country_id) references country(id)
);

create table address(
	id smallint(5) not null,
    address varchar(50),
    address2 varchar(50),
    district varchar(20),
    city_id smallint(5),
    postal_code varchar(10),
    phone varchar(20),
    location geometry,
    last_updated timestamp,
    constraint address_pk primary key(id),
    constraint address_city_fk foreign key(city_id) references city(id)
);

create table staff(
	id tinyint(3) not null,
    name varchar(100),
    constraint staff_pk primary key(id)
);

create table store(
	id tinyint(3) not null,
	manager_staff_id tinyint(3) not null,
    address_id smallint(5) not null,
    last_updated timestamp,
    constraint store_pk primary key(id),
    constraint store_address_fk foreign key(address_id) references address(id),
    constraint store_staff_fk foreign key(manager_staff_id) references staff(id)
);







