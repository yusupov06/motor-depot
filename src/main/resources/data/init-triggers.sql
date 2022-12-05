
create or replace function change_car_condition_to_active_when_driver_added() returns trigger
    language plpgsql
as
$$
BEGIN
update car SET condition = 'ACTIVE' where id = new.car_id;
return new;
END;
$$;

alter function change_car_condition_to_active_when_driver_added() owner to postgres;

create trigger change_car_condition_to_active_when_driver_added_trigger
    after insert
    on driver
    for each row
    execute procedure change_car_condition_to_active_when_driver_added();

create or replace function change_car_condition_to_active_when_driver_updated() returns trigger
    language plpgsql
as
$$
BEGIN
update car SET condition = 'ACTIVE' where id = new.car_id;
return new;
END;
$$;

alter function change_car_condition_to_active_when_driver_updated() owner to postgres;

create trigger change_car_condition_to_active_when_driver_updated_trigger
    after update
    on driver
    for each row
    execute procedure change_car_condition_to_active_when_driver_updated();

create or replace function change_car_condition_to_notactive_when_driver_deleted() returns trigger
    language plpgsql
as
$$
BEGIN
update car SET condition = 'NOT_ACTIVE' where id = old.car_id;
return new;
END;
$$;

alter function change_car_condition_to_notactive_when_driver_deleted() owner to postgres;

create trigger change_car_condition_to_notactive_when_driver_deleted_trigger
    after delete
    on driver
    for each row
    execute procedure change_car_condition_to_notactive_when_driver_deleted();

create or replace function change_car_condition_to_notactive_when_driver_updated() returns trigger
    language plpgsql
as
$$
BEGIN
update car SET condition = 'NOT_ACTIVE' where old.car_id <> new.car_id and id = old.car_id;
return new;
END;
$$;

alter function change_car_condition_to_notactive_when_driver_updated() owner to postgres;

create trigger change_car_condition_to_notactive_when_driver_updated_trigger
    after update
    on driver
    for each row
    execute procedure change_car_condition_to_notactive_when_driver_updated();

create or replace function change_driver_status_to_busy_when_cruise_added() returns trigger
    language plpgsql
as
$$
BEGIN
update driver SET status = 'BUSY' where id = new.driver_id;
return new;
END;
$$;

alter function change_driver_status_to_busy_when_cruise_added() owner to postgres;

create trigger change_driver_status_to_busy_when_cruise_added_trigger
    after insert
    on cruise
    for each row
    execute procedure change_driver_status_to_busy_when_cruise_added();

create or replace function change_driver_status_when_cruise_updated() returns trigger
    language plpgsql
as
$$
BEGIN
update driver SET status = 'BUSY' where id = new.driver_id;
update driver SET status = 'FREE' where id = old.driver_id;
return new;
END;
$$;

alter function change_driver_status_when_cruise_updated() owner to postgres;

create trigger change_driver_status_to_busy_when_cruise_updated_trigger
    after update
    on cruise
    for each row
    execute procedure change_driver_status_when_cruise_updated();

create or replace function change_request_status_to_approved_when_cruise_added() returns trigger
    language plpgsql
as
$$
BEGIN
update request SET status = 'APPROVED' where id = new.request_id;
return new;
END;
$$;

alter function change_request_status_to_approved_when_cruise_added() owner to postgres;

create trigger change_request_status_to_approved_when_cruise_added_trigger
    after insert
    on cruise
    for each row
    execute procedure change_request_status_to_approved_when_cruise_added();

create or replace function change_request_status_when_cruise_updated() returns trigger
    language plpgsql
as
$$
BEGIN
update request SET status = 'APPROVED' where id = new.request_id;
update request SET status = 'CREATED' where id = old.request_id;
return new;
END;
$$;

alter function change_request_status_when_cruise_updated() owner to postgres;

create trigger change_request_status_when_cruise_updated_trigger
    after update
    on cruise
    for each row
    execute procedure change_request_status_when_cruise_updated();

create or replace function change_driver_status_to_free_when_cruise_finished() returns trigger
    language plpgsql
as
$$
BEGIN
update driver SET status = 'FREE' where id = new.driver_id and new.status = 'FINISHED';
update request SET status = 'COMPLETED' where id = new.request_id and new.status = 'FINISHED';
return new;
END;
$$;

alter function change_driver_status_to_free_when_cruise_finished() owner to postgres;

create trigger change_driver_status_to_free_when_cruise_finished_trigger
    after update
    on cruise
    for each row
    execute procedure change_driver_status_to_free_when_cruise_finished();

