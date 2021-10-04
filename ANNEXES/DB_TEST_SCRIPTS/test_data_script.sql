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
SELECT u.PK_USER, u.FK_OFFICIAL, u.FK_EMAIL, cast(AES_DECRYPT(UNHEX(u.password),'key') AS char) from si_db.si_users u; 
-- INSERT INTO `si_db`.`si_users` (`PK_USER`, `FK_official`, `FK_email`, `password`) VALUES ('50', '50', 'informatica@sanpablo.go.cr', ?);
-- INSERT INTO `si_db`.`si_users` (`PK_USER`, `FK_official`, `FK_email`, `password`) VALUES ('51', '51', 'controlinterno@sanpablo.go.cr', ?);
-- SELECT HEX(AES_ENCRYPT('Informacion', 'passwordSegura'));
-- SELECT AES_DECRYPT(UNHEX('B18464A9F19465BFA2E59F2476E7C095'),'passwordSegura');
-- SELECT cast(AES_DECRYPT(UNHEX('4062734278A314418B6AC1900551F2D7'),'passwordSegura') AS char);
-- FUNCTION TO GET THE PWD IN VARCHAR 
-- DROP FUNCTION DESENCRYPT_PWD;
-- DELIMITER $$
-- CREATE FUNCTION DESENCRYPT_PWD(P_PK_USER INTEGER) RETURNS VARCHAR(100)
-- READS SQL DATA
-- BEGIN
	-- DECLARE pwd VARCHAR(100);
	-- SELECT IFNULL(password,'') INTO pwd
    -- FROM si_db.si_users
    -- WHERE si_db.si_users.PK_USER = P_PK_USER;
	-- return pwd;
-- END$$
-- DELIMITER ;
-- SELECT DESENCRYPT_PWD(50);
-- SELECT u.PK_USER, u.FK_OFFICIAL, u.FK_EMAIL, CAST(u.password as CHAR) from si_db.si_users u;

UPDATE `si_db`.`si_users` SET `password` = SHA2('contra1', 256) WHERE (`PK_USER` = '50');
UPDATE `si_db`.`si_users` SET `password` = SHA2('contra2', 256) WHERE (`PK_USER` = '51');