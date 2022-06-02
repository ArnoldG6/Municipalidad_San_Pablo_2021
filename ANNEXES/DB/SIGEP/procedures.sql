
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