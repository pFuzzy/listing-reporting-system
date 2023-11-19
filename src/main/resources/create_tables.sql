# CREATE DATABASE listing_reporting_system;
CREATE TABLE listing(
                        id varchar(36) PRIMARY KEY NOT NULL ,
                        title text NOT NULL,
                        description text NOT NULL,
                        location_id varchar(36) NOT NULL,
                        listing_price DECIMAL(10,2),
                        currency varchar(3) NOT NULL,
                        quantity int NOT NULL,
                        listing_status bigint NOT NULL,
                        marketplace bigint NOT NULL,
                        upload_time date,
                        owner_email_address varchar(255)
);

CREATE TABLE location(
                         id varchar(36) PRIMARY KEY NOT NULL ,
                         manager_name varchar(255),
                         phone_number varchar(255),
                         primary_address varchar(255),
                         secondary_address varchar(255),
                         country varchar(255),
                         town varchar(255),
                         postal_code varchar(255)
);

CREATE TABLE listing_status(
                               id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
                               status_name varchar(255)
);

CREATE TABLE marketplace(
                            id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
                            marketplace_name varchar(255)
);

ALTER TABLE listing
    ADD FOREIGN KEY (location_id) REFERENCES location(id);

ALTER TABLE listing
    ADD FOREIGN KEY (listing_status) REFERENCES listing_status(id);

ALTER TABLE listing
    ADD FOREIGN KEY (marketplace) REFERENCES marketplace(id);