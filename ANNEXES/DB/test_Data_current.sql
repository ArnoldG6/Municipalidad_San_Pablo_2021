ALTER SCHEMA `SFR`  DEFAULT COLLATE utf8mb4_spanish2_ci ;
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
INSERT INTO SFR.T_Plan (PK_ID, ID, Name, Description, EntryDate, Status, AuthorName, Type, Subtype)
VALUES
(1, '2020LA-00002-01',
'CONTRATACIÓN PARA REALIZAR TRABAJOS DE CONSTRUCCIÓN DE ACERAS, CORDON DE CAÑO, SISTEMAS DE CANALIZACIÓN PARA DESFOGUE PLUVIAL, MURO DE RETENCIÓN Y CARPETA ASFÁLTICA'
,'CONTRATACIÓN PARA REALIZAR TRABAJOS DE CONSTRUCCIÓN DE ACERAS, CORDON DE CAÑO, SISTEMAS DE CANALIZACIÓN PARA DESFOGUE PLUVIAL, MURO DE RETENCIÓN Y CARPETA ASFÁLTICA EN UN TRAMO DE LAS RUTA CANTONAL CONOCIDA COMO CALLE CORDERO, CODIGO DE CAMINO 409020-000 EN EL CANTÓN: "POR UN SAN PABLO ACCESIBLE E INCLUSIVO"'
,NOW(), 'Activo', 'Ismael Salazar','Construir, Adquirir e Implementar', 'Gestionar los proyectos'
);
INSERT INTO SFR.T_Plan (PK_ID, ID, Name, Description, EntryDate, Status, AuthorName, Type, Subtype)
VALUES
(2, '2020LA-00003-01', 
'CONTRATACIÓN PARA REALIZAR TRABAJOS DE MOVIMIENTO DE TIERRA CON SUB-EXCAVACIÓN, CONSTRUCCIÓN DE TUBERÍA LONGITUDINAL Y TUBERÍAS TRANSVERSALES, CONSTRUCCIÓN DE TRAGANTES, POZOS DE INSPECCIÓN, COMPACTACIÓN Y CONFORMACIÓN DE SUBRASANTE, COLOCACIÓN DE SUB-BASE GRANULAR, COLOCACIÓN DE BASE GRANULAR DOSIFICADA, CONSTRUCCIÓN DE CORDÓN Y CUNETA E IMPLEMENTAR SALIDAS DE AGUAS PLUVIALES A LA RED PÚBLICA, DEMOLICIÓN Y CONSTRUCCIÓN DE ACERAS Y PERFILADO Y COLOCACIÓN DE MEZCLA ASFÁLTICA EN UN TRAMO DE LAS RUTA CANTONAL CONOCIDA COMO CALLEN RINCÓN DE RICADOR CÓDIGO 4-09-006- EN EL CANTÓN DE SAN PABLO DE HEREDIA"'
,'CONTRATACIÓN PARA REALIZAR TRABAJOS DE MOVIMIENTO DE TIERRA CON SUB-EXCAVACIÓN, CONSTRUCCIÓN DE TUBERÍA LONGITUDINAL Y TUBERÍAS TRANSVERSALES, CONSTRUCCIÓN DE TRAGANTES, POZOS DE INSPECCIÓN, COMPACTACIÓN Y CONFORMACIÓN DE SUBRASANTE, COLOCACIÓN DE SUB-BASE GRANULAR, COLOCACIÓN DE BASE GRANULAR DOSIFICADA, CONSTRUCCIÓN DE CORDÓN Y CUNETA E IMPLEMENTAR SALIDAS DE AGUAS PLUVIALES A LA RED PÚBLICA, DEMOLICIÓN Y CONSTRUCCIÓN DE ACERAS Y PERFILADO Y COLOCACIÓN DE MEZCLA ASFÁLTICA EN UN TRAMO DE LAS RUTA CANTONAL CONOCIDA COMO CALLEN RINCÓN DE RICADOR CÓDIGO 4-09-006- EN EL CANTÓN DE SAN PABLO DE HEREDIA"'
,NOW(), 'Activo', 'Ismael Salazar','Construir, Adquirir e Implementar', 'Gestionar los proyectos'
);
-- -------------------------------------------------T_UserPlan---------------------------------------------------
-- INSERT INTO SFR.T_UserPlan (FK_PLAN, FK_USER) VALUES ('2020LA-00002-01', 51);
-- INSERT INTO SFR.T_UserPlan (FK_PLAN, FK_USER) VALUES ('2020LA-00003-01', 51);
-- -------------------------------------------------T_Risk---------------------------------------------------
INSERT INTO `SFR`.`T_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `SpecType`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`)
VALUES (
1 , 
'1',
'CAMBIO DE GOBIERNO DE REPÚBLICA ', 
'CADA CUATRO AÑOS SE DA UN CAMBIO DE GOBIERNO EN COSTA RICA', 
'EXTERNO', 
'Político', 
'CAMBIOS DE GOBIERNO DE LA REPÚBLICA', 
'0.9', 
'30', 
'27', 
'PREVENIR CAMBIOS DE LEYES CON ANTICIPACIÓN ANTE UN CAMBIO DE GOBIERNO ESTABLECIDO CADA CUATRO AÑOS.'
);

