alter table if exists members drop constraint if exists MEM_PRJ_FK;
alter table if exists members drop constraint if exists MEM_USR_FK;
alter table if exists messages drop constraint if exists CHT_FK;
alter table if exists messages drop constraint if exists USR_CHT_FK;
alter table if exists projects drop constraint if exists USR_CHT_FK;
alter table if exists tasks drop constraint if exists PRJ_TSK_FK;
alter table if exists user_role drop constraint if exists FKj345gk1bovqvfame88rcx7yyx;

drop table if exists members cascade;
drop table if exists messages cascade;
drop table if exists projects cascade;
drop table if exists tasks cascade;
drop table if exists user_role cascade;
drop table if exists users cascade;

drop sequence if exists messages_seq;
drop sequence if exists projects_seq;
drop sequence if exists tasks_seq;
drop sequence if exists users_seq;

create sequence messages_seq start with 1 increment by 50;
create sequence projects_seq start with 1 increment by 50;
create sequence tasks_seq start with 1 increment by 50;
create sequence users_seq start with 1 increment by 50;

create table members (project_id bigint not null, user_id bigint not null, primary key (project_id, user_id));
create table messages (chat_id bigint not null, creation_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null, id bigint not null, user_id bigint not null, text TEXT not null, primary key (id));
create table projects (creation_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null, id bigint not null, owner_id bigint not null, name TEXT not null, primary key (id));
create table tasks (creation_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null, id bigint not null, project_id bigint not null, text TEXT not null, primary key (id));
create table user_role (user_id bigint not null, roles varchar(255) check (roles in ('USER')));
create table users (active boolean, id bigint not null, password TEXT, username TEXT unique, primary key (id));

alter table if exists members add constraint MEM_PRJ_FK foreign key (project_id) references projects;
alter table if exists members add constraint MEM_USR_FK foreign key (user_id) references users;
alter table if exists messages add constraint CHT_FK foreign key (chat_id) references projects;
alter table if exists messages add constraint USR_CHT_FK foreign key (user_id) references users;
alter table if exists projects add constraint USR_CHT_FK foreign key (owner_id) references users;
alter table if exists tasks add constraint PRJ_TSK_FK foreign key (project_id) references projects;
alter table if exists user_role add constraint FKj345gk1bovqvfame88rcx7yyx foreign key (user_id) references users;