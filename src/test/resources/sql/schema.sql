create SCHEMA IF NOT EXISTS subscription;
drop table if exists subscription.account;

create table subscription.account (
    name varchar(255) not null
);

insert into subscription.account (name) values ('account1');