INSERT INTO `SFR`.`T_Risk` (`PK_ID`, `ID`,`Name`, `Factors`, `GeneralType`, `AreaType`, `SpecType`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`)
VALUES (
2 , 
'2',
'DESASTRES NATURALES', 
'Desastres naturales como terremotos, ciclones, fuertes lluvias, etc., pueden, además de afectar a los habitantes, destruir importante infraestructura de administración municipal.\n\nExisten otros eventos de menor impacto (fuertes lluvias), que pueden afectar la infraestructura cantonal de administración municipal, por carencia o insuficiencia.\n', 
'EXTERNO', 
'Eventos naturales', 
'DESASTRES NATURALES', 
'0.7', 
'50', 
'35', 
'TOMAR EN CUENTA EL CASO DE QUE SURJAN TORMENTAS TROPICALES QUE SE PUEDAN ANTICIPAR Y DOCUMENTAR PARA PREVENIR POSIBLES ATRASOS EN LAS AGENDAS DE LOS PROYECTOS.'
);

INSERT INTO `SFR`.`T_Risk` (`PK_ID`, `ID`,`Name`, `Factors`, `GeneralType`, `AreaType`, `SpecType`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`)
VALUES (
3 , 
'3', 
'RIESGO DE PRUEBA', 
'ESTA ES OTRA DESCRIPCION DE PRUEBA', 
'EXTERNO', 
'Económico', 
'Cambios en las condiciones económicas del país.', 
'0.7', 
'30', 
'21', 
'ESTA ES OTRA MEDIDA DE MITIGACION'
);

