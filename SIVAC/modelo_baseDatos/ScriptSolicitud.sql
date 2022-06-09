-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema db_Message
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema db_Message
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_Message` DEFAULT CHARACTER SET utf8 ;
USE `db_Message` ;

-- -----------------------------------------------------
-- Table `db_Message`.`T_Message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_Message`.`T_Message` (
  `id_message` INT NOT NULL AUTO_INCREMENT,
  `id_user` VARCHAR(15) NOT NULL,
  `Message` VARCHAR(200) NOT NULL,
  `status` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id_message`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
