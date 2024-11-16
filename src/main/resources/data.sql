-- Insert roles
insert into roles (active,description, role_name) values (true,'administrator roles' , 'ROLE_ADMIN');
insert into roles (active,description, role_name) values (true,'user roles' , 'ROLE_USER');
insert into roles (active,description, role_name) values (true,'planner roles' , 'ROLE_PLANNER');

-- Insert users not possible due to password encoding


-- Assign roles to users




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
insert into event (location_id, start_date, end_date, start_time, end_time, sport_id, available_places)
values ((select id from location where name = 'Sports hall'), '2024-11-15', '2024-11-15', '08:00:00', '09:00:00', (select id from sport where name = 'Dancehall'), 40);
insert into event (location_id, start_date, end_date, start_time, end_time, sport_id, available_places)
values ((select id from location where name = 'Spinning Hall'), '2024-11-15', '2024-11-15', '18:00:00', '19:00:00', (select id from sport where name = 'Spinning'), 25);
insert into event (location_id, start_date, end_date, start_time, end_time, sport_id, available_places)
values ((select id from location where name = 'Gym'), '2024-11-16', '2024-11-16', '18:00:00', '19:00:00', (select id from sport where name = 'Fitness'), 100);