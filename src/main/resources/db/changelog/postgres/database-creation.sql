CREATE TABLE user
(
    id             CHAR(36)     NOT NULL PRIMARY KEY,
    username       VARCHAR(45)  NOT NULL UNIQUE,
    password       VARCHAR(128) NOT NULL,
    role           VARCHAR(50)  NOT NULL,
    phone_number   VARCHAR(25),
    balance        DOUBLE                DEFAULT 0.0,
    account_status BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE voucher
(
    id            CHAR(36)     NOT NULL PRIMARY KEY,
    title         VARCHAR(255) NOT NULL,
    description   TEXT,
    price         DOUBLE       NOT NULL,
    tour_type     VARCHAR(50)  NOT NULL,
    transfer_type VARCHAR(50)  NOT NULL,
    hotel_type    VARCHAR(50)  NOT NULL,
    status        VARCHAR(50)  NOT NULL,
    arrival_date  DATE         NOT NULL,
    eviction_date DATE         NOT NULL,
    user_id       CHAR(32),
    is_hot        BOOLEAN      NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

