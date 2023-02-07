DROP DATABASE IF EXISTS `paymybuddy`;
CREATE DATABASE paymybuddy;
USE paymybuddy;

-- TABLE CREATION
-- user
DROP TABLE IF EXISTS `user`;

CREATE TABLE user (
`id` INTEGER NOT NULL AUTO_INCREMENT,
`email` VARCHAR(100) NOT NULL UNIQUE,
`password` VARCHAR(100) NOT NULL,
`firstname` VARCHAR(100) NOT NULL,
`lastname` VARCHAR(100) NOT NULL,
`amount` INTEGER,
PRIMARY KEY (`id`)
);

-- bank_account
DROP TABLE IF EXISTS `bank_account`;

CREATE TABLE bank_account (
`id` INTEGER NOT NULL AUTO_INCREMENT,
`user_id` INTEGER NOT NULL,
`name` VARCHAR(100) NOT NULL,
`amount` INTEGER,
PRIMARY KEY (`id`),
FOREIGN KEY (`user_id`) REFERENCES user(`id`)
);

-- connection
DROP TABLE IF EXISTS `connection`;

CREATE TABLE connection (
`id` INTEGER NOT NULL AUTO_INCREMENT,
`user_id` INTEGER NOT NULL,
`friend_id` INTEGER NOT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`user_id`) REFERENCES user(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (`friend_id`) REFERENCES user(`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

-- in_app_transaction
DROP TABLE IF EXISTS `in_app_transaction`;

CREATE TABLE in_app_transaction (
`id` INTEGER NOT NULL AUTO_INCREMENT,
`sender_id` INTEGER NOT NULL,
`receiver_id` INTEGER NOT NULL,
`amount` INTEGER NOT NULL,
`comment` VARCHAR(250),
`fee` FLOAT NOT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`sender_id`) REFERENCES user(`id`),
FOREIGN KEY (`receiver_id`) REFERENCES user(`id`)
);

-- bank_transaction
DROP TABLE IF EXISTS `bank_transaction`;

CREATE TABLE bank_transaction (
`id` INTEGER NOT NULL AUTO_INCREMENT,
`user_id` INTEGER NOT NULL,
`bank_id` INTEGER NOT NULL,
`amount` INTEGER NOT NULL,
`comment` VARCHAR(250),
`fee` FLOAT NOT NULL,
PRIMARY KEY (`id`),
FOREIGN KEY (`user_id`) REFERENCES user(`id`),
FOREIGN KEY (`bank_id`) REFERENCES bank_account(`id`)
);



-- DATA INSERTION
-- password -> 123
INSERT INTO `user` (`id`, `email`, `password`, `firstname`, `lastname`, `amount`) VALUES
(1, 'mtampion@mail.fr', '$2a$12$GKclbJOO27.E9nFjSE6lJuARu2OFK.0rOYyBABl16t99iWSBzxSPO', 'Mocktar', 'Tampion', 0),
(2, 'bafritte@mail.fr', '$2a$12$GKclbJOO27.E9nFjSE6lJuARu2OFK.0rOYyBABl16t99iWSBzxSPO', 'Barak', 'Afritte', 0),
(3, 'dzou@mail.fr', '$2a$12$GKclbJOO27.E9nFjSE6lJuARu2OFK.0rOYyBABl16t99iWSBzxSPO', 'Debby', 'Zou', 0),
(4, 'jtamar@mail.fr', '$2a$12$GKclbJOO27.E9nFjSE6lJuARu2OFK.0rOYyBABl16t99iWSBzxSPO', 'Justin', 'Tamar', 0);

INSERT INTO `bank_account` (`id`, `user_id`, `name`, `amount`) VALUES
(1, 1, '"Caisse d\'épargne"', 1300),
(2, 2, 'Boursorama', 1200),
(3, 3, 'Crédit mutuel', 1500),
(4, 4, 'Société générale', 1100);