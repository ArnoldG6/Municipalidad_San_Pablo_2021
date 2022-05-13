ALTER SCHEMA `si_db`  DEFAULT COLLATE utf8mb4_spanish2_ci ;
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
INSERT INTO `si_db`.`SI_OFFICIALS` (`PK_OFFICIAL`, `name`, `surname`, `email`, `FK_department`) VALUES ('123', 'Luis Diego', 'Ramirez', 'ld.ramirezch14@gmail.com', '103');
INSERT INTO `si_db`.`SI_OFFICIALS` (`PK_OFFICIAL`, `name`, `surname`, `email`, `FK_department`) VALUES ('50', 'JOSEPH', 'GRANDA', 'informatica@sanpablo.go.cr', '103');
INSERT INTO `si_db`.`SI_OFFICIALS` (`PK_OFFICIAL`, `name`, `surname`, `email`, `FK_department`) VALUES ('51', 'ISMAEL', 'SALAZAR', 'controlinterno@sanpablo.go.cr', '100');
INSERT INTO `si_db`.`SI_OFFICIALS` (`PK_OFFICIAL`, `name`, `surname`, `email`, `FK_department`) VALUES ('52', 'USUARIO PRUEBA', '1', 'usuario1@sanpablo.go.cr', '100');
INSERT INTO `si_db`.`SI_OFFICIALS` (`PK_OFFICIAL`, `name`, `surname`, `email`, `FK_department`) VALUES ('53', 'USUARIO PRUEBA', '2', 'usuario2@sanpablo.go.cr', '100');
-- -------------------------------------------------SI_USERS--------------------------------------------------
INSERT INTO `si_db`.`SI_USERS` (`PK_USER`, `FK_official`, `FK_email`, `password`) VALUES ('123', '123', 'ld.ramirezch14@gmail.com', SHA2('123', 256));
INSERT INTO `si_db`.`SI_USERS` (`PK_USER`, `FK_official`, `FK_email`, `password`) VALUES ('50', '50', 'informatica@sanpablo.go.cr', SHA2('contra1', 256));
INSERT INTO `si_db`.`SI_USERS` (`PK_USER`, `FK_official`, `FK_email`, `password`) VALUES ('51', '51', 'controlinterno@sanpablo.go.cr', SHA2('contra2', 256));
INSERT INTO `si_db`.`SI_USERS` (`PK_USER`, `FK_official`, `FK_email`, `password`) VALUES ('52', '52', 'usuario1@sanpablo.go.cr', SHA2('contra3', 256));
INSERT INTO `si_db`.`SI_USERS` (`PK_USER`, `FK_official`, `FK_email`, `password`) VALUES ('53', '53', 'usuario2@sanpablo.go.cr', SHA2('contra4', 256));
-- -----------------------------------------------SI_USER_ROLES------------------------------------------------
INSERT INTO `si_db`.`SI_USER_ROLES` (`PK_USER_ROL`,`FK_USER`,`FK_ROL`) values (1,123,2);
INSERT INTO `si_db`.`SI_USER_ROLES` (`PK_USER_ROL`,`FK_USER`,`FK_ROL`) values (2,50,2);
INSERT INTO `si_db`.`SI_USER_ROLES` (`PK_USER_ROL`,`FK_USER`,`FK_ROL`) values (3,51,1);
INSERT INTO `si_db`.`SI_USER_ROLES` (`PK_USER_ROL`,`FK_USER`,`FK_ROL`) values (4,52,3);
INSERT INTO `si_db`.`SI_USER_ROLES` (`PK_USER_ROL`,`FK_USER`,`FK_ROL`) values (5,53,3);
commit;
-- ---------------------------------------------DATA FOR si_db---------------------------------------------------
-- --------------------------------------------------T_Plan----------------------------------------------------
INSERT INTO si_db.T_SFR_Plan (PK_ID, ID, Name, Description, EntryDate, Status, AuthorName, Type, Subtype)
VALUES
(1, '2020LA-00002-01',
'CONTRATACIÓN PARA REALIZAR TRABAJOS DE CONSTRUCCIÓN DE ACERAS, CORDON DE CAÑO, SISTEMAS DE CANALIZACIÓN PARA DESFOGUE PLUVIAL, MURO DE RETENCIÓN Y CARPETA ASFÁLTICA'
,'CONTRATACIÓN PARA REALIZAR TRABAJOS DE CONSTRUCCIÓN DE ACERAS, CORDON DE CAÑO, SISTEMAS DE CANALIZACIÓN PARA DESFOGUE PLUVIAL, MURO DE RETENCIÓN Y CARPETA ASFÁLTICA EN UN TRAMO DE LAS RUTA CANTONAL CONOCIDA COMO CALLE CORDERO, CODIGO DE CAMINO 409020-000 EN EL CANTÓN: "POR UN SAN PABLO ACCESIBLE E INCLUSIVO"'
,NOW(), 'Activo', 'Ismael Salazar','Construir, Adquirir e Implementar', 'Gestionar los proyectos'
);
INSERT INTO si_db.T_SFR_Plan (PK_ID, ID, Name, Description, EntryDate, Status, AuthorName, Type, Subtype)
VALUES
(2, '2020LA-00003-01', 
'CONTRATACIÓN PARA REALIZAR TRABAJOS DE MOVIMIENTO DE TIERRA CON SUB-EXCAVACIÓN'
,'CONTRATACIÓN PARA REALIZAR TRABAJOS DE MOVIMIENTO DE TIERRA CON SUB-EXCAVACIÓN, CONSTRUCCIÓN DE TUBERÍA LONGITUDINAL Y TUBERÍAS TRANSVERSALES, CONSTRUCCIÓN DE TRAGANTES, POZOS DE INSPECCIÓN, COMPACTACIÓN Y CONFORMACIÓN DE SUBRASANTE, COLOCACIÓN DE SUB-BASE GRANULAR, COLOCACIÓN DE BASE GRANULAR DOSIFICADA, CONSTRUCCIÓN DE CORDÓN Y CUNETA E IMPLEMENTAR SALIDAS DE AGUAS PLUVIALES A LA RED PÚBLICA, DEMOLICIÓN Y CONSTRUCCIÓN DE ACERAS Y PERFILADO Y COLOCACIÓN DE MEZCLA ASFÁLTICA EN UN TRAMO DE LAS RUTA CANTONAL CONOCIDA COMO CALLEN RINCÓN DE RICADOR CÓDIGO 4-09-006- EN EL CANTÓN DE SAN PABLO DE HEREDIA"'
,NOW(), 'Activo', 'Ismael Salazar','Construir, Adquirir e Implementar', 'Gestionar los proyectos'
);
-- -------------------------------------------------T_SFR_PlanUser---------------------------------------------------
INSERT INTO si_db.T_SFR_PlanUser (FK_PLAN, FK_USER) VALUES (1, 51);
INSERT INTO si_db.T_SFR_PlanUser (FK_PLAN, FK_USER) VALUES (2, 51);
INSERT INTO si_db.T_SFR_PlanUser (FK_PLAN, FK_USER) VALUES (1, 52);

