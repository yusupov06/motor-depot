
-- Role table
create table role
(
    id          bigserial primary key,
    name        varchar,
    description varchar,
    added_at    timestamp default now()
);

-- User table
create table users
(
    id           bigserial
        primary key,
    firstname    varchar,
    lastname     varchar,
    phone_number varchar not null unique ,
    password     varchar not null,
    status       varchar,
    role_id      bigint  not null references role(id),
    added_at     timestamp default now()
);

-- Request table
create table request
(
    id         bigserial
        primary key,
    name       varchar not null ,
    from_      varchar,
    to_        varchar,
    status     varchar,
    added_at   timestamp default now(),
    added_by   bigint
        references users,
    start_time timestamp
);

-- Car table
create table car
(
    id         bigserial
        primary key,
    car_model  varchar,
    car_number varchar not null ,
    condition  varchar,
    added_at   timestamp default now(),
    added_by   bigint
        references users
);


-- Driver table
create table driver
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

-- Cruise table
create table cruise
(
    id         bigserial
        primary key,
    driver_id  bigint not null references driver,
    request_id bigint not null unique references request,
    status     varchar,
    added_at   timestamp default now(),
    added_by   bigint
        constraint cruise_added_by_fk
            references users
);

-- Permission table
create table permission
(
    id      bigserial
        primary key,
    role_id bigint  not null,
    name    varchar not null
);

-- When driver deleted changing car condition to NOT_ACTIVE trigger
create function change_car_condition_when_driver_deleted() returns trigger
    language plpgsql
as
$$
BEGIN
    update car SET condition = 'NOT_ACTIVE' where id = old.car_id;
    return new;
END;
$$;

create trigger my_trigger
    after delete
    on driver
    for each row
    execute function change_car_condition_when_driver_deleted();
-- ***************************************************************


-- When driver added change car condition to ACTIVE trigger
create function change_car_condition_when_driver_added() returns trigger
    language plpgsql
as
$$
BEGIN
    update car SET condition = 'ACTIVE' where id = new.car_id;
    return new;
END;
$$;

create trigger when_driver_added_change_car_status
    after insert
    on driver
    for each row
    execute procedure change_car_condition_when_driver_added();
-- **********************************************************************

-- When Cruise added change request status to APPROVED trigger

create function change_request_status_when_cruise_added() returns trigger
    language plpgsql
as
$$
BEGIN
    update request SET status = 'APPROVED' where id = new.request_id;
    return new;
END;
$$;

create trigger change_req_status_when_cruise_added
    after insert
    on cruise
    for each row
    execute procedure change_request_status_when_cruise_added();

-- ************************************************************




