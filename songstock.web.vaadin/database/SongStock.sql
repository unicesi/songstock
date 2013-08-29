SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `songstock` ;
CREATE SCHEMA IF NOT EXISTS `songstock` DEFAULT CHARACTER SET utf8 ;
USE `songstock` ;

-- -----------------------------------------------------
-- Table `songstock`.`AdditionalCharges`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `songstock`.`AdditionalCharges` ;

CREATE  TABLE IF NOT EXISTS `songstock`.`AdditionalCharges` (
  `id` VARCHAR(45) NOT NULL ,
  `description` VARCHAR(100) NOT NULL ,
  `rate` DOUBLE NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `songstock`.`Users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `songstock`.`Users` ;

CREATE  TABLE IF NOT EXISTS `songstock`.`Users` (
  `email` VARCHAR(50) NOT NULL ,
  `name` VARCHAR(100) NOT NULL ,
  `password` VARCHAR(10) NOT NULL ,
  PRIMARY KEY (`email`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `songstock`.`Invoices`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `songstock`.`Invoices` ;

CREATE  TABLE IF NOT EXISTS `songstock`.`Invoices` (
  `id` INT(11) NOT NULL ,
  `date` DATETIME NOT NULL ,
  `value` DOUBLE NOT NULL ,
  `paymentMethod` VARCHAR(45) NOT NULL ,
  `paymentState` VARCHAR(45) NOT NULL ,
  `user` VARCHAR(50) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_Invoices_Users1_idx` (`user` ASC) ,
  CONSTRAINT `fk_Invoices_Users1`
    FOREIGN KEY (`user` )
    REFERENCES `songstock`.`Users` (`email` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `songstock`.`AdditionalCharges_Invoices`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `songstock`.`AdditionalCharges_Invoices` ;

CREATE  TABLE IF NOT EXISTS `songstock`.`AdditionalCharges_Invoices` (
  `additionalCharge` VARCHAR(45) NOT NULL ,
  `invoice` INT(11) NOT NULL ,
  `value` DOUBLE NULL DEFAULT NULL ,
  PRIMARY KEY (`additionalCharge`, `invoice`) ,
  INDEX `fk_AdditionalCharges_Invoices_Invoices1_idx` (`invoice` ASC) ,
  CONSTRAINT `fk_AdditionalCharges_CreditCards_AdditionalCharges1`
    FOREIGN KEY (`additionalCharge` )
    REFERENCES `songstock`.`AdditionalCharges` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_AdditionalCharges_Invoices_Invoices1`
    FOREIGN KEY (`invoice` )
    REFERENCES `songstock`.`Invoices` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `songstock`.`Albums`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `songstock`.`Albums` ;

CREATE  TABLE IF NOT EXISTS `songstock`.`Albums` (
  `id` INT(11) NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  `artist` VARCHAR(45) NOT NULL ,
  `releaseYear` INT(11) NOT NULL ,
  `genre` VARCHAR(45) NULL DEFAULT NULL ,
  `artwork` VARCHAR(45) NULL DEFAULT NULL ,
  `price` DOUBLE NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `songstock`.`CreditCards`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `songstock`.`CreditCards` ;

CREATE  TABLE IF NOT EXISTS `songstock`.`CreditCards` (
  `number` VARCHAR(16) NOT NULL ,
  `cardHolderName` VARCHAR(45) NOT NULL ,
  `expirationDate` DATETIME NOT NULL ,
  `billingAddress` VARCHAR(100) NOT NULL ,
  `zipCode` VARCHAR(45) NOT NULL ,
  `city` VARCHAR(45) NOT NULL ,
  `state` VARCHAR(45) NOT NULL ,
  `country` VARCHAR(45) NOT NULL ,
  `phoneNumber` VARCHAR(45) NOT NULL ,
  `user` VARCHAR(50) NOT NULL ,
  `cardBrand` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`number`) ,
  INDEX `fk_CreditCards_Users1_idx` (`user` ASC) ,
  CONSTRAINT `fk_CreditCards_Users1`
    FOREIGN KEY (`user` )
    REFERENCES `songstock`.`Users` (`email` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `songstock`.`CreditCards_Invoices`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `songstock`.`CreditCards_Invoices` ;

CREATE  TABLE IF NOT EXISTS `songstock`.`CreditCards_Invoices` (
  `invoice` INT(11) NOT NULL ,
  `creditCard` VARCHAR(16) NOT NULL ,
  PRIMARY KEY (`invoice`) ,
  INDEX `fk_CreditCards_Invoices_CreditCards1_idx` (`creditCard` ASC) ,
  CONSTRAINT `fk_CreditCards_Invoices_CreditCards1`
    FOREIGN KEY (`creditCard` )
    REFERENCES `songstock`.`CreditCards` (`number` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CreditCards_Invoices_Invoices1`
    FOREIGN KEY (`invoice` )
    REFERENCES `songstock`.`Invoices` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `songstock`.`Songs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `songstock`.`Songs` ;

CREATE  TABLE IF NOT EXISTS `songstock`.`Songs` (
  `id` INT(11) NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  `time` VARCHAR(5) NOT NULL ,
  `artist` VARCHAR(45) NOT NULL ,
  `price` DOUBLE NOT NULL ,
  `size` DOUBLE NOT NULL ,
  `quality` INT(11) NOT NULL ,
  `album` INT(11) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_Songs_Albums_idx` (`album` ASC) ,
  CONSTRAINT `fk_Songs_Albums`
    FOREIGN KEY (`album` )
    REFERENCES `songstock`.`Albums` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `songstock`.`Items`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `songstock`.`Items` ;

CREATE  TABLE IF NOT EXISTS `songstock`.`Items` (
  `id` INT(11) NOT NULL ,
  `album` INT(11) NULL DEFAULT NULL ,
  `song` INT(11) NULL DEFAULT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  `type` VARCHAR(5) NOT NULL ,
  `artist` VARCHAR(45) NOT NULL ,
  `price` DOUBLE NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_Items_Songs1_idx` (`song` ASC) ,
  INDEX `fk_Items_Albums1_idx` (`album` ASC) ,
  CONSTRAINT `fk_Items_Albums1`
    FOREIGN KEY (`album` )
    REFERENCES `songstock`.`Albums` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Items_Songs1`
    FOREIGN KEY (`song` )
    REFERENCES `songstock`.`Songs` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `songstock`.`Invoices_Items`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `songstock`.`Invoices_Items` ;

CREATE  TABLE IF NOT EXISTS `songstock`.`Invoices_Items` (
  `invoice` INT(11) NOT NULL ,
  `item` INT(11) NOT NULL ,
  PRIMARY KEY (`invoice`, `item`) ,
  INDEX `fk_PurchaseHistory_Items_Invoices1_idx` (`invoice` ASC) ,
  INDEX `fk_PurchaseHistory_Items_Items1` (`item` ASC) ,
  CONSTRAINT `fk_PurchaseHistory_Items_Invoices1`
    FOREIGN KEY (`invoice` )
    REFERENCES `songstock`.`Invoices` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PurchaseHistory_Items_Items1`
    FOREIGN KEY (`item` )
    REFERENCES `songstock`.`Items` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `songstock`.`PurchaseLogs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `songstock`.`PurchaseLogs` ;

CREATE  TABLE IF NOT EXISTS `songstock`.`PurchaseLogs` (
  `user` VARCHAR(50) NOT NULL ,
  `item` INT(11) NOT NULL ,
  PRIMARY KEY (`user`, `item`) ,
  INDEX `fk_PurchaseLogs_Items1_idx` (`item` ASC) ,
  CONSTRAINT `fk_PurchaseLogs_Items1`
    FOREIGN KEY (`item` )
    REFERENCES `songstock`.`Items` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PurchaseLogs_Users1`
    FOREIGN KEY (`user` )
    REFERENCES `songstock`.`Users` (`email` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `songstock`.`ShoppingCarts`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `songstock`.`ShoppingCarts` ;

CREATE  TABLE IF NOT EXISTS `songstock`.`ShoppingCarts` (
  `id` INT(11) NOT NULL ,
  `user` VARCHAR(50) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_ShoppingCart_Users1_idx` (`user` ASC) ,
  CONSTRAINT `fk_ShoppingCart_Users1`
    FOREIGN KEY (`user` )
    REFERENCES `songstock`.`Users` (`email` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `songstock`.`ShoppingCarts_Items`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `songstock`.`ShoppingCarts_Items` ;

CREATE  TABLE IF NOT EXISTS `songstock`.`ShoppingCarts_Items` (
  `shoppingCart` INT(11) NOT NULL ,
  `item` INT(11) NOT NULL ,
  PRIMARY KEY (`shoppingCart`, `item`) ,
  INDEX `fk_ShoppingCarts_Items_Items1_idx` (`item` ASC) ,
  CONSTRAINT `fk_ShoppingCarts_Items_Items1`
    FOREIGN KEY (`item` )
    REFERENCES `songstock`.`Items` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ShoppingCarts_Items_ShoppingCarts1`
    FOREIGN KEY (`shoppingCart` )
    REFERENCES `songstock`.`ShoppingCarts` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `songstock`.`Addresses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `songstock`.`Addresses` ;

CREATE  TABLE IF NOT EXISTS `songstock`.`Addresses` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `address` VARCHAR(100) NULL ,
  `user` VARCHAR(50) NOT NULL ,
  PRIMARY KEY (`id`, `user`) ,
  INDEX `fk_Addresses_Users1_idx` (`user` ASC) ,
  CONSTRAINT `fk_Addresses_Users1`
    FOREIGN KEY (`user` )
    REFERENCES `songstock`.`Users` (`email` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `songstock` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `songstock`.`Users`
-- -----------------------------------------------------
START TRANSACTION;
USE `songstock`;
INSERT INTO `songstock`.`Users` (`email`, `name`, `password`) VALUES ('admin', 'admin', 'admin');
INSERT INTO `songstock`.`Users` (`email`, `name`, `password`) VALUES ('afpaz@icesi.edu.co', 'Andres Paz', '123');
INSERT INTO `songstock`.`Users` (`email`, `name`, `password`) VALUES ('dduran@icesi.edu.co', 'David Duran', '123');
INSERT INTO `songstock`.`Users` (`email`, `name`, `password`) VALUES ('df.mendez73@uniandes.edu.co', 'David Mendez', '123');
INSERT INTO `songstock`.`Users` (`email`, `name`, `password`) VALUES ('jaime.chavarriaga@gmail.com', 'Jaime Chavarriaga', '123');

COMMIT;

-- -----------------------------------------------------
-- Data for table `songstock`.`Albums`
-- -----------------------------------------------------
START TRANSACTION;
USE `songstock`;
INSERT INTO `songstock`.`Albums` (`id`, `name`, `artist`, `releaseYear`, `genre`, `artwork`, `price`) VALUES (1, 'Too Hot To Sleep', 'Survivor', 1988, 'Rock', NULL, 20.59);
INSERT INTO `songstock`.`Albums` (`id`, `name`, `artist`, `releaseYear`, `genre`, `artwork`, `price`) VALUES (2, 'Vital Signs', 'Survivor', 1984, 'Rock', NULL, 21.02);
INSERT INTO `songstock`.`Albums` (`id`, `name`, `artist`, `releaseYear`, `genre`, `artwork`, `price`) VALUES (3, 'When Seconds Count', 'Survivor', 1986, 'Rock', NULL, 22.47);
INSERT INTO `songstock`.`Albums` (`id`, `name`, `artist`, `releaseYear`, `genre`, `artwork`, `price`) VALUES (4, 'How to Dismantle an Atomic Bomb', 'U2', 2004, 'Rock', NULL, 18.76);
INSERT INTO `songstock`.`Albums` (`id`, `name`, `artist`, `releaseYear`, `genre`, `artwork`, `price`) VALUES (5, 'The Joshua Tree', 'U2', 1987, 'Rock', NULL, 22.13);
INSERT INTO `songstock`.`Albums` (`id`, `name`, `artist`, `releaseYear`, `genre`, `artwork`, `price`) VALUES (6, 'Synchronicity', 'The Police', 1983, 'Rock', NULL, 22.43);
INSERT INTO `songstock`.`Albums` (`id`, `name`, `artist`, `releaseYear`, `genre`, `artwork`, `price`) VALUES (7, 'Painkiller', 'Judas Priest', 1990, 'Heavy Metal', NULL, 20.45);

COMMIT;

-- -----------------------------------------------------
-- Data for table `songstock`.`Songs`
-- -----------------------------------------------------
START TRANSACTION;
USE `songstock`;
INSERT INTO `songstock`.`Songs` (`id`, `name`, `time`, `artist`, `price`, `size`, `quality`, `album`) VALUES (1, 'Desperate Dreams', '04:49', 'Survivor', 2, 11.1, 320, 1);
INSERT INTO `songstock`.`Songs` (`id`, `name`, `time`, `artist`, `price`, `size`, `quality`, `album`) VALUES (2, 'High On You', '04:05', 'Survivor', 2.1, 9.4, 320, 2);
INSERT INTO `songstock`.`Songs` (`id`, `name`, `time`, `artist`, `price`, `size`, `quality`, `album`) VALUES (3, 'I Can\'t Hold Back', '04:04', 'Survivor', 2.54, 9.3, 320, 2);
INSERT INTO `songstock`.`Songs` (`id`, `name`, `time`, `artist`, `price`, `size`, `quality`, `album`) VALUES (4, 'Is This Love', '03:46', 'Survivor', 1.59, 8.7, 320, 3);
INSERT INTO `songstock`.`Songs` (`id`, `name`, `time`, `artist`, `price`, `size`, `quality`, `album`) VALUES (5, 'Man Against The World', '03:39', 'Survivor', 2.21, 8.4, 320, 3);
INSERT INTO `songstock`.`Songs` (`id`, `name`, `time`, `artist`, `price`, `size`, `quality`, `album`) VALUES (6, 'City Of Blinding Lights', '05:44', 'U2', 1.45, 5.3, 128, 4);
INSERT INTO `songstock`.`Songs` (`id`, `name`, `time`, `artist`, `price`, `size`, `quality`, `album`) VALUES (7, 'Crumbs From Your Table', '05:03', 'U2', 1.5, 4.7, 128, 4);
INSERT INTO `songstock`.`Songs` (`id`, `name`, `time`, `artist`, `price`, `size`, `quality`, `album`) VALUES (8, 'A Man And A Woman', '04:30', 'U2', 1.37, 4.2, 128, 4);
INSERT INTO `songstock`.`Songs` (`id`, `name`, `time`, `artist`, `price`, `size`, `quality`, `album`) VALUES (9, 'Sometimes You Can\'t Make it on Your Own', '05:04', 'U2', 1.5, 4.7, 128, 4);
INSERT INTO `songstock`.`Songs` (`id`, `name`, `time`, `artist`, `price`, `size`, `quality`, `album`) VALUES (10, 'Still Haven\'t Found What I\'m Looking For', '04:44', 'U2', 1.43, 4.4, 128, 5);
INSERT INTO `songstock`.`Songs` (`id`, `name`, `time`, `artist`, `price`, `size`, `quality`, `album`) VALUES (11, 'Where the Streets Have No Name', '05:36', 'U2', 1.67, 5.2, 128, 5);
INSERT INTO `songstock`.`Songs` (`id`, `name`, `time`, `artist`, `price`, `size`, `quality`, `album`) VALUES (12, 'Every Breath You Take', '03:55', 'The Police', 1.32, 3.6, 128, 6);
INSERT INTO `songstock`.`Songs` (`id`, `name`, `time`, `artist`, `price`, `size`, `quality`, `album`) VALUES (13, 'Wrapped Around Your Finger', '05:19', 'The Police', 1.75, 4.9, 128, 6);
INSERT INTO `songstock`.`Songs` (`id`, `name`, `time`, `artist`, `price`, `size`, `quality`, `album`) VALUES (14, 'Hell Patrol', '03:37', 'Judas Priest', 1.23, 3.4, 128, 7);
INSERT INTO `songstock`.`Songs` (`id`, `name`, `time`, `artist`, `price`, `size`, `quality`, `album`) VALUES (15, 'Night Crawler', '05:45', 'Judas Priest', 1.65, 5.4, 128, 7);
INSERT INTO `songstock`.`Songs` (`id`, `name`, `time`, `artist`, `price`, `size`, `quality`, `album`) VALUES (16, 'Painkiller', '06:06', 'Judas Priest', 1.45, 5.6, 128, 7);

COMMIT;
