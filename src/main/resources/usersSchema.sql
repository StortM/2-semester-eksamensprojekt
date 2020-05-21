DROP DATABASE IF EXISTS userSchema;
CREATE DATABASE userSchema;

USE userSchema;

CREATE TABLE users(
	username varchar(50) not null primary key,
    password varchar(50) not null,
    enabled boolean not null
);

CREATE TABLE authorities(
	username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username,authority);