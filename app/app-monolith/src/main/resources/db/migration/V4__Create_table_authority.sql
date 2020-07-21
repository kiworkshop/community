create table authority (
    id   bigint not null auto_increment,
    role varchar(20),
    primary key (id)
);

insert into authority (role) values ('ROLE_USER'), ('ROLE_ADMIN');