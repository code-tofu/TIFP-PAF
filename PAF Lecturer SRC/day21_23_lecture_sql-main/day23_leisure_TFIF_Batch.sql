use leisure;

select * from employee;

select * from dependant;

insert into employee (first_name, last_name, salary) values ('darren', 'low', 9000.00);
insert into employee (first_name, last_name, salary) values ('daniel', 'lim', 12000.00);
insert into employee (first_name, last_name, salary) values ('derrick', 'tan', 15000.00);
insert into employee (first_name, last_name, salary) values ('dennis', 'lim', 7500.00);

insert into dependant (employee_id, full_name, relationship, birth_date) values (8, 'sky low', 'daughter', '2000-08-05');
insert into dependant (employee_id, full_name, relationship, birth_date) values (8, 'gabriel low', 'son', '1998-08-08');
insert into dependant (employee_id, full_name, relationship, birth_date) values (9, 'aeriel low', 'daughter', '2005-05-05');
insert into dependant (employee_id, full_name, relationship, birth_date) values (10, 'max tan', 'son', '2010-01-01');
insert into dependant (employee_id, full_name, relationship, birth_date) values (11, 'zavier lim', 'son', '1995-07-07');
insert into dependant (employee_id, full_name, relationship, birth_date) values (11, 'james lim', 'son', '1995-07-07');
