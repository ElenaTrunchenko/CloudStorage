create table files
(
    id bigserial primary key,
    name varchar(300) unique,
    size bigint,
);