-- -------------------------------------------------T_SFR_PlanTypes---------------------------------------------------
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (null,"Evaluar, Dirigir y Monitorear",null,0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (null,"Alinear, Planificar y Organizar",null,0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (null,"Construir, Adquirir e Implementar",null,0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (null,"Entregar, Dar servicio y soporte",null,0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (null,"Monitorear, Evaluar y Valorar",null,0);

INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (1,"Asegurar el establecimiento y el mantenimiento del marco de Gobierno","EDM01",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (1,"Asegurar la obtención de beneficios","EDM02",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (1,"Asegurar la optimización del riesgo","EDM03",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (1,"Asegurar la optimización de los recursos","EDM04",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (1,"Asegurar el compromiso de las partes interesadas","EDM05",0);

INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar el marco de gestión de I&T","APO01",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar la estrategia","APO02",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar la arquitectura empresarial","APO03",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar la innovación","APO04",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar el portafolio","APO05",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar el presupuesto y los costos","APO06",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar los recursos humanos","APO07",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar las relaciones","APO08",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar los acuerdos de servicio","APO09",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar los proveedores","APO10",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar la calidad","APO11",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar el riesgo","APO12",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar la seguridad","APO13",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (2,"Gestionar los datos","APO14",0);

INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestionar los programas","BAI01",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestionar la definición de requisitos","BAI02",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestionar la identificación y construcción de soluciones","BAI03",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestionar la disponibilidad y capacidad","BAI04",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestionar el cambio organizativo","BAI05",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestionar los cambios de TI","BAI06",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestionar la aceptación y la transición de los cambios de TI","BAI07",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestionar el conocimiento","BAI08",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestionar los activos","BAI09",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestionar la configuración","BAI10",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (3,"Gestionar los proyectos","BAI11",0);

INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (4,"Gestionar las operaciones","DSS01",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (4,"Gestionar las peticiones y los incidentes de servicio","DSS02",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (4,"Gestionar los problemas","DSS03",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (4,"Gestionar la continuidad","DSS04",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (4,"Gestionar los servicios de seguridad","DSS05",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (4,"Gestionar los controles de procesos de negocio","DSS06",0);

INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (5,"Gestionar el monitoreo del desempeño y la conformidad","MEA01",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (5,"Gestionar el sistema de control interno","MEA02",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (5,"Gestionar el cumplimiento delos requerimientos externos","MEA03",0);
INSERT INTO `si_db`.`T_SFR_PlanTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`) values (5,"Gestionar el aseguramiento","MEA04",0);

-- -------------------------------------------------T_SFR_RiskTypes---------------------------------------------------
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (null,"Externo",null,0,null);
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (null,"Interno",null,0,null);

INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (1,"Político","REP",0,null);
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (1,"Legal","REL",0,null);
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (1,"Económico","REE",0,null);
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (1,"Tecnologías de la información","RET",0,null);
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (1,"Eventos Naturales","REN",0,null);
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (1,"Ambiental","REA",0,null);

INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (2,"Estratégicos","RIE",0,null);
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (2,"Financieros","RIF",0,null);
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (2,"Desarrollo de los procesos","RID",0,null);
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (2,"Tecnológicos y de información","RIT",0,null);
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (2,"Gestión de procesos sustantivos","RIP",0,null);
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (2,"Funcionario municipal","RIM",0,null);

INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (3,"Cambios de gobierno de la República","CGR",0,"Repercute en la forma de gestionar la corporación municipal, puede alterar la continuidad de las actividades y proyectos que se desarrollan.");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (3,"Cambio de Concejo Municipal","CCM",0,"Repercute en la forma de gestionar la corporación municipal, puede alterar la continuidad de las actividades y proyectos que se desarrollan.");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (3,"Cambio de Alcaldía","CA",0,"Repercute en la forma de gestionar la corporación municipal, puede alterar la continuidad de las actividades y proyectos que se desarrollan.");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (3,"Cambio de políticas públicas","CPP",0,"Repercute en la forma de gestionar la corporación municipal, puede alterar la continuidad de las actividades y proyectos que se desarrollan.");

INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (4,"Cambios en la normativa de alcance general","CNG",0,"Cambios en las normas generales, pueden eliminar o cambiar la continuidad de las actividades y proyectos que desarrollan las municipalidades.");

INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (5,"Cambios en las condiciones económicas del país","CEP",0,"Cambios económicos repercuten en la disponibilidad de fondos de las familias y su capacidad de pago de impuestos y tasas municipales.");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (5,"Cambios en las condiciones económicas regionales y cantonales","CER",0,"Cambios económicos repercuten en la disponibilidad de fondos de las familias y su capacidad de pago de impuestos y tasas municipales.");

INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (6,"Innovación de equipos tecnológicos para el procesamiento de la información","IET",0,"Obsolescencia de equipos y sistemas de información.
Problemas de conectividad con los munícipes y de desarrollo de un eficiente gobierno municipal.");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (6,"Desarrollo de nuevos sistemas de información","DSI",0,"Obsolescencia de equipos y sistemas de información.
Problemas de conectividad con los munícipes y de desarrollo de un eficiente gobierno municipal.");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (6,"Telecomunicaciones y conectividad","TMC",0,"Obsolescencia de equipos y sistemas de información.
Problemas de conectividad con los munícipes y de desarrollo de un eficiente gobierno municipal.");

INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (7,"Desastres naturales","DSN",0,"Desastres naturales como terremotos, ciclones, fuertes lluvias, etc., pueden, además de afectar a los habitantes, destruir importante infraestructura de administración municipal.

Existen otros eventos de menor impacto (fuertes lluvias), que pueden afectar la infraestructura cantonal de administración municipal, por carencia o insuficiencia.");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (7,"Eventos de la naturaleza que producen efectos en la población, por carencia de infraestructura","ECI",0,"Desastres naturales como terremotos, ciclones, fuertes lluvias, etc., pueden, además de afectar a los habitantes, destruir importante infraestructura de administración municipal.
Existen otros eventos de menor impacto (fuertes lluvias), que pueden afectar la infraestructura cantonal de administración municipal, por carencia o insuficiencia.");

INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (8,"Cambio climático","CCT",0,"El cambio climático puede traer efectos en la prestación de servicios básicos.");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (8,"Contaminación por manejo de desechos sólidos, gaseosos y líquidos","CMD",0,"La contaminación por desechos puede afectar a grupos de familias o sectores, lo que obliga a la intervención de la municipalidad con la consecuente aplicación de recursos.");

INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (9,"Planificación Estratégica de la Municipalidad","PEM",0,"Eventos, que no permitan que la planificación estratégica institucional, se fundamente en una adecuada identificación del entorno del cantón o que tenga una adecuada vinculación con los planes operativos.");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (9,"Estructura organizativa de la Municipalidad","EOM",0,"Eventos que no permiten que la estructura administrativa en su conformación y funcionamiento logre el alcance eficiente de los objetivos.");

INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (10,"Presupuesto","PST",0,"No asociar adecuadamente los objetivos y metas del PAO, con las disponibilidades presupuestarias; de administración del presupuesto (alcance de metas); de no lograr las metas propuestas de ingresos y gastos.");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (10,"Flujo de caja","FCJ",0,"Las disponibilidades de efectivo afectan el desarrollo de las actividades o proyectos.");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (10,"Morosidad","MRS",0,"Cambios en la morosidad afectan la disponibilidad de fondos, requeridos para la operación y desarrollo de actividades y proyectos.");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (10,"Costo prestación servicios","CPS",0,"Que el ingreso por servicios no cubra los costos (déficit en prestación).");

INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (11,"Deficiencia en la ejecución de los procesos, subprocesos y actividades municipales","DEP",0,"Deficiencias que se presenten en procesos, particulares, afectan el alcance de los objetivos y metas institucionales, por la interrelación de los procesos en el alcance de los resultados.");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (11,"Articulación entre los procesos","AEP",0,"Falta de articulación entre los procesos afectan el alcance de objetivos y metas.");

INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (12,"Gestión de la información","RG",15,"");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (12,"Gestión de la continuidad","RC",26,"");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (12,"Gestión de las comunicaciones","RA",7,"");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (12,"Centros de datos","RD",36,"");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (12,"Gestión de proveedores","RP",10,"");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (12,"Cumplimiento","RI",9,"");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (12,"Seguridad de la información","RS",15,"");

INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (13,"Gestión de la contratación administrativa","GCA",0,"La contratación administrativa, repercute en toda la gestión municipal. El incumplimiento de la normativa que la rige implica responsabilidades administrativas, civiles y penales.");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (13,"Gestión tributaria","GTB",0,"Los ingresos de la Municipalidad están directamente asociados a los impuestos y tasas que se cobran.");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (13,"Proceso financiero - Presupuesto","PFP",0,"La adecuada asignación de los fondos, elaboración de documentos presupuestos, es fundamental para el alcance de los objetivos y metas. El incumplimiento de la normativa que la rige implica responsabilidades administrativas, civiles y penales.");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (13,"Proceso financiero – Tesorería","PFT",0,"La tesorería debe mantener rigurosos procedimientos que garanticen la recepción y gestión de los flujos de efectivo sin que se presenten pérdidas.");
INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (13,"Proceso obras y servicios públicos","PSP",0,"Los proyectos de obra pública deben gestionarse para concluirse en tiempo, dentro los costos, calidad y alcance.");

INSERT INTO `si_db`.`T_SFR_RiskTypes` (`PARENT`,`NAME`,`ID_NAME`,`ID_AMOUNT`,`CONSEQUENCE`) values (14,"Integridad","ITG",0,"Actos no autorizados, ilegales, fraudulentos, inoportunos, que produzcan pérdidas y afectan la gestión municipal.");


-- RIESGOS
-- -------------------------------------------------T_SFR_Risk---------------------------------------------------
INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`)
VALUES (
1 , 
'1',
'CAMBIO DE GOBIERNO DE REPÚBLICA ', 
'CADA CUATRO AÑOS SE DA UN CAMBIO DE GOBIERNO EN COSTA RICA', 
'Externo', 
'Político',
'Cambios de gobierno de la República',
'CAMBIOS DE GOBIERNO DE LA REPÚBLICA', 
'0.9', 
'30', 
'27', 
'PREVENIR CAMBIOS DE LEYES CON ANTICIPACIÓN ANTE UN CAMBIO DE GOBIERNO ESTABLECIDO CADA CUATRO AÑOS.',
50,
"Repercute en la forma de gestionar la corporación municipal, puede alterar la continuidad de las actividades y proyectos que se desarrollan."
);

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`, `ID`,`Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`)
VALUES (
2 , 
'2',
'DESASTRES NATURALES', 
'Desastres naturales como terremotos, ciclones, fuertes lluvias, etc., pueden, además de afectar a los habitantes, destruir importante infraestructura de administración municipal.\n\nExisten otros eventos de menor impacto (fuertes lluvias), que pueden afectar la infraestructura cantonal de administración municipal, por carencia o insuficiencia.\n', 
'Externo', 
'Eventos naturales',
'Desastres naturales',
'DESASTRES NATURALES', 
'0.7', 
'50', 
'35', 
'TOMAR EN CUENTA EL CASO DE QUE SURJAN TORMENTAS TROPICALES QUE SE PUEDAN ANTICIPAR Y DOCUMENTAR PARA PREVENIR POSIBLES ATRASOS EN LAS AGENDAS DE LOS PROYECTOS.',
50,
"Desastres naturales como terremotos, ciclones, fuertes lluvias, etc., pueden, además de afectar a los habitantes, destruir importante infraestructura de administración municipal."
);

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`, `ID`,`Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`)
VALUES (
3 , 
'3', 
'RIESGO DE PRUEBA', 
'ESTA ES OTRA DESCRIPCION DE PRUEBA', 
'Externo', 
'Económico',
'Cambios en las condiciones económicas del país',
'Cambios en las condiciones económicas del país.', 
'0.7', 
'30', 
'21', 
'ESTA ES OTRA MEDIDA DE MITIGACION',
50,
"Cambios económicos repercuten en la disponibilidad de fondos de las familias y su capacidad de pago de impuestos y tasas municipales."
);

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`, `ID`,`Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`)
VALUES (
4 ,
'4', 
'CAMBIO CLIMATICO', 
'El cambio climático puede traer efectos en la prestación de servicios básicos.', 
'Externo', 
'Ambiental', 
'Cambio climático',
'Cambio climático.', 
'0.5', 
'50', 
'25', 
'ESTA ES UNA MEDIDA DE MITIGACION',
50,
"El cambio climático puede traer efectos en la prestación de servicios básicos."
);

-- -------------------------------------------------RIESGO EN LA GESTION DE LA INFORMACION---------------------------------------------------

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`)
VALUES (5, 'RIT-RG01','Abuso de derechos por parte de los usuarios del sistema', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la información', 'N/A', '0.9', '30', '27', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (6 ,'RIT-RG02','Acceso no autorizado a aplicaciones por parte de los usuarios', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la información', 'N/A', '0.9', '30', '27', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (7, 'RIT-RG03','Conformación inadecuada de contraseñas (insegura, débiles)', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (8, 'RIT-RG04','Uso compartido de contraseñas por parte de los usuarios', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (9, 'RIT-RG05','Robo o perdida de información por controles inadeacuados', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (10, 'RIT-RG06','Capacitación inadecuada a los usuarios del sistema en la forma de administrar los recursoa asignados', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (11, 'RIT-RG07','Confidencialidad de la información comprometida', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (12, 'RIT-RG08','Privacidad de la información comprometida', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (13, 'RIT-RG09','Problemas en el acceso a las aplicaciones', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (14, 'RIT-RG10','Integridad de la Información comprometida por usuarios internos', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (15, 'RIT-RG11','Integridad de la Información comprometida por accesos externos no autorizados', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (16, 'RIT-RG12','No hay disponibilidad de la información', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (17, 'RIT-RG13','Clasificación inadecuada de la información', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (18, 'RIT-RG14','Etiquetado onadecuado de la in formación', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (19, 'RIT-RG15','Robo o perdida de información por ataques de Hackers, Malware', 'SIN INFORMACION' , 'Interno', 'Tecnológicos y de información', 'Gestión de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");


-- -------------------------------------------------RIESGOS EN LA GESTION DE LA CONTINUIDAD---------------------------------------------------

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (20, 'RIT-RC01','Mala identificación de los respaldos de información', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (21, 'RIT-RC02','Respaldos de información no verificados', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (22, 'RIT-RC03','Respaldos de información almacenados en forma incorrecta', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (23, 'RIT-RC04','Inadecuado traslado y custodia de los respaldos', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (24, 'RIT-RC05','Técnicas de recuperación/restauración de los archivos no estandarizada', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (25, 'RIT-RC06','Errores en el respaldo y recuperación de los datos', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (26, 'RIT-RC07','Interrupción del servicio por falta de capacidad de almacenamiento o por fallas en los dispositivos de almacenamiento', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (27, 'RIT-RC08','Plan de continuidad o contingencia no documentado', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (28, 'RIT-RC09','Plan de continuidad o contingencia incompleto', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (29, 'RIT-RC10','Plan de continuidad o contingencia no probado', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (30, 'RIT-RC11','Plan de continuidad o contingencia no aprobado por las altas autoridades', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (31, 'RIT-RC12','Plan de continuidad o contingencia desactualizado', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (32, 'RIT-RC13','Personal interno poco preparado para enfrentar una contingencia', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (33, 'RIT-RC14','No se cuenta con suficiente personal para enfrentar una contingencia', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (34, 'RIT-RC15','Plan de continuidad o contingencia no comunicado a las partes interesadas', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (35, 'RIT-RC16','Robo o pérdida  de medios de almacenamiento', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (36, 'RIT-RC17','Desastres naturales (terremotos, inundaciones, tornados, huracanes, etc.)', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (37, 'RIT-RC18','Incendio', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (38, 'RIT-RC19','Epidemia', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (39, 'RIT-RC20','Electromanetismo', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (40, 'RIT-RC21','Técnicas de recuperación/restauración de los archivos no estandarizada', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (41, 'RIT-RC22','Desorden civil', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (42, 'RIT-RC23','Acciones emprendidas por empleados inescrupulosos que pueden causar daños tanto a las instalaciones', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (43, 'RIT-RC24','Interrupciones prolongadas de los servicios básicos como la electricidad, el agua potable y las comunicaciones', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (44, 'RIT-RC25','Actos criminales, como vandalismo, terrorismo, etc', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (45, 'RIT-RC26','Robo o pérdida  de medios de almacenamiento', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de la continuidad', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");


-- -------------------------------------------------RIESGOS EN LA GESTION DE LAS  COMUNICACIONES---------------------------------------------------

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (46, 'RIT-RA01','Fallas en la infraestructura tecnológica de los proveedores externos (ICE. RACSA, CNFL) que soporta la prestación de servicios, afectando la disponibilidad', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de las comunicaciones', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (47, 'RIT-RA02','Fallas en las comunicaciones debido problemas internos', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de las comunicaciones', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (48, 'RIT-RA3','Fallas por eventos que afecten las lineas de transmision internas o externas', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de las comunicaciones', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (49, 'RIT-RA04','Falta de disponibilidad en las lineas de comunicaciones', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de las comunicaciones', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (50, 'RIT-RA05','Fallas producidas por errores o problemas en la transmisión', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de las comunicaciones', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (51, 'RIT-RA06','Errores en la configuración de equipos de comunicaciones', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de las comunicaciones', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (52, 'RIT-RA07','Monitoreo inadecuado de las comunicaciones', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de las comunicaciones', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");


-- -------------------------------------------------RIESGOS EN CENTROS DE DATOS---------------------------------------------------

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (53, 'RIT-RD01','Falta de disponibilidad del personal técnico (SO, base de datos, comunicaciones, etc.)', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (54, 'RIT-RD02','No existe de un plan formal, actulalizado y comunicado formalmente para la recuperación de las aplicaciones', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (55, 'RIT-RD03','Fallas eléctricas en el centro de cómputo', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (56, 'RIT-RD04','Daños que se presenten en los equipos  por vandalismo, uso inadecuado o fallas en la administración', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (57, 'RIT-RD05','Datos se replican en forma incorrecta', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (58, 'RIT-RD06','Fallas en el equipo de aire acondicionado, UPS o planta eléctrica','SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (59, 'RIT-RD07','Controles inadecuados para el monitoreo, seguimiento y protocolos formales para atención y escalamiento de incidentes', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (60, 'RIT-RD08','Aplicaciones anTecnológicos y de informaciónuados que no soportan la carga de trabajo, el volumen, las funcionalidades','SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (61, 'RIT-RD09','Inadecuado mantenimiento de los sistemas', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (62, 'RIT-RD10','Reprocesos en las pruebas y atrasos en la implementación por no contar con una infraestructura para la realización de las pruebas técnicas', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (63, 'RIT-RD11','Dependencia de los proveedores para el suministro de servicios, repuestos o de mantenimientos a los equipos donde corren los sistemas críTecnológicos y de informaciónos', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (64, 'RIT-RD12','Utilización incorrecta de los equipos de cómputo', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (65, 'RIT-RD13','Mal funcionamiento de una base de datos', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (66, 'RIT-RD14','Daño en una base de datos o archivos críTecnológicos y de informaciónos', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (67, 'RIT-RD15','Problemas de acceso a una base de datos', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (68, 'RIT-RD16','Administración inadecuada de procesos de actualización', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (69, 'RIT-RD17','Falta de capacitación o capacitación iadecuada de los encaragados de los procesos de actualización', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (70, 'RIT-RD18','Falta de procedimientos o procedimientos inadecuados para la ejecución de tareas críTecnológicos y de informaciónas', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (71, 'RIT-RD19','Controles deficientes en ambientes de pruebas', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (72, 'RIT-RD20','Controles deficientes en ambientes de producción', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (73, 'RIT-RD21','Falla en un servidor o varios a la vez', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (74, 'RIT-RD22','Pérdidas o suspensión temporal del servicio por una incorrecta configuración de parámetros en los sistemas', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (75, 'RIT-RD23','Errores en la configuración de equipos (servidores)', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (76, 'RIT-RD24','Mal diseño de las aplicaciones generando problemas de funcionamiento', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (77, 'RIT-RD25','Problemas en la distribución del cableado eléctrico o de comunicaciones', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (78, 'RIT-RD26','Insuficiente personal capacitado para realizar las tareas de operación, monitoreo y soporte de los servicios en producción', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (79, 'RIT-RD27','Afectación en la gestión y los  programas de trabajo porque  no se realizaron  las pruebas de aceptación dentro del tiempo planificado', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (80, 'RIT-RD28','Inundación por daño de tuberías internas del edificio', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (81, 'RIT-RD29','Fallas producidas por errores de programación que afectan la calidad del servicio', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (82, 'RIT-RD30','Afectación del servicio por generación de incidentes y problemas asociados a una mala implementación de cambios', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (83, 'RIT-RD31','Afectación del servicio por no tramitar oportunamente un cambio requerido urgente', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (84, 'RIT-RD32','Reprocesos en las pruebas y atrasos en la implementación por integración de aplicaciones incompletas o erróneas', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (85, 'RIT-RD33','Pérdida de información producidas por fallas en los controles de seguridad', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (86, 'RIT-RD34','Pérdida de información por la inadecuada utilización de los equipos de cómputo', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (87, 'RIT-RD35','No contar con las condiciones ambientales recomendadas por el fabricante para la operación adecuada de los equipos', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (88, 'RIT-RD36','Pérdida de información por la inadecuada utilización de los sistemas en utilización en la Institución', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Centros de datos', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");


-- -------------------------------------------------RIESGOS EN LA GESTION DE PROVEEDORES---------------------------------------------------


INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (89, 'RIT-RP01','Incumplimiento de contratos por parte del proveedor', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de proveedores', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (90, 'RIT-RP02','Incumplimiento de contratos por parte de la Institución', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de proveedores', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (91, 'RIT-RP03','Deficiencias en los servicios de los proveedores', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de proveedores', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (92, 'RIT-RP04','No contar con proveedores que estén preparados para ayudar a enfrentar una contingencia de tipo tecnológico', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de proveedores', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (93, 'RIT-RP05','Alta dependencia de proveedores claves a nivel de tecnología para proporcionar los servicios', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de proveedores', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (94, 'RIT-RP06','Contratos obsolectos', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de proveedores', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (95, 'RIT-RP07','Fallas en la gestión de licenciamientos', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de proveedores', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (96, 'RIT-RP08','Fallas en el control de vencimiento de los contratos', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de proveedores', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (97, 'RIT-RP09','Inexistencia de contratos', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de proveedores', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (99, 'RIT-RP10','Contratos no alineados a niveles de servicio (SLA)', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Gestión de proveedores', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");


-- -------------------------------------------------RIESGOS  DE CUMPLIMIENTO---------------------------------------------------

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (100, 'RIT-RI01','Incumplimiento por entrega de información incompleta a entes reguladores', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Cumplimiento', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (101, 'RIT-RI02','Incumplimiento de la legislación vigente', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Cumplimiento', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (102, 'RIT-RI03','Incumplimiento de normativas externas', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Cumplimiento', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (103, 'RIT-RI04','Incumplimiento en las fechas de entrega de la información a entes reguladores', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Cumplimiento', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (104, 'RIT-RI05','No contar con el apoyo de las altas autoridades', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Cumplimiento', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (105, 'RIT-RI06','Insuficientes recursos (humanos, equipos, espacio físico, etc.) para trabajar en la implementación', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Cumplimiento', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (106, 'RIT-RI07','No contar con una cultura de riesgos en la institución', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Cumplimiento', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (107, 'RIT-RI08','Los responsables de TI no cuentan con el suficiente apoyo de las altas autoridades para realizar su gestión', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Cumplimiento', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (108, 'RIT-RI09','No se cuenta con políTecnológicos y de informaciónas institucionales para la gestión de TI', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Cumplimiento', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");


-- -------------------------------------------------RIESGOS EN SEGURIDAD DE LA INFORMACION---------------------------------------------------

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (109, 'RIT-RS01','Incumplimiento de políTecnológicos y de informaciónas de seguridad', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Seguridad de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (110, 'RIT-RS02','Falta de capacitación y concientizacion en seguridad de la información', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Seguridad de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (111, 'RIT-RS03','PolíTecnológicos y de informaciónas de seguridad no documentadas o estan desactualizadas', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Seguridad de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (112, 'RIT-RS04','Normativas de seguridad no documentadas o estan desactualizadas', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Seguridad de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (113, 'RIT-RS05','Controles de seguridad no documentadas o estan desactualizadas', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Seguridad de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (114, 'RIT-RS06','Procedimientos de seguridad no documentadas o estan desactualizadas', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Seguridad de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (115, 'RIT-RS07','Procesos de seguridad no documentados o estan desactualizadas', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Seguridad de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (116, 'RIT-RS08','Ataques de denegación de servicios', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Seguridad de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (117, 'RIT-RS09','No se actualiza en forma adecuada la plataforma tecnológica que atiende los servicios de Internet', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Seguridad de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (118, 'RIT-RS10','Plataforma de seguridad mal atendida, monitoreo inadecuado de incidentes de seguridad', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Seguridad de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (119, 'RIT-RS11','Capacitación inadecuada en ingeniería social', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Seguridad de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (120, 'RIT-RS12','Perdida de equipos de cómputo (principalmente portátiles) sin la debida protección, con la consiguiente perdida de información confidencial)', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Seguridad de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (121, 'RIT-RS13','Perfiles de acceso no definidos o mal congigurados', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Seguridad de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (122, 'RIT-RS14','Red interna puede ser vulnerada por parte de cibercriminales', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Seguridad de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

INSERT INTO `si_db`.`T_SFR_Risk` (`PK_ID`,`ID`, `Name`, `Factors`, `GeneralType`, `AreaType`, `AreaSpecificType`, `Description`, `Probability`, `Impact`, `Magnitude`, `MitigationMeasures`, `Author`, `Consequences`) 
VALUES (123, 'RIT-RS15','Gestión inadecuada en el parchado de aplicaciones o equipos', 'SIN INFORMACION', 'Interno', 'Tecnológicos y de información', 'Seguridad de la información', 'N/A', '0.1', '01', '01', 'SIN INFORMACION', 50, "N/A");

-- -------------------------------------------------T_SFR_RiskPlan---------------------------------------------------
INSERT INTO si_db.T_SFR_RiskPlan (FK_PLAN, FK_RISK) VALUES ( 1, 1);
INSERT INTO si_db.T_SFR_RiskPlan (FK_PLAN, FK_RISK) VALUES ( 1, 89);
INSERT INTO si_db.T_SFR_RiskPlan (FK_PLAN, FK_RISK) VALUES ( 1, 105);
INSERT INTO si_db.T_SFR_RiskPlan (FK_PLAN, FK_RISK) VALUES ( 2, 2);
INSERT INTO si_db.T_SFR_RiskPlan (FK_PLAN, FK_RISK) VALUES ( 2, 96);
INSERT INTO si_db.T_SFR_RiskPlan (FK_PLAN, FK_RISK) VALUES ( 2, 16);
commit;

-- -------------------------------------------------INCIDENCIAS---------------------------------------------------

INSERT INTO `si_db`.`T_SFR_Incidence` (`PK_ID`, `Name`, `EntryDate`, `Description`, `Cause`, `Affectation`, `riskID`)
VALUES (1, 'Proveedor imcumplió con el contrato.', NOW(), 'Proveedor de materias primas para el desarrollo de carreteras imcumplió con el pedido acordado en el contrato.', 'Actividad Antrópica', 40, 89);

INSERT INTO `si_db`.`T_SFR_Incidence` (`PK_ID`, `Name`, `EntryDate`, `Description`, `Cause`, `Affectation`, `riskID`)
VALUES (2, 'Faltante de recursos en reparación de calle.', NOW(), 'Faltaron materiales para terminar una reparación de una calle.', 'Fallo del proceso', 50, 105);

INSERT INTO `si_db`.`T_SFR_Incidence` (`PK_ID`, `Name`, `EntryDate`, `Description`, `Cause`, `Affectation`, `riskID`)
VALUES (3, 'No se renovó a tiempo contrato con proveedor de cemento.', NOW(), 'Se perdieron las informaciones relacionadas a la recontratación de un proveedor.', 'Fallo humano', 70, 96);

INSERT INTO `si_db`.`T_SFR_Incidence` (`PK_ID`, `Name`, `EntryDate`, `Description`, `Cause`, `Affectation`, `riskID`)
VALUES (4, 'No se encotró información sobre proyecto de recuperación de un lote.', NOW(), 'Se perdieron las informaciones relacionadas a la recuperación y limpieza de un lote.', 'Fallo humano', 60, 16);

INSERT INTO `si_db`.`T_SFR_PlanIncidence` (`FK_PLAN`, `FK_INCIDENCE`) VALUES (1,1);
INSERT INTO `si_db`.`T_SFR_PlanIncidence` (`FK_PLAN`, `FK_INCIDENCE`) VALUES (1,2);
INSERT INTO `si_db`.`T_SFR_PlanIncidence` (`FK_PLAN`, `FK_INCIDENCE`) VALUES (2,3);
INSERT INTO `si_db`.`T_SFR_PlanIncidence` (`FK_PLAN`, `FK_INCIDENCE`) VALUES (2,4);

commit;

-- -------------------------------------------------COMMENTS---------------------------------------------------

INSERT INTO `si_db`.`T_SFR_Comment` (`PK_ID`, `Comment`, `Url`, `Author`, `EntryDate`)
VALUES (1, "Revisen este riesgo, es posible que les sirva ya que se presenta en proyectos anteriores.", "http://localhost:3001/SFR#/riesgo?id=RS15", "Ismael Salazar", NOW());

INSERT INTO `si_db`.`T_SFR_PlanComment` (`FK_PLAN`, `FK_COMMENT`) VALUES (1,1);


-- ----------------------------------------------------------------------------------------------------------------

INSERT INTO si_db.estado VALUES(1,"En proceso");
INSERT INTO si_db.estado VALUES(2,"Aceptada");
INSERT INTO si_db.estado VALUES(3,"Denegada");

INSERT INTO si_db.gradoAcademico values(1,"Primer grado");
INSERT INTO si_db.gradoAcademico values(2,"Segundo grado");
INSERT INTO si_db.gradoAcademico values(3,"Tercer grado");
INSERT INTO si_db.gradoAcademico values(4,"Cuarto grado");
INSERT INTO si_db.gradoAcademico values(5,"Quinto grado");
INSERT INTO si_db.gradoAcademico values(6,"Sexto grado");
INSERT INTO si_db.gradoAcademico values(7,"Septimo año");
INSERT INTO si_db.gradoAcademico values(8,"Octavo año");
INSERT INTO si_db.gradoAcademico values(9,"Noveno año");
INSERT INTO si_db.gradoAcademico values(10,"Decimo año");
INSERT INTO si_db.gradoAcademico values(11,"Undecimo año");
INSERT INTO si_db.gradoAcademico values(12,"Duodecimo año");


commit;