INSERT INTO `SFR`.`T_Risk` (`PK_ID`, `ID`,`Name`, `Factors`, `GeneralType`, `AreaType`, `SpecType`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`)
VALUES (
4 ,
'4', 
'CAMBIO CLIMATICO', 
'El cambio climático puede traer efectos en la prestación de servicios básicos.', 
'EXTERNO', 
'Ambiental', 
'Cambio climático.', 
'0.5', 
'50', 
'25', 
'ESTA ES UNA MEDIDA DE MITIGACION'
);
-- -------------------------------------------------T_RiskPlan---------------------------------------------------
INSERT INTO SFR.T_RiskPlan (FK_PLAN, FK_RISK) VALUES ( 1, 1);
INSERT INTO SFR.T_RiskPlan (FK_PLAN, FK_RISK) VALUES ( 2 , 2);
commit;

-- -------------------------------------------------T_Plan_Types---------------------------------------------------
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (null,"Evaluar, Dirigir y Monitorear",null,0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (null,"Alinear, Planificar y Organizar",null,0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (null,"Construir, Adquirir e Implementar",null,0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (null,"Entregar, Dar servicio y soporte",null,0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (null,"Monitorear, Evaluar y Valorar",null,0);

INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (1,"Asegurar el establecimiento y el mantenimiento del marco de Gobierno","EDM01",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (1,"Asegurar la obtención de beneficios","EDM02",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (1,"Asegurar la optimización del riesgo","EDM03",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (1,"Asegurar la optimización de los recursos","EDM04",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (1,"Asegurar el compromiso de las partes interesadas","EDM05",0);

INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar el marco de gestión de I&T","APO01",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar la estrategia","APO02",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar la arquitectura empresarial","APO03",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar la innovación","APO04",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar el portafolio","APO05",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar el presupuesto y los costos","APO06",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar los recursos humanos","APO07",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar las relaciones","APO08",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar los acuerdos de servicio","APO09",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar los proveedores","APO10",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar la calidad","APO11",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar el riesgo","APO12",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar la seguridad","APO13",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar los datos","APO14",0);

INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestionar los programas","BAI01",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestionar la definición de requisitos","BAI02",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestionar la identificación y construcción de soluciones","BAI03",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestionar la disponibilidad y capacidad","BAI04",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestionar el cambio organizativo","BAI05",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestionar los cambios de TI","BAI06",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestionar la aceptación y la transición de los cambios de TI","BAI07",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestionar el conocimiento","BAI08",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestionar los activos","BAI09",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestionar la configuración","BAI10",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestionar los proyectos","BAI11",0);

INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (4,"Gestionar las operaciones","DSS01",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (4,"Gestionar las peticiones y los incidentes de servicio","DSS02",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (4,"Gestionar los problemas","DSS03",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (4,"Gestionar la continuidad","DSS04",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (4,"Gestionar los servicios de seguridad","DSS05",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (4,"Gestionar los controles de procesos de negocio","DSS06",0);

INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (5,"Gestionar el monitoreo del desempeño y la conformidad","MEA01",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (5,"Gestionar el sistema de control interno","MEA02",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (5,"Gestionar el cumplimiento delos requerimientos externos","MEA03",0);
INSERT INTO `SFR`.`T_Plan_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (5,"Gestionar el aseguramiento","MEA04",0);

-- -------------------------------------------------T_Risk_Types---------------------------------------------------
INSERT INTO `SFR`.`T_Risk_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (null,"Externo",null,0);
INSERT INTO `SFR`.`T_Risk_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (null,"Interno",null,0);
INSERT INTO `SFR`.`T_Risk_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (null,"TIC",null,0);

INSERT INTO `SFR`.`T_Risk_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (1,"Político","REP",0);
INSERT INTO `SFR`.`T_Risk_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (1,"Legal","REL",0);
INSERT INTO `SFR`.`T_Risk_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (1,"Económico","REE",0);
INSERT INTO `SFR`.`T_Risk_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (1,"Eventos Naturales","REN",0);
INSERT INTO `SFR`.`T_Risk_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (1,"Ambiental","REA",0);

INSERT INTO `SFR`.`T_Risk_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Estratégicos","RIE",0);
INSERT INTO `SFR`.`T_Risk_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Financieros","RIF",0);
INSERT INTO `SFR`.`T_Risk_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Desarrollo de los procesos","RID",0);
INSERT INTO `SFR`.`T_Risk_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Tecnológicos y de información","RIT",0);
INSERT INTO `SFR`.`T_Risk_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestión de procesos sustantivos","RIP",0);
INSERT INTO `SFR`.`T_Risk_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Funcionario municipal","RIM",0);

INSERT INTO `SFR`.`T_Risk_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestión de la información","RG",0);
INSERT INTO `SFR`.`T_Risk_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestión de la continuidad","RC",0);
INSERT INTO `SFR`.`T_Risk_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestión de las comunicaciones","RA",0);
INSERT INTO `SFR`.`T_Risk_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Centros de datos","RD",0);
INSERT INTO `SFR`.`T_Risk_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestión de proveedores","RP",0);
INSERT INTO `SFR`.`T_Risk_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Cumplimiento","RI",0);
INSERT INTO `SFR`.`T_Risk_Types` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Seguridad de la información","RS",0);
