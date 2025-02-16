CREATE TABLE jwt_blacklist (
                               token VARCHAR(512) PRIMARY KEY ,
                               expiration_time TIMESTAMP NOT NULL
);