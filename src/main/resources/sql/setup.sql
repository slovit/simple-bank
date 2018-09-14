DROP DATABASE IF EXISTS payments;

CREATE DATABASE payments DEFAULT CHARACTER SET utf8;
USE payments;

drop table if exists accounts;

drop table if exists credit_account;

drop table if exists deposit_account;

drop table if exists transactions;

drop table if exists users;

CREATE TABLE `accounts` (
  `account_id` int(11) NOT NULL,
  `userId` int(255) NOT NULL,
  `balance` double NOT NULL,
  `creation_date` date NOT NULL,
  `expire_date` date NOT NULL,
  `type` enum('DEPOSIT','CREDIT') NOT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `user_id_UNIQUE` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `credit_account` (
  `account_id` int(11) NOT NULL,
  `credit_limit` double DEFAULT NULL,
  `arrears` double DEFAULT NULL,
  `credit_bet` double DEFAULT NULL,
  `persentage` double DEFAULT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `deposit_account` (
  `account_id` int(11) NOT NULL,
  `deposit_bet` double DEFAULT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `transactions` (
  `account_id` int(11) NOT NULL,
  `from_account` int(11) DEFAULT NULL,
  `to_account` int(11) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `date_time` datetime NOT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `users` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(100) NOT NULL,
  `password` varchar(256) NOT NULL,
  `role` enum('USER','ADMIN') NOT NULL,
  PRIMARY KEY (`userId`),
  CONSTRAINT `userId` FOREIGN KEY (`userId`) REFERENCES `accounts` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
