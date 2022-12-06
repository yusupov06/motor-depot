
insert into permission(role_id, name) values (1,'SIGN_IN');
insert into permission(role_id, name) values (1,'LOG_OUT');
insert into permission(role_id, name) values (1,'ADD_REQUEST');
insert into permission(role_id, name) values (1,'EDIT_REQUEST');
insert into permission(role_id, name) values (1,'DELETE_REQUEST');
insert into permission(role_id, name) values (1,'SHOW_REQUESTS');
insert into permission(role_id, name) values (1,'ADD_CRUISE');
insert into permission(role_id, name) values (1,'EDIT_CRUISE');
insert into permission(role_id, name) values (1,'DELETE_CRUISE');
insert into permission(role_id, name) values (1,'SHOW_CRUISES');
insert into permission(role_id, name) values (1,'EDIT_CRUISE_STATUS');
insert into permission(role_id, name) values (1,'ADD_DRIVER');
insert into permission(role_id, name) values (1,'DELETE_DRIVER');
insert into permission(role_id, name) values (1,'EDIT_DRIVER');
insert into permission(role_id, name) values (1,'SHOW_DRIVERS');
insert into permission(role_id, name) values (1,'ADD_CAR');
insert into permission(role_id, name) values (1,'DELETE_CAR');
insert into permission(role_id, name) values (1,'EDIT_CAR');
insert into permission(role_id, name) values (1,'SHOW_CARS');
insert into permission(role_id, name) values (1,'ADD_ROLE');
insert into permission(role_id, name) values (1,'EDIT_ROLE');
insert into permission(role_id, name) values (1,'DELETE_ROLE');
insert into permission(role_id, name) values (1,'SHOW_ROLES');
insert into permission(role_id, name) VALUES (2,'LOG_OUT');
insert into permission(role_id, name) VALUES (3,'LOG_OUT');
insert into permission(role_id, name) VALUES (2,'SIGN_IN');
insert into permission(role_id, name) VALUES (3,'SIGN_IN');
insert into permission(role_id, name) VALUES (2,'ADD_REQUEST');
insert into permission(role_id, name) VALUES (2,'EDIT_REQUEST');
insert into permission(role_id, name) VALUES (2,'DELETE_REQUEST');
insert into permission(role_id, name) VALUES (2,'SHOW_REQUESTS');
insert into permission(role_id, name) VALUES (3,'EDIT_CRUISE_STATUS');
insert into permission(role_id, name) VALUES (3,'SHOW_CRUISES');


insert into role (name, description)
values ('MANAGER', 'head of center');
insert into role (name, description)
values ('DISPATCHER', 'Responsible for Requests');
insert into role (name, description)
values ('DRIVER', 'DRIVER');

insert into users (id, firstname, lastname, phone_number, password, role_id, status)
values (1, 'Muhammadqodir', 'Yusupov', '+998931668648', '40bd001563085fc35165329ea1ff5c5ecbdbbeef', 1, 'ACTIVE');


insert into users (id, firstname, lastname, phone_number, password, role_id, status)
values (2, 'Alisher', 'Yusupov', '+998931001010', '40bd001563085fc35165329ea1ff5c5ecbdbbeef', 2, 'ACTIVE');

insert into users (id, firstname, lastname, phone_number, password, role_id, status)
values (3, 'Bobur', 'Yusupov', '+998931002020', '40bd001563085fc35165329ea1ff5c5ecbdbbeef', 3, 'ACTIVE');

insert into request (name, from_, to_, status, added_by) values ('Andijan-Tashkent',	'Andijan',	'Tashkent',	'CREATED',1);

insert into car (car_model, car_number, condition, characteristics, added_by)
values ('Audi',	'60A111AA',	'ACTIVE','fast',1);

insert into car (car_model, car_number, condition, characteristics, added_by)
values ('BMW',	'60A222AA',	'ACTIVE','fast, small',1);

insert into driver (id, car_id, user_id, status, added_by)
values (1, 1,3,	'FREE',1);

insert into cruise(driver_id, request_id, status, added_by)
VALUES (1,	1,	'CREATED',1);