ALTER SCHEMA `SFR`  DEFAULT COLLATE utf8_spanish2_ci ;
-- ------------------------------------------DATA FOR SI_DB--------------------------------------------------
-- ---------------------------------------------SI_DEPARTMENTS-----------------------------------------------
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('100', 'CONTROL INTERNO');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('101', 'PLANIFICACION');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('102', 'CONTABILIDAD');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('103', 'INFORMATICA');
-- ------------------------------------------------SI_ROLES--------------------------------------------------
INSERT INTO `si_db`.`SI_ROLES` (`PK_ROL`, `description`) VALUES ('1', 'ADMIN');
INSERT INTO `si_db`.`SI_ROLES` (`PK_ROL`, `description`) VALUES ('2', 'SUPER_ADMIN');
INSERT INTO `si_db`.`SI_ROLES` (`PK_ROL`, `description`) VALUES ('3', 'USER');
-- ----------------------------------------------SI_OFFICIALS------------------------------------------------
INSERT INTO `si_db`.`SI_OFFICIALS` (`PK_OFFICIAL`, `name`, `surname`, `email`, `FK_department`) VALUES ('50', 'JOSEPH', 'GRANDA', 'informatica@sanpablo.go.cr', '103');
INSERT INTO `si_db`.`SI_OFFICIALS` (`PK_OFFICIAL`, `name`, `surname`, `email`, `FK_department`) VALUES ('51', 'ISMAEL', 'SALAZAR', 'controlinterno@sanpablo.go.cr', '100');
INSERT INTO `si_db`.`SI_OFFICIALS` (`PK_OFFICIAL`, `name`, `surname`, `email`, `FK_department`) VALUES ('52', 'USUARIO PRUEBA', '1', 'usuario1@sanpablo.go.cr', '100');
INSERT INTO `si_db`.`SI_OFFICIALS` (`PK_OFFICIAL`, `name`, `surname`, `email`, `FK_department`) VALUES ('53', 'USUARIO PRUEBA', '2', 'usuario2@sanpablo.go.cr', '100');
-- -------------------------------------------------SI_USERS--------------------------------------------------
INSERT INTO `si_db`.`SI_USERS` (`PK_USER`, `FK_official`, `FK_email`, `password`) VALUES ('50', '50', 'informatica@sanpablo.go.cr', SHA2('contra1', 256));
INSERT INTO `si_db`.`SI_USERS` (`PK_USER`, `FK_official`, `FK_email`, `password`) VALUES ('51', '51', 'controlinterno@sanpablo.go.cr', SHA2('contra2', 256));
INSERT INTO `si_db`.`SI_USERS` (`PK_USER`, `FK_official`, `FK_email`, `password`) VALUES ('52', '52', 'usuario1@sanpablo.go.cr', SHA2('contra3', 256));
INSERT INTO `si_db`.`SI_USERS` (`PK_USER`, `FK_official`, `FK_email`, `password`) VALUES ('53', '53', 'usuario2@sanpablo.go.cr', SHA2('contra4', 256));
-- -----------------------------------------------SI_USER_ROLES------------------------------------------------
INSERT INTO `si_db`.`SI_USER_ROLES` (`PK_USER_ROL`,`FK_USER`,`FK_ROL`) values (1,50,2);
INSERT INTO `si_db`.`SI_USER_ROLES` (`PK_USER_ROL`,`FK_USER`,`FK_ROL`) values (2,51,1);
INSERT INTO `si_db`.`SI_USER_ROLES` (`PK_USER_ROL`,`FK_USER`,`FK_ROL`) values (3,52,3);
INSERT INTO `si_db`.`SI_USER_ROLES` (`PK_USER_ROL`,`FK_USER`,`FK_ROL`) values (4,53,3);
commit;
-- ---------------------------------------------DATA FOR SFR---------------------------------------------------
-- --------------------------------------------------T_Plan----------------------------------------------------
INSERT INTO SFR.T_Plan (PK_ID, Name, Description, EntryDate, Status, AuthorName, Type)
VALUES
('2020LA-00002-01',
'CONTRATACIÓN PARA REALIZAR TRABAJOS DE CONSTRUCCIÓN DE ACERAS, CORDON DE CAÑO, SISTEMAS DE CANALIZACIÓN PARA DESFOGUE PLUVIAL, MURO DE RETENCIÓN Y CARPETA ASFÁLTICA EN UN TRAMO DE LAS RUTA CANTONAL CONOCIDA COMO CALLE CORDERO, CODIGO DE CAMINO 409020-000 EN EL CANTÓN: "POR UN SAN PABLO ACCESIBLE E INCLUSIVO"'
,'CONTRATACIÓN PARA REALIZAR TRABAJOS DE CONSTRUCCIÓN DE ACERAS, CORDON DE CAÑO, SISTEMAS DE CANALIZACIÓN PARA DESFOGUE PLUVIAL, MURO DE RETENCIÓN Y CARPETA ASFÁLTICA EN UN TRAMO DE LAS RUTA CANTONAL CONOCIDA COMO CALLE CORDERO, CODIGO DE CAMINO 409020-000 EN EL CANTÓN: "POR UN SAN PABLO ACCESIBLE E INCLUSIVO"'
,CURDATE(), 'Activo', 'Ismael Salazar','PROYECTO'
);
INSERT INTO SFR.T_UserPlan (FK_PLAN, FK_USER) VALUES ('2020LA-00002-01', 51);
INSERT INTO SFR.T_Plan (PK_ID, Name, Description, EntryDate, Status, AuthorName, Type)
VALUES
('2020LA-00003-01',
'CONTRATACIÓN PARA REALIZAR TRABAJOS DE MOVIMIENTO DE TIERRA CON SUB-EXCAVACIÓN, CONSTRUCCIÓN DE TUBERÍA LONGITUDINAL Y TUBERÍAS TRANSVERSALES, CONSTRUCCIÓN DE TRAGANTES, POZOS DE INSPECCIÓN, COMPACTACIÓN Y CONFORMACIÓN DE SUBRASANTE, COLOCACIÓN DE SUB-BASE GRANULAR, COLOCACIÓN DE BASE GRANULAR DOSIFICADA, CONSTRUCCIÓN DE CORDÓN Y CUNETA E IMPLEMENTAR SALIDAS DE AGUAS PLUVIALES A LA RED PÚBLICA, DEMOLICIÓN Y CONSTRUCCIÓN DE ACERAS Y PERFILADO Y COLOCACIÓN DE MEZCLA ASFÁLTICA EN UN TRAMO DE LAS RUTA CANTONAL CONOCIDA COMO CALLEN RINCÓN DE RICADOR CÓDIGO 4-09-006- EN EL CANTÓN DE SAN PABLO DE HEREDIACONTRATACIÓN PARA REALIZAR TRABAJOS DE CONSTRUCCIÓN DE ACERAS, CORDON DE CAÑO, SISTEMAS DE CANALIZACIÓN PARA DESFOGUE PLUVIAL, MURO DE RETENCIÓN Y CARPETA ASFÁLTICA EN UN TRAMO DE LAS RUTA CANTONAL CONOCIDA COMO CALLE CORDERO, CODIGO DE CAMINO 409020-000 EN EL CANTÓN: "POR UN SAN PABLO ACCESIBLE E INCLUSIVO"'
,'CONTRATACIÓN PARA REALIZAR TRABAJOS DE MOVIMIENTO DE TIERRA CON SUB-EXCAVACIÓN, CONSTRUCCIÓN DE TUBERÍA LONGITUDINAL Y TUBERÍAS TRANSVERSALES, CONSTRUCCIÓN DE TRAGANTES, POZOS DE INSPECCIÓN, COMPACTACIÓN Y CONFORMACIÓN DE SUBRASANTE, COLOCACIÓN DE SUB-BASE GRANULAR, COLOCACIÓN DE BASE GRANULAR DOSIFICADA, CONSTRUCCIÓN DE CORDÓN Y CUNETA E IMPLEMENTAR SALIDAS DE AGUAS PLUVIALES A LA RED PÚBLICA, DEMOLICIÓN Y CONSTRUCCIÓN DE ACERAS Y PERFILADO Y COLOCACIÓN DE MEZCLA ASFÁLTICA EN UN TRAMO DE LAS RUTA CANTONAL CONOCIDA COMO CALLEN RINCÓN DE RICADOR CÓDIGO 4-09-006- EN EL CANTÓN DE SAN PABLO DE HEREDIACONTRATACIÓN PARA REALIZAR TRABAJOS DE CONSTRUCCIÓN DE ACERAS, CORDON DE CAÑO, SISTEMAS DE CANALIZACIÓN PARA DESFOGUE PLUVIAL, MURO DE RETENCIÓN Y CARPETA ASFÁLTICA EN UN TRAMO DE LAS RUTA CANTONAL CONOCIDA COMO CALLE CORDERO, CODIGO DE CAMINO 409020-000 EN EL CANTÓN: "POR UN SAN PABLO ACCESIBLE E INCLUSIVO"'
,CURDATE(), 'Activo', 'Ismael Salazar','PROYECTO'
);
-- -------------------------------------------------T_UserPlan---------------------------------------------------
INSERT INTO SFR.T_UserPlan (FK_PLAN, FK_USER) VALUES ('2020LA-00002-01', 51);
INSERT INTO SFR.T_UserPlan (FK_PLAN, FK_USER) VALUES ('2020LA-00003-01', 51);
-- -------------------------------------------------T_Risk---------------------------------------------------
INSERT INTO `SFR`.`T_Risk` (`PK_ID`, `Name`, `Description`, `GeneralType`, `AreaType`, `SpecType`, `Probability`, `Impact`, `AffectationLevel`, `MitigationMeasures`) VALUES ('1', 'CAMBIO DE GOBIERNO DE REPÚBLICA ', 'CADA CUATRO AÑOS SE DA UN CAMBIO DE GOBIERNO EN COSTA RICA', '1.EXTERNOS', '1.1 POLÍTICOS', '1.1.1 CAMBIOS DE GOBIERNO DE LA REPÚBLICA', '0.9', '30', '27', 'PREVENIR CAMBIOS DE LEYES CON ANTICIPACIÓN ANTE UN CAMBIO DE GOBIERNO ESTABLECIDO CADA CUATRO AÑOS.');
INSERT INTO `SFR`.`T_Risk` (`PK_ID`, `Name`, `Description`, `GeneralType`, `AreaType`, `SpecType`, `Probability`, `Impact`, `AffectationLevel`, `MitigationMeasures`) VALUES ('2', 'DESASTRES NATURALES', 'Desastres naturales como terremotos, ciclones, fuertes lluvias, etc., pueden, además de afectar a los habitantes, destruir importante infraestructura de administración municipal.\n\nExisten otros eventos de menor impacto (fuertes lluvias), que pueden afectar la infraestructura cantonal de administración municipal, por carencia o insuficiencia.\n', '1.EXTERNO', '1.5 EVENTOS DE LA NATURALEZA', '1.5.1 DESASTRES NATURALES', '0.7', '50', '35', 'TOMAR EN CUENTA EL CASO DE QUE SURJAN TORMENTAS TROPICALES QUE SE PUEDAN ANTICIPAR Y DOCUMENTAR PARA PREVENIR POSIBLES ATRASOS EN LAS AGENDAS DE LOS PROYECTOS.');
INSERT INTO `sfr`.`T_Risk` (`PK_ID`, `Name`, `Description`, `GeneralType`, `AreaType`, `SpecType`, `Probability`, `Impact`, `AffectationLevel`, `MitigationMeasures`) VALUES ('3', 'RIESGO DE PRUEBA', 'ESTA ES OTRA DESCRIPCION DE PRUEBA', 'EXTERNO', 'ECONÓMICOS', 'Cambios en las condiciones económicas del país.', '0.7', '30', '21', 'ESTA ES OTRA MEDIDA DE MITIGACION');
INSERT INTO `sfr`.`T_Risk` (`PK_ID`, `Name`, `Description`, `GeneralType`, `AreaType`, `SpecType`, `Probability`, `Impact`, `AffectationLevel`, `MitigationMeasures`) VALUES ('1', 'CAMBIO CLIMATICO', 'El cambio climático puede traer efectos en la prestación de servicios básicos.', 'EXTERNO', 'AMBIENTALES', 'Cambio climático.', '0.5', '50', '25', 'ESTA ES UNA MEDIDA DE MITIGACION');

INSERT INTO SFR.T_RiskPlan (FK_PLAN, FK_RISK) VALUES ('2020LA-00002-01', '2');
INSERT INTO SFR.T_RiskPlan (FK_PLAN, FK_RISK) VALUES ('2020LA-00003-01', '2');

commit;