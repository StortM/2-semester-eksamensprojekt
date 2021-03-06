-- -----------------------------------------------------
-- set Timezone
-- -----------------------------------------------------
SET GLOBAL time_zone = '+2:00';

-- -----------------------------------------------------
-- Schema nordic_motorhome_rental
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `nordic_motorhome_rental` ;

-- -----------------------------------------------------
-- Schema nordic_motorhome_rental
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `nordic_motorhome_rental` DEFAULT CHARACTER SET utf8 ;
USE `nordic_motorhome_rental` ;

-- -----------------------------------------------------
-- Table `nordic_motorhome_rental`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nordic_motorhome_rental`.`users` (
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  `enabled` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nordic_motorhome_rental`.`authorities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nordic_motorhome_rental`.`authorities` (
  `username` VARCHAR(50) NOT NULL,
  `authority` VARCHAR(50) NOT NULL,
  INDEX `fk_authorities_users_idx` (`username` ASC) VISIBLE,
  CONSTRAINT `fk_authorities_users`
    FOREIGN KEY (`username`)
    REFERENCES `nordic_motorhome_rental`.`users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nordic_motorhome_rental`.`autocamper_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nordic_motorhome_rental`.`autocamper_types` (
  `brand` VARCHAR(45) NOT NULL,
  `model` VARCHAR(45) NOT NULL,
  `beds` TINYINT NOT NULL,
  `seats` TINYINT NOT NULL,
  `description` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`brand`, `model`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nordic_motorhome_rental`.`autocampers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nordic_motorhome_rental`.`autocampers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `autocamper_type_brand` VARCHAR(45) NOT NULL,
  `autocamper_type_model` VARCHAR(45) NOT NULL,
  `year` SMALLINT NOT NULL,
  `price_day` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_autocampers_autocamper_type1_idx` (`autocamper_type_brand` ASC, `autocamper_type_model` ASC) VISIBLE,
  CONSTRAINT `fk_autocampers_autocamper_type1`
    FOREIGN KEY (`autocamper_type_brand` , `autocamper_type_model`)
    REFERENCES `nordic_motorhome_rental`.`autocamper_types` (`brand` , `model`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nordic_motorhome_rental`.`customers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nordic_motorhome_rental`.`customers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `phone` INT NOT NULL,
  `mail` VARCHAR(45) NOT NULL,
  `zip_code` INT NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nordic_motorhome_rental`.`bookings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nordic_motorhome_rental`.`bookings` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `autocamper_id` INT NOT NULL,
  `customer_id` INT NOT NULL,
  `period_start` DATE NOT NULL,
  `period_end` DATE NOT NULL,
  `dropoff` VARCHAR(200) NOT NULL,
  `pickup` VARCHAR(200) NOT NULL,
  `price_total` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_bookings_autocampers1_idx` (`autocamper_id` ASC) VISIBLE,
  INDEX `fk_bookings_customers1_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `fk_bookings_autocampers1`
    FOREIGN KEY (`autocamper_id`)
    REFERENCES `nordic_motorhome_rental`.`autocampers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bookings_customers1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `nordic_motorhome_rental`.`customers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nordic_motorhome_rental`.`accessories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nordic_motorhome_rental`.`accessories` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(1000) NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nordic_motorhome_rental`.`departments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nordic_motorhome_rental`.`departments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nordic_motorhome_rental`.`employees`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nordic_motorhome_rental`.`employees` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `department_id` INT NOT NULL,
  `users_username` VARCHAR(50) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `phone` INT NOT NULL,
  `mail` VARCHAR(45) NOT NULL,
  `zip_code` INT NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_employees_departments1_idx` (`department_id` ASC) VISIBLE,
  INDEX `fk_employees_users1_idx` (`users_username` ASC) VISIBLE,
  CONSTRAINT `fk_employees_departments1`
    FOREIGN KEY (`department_id`)
    REFERENCES `nordic_motorhome_rental`.`departments` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_employees_users1`
    FOREIGN KEY (`users_username`)
    REFERENCES `nordic_motorhome_rental`.`users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nordic_motorhome_rental`.`accessories_booking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `nordic_motorhome_rental`.`accessories_booking` (
  `accessories_id` INT NOT NULL,
  `bookings_id` INT NOT NULL,
  PRIMARY KEY (`accessories_id`, `bookings_id`),
  INDEX `fk_accessories_has_bookings_bookings1_idx` (`bookings_id` ASC) VISIBLE,
  INDEX `fk_accessories_has_bookings_accessories1_idx` (`accessories_id` ASC) VISIBLE,
  CONSTRAINT `fk_accessories_has_bookings_accessories1`
    FOREIGN KEY (`accessories_id`)
    REFERENCES `nordic_motorhome_rental`.`accessories` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_accessories_has_bookings_bookings1`
    FOREIGN KEY (`bookings_id`)
    REFERENCES `nordic_motorhome_rental`.`bookings` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;