create table j_role (
                        id serial primary key,
                        name varchar(2000)
);

create table j_user (
                        id serial primary key,
                        name varchar(2000),
                        role_id int not null references j_role(id)
);

create table car (
                        id serial primary key,
                        name varchar(2000),
                        car_company_id int not null references car_company(id)
);

create table car_company (
                        id serial primary key,
                        name varchar(2000)
);

create table books (
                     id serial primary key,
                     name varchar(2000) not null
);

create table authors (
                             id serial primary key,
                             name varchar(2000) not null
);

create table authors_books (
                            author_id int not null references authors(id),
                            books_id int not null references books(id)
);

create table car_models (
                     id serial primary key,
                     name varchar(2000),
                     car_mark_id int not null references car_marks(id)
);

create table car_marks (
                             id serial primary key,
                             name varchar(2000)
);

create table engine (
    id serial primary key
);

create table car (
                     id serial primary key,
                     engine_id int not null unique references engine(id)
);

create table driver (
    id serial primary key
);

create table history_owner (
                               id serial primary key,
                               driver_id int not null references driver(id),
                               car_id int not null references car(id)
);

create table candidates (
                            id         serial primary key,
                            name       text,
                            experience int,
                            salary     int
);