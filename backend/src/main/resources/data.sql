-- Insert roles
insert into roles (active,description, role_name) values (true,'administrator roles' , 'ROLE_ADMIN');
insert into roles (active,description, role_name) values (true,'user roles' , 'ROLE_USER');
insert into roles (active,description, role_name) values (true,'planner roles' , 'ROLE_PLANNER');

-- Insert users
insert into users (password, user_name,are_credentials_expired, is_enabled,is_expired,is_locked ) values ('$2a$10$bJxwWc3A3DBzke7Gnb/MZ.lLXmvOIE/DFAd6QUnBvWhn7c7D1zY4C','freddy', false,true,false,false);
insert into users (password, user_name,are_credentials_expired, is_enabled,is_expired,is_locked) values ('$2a$10$bJxwWc3A3DBzke7Gnb/MZ.lLXmvOIE/DFAd6QUnBvWhn7c7D1zY4C','freeky', false,true,false,false);

-- Assign roles to users
insert into user_role (role_id, user_id) values (1,1);
insert into user_role (role_id, user_id) values (2,2);
insert into user_role (role_id, user_id) values (2,1);

-- Insert sports
insert into sport (name, sport_type) values ('Dancehall', 'SPORTS');
insert into sport (name, sport_type) values ('Spinning', 'SPORTS');
insert into sport (name, sport_type) values ('Fitness', 'GYM');
insert into sport (name, sport_type) values ('Omnia', 'GYM');
--
-- -- Insert locations
insert into location (name, capacity) values ('Gym', 100);
insert into location (name, capacity) values ('Gym omnia', 8);
insert into location (name, capacity) values ('Spinning Hall', 25);
insert into location (name, capacity) values ('Sports hall', 40);
--
-- -- Insert sportevents
insert into event (name, description, location_id, start_date, end_date, start_time, end_time, sport_id, target_audience, available_places)
values ('Morning Yoga', 'A relaxing morning yoga session', (select id from location where name = 'Sports hall'), '2023-11-01', '2023-11-01', '08:00:00', '09:00:00', (select id from sport where name = 'Dancehall'), 'STUDENT', 20);
insert into event (name, description, location_id, start_date, end_date, start_time, end_time, sport_id, target_audience, available_places)
values ('Evening Spin', 'High-intensity spinning class', (select id from location where name = 'Spinning Hall'), '2023-11-01', '2023-11-01', '18:00:00', '19:00:00', (select id from sport where name = 'Spinning'), 'ASSOCIATION', 25);