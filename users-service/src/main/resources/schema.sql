CREATE TABLE
IF NOT EXISTS ROLE (
                id serial not null,
                name varchar(255),
                primary key (id)
                );

CREATE TABLE
IF NOT EXISTS USERS (
                id serial not null,
                email varchar(255),
                first_name varchar(255),
                is_enabled boolean,
                last_name varchar(255),
                password varchar(255),
                role_id int,
                token varchar(255),
                primary key (id)
                );

