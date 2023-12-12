delete from user_role;
delete from users;

insert into users (id, username, password, active) values
    (1, '123', '$2a$08$rPB8VIJj9LjUky/.UeTHJubxKtRetAkiFHR7.nXZLDKIoaOnYBUjm', true),
    (2, '234', '$2a$08$VnyD64kJZeR586G3BZgWGO8zjDR4IzkD9sxSvmL5mZ.l1PIQAO816', true),
    (3, '345', '$2a$08$4EBua25wWmFXnWnfusC8Ue7XnhwmWTQ8XzfAZGYMxg.mpJVrcXB72', true);

insert into user_role (user_id, roles) values
    (1, 'USER'),
    (2, 'USER'),
    (3, 'USER');