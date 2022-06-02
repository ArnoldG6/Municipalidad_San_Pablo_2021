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
