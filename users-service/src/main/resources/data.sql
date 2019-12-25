INSERT INTO ROLE (id, name) VALUES
(1, 'ADMIN'),
(2, 'USER')
ON CONFLICT DO NOTHING;

INSERT INTO USERS (id, first_name, last_name, email,role_id, password, is_enabled) VALUES
(0, 'Andrii', 'Malyshev', 'admin@gmail.com', 1, 'F/PZtTgA4k5q/9o6Jzik6w==', true)
ON CONFLICT DO NOTHING;
