SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `si_db` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE IF NOT EXISTS `si_db`.`SI_USERS` (
  `PK_USER` INT(11) NOT NULL AUTO_INCREMENT,
  `FK_official` INT(11) NOT NULL,
  `FK_email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`PK_USER`),
  UNIQUE INDEX `PK_USER_UNIQUE` (`PK_USER` ASC) INVISIBLE,
  UNIQUE INDEX `FK_official_UNIQUE` (`FK_official` ASC) VISIBLE,
  UNIQUE INDEX `FK_EMAIL_UNIQUE` (`FK_email` ASC) VISIBLE,
  CONSTRAINT `FK_official_user`
    FOREIGN KEY (`FK_official`)
    REFERENCES `si_db`.`SI_OFFICIALS` (`PK_OFFICIAL`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_email`
    FOREIGN KEY (`FK_email`)
    REFERENCES `si_db`.`SI_OFFICIALS` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `si_db`.`SI_OFFICIALS` (
  `PK_OFFICIAL` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `FK_department` INT(11) NOT NULL,
  PRIMARY KEY (`PK_OFFICIAL`),
  UNIQUE INDEX `PK_OFFICIAL_UNIQUE` (`PK_OFFICIAL` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `FK_department_idx` (`FK_department` ASC) VISIBLE,
  CONSTRAINT `FK_department`
    FOREIGN KEY (`FK_department`)
    REFERENCES `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `si_db`.`SI_DEPARTMENTS` (
  `PK_DEPARTMENT` INT(11) NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`PK_DEPARTMENT`),
  UNIQUE INDEX `PK_DEPARTMENT_UNIQUE` (`PK_DEPARTMENT` ASC) VISIBLE,
  UNIQUE INDEX `description_UNIQUE` (`description` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `si_db`.`SI_ROLES` (
  `PK_ROL` INT(11) NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`PK_ROL`),
  UNIQUE INDEX `PK_ROL_UNIQUE` (`PK_ROL` ASC) VISIBLE,
  UNIQUE INDEX `description_UNIQUE` (`description` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `si_db`.`SI_USER_ROLES` (
  `PK_USER_ROL` INT(11) NOT NULL AUTO_INCREMENT,
  `FK_user` INT(11) NOT NULL,
  `FK_rol` INT(11) NOT NULL,
  INDEX `FK_rol_idx` (`FK_rol` ASC) VISIBLE,
  INDEX `FK_user_role_idx` (`FK_user` ASC) VISIBLE,
  PRIMARY KEY (`PK_USER_ROL`),
  UNIQUE INDEX `PK_USER_ROL_UNIQUE` (`PK_USER_ROL` ASC) VISIBLE,
  CONSTRAINT `FK_user_role`
    FOREIGN KEY (`FK_user`)
    REFERENCES `si_db`.`SI_USERS` (`PK_USER`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_rol`
    FOREIGN KEY (`FK_rol`)
    REFERENCES `si_db`.`SI_ROLES` (`PK_ROL`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


USE `si_db`;
DROP procedure IF EXISTS `authenticateViaEmail`;
DELIMITER $$
USE `si_db`$$
CREATE PROCEDURE `authenticateViaEmail` (emailPrt varchar(45), passwordPrt varchar(45))
BEGIN
  SELECT PK_USER, FK_official, FK_email, password FROM si_db.SI_USERS WHERE FK_email = emailPrt AND password = SHA2(passwordPrt, 256);
END$$
DELIMITER ;

USE `si_db`;
DROP procedure IF EXISTS `authenticateViaUsername`;
DELIMITER $$
USE `si_db`$$
CREATE PROCEDURE `authenticateViaUsername` (userPrt int, passwordPrt varchar(45))
BEGIN
  SELECT PK_USER, FK_official, FK_email, password FROM si_db.SI_USERS WHERE PK_USER = userPrt AND password = SHA2(passwordPrt, 256);
END$$
DELIMITER ;

INSERT INTO `si_db`.`si_departments` (`PK_DEPARTMENT`, `description`) VALUES ('100', 'CONTROL INTERNO');
INSERT INTO `si_db`.`si_departments` (`PK_DEPARTMENT`, `description`) VALUES ('101', 'PLANIFICACION');
INSERT INTO `si_db`.`si_departments` (`PK_DEPARTMENT`, `description`) VALUES ('102', 'CONTABILIDAD');
INSERT INTO `si_db`.`si_departments` (`PK_DEPARTMENT`, `description`) VALUES ('103', 'INFORMATICA');
INSERT INTO `si_db`.`si_roles` (`PK_ROL`, `description`) VALUES ('1', 'ADMIN');
INSERT INTO `si_db`.`si_roles` (`PK_ROL`, `description`) VALUES ('2', 'SUPER_ADMIN');
INSERT INTO `si_db`.`si_roles` (`PK_ROL`, `description`) VALUES ('3', 'USER');
INSERT INTO `si_db`.`si_officials` (`PK_OFFICIAL`, `name`, `surname`, `email`, `FK_department`) VALUES ('50', 'JOSEPH', 'GRANDA', 'informatica@sanpablo.go.cr', '103');
INSERT INTO `si_db`.`si_officials` (`PK_OFFICIAL`, `name`, `surname`, `email`, `FK_department`) VALUES ('51', 'ISMAEL', 'SALAZAR', 'controlinterno@sanpablo.go.cr', '100');
INSERT INTO `si_db`.`si_users` (`PK_USER`, `FK_official`, `FK_email`, `password`) VALUES ('50', '50', 'informatica@sanpablo.go.cr', SHA2('contra1', 256));
INSERT INTO `si_db`.`si_users` (`PK_USER`, `FK_official`, `FK_email`, `password`) VALUES ('51', '51', 'controlinterno@sanpablo.go.cr', SHA2('contra2', 256));
INSERT INTO `si_db`.`si_user_roles` (`PK_USER_ROL`,`FK_USER`,`FK_ROL`) values (1,50,2);
commit;