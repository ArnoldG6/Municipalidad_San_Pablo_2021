-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema db_Notifications
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema db_Notifications
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_Notifications` DEFAULT CHARACTER SET utf8 ;
USE `db_Notifications` ;

-- -----------------------------------------------------
-- Table `db_Notifications`.`t_Notifications`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_Notifications`.`t_Notifications` (
  `id_Notification` INT NOT NULL AUTO_INCREMENT,
  `id_Transmitter` VARCHAR(15) NOT NULL,
  `id_receiver` VARCHAR(15) NOT NULL,
  `is_Read` TINYINT NULL,
  `description` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`id_Notification`, `id_Transmitter`, `id_receiver`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
