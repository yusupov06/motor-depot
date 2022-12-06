create table if not exists role
(
    id          bigserial
    primary key,
    name        varchar,
    description varchar,
    added_at    timestamp default now()
    );

alter table role
    owner to postgres;

create table if not exists users
(
    id           bigserial
    primary key,
    firstname    varchar,
    lastname     varchar,
    phone_number varchar not null
    unique,
    password     varchar not null,
    role_id      bigint  not null
    references role,
    added_at     timestamp default now(),
    status       varchar
    );

alter table users
    owner to postgres;

create table if not exists request
(
    id       bigserial
    primary key,
    name     varchar not null,
    from_    varchar not null,
    to_      varchar not null,
    status   varchar,
    characteristics varchar,
    added_at timestamp default now(),
    added_by bigint
    references users
    );

alter table request
    owner to postgres;

create table if not exists car
(
    id         bigserial
    primary key,
    car_model  varchar not null,
    car_number varchar not null,
    condition  varchar,
    characteristics  varchar,
    added_at   timestamp default now(),
    added_by   bigint
    references users
    );

alter table car
    owner to postgres;

create table if not exists driver
(
    id       bigserial
    primary key,
    car_id   bigint not null
    unique
    references car,
    user_id  bigint not null
    unique
    references users,
    status   varchar,
    added_at timestamp default now(),
    added_by bigint
    references users
    );

alter table driver
    owner to postgres;

create table if not exists cruise
(
    id         bigserial
    primary key,
    driver_id  bigint not null
    references driver,
    request_id bigint not null
    unique
    references request,
    status     varchar,
    added_at   timestamp default now(),
    added_by   bigint
    constraint cruise_added_by_fk
    references users
    );

alter table cruise
    owner to postgres;

create table if not exists permission
(
    id      bigserial
    primary key,
    role_id bigint  not null,
    name    varchar not null
);

alter table permission
    owner to postgres;
