create database if not exists moocs;

use moocs;

drop table if exists courses;

create table courses(
    id int not null primary key auto_increment,
    title text,
    institution varchar(255),
    uri varchar(255),
    free boolean,
    platform varchar(9)
)