DROP DATABASE IF EXISTS `paymybuddy`;
CREATE DATABASE paymybuddy;
USE paymybuddy;

-- TABLE CREATION
-- user
DROP TABLE IF EXISTS `user`;

CREATE TABLE user (
`user_id` INTEGER NOT NULL AUTO_INCREMENT,
`email` VARCHAR(100) NOT NULL UNIQUE,
`password` VARCHAR(100) NOT NULL,
`firstname` VARCHAR(100) NOT NULL,
`lastname` VARCHAR(100) NOT NULL,
`amount` float NOT NULL,
PRIMARY KEY (`user_id`)
);

-- bank_account
DROP TABLE IF EXISTS `bank_account`;

CREATE TABLE bank_account (
`bank_id` INTEGER NOT NULL AUTO_INCREMENT,
`user_id` INTEGER NOT NULL,
`name` VARCHAR(100) NOT NULL,
`amount` float,
PRIMARY KEY (`bank_id`),
FOREIGN KEY (`user_email`) REFERENCES user(`email`)
);

-- connection
DROP TABLE IF EXISTS `connection`;

CREATE TABLE connection (
user_id INTEGER NOT NULL,
friend_id INTEGER NOT NULL,
PRIMARY KEY (`user_id`, `friend_id`),
FOREIGN KEY (`user_id`) REFERENCES user(`user_id`),
FOREIGN KEY (`friend_id`) REFERENCES user(`user_id`)
);

-- in_app_transaction
DROP TABLE IF EXISTS `in_app_transaction`;

CREATE TABLE in_app_transaction (
`transaction_id` INTEGER NOT NULL AUTO_INCREMENT,
`sender_id` INTEGER NOT NULL,
`receiver_id` INTEGER NOT NULL,
`amount` FLOAT NOT NULL,
`comment` VARCHAR(250),
`fee` FLOAT NOT NULL,
PRIMARY KEY (`transaction_id`),
FOREIGN KEY (`sender_id`) REFERENCES connection(`user_id`),
FOREIGN KEY (`receiver_id`) REFERENCES connection(`friend_id`)
);

-- bank_transaction
DROP TABLE IF EXISTS `bank_transaction`;

CREATE TABLE bank_transaction (
transaction_id INTEGER NOT NULL AUTO_INCREMENT,
user_id INTEGER NOT NULL,
bank_id INTEGER NOT NULL,
amount FLOAT NOT NULL,
fee FLOAT NOT NULL,
PRIMARY KEY (`transaction_id`),
FOREIGN KEY (`user_id`) REFERENCES user(`user_id`),
FOREIGN KEY (`bank_id`) REFERENCES bank_account(`bank_id`)
);



-- DATA INSERTION
INSERT INTO `user` (`user_id`, `email`, `password`, `firstname`, `lastname`, `amount`) VALUES
(1, 'mtampion@mail.fr', 'p455w0rd_MT', 'Mocktar', 'Tampion', 0),
(2, 'bafritte@mail.fr', 'p455w0rd_BA', 'Barak', 'Afritte', 0),
(3, 'dzou@mail.fr', 'p455w0rd_DZ', 'Debby', 'Zou', 0),
(4, 'jtamar@mail.fr', 'p455w0rd_JT', 'Justin', 'Tamar', 0);

INSERT INTO `bank_account` (`bank_id`, `user_email`, `name`, `amount`) VALUES
(1, 'mtampion@mail.fr', '"Caisse d\'épargne"', 1300),
(2, 'bafritte@mail.fr', 'Boursorama', 1200),
(3, 'dzou@mail.fr', 'Crédit mutuel', 1500),
(4, 'jtamar@mail.fr', 'Société générale', 1100);