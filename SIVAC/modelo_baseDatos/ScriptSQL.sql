-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema bd_Muni
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bd_Muni
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bd_Muni` DEFAULT CHARACTER SET utf8 ;
USE `bd_Muni` ;

-- -----------------------------------------------------
-- Table `bd_Muni`.`T_Role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bd_Muni`.`T_Role` (
  `id_Role` INT NOT NULL,
  PRIMARY KEY (`id_Role`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bd_Muni`.`T_Administrative_File`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bd_Muni`.`T_Administrative_File` (
  `id` VARCHAR(15) NOT NULL,
  `name` VARCHAR(40) NOT NULL,
  `lastname_1` VARCHAR(20) NOT NULL,
  `lastname_2` VARCHAR(20) NULL,
  `areaMuni` VARCHAR(40) NOT NULL,
  `employment` VARCHAR(30) NOT NULL,
  `salary` DOUBLE NULL,
  `admission_Date` DATE NOT NULL,
  `public_years` INT NULL DEFAULT 0,
  `email` VARCHAR(60) NULL,
  `phone` INT NULL,
  `holidays` INT NULL DEFAULT 0,
  `early_vacations` INT NULL,
  `total_early_vacations` INT NULL,
  `is_early_vacations` TINYINT NULL,
  `Role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_T_Employee_T_Role1_idx` (`Role_id` ASC) ,
  CONSTRAINT `fk_T_Employee_T_Role1`
    FOREIGN KEY (`Role_id`)
    REFERENCES `bd_Muni`.`T_Role` (`id_Role`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bd_Muni`.`T_Vacations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bd_Muni`.`T_Vacations` (
  `vacation_number` INT NOT NULL AUTO_INCREMENT,
  `start_date` DATE NOT NULL,
  `final_date` DATE NOT NULL,
  `status` VARCHAR(10) NOT NULL,
  `id_vac` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`vacation_number`),
  INDEX `fk_T_Vacations_T_Administrative_File2_idx` (`id_vac` ASC) ,
  CONSTRAINT `fk_T_Vacations_T_Administrative_File2`
    FOREIGN KEY (`id_vac`)
    REFERENCES `bd_Muni`.`T_Administrative_File` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bd_Muni`.`T_License_CGS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bd_Muni`.`T_License_CGS` (
  `number_CGS` INT NOT NULL AUTO_INCREMENT,
  `category` VARCHAR(100) NOT NULL,
  `start_date` DATE NOT NULL,
  `final_date` DATE NOT NULL,
  `justification` VARCHAR(200) NULL DEFAULT 'Sin justificacion',
  `status` VARCHAR(10) NOT NULL,
  `PDF` LONGBLOB NULL DEFAULT NULL,
  `id_CGS` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`number_CGS`),
  INDEX `fk_T_License_CGS_T_Administrative_File1_idx` (`id_CGS` ASC) ,
  CONSTRAINT `fk_T_License_CGS_T_Administrative_File1`
    FOREIGN KEY (`id_CGS`)
    REFERENCES `bd_Muni`.`T_Administrative_File` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bd_Muni`.`T_Inhability`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bd_Muni`.`T_Inhability` (
  `inhability_number` INT NOT NULL AUTO_INCREMENT,
  `voucher` LONGBLOB NOT NULL,
  `main_Date` DATE NOT NULL,
  `id_inha` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`inhability_number`),
  INDEX `fk_T_Inhability_T_Administrative_File1_idx` (`id_inha` ASC) ,
  CONSTRAINT `fk_T_Inhability_T_Administrative_File1`
    FOREIGN KEY (`id_inha`)
    REFERENCES `bd_Muni`.`T_Administrative_File` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bd_Muni`.`T_License_SGS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bd_Muni`.`T_License_SGS` (
  `number_SGS` INT NOT NULL AUTO_INCREMENT,
  `category` VARCHAR(100) NOT NULL,
  `start_date` DATE NOT NULL,
  `final_date` DATE NOT NULL,
  `justification` VARCHAR(200) NOT NULL,
  `status` VARCHAR(10) NOT NULL,
  `id_SGS` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`number_SGS`),
  INDEX `fk_T_License_SGS_T_Administrative_File1_idx` (`id_SGS` ASC) ,
  CONSTRAINT `fk_T_License_SGS_T_Administrative_File1`
    FOREIGN KEY (`id_SGS`)
    REFERENCES `bd_Muni`.`T_Administrative_File` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bd_Muni`.`T_Early_Vacations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bd_Muni`.`T_Early_Vacations` (
  `early_id` INT NOT NULL AUTO_INCREMENT,
  `start_date` DATE NULL,
  `final_date` DATE NULL,
  `T_Administrative_File_id` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`early_id`),
  INDEX `fk_T_Early_Vacations_T_Administrative_File1_idx` (`T_Administrative_File_id` ASC) ,
  CONSTRAINT `fk_T_Early_Vacations_T_Administrative_File1`
    FOREIGN KEY (`T_Administrative_File_id`)
    REFERENCES `bd_Muni`.`T_Administrative_File` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bd_Muni`.`T_Vacation_Days`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bd_Muni`.`T_Vacation_Days` (
  `id_day` INT NOT NULL AUTO_INCREMENT,
  `days` DATE NOT NULL,
  `status` VARCHAR(15) NOT NULL,
  `id_day_user` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id_day`),
  INDEX `fk_T_Vacation_Days_T_Administrative_File1_idx` (`id_day_user` ASC) ,
  CONSTRAINT `fk_T_Vacation_Days_T_Administrative_File1`
    FOREIGN KEY (`id_day_user`)
    REFERENCES `bd_Muni`.`T_Administrative_File` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
