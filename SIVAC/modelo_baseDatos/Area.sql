-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema bd_Area
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bd_Area
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bd_Area` DEFAULT CHARACTER SET utf8 ;
USE `bd_Area` ;

-- -----------------------------------------------------
-- Table `bd_Area`.`T_Area_Muni`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bd_Area`.`T_Area_Muni` (
  `idArea` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(40) NULL,
  PRIMARY KEY (`idArea`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
