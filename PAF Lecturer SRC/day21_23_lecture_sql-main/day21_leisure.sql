# create database leisure;

use leisure;

create table tv_shows (
	prog_id int not null,
    title char(64) not null,
    lang char(16) not null,
    official_site varchar(256),
    rating enum('G', 'PG', 'NC16', 'M18', 'R21') not null,
    user_rating float default '0.0',
    release_date date,
    image blob,
    constraint pk_prog_id primary key (prog_id),
    constraint chk_user_rating
		check(user_rating between 0.0 and 10.0)
);

create table tutorials(
	id int not null auto_increment,
    title varchar(100) not null,
    author varchar(80) not null,
    submission_date date,
    constraint pk_tutorial_id primary key (id)
);

/*
insert into tutorials (title, author, submission_date) values ('CS101', 'Matthew', '2023-02-01');
insert into tutorials (title, author, submission_date) values ('CS102', 'Chua', '2023-02-01');
insert into tutorials (title, author, submission_date) values ('CS103', 'Donald', '2023-02-02');
insert into tutorials (title, author, submission_date) values ('CS104', 'Denny', '2023-02-03');
insert into tutorials (title, author, submission_date) values ('CS105', 'Yen', '2023-02-04');
insert into tutorials (title, author, submission_date) values ('CS106', 'Leong', '2023-02-05');
insert into tutorials (title, author, submission_date) values ('CS107', 'Chuk', '2023-02-06');
insert into tutorials (title, author, submission_date) values ('CS108', 'Gabriel', '2023-02-01');
insert into tutorials (title, author, submission_date) values ('CS109', 'Matthew', '2023-02-01');
insert into tutorials (title, author, submission_date) values ('CS110', 'James', '2023-02-05');
insert into tutorials (title, author, submission_date) values ('CS111', 'Brandon', '2023-02-06');
insert into tutorials (title, author, submission_date) values ('CS112', 'Matthew', '2023-02-07');
insert into tutorials (title, author, submission_date) values ('CS113', 'Esther', '2023-02-08');
insert into tutorials (title, author, submission_date) values ('CS114', 'Grace', '2023-02-05');
insert into tutorials (title, author, submission_date) values ('CS115', 'Kelvin', '2023-02-06');
*/


