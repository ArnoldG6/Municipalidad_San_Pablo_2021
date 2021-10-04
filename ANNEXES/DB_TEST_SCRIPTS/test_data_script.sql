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
