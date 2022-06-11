-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema si_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema si_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `si_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish2_ci ;
USE `si_db` ;

-- -----------------------------------------------------
-- Table `si_db`.`direccion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`direccion` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `distrito` VARCHAR(45) NOT NULL,
  `barrio` VARCHAR(45) NOT NULL,
  `direccionExacta` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`estado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`estado` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `estado` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`solicitante`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`solicitante` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cedula` VARCHAR(45) NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `primerApellido` VARCHAR(45) NOT NULL,
  `segundoApellido` VARCHAR(45) NOT NULL,
  `fechaNacimiento` VARCHAR(45) NOT NULL,
  `edad` INT NOT NULL,
  `telefonoHabitacion` VARCHAR(45) NULL DEFAULT NULL,
  `telefonoCelular` VARCHAR(45) NOT NULL,
  `correoElectronico` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`ayudatemporal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`ayudatemporal` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idSolicitante` INT NOT NULL,
  `fechaCreacion` TIMESTAMP NOT NULL,
  `claveRecuperacion` VARCHAR(45) NOT NULL,
  `motivoAyuda` VARCHAR(500) NOT NULL,
  `idDireccion` INT NOT NULL,
  `idEstado` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `solicitanteAtFk` (`idSolicitante` ASC) ,
  INDEX `direccion_fk` (`idDireccion` ASC) ,
  INDEX `estadoAtFk` (`idEstado` ASC) ,
  CONSTRAINT `direccion_fk`
    FOREIGN KEY (`idDireccion`)
    REFERENCES `si_db`.`direccion` (`id`),
  CONSTRAINT `estadoAtFk`
    FOREIGN KEY (`idEstado`)
    REFERENCES `si_db`.`estado` (`id`),
  CONSTRAINT `solicitanteAtFk`
    FOREIGN KEY (`idSolicitante`)
    REFERENCES `si_db`.`solicitante` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`estudiante`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`estudiante` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cedula` VARCHAR(45) NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `primerApellido` VARCHAR(45) NOT NULL,
  `segundoApellido` VARCHAR(45) NOT NULL,
  `fechaNacimiento` VARCHAR(45) NOT NULL,
  `edad` INT NOT NULL,
  `gradoAcademico` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`becaacademica`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`becaacademica` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idSolicitante` INT NOT NULL,
  `fechaCreacion` TIMESTAMP NOT NULL,
  `claveRecuperacion` VARCHAR(45) NOT NULL,
  `idEstudiante` INT NOT NULL,
  `idDireccion` INT NOT NULL,
  `idEstado` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `solicitanteBaFk` (`idSolicitante` ASC) ,
  INDEX `estudianteFk` (`idEstudiante` ASC) ,
  INDEX `direccionfk` (`idDireccion` ASC) ,
  INDEX `estadoBaFk` (`idEstado` ASC) ,
  CONSTRAINT `direccionfk`
    FOREIGN KEY (`idDireccion`)
    REFERENCES `si_db`.`direccion` (`id`),
  CONSTRAINT `estadoBaFk`
    FOREIGN KEY (`idEstado`)
    REFERENCES `si_db`.`estado` (`id`),
  CONSTRAINT `estudianteFk`
    FOREIGN KEY (`idEstudiante`)
    REFERENCES `si_db`.`estudiante` (`id`),
  CONSTRAINT `solicitanteBaFk`
    FOREIGN KEY (`idSolicitante`)
    REFERENCES `si_db`.`solicitante` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`gradoacademico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`gradoacademico` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nivel` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`si_departments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`si_departments` (
  `PK_DEPARTMENT` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`PK_DEPARTMENT`),
  UNIQUE INDEX `PK_DEPARTMENT_UNIQUE` (`PK_DEPARTMENT` ASC) ,
  UNIQUE INDEX `description_UNIQUE` (`description` ASC) )
ENGINE = InnoDB
AUTO_INCREMENT = 105
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`si_officials`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`si_officials` (
  `PK_OFFICIAL` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `FK_department` INT NOT NULL,
  PRIMARY KEY (`PK_OFFICIAL`),
  UNIQUE INDEX `PK_OFFICIAL_UNIQUE` (`PK_OFFICIAL` ASC) ,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) ,
  INDEX `FK_department_idx` (`FK_department` ASC) ,
  CONSTRAINT `FK_department`
    FOREIGN KEY (`FK_department`)
    REFERENCES `si_db`.`si_departments` (`PK_DEPARTMENT`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`si_users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`si_users` (
  `PK_USER` INT NOT NULL AUTO_INCREMENT,
  `FK_official` INT NOT NULL,
  `FK_email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`PK_USER`),
  UNIQUE INDEX `PK_USER_UNIQUE` (`PK_USER` ASC) ,
  UNIQUE INDEX `FK_official_UNIQUE` (`FK_official` ASC) ,
  UNIQUE INDEX `FK_EMAIL_UNIQUE` (`FK_email` ASC) ,
  CONSTRAINT `FK_email`
    FOREIGN KEY (`FK_email`)
    REFERENCES `si_db`.`si_officials` (`email`),
  CONSTRAINT `FK_official_user`
    FOREIGN KEY (`FK_official`)
    REFERENCES `si_db`.`si_officials` (`PK_OFFICIAL`))
ENGINE = InnoDB
AUTO_INCREMENT = 124
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`si_resetpass`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`si_resetpass` (
  `FK_User` INT NOT NULL,
  `resetCode` VARCHAR(45) NOT NULL,
  `expirationTime` TIMESTAMP NOT NULL,
  PRIMARY KEY (`FK_User`),
  INDEX `FK_USER_RESETPASS_idx` (`FK_User` ASC) ,
  CONSTRAINT `FK_USER_RESETPASS`
    FOREIGN KEY (`FK_User`)
    REFERENCES `si_db`.`si_users` (`PK_USER`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`si_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`si_roles` (
  `PK_ROL` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`PK_ROL`),
  UNIQUE INDEX `PK_ROL_UNIQUE` (`PK_ROL` ASC) ,
  UNIQUE INDEX `description_UNIQUE` (`description` ASC) )
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`si_user_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`si_user_roles` (
  `PK_USER_ROL` INT NOT NULL AUTO_INCREMENT,
  `FK_user` INT NOT NULL,
  `FK_rol` INT NOT NULL,
  PRIMARY KEY (`PK_USER_ROL`),
  UNIQUE INDEX `PK_USER_ROL_UNIQUE` (`PK_USER_ROL` ASC) ,
  INDEX `FK_rol_idx` (`FK_rol` ASC) ,
  INDEX `FK_user_role_idx` (`FK_user` ASC) ,
  CONSTRAINT `FK_rol`
    FOREIGN KEY (`FK_rol`)
    REFERENCES `si_db`.`si_roles` (`PK_ROL`),
  CONSTRAINT `FK_user_role`
    FOREIGN KEY (`FK_user`)
    REFERENCES `si_db`.`si_users` (`PK_USER`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`sp_budget`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`sp_budget` (
  `PK_item` VARCHAR(45) NOT NULL,
  `FK_fatherItem` VARCHAR(45) NULL DEFAULT NULL,
  `description` VARCHAR(85) NOT NULL,
  `ordinaryAmount` DOUBLE NOT NULL,
  `availableAmount` DOUBLE NOT NULL DEFAULT '0',
  `extraordinaryAmount` DOUBLE NOT NULL DEFAULT '0',
  `blockedAmount` DOUBLE NOT NULL DEFAULT '0',
  `pettyCash` DOUBLE NOT NULL DEFAULT '0',
  `tenders` DOUBLE NOT NULL DEFAULT '0',
  `increased` DOUBLE NOT NULL DEFAULT '0',
  `decreased` DOUBLE NOT NULL DEFAULT '0',
  `receives` TINYINT NOT NULL DEFAULT '0',
  PRIMARY KEY (`PK_item`),
  UNIQUE INDEX `PK_item_UNIQUE` (`PK_item` ASC) ,
  INDEX `FK_budgetFatherItem_idx` (`FK_fatherItem` ASC) ,
  CONSTRAINT `FK_budgetFatherItem`
    FOREIGN KEY (`FK_fatherItem`)
    REFERENCES `si_db`.`sp_budget` (`PK_item`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`sp_budgetlocks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`sp_budgetlocks` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `FK_budgetItem` VARCHAR(45) NULL DEFAULT NULL,
  `FK_applicant` INT NOT NULL,
  `initialDate` TIMESTAMP NOT NULL,
  `endDate` TIMESTAMP ,
  `amount` DOUBLE NOT NULL,
  `used` TINYINT NOT NULL DEFAULT '0',
  PRIMARY KEY (`PK_consecutive`),
  UNIQUE INDEX `PK_consecutive_UNIQUE` (`PK_consecutive` ASC) ,
  INDEX `FK_budgetItemLocked_idx` (`FK_budgetItem` ASC) ,
  INDEX `FK_lockApplicant_idx` (`FK_applicant` ASC) ,
  CONSTRAINT `FK_budgetItemLocked`
    FOREIGN KEY (`FK_budgetItem`)
    REFERENCES `si_db`.`sp_budget` (`PK_item`),
  CONSTRAINT `FK_lockApplicant`
    FOREIGN KEY (`FK_applicant`)
    REFERENCES `si_db`.`si_officials` (`PK_OFFICIAL`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`sp_requeststatuses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`sp_requeststatuses` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL,
  PRIMARY KEY (`PK_consecutive`),
  UNIQUE INDEX `PK_consecutive_UNIQUE` (`PK_consecutive` ASC) ,
  UNIQUE INDEX `description_UNIQUE` (`description` ASC) )
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`sp_budgetbalancecertificaterequests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`sp_budgetbalancecertificaterequests` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `PK_date` DATE NOT NULL,
  `FK_budgetItem` VARCHAR(45) NULL DEFAULT NULL,
  `FK_applicant` INT NOT NULL,
  `FK_status` INT NOT NULL DEFAULT '1',
  `amount` DOUBLE NOT NULL,
  `description` VARCHAR(85) NULL DEFAULT NULL,
  `FK_budgetItemLocked` INT NULL DEFAULT NULL,
  `refusalReason` VARCHAR(85) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_consecutive`, `PK_date`),
  INDEX `FK_scspBudgetItem_idx` (`FK_budgetItem` ASC) ,
  INDEX `FK_scspApplicant_idx` (`FK_applicant` ASC) ,
  INDEX `FK_scspStatus_idx` (`FK_status` ASC) ,
  INDEX `FK_scspBudgetItemLocked_idx` (`FK_budgetItemLocked` ASC) ,
  CONSTRAINT `FK_scspApplicant`
    FOREIGN KEY (`FK_applicant`)
    REFERENCES `si_db`.`si_officials` (`PK_OFFICIAL`),
  CONSTRAINT `FK_scspBudgetItem`
    FOREIGN KEY (`FK_budgetItem`)
    REFERENCES `si_db`.`sp_budget` (`PK_item`),
  CONSTRAINT `FK_scspBudgetItemLocked`
    FOREIGN KEY (`FK_budgetItemLocked`)
    REFERENCES `si_db`.`sp_budgetlocks` (`PK_consecutive`),
  CONSTRAINT `FK_scspStatus`
    FOREIGN KEY (`FK_status`)
    REFERENCES `si_db`.`sp_requeststatuses` (`PK_consecutive`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`sp_budgetbalancecertificates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`sp_budgetbalancecertificates` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `PK_date` DATE NOT NULL,
  `FK_transmitter` INT NOT NULL,
  `FK_beneficiary` INT NOT NULL,
  `FK_budgetItem` VARCHAR(45) NOT NULL,
  `FK_budgetItemLocked` INT NULL DEFAULT NULL,
  `FK_scspConsecutive` INT NOT NULL,
  `FK_scspDate` DATE NOT NULL,
  `amount` DOUBLE NOT NULL,
  `documentPath` VARCHAR(256) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_consecutive`, `PK_date`),
  INDEX `FK_cspTransmiter_idx` (`FK_transmitter` ASC) ,
  INDEX `FK_cspBeneficiary_idx` (`FK_beneficiary` ASC) ,
  INDEX `FK_cspBudgetItem_idx` (`FK_budgetItem` ASC) ,
  INDEX `FK_scspConsecutivePK_idx` (`FK_scspConsecutive` ASC) ,
  INDEX `FK_scspDatePK_idx` (`FK_scspDate` ASC) ,
  INDEX `FK_cspBudgetItemLocked_idx` (`FK_budgetItemLocked` ASC) ,
  INDEX `FK_scsp` (`FK_scspConsecutive` ASC, `FK_scspDate` ASC) ,
  CONSTRAINT `FK_cspBeneficiary`
    FOREIGN KEY (`FK_beneficiary`)
    REFERENCES `si_db`.`sp_budgetbalancecertificaterequests` (`FK_applicant`),
  CONSTRAINT `FK_cspBudgetItem`
    FOREIGN KEY (`FK_budgetItem`)
    REFERENCES `si_db`.`sp_budgetbalancecertificaterequests` (`FK_budgetItem`),
  CONSTRAINT `FK_cspBudgetItemLocked`
    FOREIGN KEY (`FK_budgetItemLocked`)
    REFERENCES `si_db`.`sp_budgetbalancecertificaterequests` (`FK_budgetItemLocked`),
  CONSTRAINT `FK_cspTransmiter`
    FOREIGN KEY (`FK_transmitter`)
    REFERENCES `si_db`.`si_officials` (`PK_OFFICIAL`),
  CONSTRAINT `FK_scsp`
    FOREIGN KEY (`FK_scspConsecutive` , `FK_scspDate`)
    REFERENCES `si_db`.`sp_budgetbalancecertificaterequests` (`PK_consecutive` , `PK_date`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`sp_initialbiddingactrequests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`sp_initialbiddingactrequests` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `PK_date` DATE NOT NULL,
  `FK_applicant` INT NOT NULL,
  `FK_cspConsecutive` INT NOT NULL,
  `FK_cspDate` DATE NOT NULL,
  `FK_status` INT NOT NULL DEFAULT '1',
  `documentPath` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`PK_consecutive`, `PK_date`),
  INDEX `FK_ailApplicant_idx` (`FK_applicant` ASC) ,
  INDEX `FK_ibarStatus_idx` (`FK_status` ASC) ,
  INDEX `FK_cspConsecutive_idx` (`FK_cspConsecutive` ASC) ,
  INDEX `FK_cspDate_idx` (`FK_cspDate` ASC) ,
  INDEX `FK_csp` (`FK_cspConsecutive` ASC, `FK_cspDate` ASC) ,
  CONSTRAINT `FK_ailApplicant`
    FOREIGN KEY (`FK_applicant`)
    REFERENCES `si_db`.`si_officials` (`PK_OFFICIAL`),
  CONSTRAINT `FK_csp`
    FOREIGN KEY (`FK_cspConsecutive` , `FK_cspDate`)
    REFERENCES `si_db`.`sp_budgetbalancecertificates` (`PK_consecutive` , `PK_date`),
  CONSTRAINT `FK_ibarstatus`
    FOREIGN KEY (`FK_status`)
    REFERENCES `si_db`.`sp_requeststatuses` (`PK_consecutive`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`sp_biddingacts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`sp_biddingacts` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `PK_date` DATE NOT NULL,
  `FK_transmitter` INT NOT NULL,
  `FK_beneficiary` INT NOT NULL,
  `FK_ailConsecutive` INT NOT NULL,
  `FK_ailDate` DATE NOT NULL,
  `documentPath` VARCHAR(256) NOT NULL,
  `FK_status` INT NOT NULL DEFAULT '1',
  PRIMARY KEY (`PK_consecutive`, `PK_date`),
  INDEX `FK_baBeneficiary_idx` (`FK_beneficiary` ASC) ,
  INDEX `FK_baStatus_idx` (`FK_status` ASC) ,
  INDEX `FK_baTransmitter_idx` (`FK_transmitter` ASC) ,
  INDEX `FK_ailConsecutive_idx` (`FK_ailConsecutive` ASC) ,
  INDEX `FK_ailDate_idx` (`FK_ailDate` ASC) ,
  INDEX `FK_ail` (`FK_ailConsecutive` ASC, `FK_ailDate` ASC) ,
  CONSTRAINT `FK_ail`
    FOREIGN KEY (`FK_ailConsecutive` , `FK_ailDate`)
    REFERENCES `si_db`.`sp_initialbiddingactrequests` (`PK_consecutive` , `PK_date`),
  CONSTRAINT `FK_bastatus`
    FOREIGN KEY (`FK_status`)
    REFERENCES `si_db`.`sp_requeststatuses` (`PK_consecutive`),
  CONSTRAINT `FK_dhBeneficiary`
    FOREIGN KEY (`FK_beneficiary`)
    REFERENCES `si_db`.`sp_initialbiddingactrequests` (`FK_applicant`),
  CONSTRAINT `FK_dhTransmitter`
    FOREIGN KEY (`FK_transmitter`)
    REFERENCES `si_db`.`si_officials` (`PK_OFFICIAL`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`sp_budgetcodecertificaterequests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`sp_budgetcodecertificaterequests` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `PK_date` DATE NOT NULL,
  `FK_budgetItem` VARCHAR(45) NULL DEFAULT NULL,
  `FK_applicant` INT NOT NULL,
  `FK_status` INT NOT NULL DEFAULT '1',
  `amount` DOUBLE NOT NULL,
  `description` VARCHAR(85) NULL DEFAULT NULL,
  `FK_budgetItemLocked` INT NULL DEFAULT NULL,
  `refusalReason` VARCHAR(85) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_consecutive`, `PK_date`),
  INDEX `FK_sccpBudgetItem_idx` (`FK_budgetItem` ASC) ,
  INDEX `FK_sccpStatus_idx` (`FK_status` ASC) ,
  INDEX `FK_sccpApplicant_idx` (`FK_applicant` ASC) ,
  INDEX `FK_sccpBudgetItemLocked_idx` (`FK_budgetItemLocked` ASC) ,
  CONSTRAINT `FK_sccpApplicant`
    FOREIGN KEY (`FK_applicant`)
    REFERENCES `si_db`.`si_officials` (`PK_OFFICIAL`),
  CONSTRAINT `FK_sccpBudgetItem`
    FOREIGN KEY (`FK_budgetItem`)
    REFERENCES `si_db`.`sp_budget` (`PK_item`),
  CONSTRAINT `FK_sccpBudgetItemLocked`
    FOREIGN KEY (`FK_budgetItemLocked`)
    REFERENCES `si_db`.`sp_budgetlocks` (`PK_consecutive`),
  CONSTRAINT `FK_sccpStatus`
    FOREIGN KEY (`FK_status`)
    REFERENCES `si_db`.`sp_requeststatuses` (`PK_consecutive`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`sp_budgetcodecertificates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`sp_budgetcodecertificates` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `PK_date` DATE NOT NULL,
  `FK_transmitter` INT NOT NULL,
  `FK_beneficiary` INT NOT NULL,
  `FK_budgetItem` VARCHAR(45) NOT NULL,
  `FK_budgetItemLocked` INT NULL DEFAULT NULL,
  `FK_sccpConsecutive` INT NOT NULL,
  `FK_sccpDate` DATE NOT NULL,
  `amount` DOUBLE NOT NULL,
  `documentPath` VARCHAR(256) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_consecutive`, `PK_date`),
  INDEX `FK_ccpTransmiter_idx` (`FK_transmitter` ASC) ,
  INDEX `FK_ccpBeneficiary_idx` (`FK_beneficiary` ASC) ,
  INDEX `FK_ccpBudgetItem_idx` (`FK_budgetItem` ASC) ,
  INDEX `FK_sccpConsecutivePK_idx` (`FK_sccpConsecutive` ASC) ,
  INDEX `FK_sccpDatePK_idx` (`FK_sccpDate` ASC) ,
  INDEX `FK_ccpBudgetItemLocked_idx` (`FK_budgetItemLocked` ASC) ,
  INDEX `FK_sccp` (`FK_sccpConsecutive` ASC, `FK_sccpDate` ASC) ,
  CONSTRAINT `FK_ccpBeneficiary`
    FOREIGN KEY (`FK_beneficiary`)
    REFERENCES `si_db`.`sp_budgetcodecertificaterequests` (`FK_applicant`),
  CONSTRAINT `FK_ccpBudgetItem`
    FOREIGN KEY (`FK_budgetItem`)
    REFERENCES `si_db`.`sp_budgetcodecertificaterequests` (`FK_budgetItem`),
  CONSTRAINT `FK_ccpBudgetItemLocked`
    FOREIGN KEY (`FK_budgetItemLocked`)
    REFERENCES `si_db`.`sp_budgetcodecertificaterequests` (`FK_budgetItemLocked`),
  CONSTRAINT `FK_ccpTransmiter`
    FOREIGN KEY (`FK_transmitter`)
    REFERENCES `si_db`.`si_officials` (`PK_OFFICIAL`),
  CONSTRAINT `FK_sccp`
    FOREIGN KEY (`FK_sccpConsecutive` , `FK_sccpDate`)
    REFERENCES `si_db`.`sp_budgetcodecertificaterequests` (`PK_consecutive` , `PK_date`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`sp_budgetlog`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`sp_budgetlog` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `PK_date` DATE NOT NULL,
  `FK_budgetItem` VARCHAR(45) NOT NULL,
  `initialAmount` DOUBLE NOT NULL,
  `finalAmount` DOUBLE NOT NULL,
  `increased` DOUBLE NOT NULL DEFAULT '0',
  `decreased` DOUBLE NOT NULL DEFAULT '0',
  PRIMARY KEY (`PK_consecutive`, `PK_date`),
  INDEX `FK_blBudgetItem_idx` (`FK_budgetItem` ASC) ,
  CONSTRAINT `FK_blBudgetItem`
    FOREIGN KEY (`FK_budgetItem`)
    REFERENCES `si_db`.`sp_budget` (`PK_item`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`sp_budgetmodificationrequest`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`sp_budgetmodificationrequest` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `PK_date` DATE NOT NULL,
  `FK_applicant` INT NOT NULL,
  `FK_status` INT NOT NULL DEFAULT '1',
  `documentPath` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`PK_consecutive`, `PK_date`),
  INDEX `Msp_Applicant_idx` (`FK_applicant` ASC) ,
  INDEX `FK_mspStatus_idx` (`FK_status` ASC) ,
  CONSTRAINT `FK_mspApplicant`
    FOREIGN KEY (`FK_applicant`)
    REFERENCES `si_db`.`si_officials` (`PK_OFFICIAL`),
  CONSTRAINT `FK_mspStatus`
    FOREIGN KEY (`FK_status`)
    REFERENCES `si_db`.`sp_requeststatuses` (`PK_consecutive`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`sp_budgetmodificationresolution`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`sp_budgetmodificationresolution` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `PK_date` DATE NOT NULL,
  `resolutionPath` VARCHAR(256) NOT NULL,
  `FK_requestConsecutive` INT NOT NULL,
  `FK_requestDate` DATE NOT NULL,
  `description` VARCHAR(85) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_consecutive`, `PK_date`),
  INDEX `FK_request_idx` (`FK_requestConsecutive` ASC, `FK_requestDate` ASC) ,
  CONSTRAINT `FK_request`
    FOREIGN KEY (`FK_requestConsecutive` , `FK_requestDate`)
    REFERENCES `si_db`.`sp_budgetmodificationrequest` (`PK_consecutive` , `PK_date`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`sp_purchaseorders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`sp_purchaseorders` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `PK_date` DATE NOT NULL,
  `FK_transmitter` INT NOT NULL,
  `FK_alConsecutive` INT NOT NULL,
  `FK_alDate` DATE NOT NULL,
  `documentPath` VARCHAR(256) NOT NULL,
  `FK_status` INT NOT NULL DEFAULT '1',
  PRIMARY KEY (`PK_consecutive`, `PK_date`),
  INDEX `FK_poTransmitter_idx` (`FK_transmitter` ASC) ,
  INDEX `FK_poStatus_idx` (`FK_status` ASC) ,
  INDEX `FK_alConsecutive_idx` (`FK_alConsecutive` ASC) ,
  INDEX `FK_alDate_idx` (`FK_alDate` ASC) ,
  INDEX `FK_al` (`FK_alConsecutive` ASC, `FK_alDate` ASC) ,
  CONSTRAINT `FK_al`
    FOREIGN KEY (`FK_alConsecutive` , `FK_alDate`)
    REFERENCES `si_db`.`sp_biddingacts` (`PK_consecutive` , `PK_date`),
  CONSTRAINT `FK_postatus`
    FOREIGN KEY (`FK_status`)
    REFERENCES `si_db`.`sp_requeststatuses` (`PK_consecutive`),
  CONSTRAINT `FK_poTransmitter`
    FOREIGN KEY (`FK_transmitter`)
    REFERENCES `si_db`.`si_officials` (`PK_OFFICIAL`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`sp_selectedbudget`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`sp_selectedbudget` (
  `PK_id` INT NOT NULL AUTO_INCREMENT,
  `FK_modConsecutive` INT NOT NULL,
  `FK_modDate` DATE NOT NULL,
  `FK_budgetA` VARCHAR(45) NOT NULL,
  `FK_budgetB` VARCHAR(45) NOT NULL,
  `amount` DOUBLE NOT NULL,
  `FK_cspConsecutive` INT NOT NULL,
  `FK_cspDate` DATE NOT NULL,
  PRIMARY KEY (`PK_id`),
  INDEX `FK_mspbudgetA_idx` (`FK_budgetA` ASC) ,
  INDEX `FK_mspbudgetB_idx` (`FK_budgetB` ASC) ,
  INDEX `FK_mod` (`FK_modConsecutive` ASC, `FK_modDate` ASC) ,
  INDEX `FK_cspM_idx` (`FK_cspConsecutive` ASC, `FK_cspDate` ASC) ,
  CONSTRAINT `FK_cspM`
    FOREIGN KEY (`FK_cspConsecutive` , `FK_cspDate`)
    REFERENCES `si_db`.`sp_budgetbalancecertificates` (`PK_consecutive` , `PK_date`),
  CONSTRAINT `FK_mod`
    FOREIGN KEY (`FK_modConsecutive` , `FK_modDate`)
    REFERENCES `si_db`.`sp_budgetmodificationrequest` (`PK_consecutive` , `PK_date`),
  CONSTRAINT `FK_mspbudgetA`
    FOREIGN KEY (`FK_budgetA`)
    REFERENCES `si_db`.`sp_budget` (`PK_item`),
  CONSTRAINT `FK_mspbudgetB`
    FOREIGN KEY (`FK_budgetB`)
    REFERENCES `si_db`.`sp_budget` (`PK_item`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`t_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_role` (
  `id_Role` INT NOT NULL,
  PRIMARY KEY (`id_Role`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_spanish2_ci;


-- -----------------------------------------------------
-- Table `si_db`.`t_administrative_file`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_administrative_file` (
  `id` VARCHAR(15) NOT NULL,
  `name` VARCHAR(40) NOT NULL,
  `lastname_1` VARCHAR(20) NOT NULL,
  `lastname_2` VARCHAR(20) NULL DEFAULT NULL,
  `areaMuni` VARCHAR(40) NOT NULL,
  `employment` VARCHAR(30) NOT NULL,
  `salary` DOUBLE NULL DEFAULT NULL,
  `admission_Date` DATE NOT NULL,
  `public_years` INT NULL DEFAULT '0',
  `email` VARCHAR(60) NULL DEFAULT NULL,
  `phone` INT NULL DEFAULT NULL,
  `holidays` INT NULL DEFAULT '0',
  `early_vacations` INT NULL DEFAULT NULL,
  `total_early_vacations` INT NULL DEFAULT NULL,
  `is_early_vacations` TINYINT NULL DEFAULT NULL,
  `Role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_T_Employee_T_Role1_idx` (`Role_id` ASC) ,
  CONSTRAINT `fk_T_Employee_T_Role1`
    FOREIGN KEY (`Role_id`)
    REFERENCES `si_db`.`t_role` (`id_Role`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_spanish2_ci;


-- -----------------------------------------------------
-- Table `si_db`.`t_area_muni`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_area_muni` (
  `idArea` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(40) NULL DEFAULT NULL,
  PRIMARY KEY (`idArea`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`t_early_vacations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_early_vacations` (
  `early_id` INT NOT NULL AUTO_INCREMENT,
  `start_date` DATE NULL DEFAULT NULL,
  `final_date` DATE NULL DEFAULT NULL,
  `T_Administrative_File_id` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`early_id`),
  INDEX `fk_T_Early_Vacations_T_Administrative_File1_idx` (`T_Administrative_File_id` ASC) ,
  CONSTRAINT `fk_T_Early_Vacations_T_Administrative_File1`
    FOREIGN KEY (`T_Administrative_File_id`)
    REFERENCES `si_db`.`t_administrative_file` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_spanish2_ci;


-- -----------------------------------------------------
-- Table `si_db`.`t_holidays`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_holidays` (
  `id_Holidays` INT NOT NULL AUTO_INCREMENT,
  `day` INT NOT NULL,
  `month` INT NOT NULL,
  `name` VARCHAR(80) NULL DEFAULT NULL,
  PRIMARY KEY (`id_Holidays`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`t_inhability`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_inhability` (
  `inhability_number` INT NOT NULL AUTO_INCREMENT,
  `voucher` LONGBLOB NOT NULL,
  `main_Date` DATE NOT NULL,
  `id_inha` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`inhability_number`),
  INDEX `fk_T_Inhability_T_Administrative_File1_idx` (`id_inha` ASC) ,
  CONSTRAINT `fk_T_Inhability_T_Administrative_File1`
    FOREIGN KEY (`id_inha`)
    REFERENCES `si_db`.`t_administrative_file` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_spanish2_ci;


-- -----------------------------------------------------
-- Table `si_db`.`t_license_cgs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_license_cgs` (
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
    REFERENCES `si_db`.`t_administrative_file` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_spanish2_ci;


-- -----------------------------------------------------
-- Table `si_db`.`t_license_sgs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_license_sgs` (
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
    REFERENCES `si_db`.`t_administrative_file` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_spanish2_ci;


-- -----------------------------------------------------
-- Table `si_db`.`t_message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_message` (
  `id` VARCHAR(15) NOT NULL DEFAULT 'No lo dio',
  `Message` VARCHAR(200) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`t_notifications`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_notifications` (
  `id_Notification` INT NOT NULL AUTO_INCREMENT,
  `id_Transmitter` VARCHAR(15) NOT NULL,
  `id_receiver` VARCHAR(15) NOT NULL,
  `is_Read` TINYINT NULL DEFAULT NULL,
  `description` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`id_Notification`, `id_Transmitter`, `id_receiver`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`t_sfr_comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_sfr_comment` (
  `PK_ID` INT NOT NULL,
  `Comment` VARCHAR(2000) NOT NULL,
  `Url` VARCHAR(1000) NOT NULL,
  `Author` VARCHAR(1000) NOT NULL,
  `EntryDate` DATETIME NOT NULL,
  PRIMARY KEY (`PK_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`t_sfr_risk`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_sfr_risk` (
  `PK_ID` INT NOT NULL AUTO_INCREMENT,
  `ID` VARCHAR(100) NOT NULL,
  `Name` VARCHAR(300) NOT NULL,
  `Factors` VARCHAR(2000) NOT NULL,
  `GeneralType` VARCHAR(100) NOT NULL,
  `AreaType` VARCHAR(100) NOT NULL,
  `AreaSpecificType` VARCHAR(100) NOT NULL,
  `Description` VARCHAR(2000) NOT NULL,
  `Probability` FLOAT NOT NULL,
  `Impact` SMALLINT NOT NULL,
  `Magnitude` FLOAT NOT NULL,
  `MitigationMeasures` VARCHAR(2000) NOT NULL,
  `Author` INT NULL DEFAULT NULL,
  `Consequences` VARCHAR(2000) NOT NULL,
  PRIMARY KEY (`PK_ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC) ,
  INDEX `FK_RISK_AUTHOR_idx` (`Author` ASC) ,
  CONSTRAINT `FK_RISK_AUTHOR`
    FOREIGN KEY (`Author`)
    REFERENCES `si_db`.`si_users` (`PK_USER`))
ENGINE = InnoDB
AUTO_INCREMENT = 124
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`t_sfr_incidence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_sfr_incidence` (
  `PK_ID` INT NOT NULL,
  `Name` VARCHAR(1000) NOT NULL,
  `EntryDate` DATE NOT NULL,
  `Description` VARCHAR(2000) NOT NULL,
  `Cause` VARCHAR(1000) NOT NULL,
  `Affectation` INT NOT NULL,
  `riskID` INT NULL DEFAULT NULL,
  PRIMARY KEY (`PK_ID`),
  INDEX `riskID_idx` (`riskID` ASC) ,
  CONSTRAINT `riskID`
    FOREIGN KEY (`riskID`)
    REFERENCES `si_db`.`t_sfr_risk` (`PK_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`t_sfr_plan`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_sfr_plan` (
  `PK_ID` INT NOT NULL AUTO_INCREMENT,
  `ID` VARCHAR(100) NOT NULL,
  `Name` VARCHAR(1000) NOT NULL,
  `Description` VARCHAR(2000) NOT NULL,
  `EntryDate` DATETIME NOT NULL,
  `Status` VARCHAR(45) NOT NULL,
  `AuthorName` VARCHAR(90) NOT NULL,
  `Type` VARCHAR(100) NOT NULL,
  `Subtype` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`PK_ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC) )
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`t_sfr_plancomment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_sfr_plancomment` (
  `FK_PLAN` INT NOT NULL,
  `FK_COMMENT` INT NOT NULL,
  PRIMARY KEY (`FK_PLAN`, `FK_COMMENT`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`t_sfr_planincidence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_sfr_planincidence` (
  `FK_PLAN` INT NOT NULL,
  `FK_INCIDENCE` INT NOT NULL,
  PRIMARY KEY (`FK_PLAN`, `FK_INCIDENCE`),
  INDEX `FK_INCIDENCE_idx` (`FK_INCIDENCE` ASC) ,
  CONSTRAINT `FK_INCIDENCE`
    FOREIGN KEY (`FK_INCIDENCE`)
    REFERENCES `si_db`.`t_sfr_incidence` (`PK_ID`),
  CONSTRAINT `FK_PLANINCIDENCE`
    FOREIGN KEY (`FK_PLAN`)
    REFERENCES `si_db`.`t_sfr_plan` (`PK_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`t_sfr_plantypes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_sfr_plantypes` (
  `PK_ID` INT NOT NULL AUTO_INCREMENT,
  `Parent` INT NULL DEFAULT NULL,
  `Name` VARCHAR(100) NOT NULL,
  `ID_Name` VARCHAR(100) NULL DEFAULT NULL,
  `ID_Amount` INT NULL DEFAULT NULL,
  PRIMARY KEY (`PK_ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID_Name` ASC) ,
  INDEX `Parent_idx` (`Parent` ASC) ,
  CONSTRAINT `FK_PLAN_PARENT`
    FOREIGN KEY (`Parent`)
    REFERENCES `si_db`.`t_sfr_plantypes` (`PK_ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`t_sfr_planuser`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_sfr_planuser` (
  `FK_PLAN` INT NOT NULL,
  `FK_USER` INT NOT NULL,
  PRIMARY KEY (`FK_PLAN`, `FK_USER`),
  INDEX `FK_INVOLUCRADO_USER_idx` (`FK_USER` ASC) ,
  CONSTRAINT `FK_INVOLUCRADO_PLAN`
    FOREIGN KEY (`FK_PLAN`)
    REFERENCES `si_db`.`t_sfr_plan` (`PK_ID`),
  CONSTRAINT `FK_INVOLUCRADO_USER`
    FOREIGN KEY (`FK_USER`)
    REFERENCES `si_db`.`si_users` (`PK_USER`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`t_sfr_riskplan`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_sfr_riskplan` (
  `FK_PLAN` INT NOT NULL,
  `FK_RISK` INT NOT NULL,
  PRIMARY KEY (`FK_PLAN`, `FK_RISK`),
  INDEX `FK_RISK_idx` (`FK_RISK` ASC) ,
  CONSTRAINT `FK_PLANRISK`
    FOREIGN KEY (`FK_PLAN`)
    REFERENCES `si_db`.`t_sfr_plan` (`PK_ID`),
  CONSTRAINT `FK_RISK`
    FOREIGN KEY (`FK_RISK`)
    REFERENCES `si_db`.`t_sfr_risk` (`PK_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`t_sfr_risktypes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_sfr_risktypes` (
  `PK_ID` INT NOT NULL AUTO_INCREMENT,
  `Parent` INT NULL DEFAULT NULL,
  `Name` VARCHAR(200) NOT NULL,
  `ID_Name` VARCHAR(100) NULL DEFAULT NULL,
  `ID_Amount` INT NULL DEFAULT NULL,
  `Consequence` VARCHAR(2000) NULL DEFAULT NULL,
  PRIMARY KEY (`PK_ID`),
  UNIQUE INDEX `ID_Name_UNIQUE` (`ID_Name` ASC) ,
  INDEX `Parent_idx` (`Parent` ASC) ,
  CONSTRAINT `FK_RISK_PARENT`
    FOREIGN KEY (`Parent`)
    REFERENCES `si_db`.`t_sfr_risktypes` (`PK_ID`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`t_sfr_transaction_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_sfr_transaction_log` (
  `idt_sfr_transaction_log` INT NOT NULL AUTO_INCREMENT,
  `transaction_timestamp` TIMESTAMP NOT NULL,
  `username` INT NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `transaction_name` VARCHAR(100) NOT NULL,
  `success` TINYINT NOT NULL,
  `description` VARCHAR(2000) NOT NULL,
  PRIMARY KEY (`idt_sfr_transaction_log`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`t_si_db_transaction_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_si_db_transaction_log` (
  `idt_sfr_transaction_log` INT NOT NULL AUTO_INCREMENT,
  `transaction_timestamp` TIMESTAMP NOT NULL,
  `username_or_email` VARCHAR(100) NOT NULL,
  `transaction_name` VARCHAR(100) NOT NULL,
  `success` TINYINT NOT NULL,
  `description` VARCHAR(2000) NULL DEFAULT NULL,
  PRIMARY KEY (`idt_sfr_transaction_log`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `si_db`.`t_vacation_days`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_vacation_days` (
  `id_day` INT NOT NULL AUTO_INCREMENT,
  `days` DATE NOT NULL,
  `status` VARCHAR(15) NOT NULL,
  `id_day_user` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id_day`),
  INDEX `fk_T_Vacation_Days_T_Administrative_File1_idx` (`id_day_user` ASC) ,
  CONSTRAINT `fk_T_Vacation_Days_T_Administrative_File1`
    FOREIGN KEY (`id_day_user`)
    REFERENCES `si_db`.`t_administrative_file` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_spanish2_ci;


-- -----------------------------------------------------
-- Table `si_db`.`t_vacations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`t_vacations` (
  `vacation_number` INT NOT NULL AUTO_INCREMENT,
  `start_date` DATE NOT NULL,
  `final_date` DATE NOT NULL,
  `status` VARCHAR(10) NOT NULL,
  `id_vac` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`vacation_number`),
  INDEX `fk_T_Vacations_T_Administrative_File2_idx` (`id_vac` ASC) ,
  CONSTRAINT `fk_T_Vacations_T_Administrative_File2`
    FOREIGN KEY (`id_vac`)
    REFERENCES `si_db`.`t_administrative_file` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_spanish2_ci;

USE `si_db` ;

-- -----------------------------------------------------
-- procedure addHolidays
-- -----------------------------------------------------

DELIMITER $$
USE `si_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addHolidays`(in dias INT, in idEmployee INT)
BEGIN
	UPDATE t_administrative_file SET holidays = holidays + dias where id = idEmployee;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure authUser
-- -----------------------------------------------------

DELIMITER $$
USE `si_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `authUser`(p_user varchar(45), p_pwd varchar(256))
BEGIN
  SELECT PK_USER, FK_official, FK_email 
  FROM si_db.SI_USERS 
  WHERE (password = p_pwd AND 
  ((UPPER(FK_email) = UPPER(p_user) 
  OR UPPER(PK_USER) = UPPER(p_user))));
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure authenticateViaEmail
-- -----------------------------------------------------

DELIMITER $$
USE `si_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `authenticateViaEmail`(emailPrt varchar(45), passwordPrt varchar(45))
BEGIN
  SELECT PK_USER, FK_official, FK_email, password FROM si_db.SI_USERS WHERE FK_email = emailPrt AND password = SHA2(passwordPrt, 256);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure authenticateViaUsername
-- -----------------------------------------------------

DELIMITER $$
USE `si_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `authenticateViaUsername`(userPrt int, passwordPrt varchar(45))
BEGIN
  SELECT PK_USER, FK_official, FK_email, password FROM si_db.SI_USERS WHERE PK_USER = userPrt AND password = SHA2(passwordPrt, 256);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure checkPwdResetCodeValidity
-- -----------------------------------------------------

DELIMITER $$
USE `si_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkPwdResetCodeValidity`(
IN P_IN_FK_USER INT
)
BEGIN
	SET sql_safe_updates=0;
	DELETE FROM si_db.si_resetpass WHERE expirationTime < NOW();
    SET sql_safe_updates=1;
    SELECT resetCode FROM si_db.si_resetpass WHERE P_IN_FK_USER = fk_user;
	commit;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function fun_toUpperAll
-- -----------------------------------------------------

DELIMITER $$
USE `si_db`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `fun_toUpperAll`(str_value varchar(5000)) RETURNS varchar(5000) CHARSET utf8
    DETERMINISTIC
BEGIN
	DECLARE i INT;
    DECLARE len INT;
    
    SET len = CHAR_LENGTH(str_value);
    SET str_value = LCASE(str_value);
    SET i = 0;
    
    WHILE (i < len) DO
		IF (MID(str_value, i, 1) = ' ' OR i = 0) THEN
			IF (i < len) THEN
				SET str_value = CONCAT(LEFT(str_value, i), UCASE(MID(str_value, i + 1, 1)), RIGHT(str_value, len - i - 1));
            END IF;
        END IF;
		SET i = i + 1;
	END WHILE;
    
    RETURN str_value;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function fun_toUpperFirstOnly
-- -----------------------------------------------------

DELIMITER $$
USE `si_db`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `fun_toUpperFirstOnly`(str_value varchar(5000)) RETURNS varchar(5000) CHARSET utf8
    DETERMINISTIC
BEGIN
	return concat(ucase(left(str_value,1)), lcase(substring(str_value, 2)));
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function fun_toUpperStandard
-- -----------------------------------------------------

DELIMITER $$
USE `si_db`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `fun_toUpperStandard`(str_value varchar(5000)) RETURNS varchar(5000) CHARSET utf8
    DETERMINISTIC
BEGIN
	DECLARE i INT;
    DECLARE len INT;
    
    SET len = CHAR_LENGTH(str_value);
    SET str_value = LCASE(str_value);
    SET i = 0;
    
    WHILE (i < len) DO
		IF (MID(str_value, i, 2) = '. ' OR i = 0) THEN
			IF (i < len) THEN
				IF (i != 0) THEN
					SET i = i + 1;
				END IF;
				SET str_value = CONCAT(LEFT(str_value, i ), UCASE(MID(str_value, i + 1, 1)), RIGHT(str_value, len - i - 1));
            END IF;
        END IF;
		SET i = i + 1;
	END WHILE;
    
    RETURN str_value;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insertResetCode
-- -----------------------------------------------------

DELIMITER $$
USE `si_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertResetCode`(
IN P_IN_FK_USER INT,
IN P_IN_RESET_CODE VARCHAR(45)
)
BEGIN
	SET @exp_time := NOW() + INTERVAL 30 MINUTE;
	INSERT INTO si_db.si_resetpass(FK_User,resetCode,expirationTime) VALUES (P_IN_FK_USER,P_IN_RESET_CODE, @exp_time);
    commit;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insertUser
-- -----------------------------------------------------

DELIMITER $$
USE `si_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertUser`(
IN P_IN_PK_USER INT,
IN P_IN_FK_OFFICIAL INT,
IN P_IN_FK_EMAIL varchar(256),
IN P_IN_PASSWORD varchar(256)
)
BEGIN
	INSERT INTO si_db.si_users (PK_USER, FK_official, FK_email, password) VALUES (P_IN_PK_USER, P_IN_FK_OFFICIAL, P_IN_FK_EMAIL, P_IN_PASSWORD);
    commit;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insertUser_roles
-- -----------------------------------------------------

DELIMITER $$
USE `si_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertUser_roles`(
IN P_IN_PK_USER_ROL INT,
IN P_IN_FK_USER INT,
IN P_IN_FK_ROL INT
)
BEGIN
    INSERT INTO si_db.si_user_roles (PK_USER_ROL, FK_user, FK_rol) VALUES (P_IN_PK_USER_ROL, P_IN_FK_USER, P_IN_FK_ROL);
    commit;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure recordTransaction
-- -----------------------------------------------------

DELIMITER $$
USE `si_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `recordTransaction`(
IN P_USERNAME INT,
IN P_EMAIL VARCHAR(100),
IN P_TRANSACTION_NAME VARCHAR(100),
IN P_SUCCESS TINYINT,
IN P_DESCRIPTION VARCHAR(500)
)
BEGIN
	INSERT 
    INTO si_db.t_sfr_transaction_log(transaction_timestamp,username,email,transaction_name,success,description) 
    VALUES (NOW(), P_USERNAME, P_EMAIL, P_TRANSACTION_NAME, P_SUCCESS, P_DESCRIPTION);
    commit;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure recordUserTransaction
-- -----------------------------------------------------

DELIMITER $$
USE `si_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `recordUserTransaction`(
IN P_EMAIL_OR_USERNAME VARCHAR(100),
IN P_TRANSACTION_NAME VARCHAR(100),
IN P_SUCCESS TINYINT,
IN P_DESCRIPTION VARCHAR(500)
)
BEGIN
	INSERT 
    INTO si_db.t_si_db_transaction_log(transaction_timestamp,username_or_email,transaction_name,success,description) 
    VALUES (NOW(), P_EMAIL_OR_USERNAME, P_TRANSACTION_NAME, P_SUCCESS, P_DESCRIPTION);
    commit;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure removeLockPrc
-- -----------------------------------------------------

DELIMITER $$
USE `si_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `removeLockPrc`(id_lock VARCHAR(11))
BEGIN
  DECLARE idBudget VARCHAR(45) DEFAULT NULL;
    DECLARE localAmount DOUBLE DEFAULT NULL;
    SELECT FK_budgetItem, amount INTO idBudget, localAmount FROM `si_db`.`SP_BudgetLocks` WHERE (`PK_consecutive` = id_lock AND used = '0');
    IF idBudget IS NOT NULL THEN
    UPDATE `si_db`.`SP_Budget` SET blockedAmount = blockedAmount - localAmount WHERE PK_item = idBudget;
    UPDATE `si_db`.`SP_Budget` SET availableAmount = availableAmount + localAmount WHERE PK_item = idBudget;
    UPDATE `si_db`.`SP_BudgetBalanceCertificates` SET FK_budgetItemLocked = NULL WHERE FK_budgetItemLocked = id_lock;
    DELETE FROM `si_db`.`SP_BudgetLocks` WHERE (`PK_consecutive` = id_lock AND used = '0');
  END IF;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure retrieveCurrentLocks
-- -----------------------------------------------------

DELIMITER $$
USE `si_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `retrieveCurrentLocks`()
BEGIN
  SELECT * FROM si_db.SP_BudgetLocks WHERE endDate > CURDATE();
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure searchBudgetByNameOrCode
-- -----------------------------------------------------

DELIMITER $$
USE `si_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `searchBudgetByNameOrCode`(parameter varchar(85))
BEGIN
  SELECT * FROM si_db.SP_Budget WHERE PK_item LIKE CONCAT('%', parameter, '%') OR description LIKE CONCAT('%', parameter, '%') ORDER BY PK_item ASC;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure setUserPassword
-- -----------------------------------------------------

DELIMITER $$
USE `si_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `setUserPassword`(
IN P_IN_FK_USER INT,
IN P_IN_PWD_HASH VARCHAR(256)
)
BEGIN
	UPDATE si_db.si_users
    SET password = P_IN_PWD_HASH
    WHERE PK_USER = P_IN_FK_USER;
	DELETE FROM si_db.si_resetpass WHERE FK_USER = P_IN_FK_USER;
	commit;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure verifyHolidays
-- -----------------------------------------------------

DELIMITER $$
USE `si_db`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `verifyHolidays`()
BEGIN
	/*DECLARACION DE VARIABLES*/
DECLARE var_id varchar(15);
DECLARE var_admission date;
DECLARE var_public integer;
DECLARE annio_cumplido integer;
DECLARE total_annios integer;
DECLARE verificar_dia integer;
DECLARE verificar_mes integer;
DECLARE var_final INTEGER DEFAULT 0;


	/*DECLARACION DEL CURSOR*/
DECLARE vacations CURSOR FOR SELECT id, admission_Date, public_years from t_administrative_file;

	/*VARIABLE EN CASO DE QUE NO EXISTA UN DATO*/
DECLARE CONTINUE HANDLER FOR NOT FOUND SET var_final = 1;

	/*SE ABRE EL CURSOR*/
OPEN vacations;

holidays: LOOP
	/*RECORRE LA TABLA*/
FETCH vacations INTO var_id, var_admission, var_public;
SET @annio_cumplido:=(select datediff(curdate(),var_admission)/365);
SET @total_annios:=@annio_cumplido + var_public;
SET @verificar_dia:= (select day(var_admission));
SET @verificar_mes:=(select month(var_admission));

/*VERIFICA SI YA RECORRIO TODO*/
IF var_final = 1 THEN
      LEAVE holidays;
END IF;

	/*VALIDACIONES PARA LAS VACACIONES Y SETEAR LAS VACACIONES Y VERIFICAR DIA Y MES*/
    if (select day(curdate())) = (@verificar_dia) and (select month(curdate())) = (@verificar_mes) then
	  if @annio_cumplido>=1 then 
		if @total_annios < 5 then  call addHolidays(15, var_id);

		elseif @total_annios < 9 then call addHolidays(20, var_id);

		else call addHolidays(30,var_id) ;

		end if;
	 end if;
   end if;	
END LOOP holidays;

	/*SE CIERRA EL CURSOR*/
CLOSE vacations;

END$$

DELIMITER ;
USE `si_db`;

DELIMITER $$
USE `si_db`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `si_db`.`SI_DEPARTMENTS_BEFORE_INSERT`
BEFORE INSERT ON `si_db`.`si_departments`
FOR EACH ROW
BEGIN
	DECLARE newDesc varchar(45);
    SELECT fun_toUpperAll(NEW.description) INTO newDesc;
	SET NEW.description = newDesc;
END$$

USE `si_db`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `si_db`.`SI_OFFICIALS_BEFORE_INSERT`
BEFORE INSERT ON `si_db`.`si_officials`
FOR EACH ROW
BEGIN
	DECLARE newName varchar(45);
    DECLARE newSurname varchar(45);
    
    SELECT fun_toUpperAll(NEW.name) INTO newName;
    SELECT fun_toUpperAll(NEW.surname) INTO newSurname;
    
    SET NEW.name = newName;
    SET NEW.surname = newSurname;
    SET NEW.email = LCase(NEW.email);    
END$$

USE `si_db`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `si_db`.`SI_OFFICIALS_BEFORE_UPDATE`
BEFORE UPDATE ON `si_db`.`si_officials`
FOR EACH ROW
BEGIN
	DECLARE newName varchar(45);
    DECLARE newSurname varchar(45);
    
    SELECT fun_toUpperAll(NEW.name) INTO newName;
    SELECT fun_toUpperAll(NEW.surname) INTO newSurname;
    
    SET NEW.name = newName;
    SET NEW.surname = newSurname;
    SET NEW.email = LCase(NEW.email); 
END$$

USE `si_db`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `si_db`.`SI_USERS_BEFORE_INSERT`
BEFORE INSERT ON `si_db`.`si_users`
FOR EACH ROW
BEGIN
	SET NEW.FK_email = LCASE(NEW.FK_email);
END$$

USE `si_db`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `si_db`.`SI_USERS_BEFORE_UPDATE`
BEFORE UPDATE ON `si_db`.`si_users`
FOR EACH ROW
BEGIN
	SET NEW.FK_email = LCASE(NEW.FK_email);
END$$

USE `si_db`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `si_db`.`SI_ROLES_BEFORE_INSERT`
BEFORE INSERT ON `si_db`.`si_roles`
FOR EACH ROW
BEGIN    
    SET NEW.description = UCASE(NEW.description);
END$$

USE `si_db`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `si_db`.`SI_ROLES_BEFORE_UPDATE`
BEFORE UPDATE ON `si_db`.`si_roles`
FOR EACH ROW
BEGIN
    SET NEW.description = UCASE(NEW.description);
END$$

USE `si_db`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `si_db`.`budgetLogChangesTrigger`
AFTER UPDATE ON `si_db`.`sp_budget`
FOR EACH ROW
BEGIN
	IF OLD.availableAmount - NEW.availableAmount > 0 THEN
		INSERT INTO `si_db`.`sp_budgetlog` (`PK_date`, `FK_budgetItem`, `initialAmount`, `finalAmount`, `increased`, `decreased`) VALUES (CURDATE(), NEW.PK_item, OLD.availableAmount, NEW.availableAmount, OLD.availableAmount - NEW.availableAmount, 0);
    ELSEIF OLD.availableAmount - NEW.availableAmount < 0 THEN
		INSERT INTO `si_db`.`sp_budgetlog` (`PK_date`, `FK_budgetItem`, `initialAmount`, `finalAmount`, `increased`, `decreased`) VALUES (CURDATE(), NEW.PK_item, OLD.availableAmount, NEW.availableAmount, 0, (OLD.availableAmount - NEW.availableAmount) * -1);
    END IF;
END$$

USE `si_db`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `si_db`.`T_SFR_Comment_BEFORE_INSERT`
BEFORE INSERT ON `si_db`.`t_sfr_comment`
FOR EACH ROW
BEGIN
	DECLARE newComment varchar(2000);
    DECLARE newAuthor varchar(1000);
    
    SELECT fun_toUpperStandard(NEW.Comment) INTO newComment;
    SELECT fun_toUpperAll(NEW.Author) INTO newAuthor;
    
    SET NEW.Comment = newComment;
    SET NEW.Author = newAuthor;
END$$

USE `si_db`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `si_db`.`T_SFR_Comment_BEFORE_UPDATE`
BEFORE UPDATE ON `si_db`.`t_sfr_comment`
FOR EACH ROW
BEGIN
	DECLARE newComment varchar(2000);
    DECLARE newAuthor varchar(1000);
    
    SELECT fun_toUpperStandard(NEW.Comment) INTO newComment;
    SELECT fun_toUpperAll(NEW.Author) INTO newAuthor;
    
    SET NEW.Comment = newComment;
    SET NEW.Author = newAuthor;
END$$

USE `si_db`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `si_db`.`T_SFR_Risk_BEFORE_INSERT`
BEFORE INSERT ON `si_db`.`t_sfr_risk`
FOR EACH ROW
BEGIN
	DECLARE newName varchar(300);
    DECLARE newFactors varchar(2000);
	DECLARE newDescription varchar(2000);
	DECLARE newMitigation varchar(2000);
    DECLARE newConcequences varchar(2000);

    SELECT fun_toUpperFirstOnly(NEW.Name) INTO newName;
    SELECT fun_toUpperStandard(NEW.Factors) INTO newFactors;
    SELECT fun_toUpperStandard(NEW.Description) INTO newDescription;
    SELECT fun_toUpperStandard(NEW.MitigationMeasures) INTO newMitigation;
    SELECT fun_toUpperStandard(NEW.Consequences) INTO newConcequences;
    
    SET NEW.ID = UCASE(NEW.ID);
    SET NEW.Name = newName;
    SET NEW.Factors = newFactors;
    SET NEW.Description = newDescription;
    SET NEW.MitigationMeasures = newMitigation;
    SET NEW.Consequences = newConcequences;
END$$

USE `si_db`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `si_db`.`T_SFR_Risk_BEFORE_UPDATE`
BEFORE UPDATE ON `si_db`.`t_sfr_risk`
FOR EACH ROW
BEGIN
	DECLARE newName varchar(300);
    DECLARE newFactors varchar(2000);
	DECLARE newDescription varchar(2000);
	DECLARE newMitigation varchar(2000);
    DECLARE newConcequences varchar(2000);

    SELECT fun_toUpperFirstOnly(NEW.Name) INTO newName;
    SELECT fun_toUpperStandard(NEW.Factors) INTO newFactors;
    SELECT fun_toUpperStandard(NEW.Description) INTO newDescription;
    SELECT fun_toUpperStandard(NEW.MitigationMeasures) INTO newMitigation;
    SELECT fun_toUpperStandard(NEW.Consequences) INTO newConcequences;
    
    SET NEW.ID = UCASE(NEW.ID);
    SET NEW.Name = newName;
    SET NEW.Factors = newFactors;
    SET NEW.Description = newDescription;
    SET NEW.MitigationMeasures = newMitigation;
    SET NEW.Consequences = newConcequences;
END$$

USE `si_db`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `si_db`.`T_SFR_Incidence_BEFORE_INSERT`
BEFORE INSERT ON `si_db`.`t_sfr_incidence`
FOR EACH ROW
BEGIN
	DECLARE newName varchar(1000);
    DECLARE newDescription varchar(2000);
    DECLARE newCause varchar(1000);
    
    SELECT fun_toUpperAll(NEW.Name) INTO newName;
    SELECT fun_toUpperStandard(NEW.Description) INTO newDescription;
    SELECT fun_toUpperFirstOnly(NEW.Cause) INTO newCause;
    
    SET NEW.Name = newName;
    SET NEW.Description = newDescription;
    SET NEW.Cause = newCause;
END$$

USE `si_db`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `si_db`.`T_SFR_Incidence_BEFORE_UPDATE`
BEFORE UPDATE ON `si_db`.`t_sfr_incidence`
FOR EACH ROW
BEGIN
	DECLARE newName varchar(1000);
    DECLARE newDescription varchar(2000);
    DECLARE newCause varchar(1000);
    
    SELECT fun_toUpperAll(NEW.Name) INTO newName;
    SELECT fun_toUpperStandard(NEW.Description) INTO newDescription;
    SELECT fun_toUpperFirstOnly(NEW.Cause) INTO newCause;
    
    SET NEW.Name = newName;
    SET NEW.Description = newDescription;
    SET NEW.Cause = newCause;
END$$

USE `si_db`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `si_db`.`T_SFR_Plan_BEFORE_INSERT`
BEFORE INSERT ON `si_db`.`t_sfr_plan`
FOR EACH ROW
BEGIN
	DECLARE newName varchar(1000);
    DECLARE newDescription varchar(2000);
    DECLARE newStatus varchar(45);
    DECLARE newAuthor varchar(90);

    SELECT fun_toUpperFirstOnly(NEW.Name) INTO newName;
    SELECT fun_toUpperStandard(NEW.Description) INTO newDescription;
    SELECT fun_toUpperFirstOnly(NEW.Status) INTO newStatus;
    
    SET NEW.ID = UCASE(NEW.ID);
    SET NEW.Name = newName;
    SET NEW.Description = newDescription;
    SET NEW.Status = newStatus;
END$$

USE `si_db`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `si_db`.`T_SFR_Plan_BEFORE_UPDATE`
BEFORE UPDATE ON `si_db`.`t_sfr_plan`
FOR EACH ROW
BEGIN
	DECLARE newName varchar(1000);
    DECLARE newDescription varchar(2000);
    DECLARE newStatus varchar(45);
    DECLARE newAuthor varchar(90);

    SELECT fun_toUpperFirstOnly(NEW.Name) INTO newName;
    SELECT fun_toUpperStandard(NEW.Description) INTO newDescription;
    SELECT fun_toUpperFirstOnly(NEW.Status) INTO newStatus;
    
    SET NEW.ID = UCASE(NEW.ID);
    SET NEW.Name = newName;
    SET NEW.Description = newDescription;
    SET NEW.Status = newStatus;
END$$

USE `si_db`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `si_db`.`T_SFR_PlanTypes_BEFORE_INSERT`
BEFORE INSERT ON `si_db`.`t_sfr_plantypes`
FOR EACH ROW
BEGIN
    SET NEW.ID_Name = UCASE(NEW.ID_Name);
END$$

USE `si_db`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `si_db`.`T_SFR_PlanTypes_BEFORE_UPDATE`
BEFORE UPDATE ON `si_db`.`t_sfr_plantypes`
FOR EACH ROW
BEGIN
    SET NEW.ID_Name = UCASE(NEW.ID_Name);
END$$

USE `si_db`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `si_db`.`T_SFR_RiskTypes_BEFORE_INSERT`
BEFORE INSERT ON `si_db`.`t_sfr_risktypes`
FOR EACH ROW
BEGIN
    SET NEW.ID_Name = UCASE(NEW.ID_Name);
END$$

USE `si_db`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `si_db`.`T_SFR_RiskTypes_BEFORE_UPDATE`
BEFORE UPDATE ON `si_db`.`t_sfr_risktypes`
FOR EACH ROW
BEGIN
    SET NEW.ID_Name = UCASE(NEW.ID_Name);
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
