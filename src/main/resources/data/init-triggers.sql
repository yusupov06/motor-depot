


create function change_request_status_to_approved_when_cruise_added() returns trigger
    language plpgsql
as
$$
BEGIN
update request SET status = 'APPROVED' where id = new.request_id;
return new;
END;
$$;

create trigger change_request_status_when_cruise_updated_trigger
    AFTER update ON cruise
    FOR EACH ROW
    EXECUTE FUNCTION change_request_status_when_cruise_updated();

create function change_request_status_when_cruise_updated() returns trigger
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

