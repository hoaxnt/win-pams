CREATE DATABASE `win-pams`;

USE `win-pams`;

CREATE TABLE users
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    first_name     VARCHAR(255) NOT NULL,
    last_name      VARCHAR(255) NOT NULL,
    contact_number VARCHAR(255) NOT NULL,
    email          VARCHAR(255) NOT NULL,
    username       VARCHAR(255) NOT NULL,
    password       VARCHAR(255) NOT NULL,
    is_admin       BOOLEAN   DEFAULT FALSE,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE pets
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    breed       VARCHAR(255),
    age         INT,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE pet_photos
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    pet_id     INT,
    photo_url  VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (pet_id) REFERENCES pets (id)
);

CREATE TABLE adoption_requests
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    user_id         INT,
    pet_id          INT,
    status          ENUM('pending', 'approved', 'rejected') DEFAULT 'pending',
    additional_info TEXT,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (pet_id) REFERENCES pets (id)
);

CREATE TABLE interviews
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    adoption_request_id INT       NOT NULL,
    interviewer_id      INT       NOT NULL,
    start               TIMESTAMP NOT NULL,
    end                 TIMESTAMP NOT NULL,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (interviewer_id) REFERENCES users (id),
    FOREIGN KEY (adoption_request_id) REFERENCES adoption_requests (id)
);