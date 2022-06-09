-- -----------------------------------------------------
-- Table `si_db`.`SP_RequestStatuses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`SP_RequestStatuses` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL,
  PRIMARY KEY (`PK_consecutive`),
  UNIQUE INDEX `PK_consecutive_UNIQUE` (`PK_consecutive` ASC) VISIBLE,
  UNIQUE INDEX `description_UNIQUE` (`description` ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `si_db`.`SP_Budget`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`SP_Budget` (
  `PK_item` VARCHAR(45) NOT NULL,
  `FK_fatherItem` VARCHAR(45) NULL,
  `description` VARCHAR(85) NOT NULL,
  `ordinaryAmount` DOUBLE NOT NULL CONSTRAINT `CK_budgetOrdinaryAmount` CHECK (`ordinaryAmount` >= 0),
  `availableAmount` DOUBLE NOT NULL DEFAULT 0 CONSTRAINT `CK_budgetAvailableAmount` CHECK (`availableAmount` >= 0),
  `extraordinaryAmount` DOUBLE NOT NULL DEFAULT 0 CONSTRAINT `CK_budgetExtraordinaryAmount` CHECK (`extraordinaryAmount` >= 0),
  `blockedAmount` DOUBLE NOT NULL DEFAULT 0 CONSTRAINT `CK_budgetBlockedAmount` CHECK (`blockedAmount` >= 0),
  `pettyCash` DOUBLE NOT NULL DEFAULT 0 CONSTRAINT `CK_budgetPettyCashAmount` CHECK (`pettyCash` >= 0),
  `tenders` DOUBLE NOT NULL DEFAULT 0 CONSTRAINT `CK_budgetTendersAmount` CHECK (`tenders` >= 0),
  `increased` DOUBLE NOT NULL DEFAULT 0 CONSTRAINT `CK_budgetIncreasedAmount` CHECK (`increased` >= 0),
  `decreased` DOUBLE NOT NULL DEFAULT 0 CONSTRAINT `CK_budgetDecreasedAmount` CHECK (`decreased` >= 0),
  `receives` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`PK_item`),
  UNIQUE INDEX `PK_item_UNIQUE` (`PK_item` ASC) VISIBLE,
  INDEX `FK_budgetFatherItem_idx` (`FK_fatherItem` ASC) VISIBLE,
  CONSTRAINT `FK_budgetFatherItem`
    FOREIGN KEY (`FK_fatherItem`)
    REFERENCES `si_db`.`SP_Budget` (`PK_item`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `si_db`.`SP_BudgetLocks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`SP_BudgetLocks` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `FK_budgetItem` VARCHAR(45),
  `FK_applicant` INT(11) NOT NULL,
  `initialDate` TIMESTAMP NOT NULL,
  `endDate` TIMESTAMP NOT NULL,
  `amount` DOUBLE NOT NULL CONSTRAINT `CK_budgetLockAmount` CHECK (`amount` > 0),
  `used` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`PK_consecutive`),
  UNIQUE INDEX `PK_consecutive_UNIQUE` (`PK_consecutive` ASC) VISIBLE,
  INDEX `FK_budgetItemLocked_idx` (`FK_budgetItem` ASC) VISIBLE,
  INDEX `FK_lockApplicant_idx` (`FK_applicant` ASC) VISIBLE,
  CONSTRAINT `FK_budgetItemLocked`
    FOREIGN KEY (`FK_budgetItem`)
    REFERENCES `si_db`.`SP_Budget` (`PK_item`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_lockApplicant`
    FOREIGN KEY (`FK_applicant`)
    REFERENCES `si_db`.`SI_OFFICIALS` (`PK_OFFICIAL`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `si_db`.`SP_BudgetBalanceCertificateRequests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`SP_BudgetBalanceCertificateRequests` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `PK_date` DATE NOT NULL,
  `FK_budgetItem` VARCHAR(45) NULL,
  `FK_applicant` INT(11) NOT NULL,
  `FK_status` INT NOT NULL DEFAULT 1,
  `amount` DOUBLE NOT NULL CONSTRAINT `CK_scspAmount` CHECK (`amount` > 150000),
  `description` VARCHAR(85) NULL,
  `FK_budgetItemLocked` INT DEFAULT NULL,
  `refusalReason` VARCHAR(85) NULL,
  PRIMARY KEY (`PK_consecutive`, `PK_date`),
  INDEX `FK_scspBudgetItem_idx` (`FK_budgetItem` ASC) VISIBLE,
  INDEX `FK_scspApplicant_idx` (`FK_applicant` ASC) VISIBLE,
  INDEX `FK_scspStatus_idx` (`FK_status` ASC) VISIBLE,
  INDEX `FK_scspBudgetItemLocked_idx` (`FK_budgetItemLocked` ASC) VISIBLE,
  CONSTRAINT `FK_scspBudgetItem`
    FOREIGN KEY (`FK_budgetItem`)
    REFERENCES `si_db`.`SP_Budget` (`PK_item`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_scspApplicant`
    FOREIGN KEY (`FK_applicant`)
    REFERENCES `si_db`.`SI_OFFICIALS` (`PK_OFFICIAL`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_scspStatus`
    FOREIGN KEY (`FK_status`)
    REFERENCES `si_db`.`SP_RequestStatuses` (`PK_consecutive`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
 CONSTRAINT `FK_scspBudgetItemLocked`
    FOREIGN KEY (`FK_budgetItemLocked`)
    REFERENCES `si_db`.`SP_BudgetLocks` (`PK_consecutive`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `si_db`.`SP_BudgetCodeCertificateRequests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`SP_BudgetCodeCertificateRequests` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `PK_date` DATE NOT NULL,
  `FK_budgetItem` VARCHAR(45) NULL,
  `FK_applicant` INT(11) NOT NULL,
  `FK_status` INT NOT NULL DEFAULT 1,
  `amount` DOUBLE NOT NULL CONSTRAINT `CK_sccp` CHECK (`amount` > 0 AND `amount` <= 150000),
  `description` VARCHAR(85) NULL,
  `FK_budgetItemLocked` INT DEFAULT NULL,
  `refusalReason` VARCHAR(85) NULL,
  PRIMARY KEY (`PK_consecutive`, `PK_date`),
  INDEX `FK_sccpBudgetItem_idx` (`FK_budgetItem` ASC) VISIBLE,
  INDEX `FK_sccpStatus_idx` (`FK_status` ASC) VISIBLE,
  INDEX `FK_sccpApplicant_idx` (`FK_applicant` ASC) VISIBLE,
  INDEX `FK_sccpBudgetItemLocked_idx` (`FK_budgetItemLocked` ASC) VISIBLE,
  CONSTRAINT `FK_sccpBudgetItem`
    FOREIGN KEY (`FK_budgetItem`)
    REFERENCES `si_db`.`SP_Budget` (`PK_item`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_sccpStatus`
    FOREIGN KEY (`FK_status`)
    REFERENCES `si_db`.`SP_RequestStatuses` (`PK_consecutive`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_sccpApplicant`
    FOREIGN KEY (`FK_applicant`)
    REFERENCES `si_db`.`SI_OFFICIALS` (`PK_OFFICIAL`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
 CONSTRAINT `FK_sccpBudgetItemLocked`
    FOREIGN KEY (`FK_budgetItemLocked`)
    REFERENCES `si_db`.`SP_BudgetLocks` (`PK_consecutive`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `si_db`.`SP_BudgetBalanceCertificates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`SP_BudgetBalanceCertificates` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `PK_date` DATE NOT NULL,
  `FK_transmitter` INT(11) NOT NULL,
  `FK_beneficiary` INT(11) NOT NULL,
  `FK_budgetItem` VARCHAR(45) NOT NULL,
  `FK_budgetItemLocked` INT DEFAULT NULL,
  `FK_scspConsecutive` INT NOT NULL,
  `FK_scspDate` DATE NOT NULL,
  `amount` DOUBLE NOT NULL,
  `documentPath` VARCHAR(256) NULL,
  PRIMARY KEY (`PK_consecutive`, `PK_date`),
  INDEX `FK_cspTransmiter_idx` (`FK_transmitter` ASC) VISIBLE,
  INDEX `FK_cspBeneficiary_idx` (`FK_beneficiary` ASC) VISIBLE,
  INDEX `FK_cspBudgetItem_idx` (`FK_budgetItem` ASC) VISIBLE,
  INDEX `FK_scspConsecutivePK_idx` (`FK_scspConsecutive` ASC) VISIBLE,
  INDEX `FK_scspDatePK_idx` (`FK_scspDate` ASC) VISIBLE,
  INDEX `FK_cspBudgetItemLocked_idx` (`FK_budgetItemLocked` ASC) VISIBLE,
  CONSTRAINT `FK_cspTransmiter`
    FOREIGN KEY (`FK_transmitter`)
    REFERENCES `si_db`.`SI_OFFICIALS` (`PK_OFFICIAL`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_cspBeneficiary`
    FOREIGN KEY (`FK_beneficiary`)
    REFERENCES `si_db`.`SP_BudgetBalanceCertificateRequests` (`FK_applicant`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_cspBudgetItem`
    FOREIGN KEY (`FK_budgetItem`)
    REFERENCES `si_db`.`SP_BudgetBalanceCertificateRequests` (`FK_budgetItem`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_scsp`
    FOREIGN KEY (`FK_scspConsecutive`, `FK_scspDate`)
    REFERENCES `si_db`.`SP_BudgetBalanceCertificateRequests` (`PK_consecutive`, `PK_date`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_cspBudgetItemLocked`
    FOREIGN KEY (`FK_budgetItemLocked`)
    REFERENCES `si_db`.`SP_BudgetBalanceCertificateRequests` (`FK_budgetItemLocked`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `si_db`.`SP_BudgetCodeCertificates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`SP_BudgetCodeCertificates` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `PK_date` DATE NOT NULL,
  `FK_transmitter` INT(11) NOT NULL,
  `FK_beneficiary` INT(11) NOT NULL,
  `FK_budgetItem` VARCHAR(45) NOT NULL,
  `FK_budgetItemLocked` INT DEFAULT NULL,
  `FK_sccpConsecutive` INT NOT NULL,
  `FK_sccpDate` DATE NOT NULL,
  `amount` DOUBLE NOT NULL,
  `documentPath` VARCHAR(256) NULL,
  PRIMARY KEY (`PK_consecutive`, `PK_date`),
  INDEX `FK_ccpTransmiter_idx` (`FK_transmitter` ASC) VISIBLE,
  INDEX `FK_ccpBeneficiary_idx` (`FK_beneficiary` ASC) VISIBLE,
  INDEX `FK_ccpBudgetItem_idx` (`FK_budgetItem` ASC) VISIBLE,
  INDEX `FK_sccpConsecutivePK_idx` (`FK_sccpConsecutive` ASC) VISIBLE,
  INDEX `FK_sccpDatePK_idx` (`FK_sccpDate` ASC) VISIBLE,
  INDEX `FK_ccpBudgetItemLocked_idx` (`FK_budgetItemLocked` ASC) VISIBLE,
  CONSTRAINT `FK_ccpTransmiter`
    FOREIGN KEY (`FK_transmitter`)
    REFERENCES `si_db`.`SI_OFFICIALS` (`PK_OFFICIAL`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_ccpBeneficiary`
    FOREIGN KEY (`FK_beneficiary`)
    REFERENCES `si_db`.`SP_BudgetCodeCertificateRequests` (`FK_applicant`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_ccpBudgetItem`
    FOREIGN KEY (`FK_budgetItem`)
    REFERENCES `si_db`.`SP_BudgetCodeCertificateRequests` (`FK_budgetItem`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_sccp`
    FOREIGN KEY (`FK_sccpConsecutive`, `FK_sccpDate`)
    REFERENCES `si_db`.`SP_BudgetCodeCertificateRequests` (`PK_consecutive`, `PK_date`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_ccpBudgetItemLocked`
    FOREIGN KEY (`FK_budgetItemLocked`)
    REFERENCES `si_db`.`SP_BudgetCodeCertificateRequests` (`FK_budgetItemLocked`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `si_db`.`SP_InitialBiddingActRequests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`SP_InitialBiddingActRequests` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `PK_date` DATE NOT NULL,
  `FK_applicant` INT(11) NOT NULL,
  `FK_cspConsecutive` INT NOT NULL,
  `FK_cspDate` DATE NOT NULL,
  `FK_status` INT NOT NULL DEFAULT 1,
  `documentPath` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`PK_consecutive`, `PK_date`),
  INDEX `FK_ailApplicant_idx` (`FK_applicant` ASC) VISIBLE,
  INDEX `FK_ibarStatus_idx` (`FK_status` ASC) VISIBLE,
  INDEX `FK_cspConsecutive_idx` (`FK_cspConsecutive` ASC) VISIBLE,
  INDEX `FK_cspDate_idx` (`FK_cspDate` ASC) VISIBLE,
  CONSTRAINT `FK_ailApplicant`
    FOREIGN KEY (`FK_applicant`)
    REFERENCES `si_db`.`SI_OFFICIALS` (`PK_OFFICIAL`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_ibarstatus`
    FOREIGN KEY (`FK_status`)
    REFERENCES `si_db`.`SP_RequestStatuses` (`PK_consecutive`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_csp`
    FOREIGN KEY (`FK_cspConsecutive`, `FK_cspDate`)
    REFERENCES `si_db`.`SP_BudgetBalanceCertificates` (`PK_consecutive`, `PK_date`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `si_db`.`BiddingActs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`SP_BiddingActs` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `PK_date` DATE NOT NULL,
  `FK_transmitter` INT(11) NOT NULL,
  `FK_beneficiary` INT(11) NOT NULL,
  `FK_ailConsecutive` INT NOT NULL,
  `FK_ailDate` DATE NOT NULL,
  `documentPath` VARCHAR(256) NOT NULL,
  `FK_status` INT NOT NULL DEFAULT 1,
  PRIMARY KEY (`PK_consecutive`, `PK_date`),
  INDEX `FK_baBeneficiary_idx` (`FK_beneficiary` ASC) VISIBLE,
  INDEX `FK_baStatus_idx` (`FK_status` ASC) VISIBLE,
  INDEX `FK_baTransmitter_idx` (`FK_transmitter` ASC) VISIBLE,
  INDEX `FK_ailConsecutive_idx` (`FK_ailConsecutive` ASC) VISIBLE,
  INDEX `FK_ailDate_idx` (`FK_ailDate` ASC) VISIBLE,
  CONSTRAINT `FK_dhTransmitter`
    FOREIGN KEY (`FK_transmitter`)
    REFERENCES `si_db`.`SI_OFFICIALS` (`PK_OFFICIAL`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_bastatus`
    FOREIGN KEY (`FK_status`)
    REFERENCES `si_db`.`SP_RequestStatuses` (`PK_consecutive`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_dhBeneficiary`
    FOREIGN KEY (`FK_beneficiary`)
    REFERENCES `si_db`.`SP_InitialBiddingActRequests` (`FK_applicant`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_ail`
    FOREIGN KEY (`FK_ailConsecutive`, `FK_ailDate`)
    REFERENCES `si_db`.`SP_InitialBiddingActRequests` (`PK_consecutive`, `PK_date`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `si_db`.`SP_PurchaseOrder`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`SP_PurchaseOrders` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `PK_date` DATE NOT NULL,
  `FK_transmitter` INT(11) NOT NULL,
  `FK_alConsecutive` INT NOT NULL,
  `FK_alDate` DATE NOT NULL,
  `documentPath` VARCHAR(256) NOT NULL,
  `FK_status` INT NOT NULL DEFAULT 1,
  PRIMARY KEY (`PK_consecutive`, `PK_date`),
  INDEX `FK_poTransmitter_idx` (`FK_transmitter` ASC) VISIBLE,
  INDEX `FK_poStatus_idx` (`FK_status` ASC) VISIBLE,
  INDEX `FK_alConsecutive_idx` (`FK_alConsecutive` ASC) VISIBLE,
  INDEX `FK_alDate_idx` (`FK_alDate` ASC) VISIBLE,
  CONSTRAINT `FK_poTransmitter`
    FOREIGN KEY (`FK_transmitter`)
    REFERENCES `si_db`.`SI_OFFICIALS` (`PK_OFFICIAL`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_postatus`
    FOREIGN KEY (`FK_status`)
    REFERENCES `si_db`.`SP_RequestStatuses` (`PK_consecutive`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_al`
    FOREIGN KEY (`FK_alConsecutive`, `FK_alDate`)
    REFERENCES `si_db`.`SP_BiddingActs` (`PK_consecutive`, `PK_date`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `si_db`.`sp_budgetmodificationrequest`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`sp_budgetmodificationrequest` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `PK_date` DATE NOT NULL,
  `FK_applicant` INT NOT NULL,
  `FK_status` INT NOT NULL DEFAULT '1',
  PRIMARY KEY (`PK_consecutive`, `PK_date`),
  INDEX `Msp_Applicant_idx` (`FK_applicant` ASC) VISIBLE,
  INDEX `FK_mspStatus_idx` (`FK_status` ASC) VISIBLE,
  CONSTRAINT `FK_mspApplicant`
    FOREIGN KEY (`FK_applicant`)
    REFERENCES `si_db`.`si_officials` (`PK_OFFICIAL`),
  CONSTRAINT `FK_mspStatus`
    FOREIGN KEY (`FK_status`)
    REFERENCES `si_db`.`sp_requeststatuses` (`PK_consecutive`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`sp_budgetmodificationresolution`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`sp_budgetmodificationresolution` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `PK_date` DATE NOT NULL,
  `resolutionPath` VARCHAR(256) NOT NULL,
  `FK_requestConsecutive` INT NOT NULL,
  `FK_requestDate` DATE NOT NULL,
  `description` VARCHAR(85) NULL,
  PRIMARY KEY (`PK_consecutive`, `PK_date`),
  INDEX `FK_request_idx` (`FK_requestConsecutive` ASC, `FK_requestDate` ASC) VISIBLE,
  CONSTRAINT `FK_request`
    FOREIGN KEY (`FK_requestConsecutive` , `FK_requestDate`)
    REFERENCES `si_db`.`sp_budgetmodificationrequest` (`PK_consecutive` , `PK_date`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

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
  PRIMARY KEY (`PK_id`),
  INDEX `FK_mspbudgetA_idx` (`FK_budgetA` ASC) VISIBLE,
  INDEX `FK_mspbudgetB_idx` (`FK_budgetB` ASC) VISIBLE,
  INDEX `FK_mod` (`FK_modConsecutive` ASC, `FK_modDate` ASC) VISIBLE,
  CONSTRAINT `FK_mod`
    FOREIGN KEY (`FK_modConsecutive` , `FK_modDate`)
    REFERENCES `si_db`.`sp_budgetmodificationrequest` (`PK_consecutive` , `PK_date`),
  CONSTRAINT `FK_mspbudgetA`
    FOREIGN KEY (`FK_budgetA`)
    REFERENCES `si_db`.`sp_budget` (`PK_item`),
  CONSTRAINT `FK_mspbudgetB`
    FOREIGN KEY (`FK_budgetB`)
    REFERENCES `si_db`.`sp_budget` (`PK_item`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `si_db`.`SP_BudgetLog`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `si_db`.`SP_BudgetLog` (
  `PK_consecutive` INT NOT NULL AUTO_INCREMENT,
  `PK_date` DATE NOT NULL,
  `FK_budgetItem` VARCHAR(45) NOT NULL,
  `initialAmount`  DOUBLE NOT NULL,
  `finalAmount` DOUBLE NOT NULL,
  `increased`  DOUBLE NOT NULL DEFAULT 0,
  `decreased` DOUBLE NOT NULL DEFAULT 0,
  PRIMARY KEY (`PK_consecutive`, `PK_date`),
  INDEX `FK_blBudgetItem_idx` (`FK_budgetItem` ASC) VISIBLE,
  CONSTRAINT `FK_blBudgetItem`
    FOREIGN KEY (`FK_budgetItem`)
    REFERENCES `si_db`.`sp_budget` (`PK_item`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `si_db`;
DROP procedure IF EXISTS `searchBudgetByNameOrCode`;
DELIMITER $$
USE `si_db`$$
CREATE PROCEDURE `searchBudgetByNameOrCode` (parameter varchar(85))
BEGIN
  SELECT * FROM si_db.SP_Budget WHERE PK_item LIKE CONCAT('%', parameter, '%') OR description LIKE CONCAT('%', parameter, '%') ORDER BY PK_item ASC;
END$$
DELIMITER ;

USE `si_db`;
DROP procedure IF EXISTS `retrieveCurrentLocks`;
DELIMITER $$
USE `si_db`$$
CREATE PROCEDURE `retrieveCurrentLocks` ()
BEGIN
  SELECT * FROM si_db.SP_BudgetLocks WHERE endDate > CURDATE();
END$$
DELIMITER ;

USE `si_db`;
DROP procedure IF EXISTS `removeLockPrc`;

USE `si_db`;
DROP procedure IF EXISTS `si_db`.`removeLockPrc`;
;

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

USE `si_db`;
DROP TRIGGER IF EXISTS `budgetLogChangesTrigger`;
DELIMITER $$
USE `si_db`$$
CREATE TRIGGER `budgetLogChangesTrigger` AFTER UPDATE ON `si_db`.`sp_budget`
FOR EACH ROW BEGIN
	IF OLD.availableAmount - NEW.availableAmount > 0 THEN
		INSERT INTO `si_db`.`sp_budgetlog` (`PK_date`, `FK_budgetItem`, `initialAmount`, `finalAmount`, `increased`, `decreased`) VALUES (CURDATE(), NEW.PK_item, OLD.availableAmount, NEW.availableAmount, OLD.availableAmount - NEW.availableAmount, 0);
    ELSEIF OLD.availableAmount - NEW.availableAmount < 0 THEN
		INSERT INTO `si_db`.`sp_budgetlog` (`PK_date`, `FK_budgetItem`, `initialAmount`, `finalAmount`, `increased`, `decreased`) VALUES (CURDATE(), NEW.PK_item, OLD.availableAmount, NEW.availableAmount, 0, (OLD.availableAmount - NEW.availableAmount) * -1);
    END IF;
END$$
DELIMITER ;