create table users
(
    password varchar(255) not null,
    login varchar(100) not null,
    foreign key (password, login)
);