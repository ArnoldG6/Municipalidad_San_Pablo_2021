ALTER SCHEMA `si_db`  DEFAULT COLLATE utf8mb4_spanish2_ci ;
-- ------------------------------------------STARTER DATA FOR SI_DB--------------------------------------------------
-- ---------------------------------------------SI_DEPARTMENTS-----------------------------------------------
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('100', 'Concejo Municipal');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('101', 'Secretaría del Concejo Municipal');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('102', 'Alcaldía Municipal');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('103', 'Auditoria interna');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('104', 'Planificación, presupuesto y control');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('105', 'Asesoría legal');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('106', 'Relaciones públicas y proyección institucional');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('107', 'Unidad de Fiscalización de obra privada');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('108', 'Contabilidad');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('109', 'Valoración de bienes inmuebles');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('110', 'Gestión de cobro');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('111', 'Tesorería');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('112', 'Tecnologías de la Información');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('113', 'Salud Ocupacional');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('114', 'Planillas');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('115', 'Proveeduría');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('116', 'Servicios Generales');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('117', 'Plataforma de servicios');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('118', 'Policía municipal');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('119', 'Ecología y medio ambiente');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('120', 'Desarrollo Social Inclusivo');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('121', 'Archivo Central');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('122', 'Licencias comerciales');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('123', 'Infraestructura pública');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('124', 'Infraestructura privada');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('125', 'Planificación y ordenamiento territorial');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('126', 'Donaciones');
INSERT INTO `si_db`.`SI_DEPARTMENTS` (`PK_DEPARTMENT`, `description`) VALUES ('127', 'Otro');
-- ------------------------------------------------SI_ROLES--------------------------------------------------
INSERT INTO `si_db`.`SI_ROLES` (`PK_ROL`, `description`) VALUES ('1', 'ADMIN');
INSERT INTO `si_db`.`SI_ROLES` (`PK_ROL`, `description`) VALUES ('2', 'SUPER_ADMIN');
INSERT INTO `si_db`.`SI_ROLES` (`PK_ROL`, `description`) VALUES ('3', 'USER');
INSERT INTO `si_db`.`SI_ROLES` (`PK_ROL`, `description`) VALUES ('4', 'BUDGET_ADMIN');
INSERT INTO `si_db`.`SI_ROLES` (`PK_ROL`, `description`) VALUES ('5', 'TENDER_ADMIN');
-- ----------------------------------------------SI_OFFICIALS------------------------------------------------
INSERT INTO `si_db`.`SI_OFFICIALS` (`PK_OFFICIAL`, `name`, `surname`, `email`, `FK_department`) VALUES ('117040615', 'Luis Diego', 'Ramirez', 'ld.ramirezch14@gmail.com', '112');
INSERT INTO `si_db`.`SI_OFFICIALS` (`PK_OFFICIAL`, `name`, `surname`, `email`, `FK_department`) VALUES ('50', 'JOSEPH', 'GRANDA', 'informatica@sanpablo.go.cr', '103');
INSERT INTO `si_db`.`SI_OFFICIALS` (`PK_OFFICIAL`, `name`, `surname`, `email`, `FK_department`) VALUES ('51', 'ISMAEL', 'SALAZAR', 'controlinterno@sanpablo.go.cr', '100');
-- -------------------------------------------------SI_USERS--------------------------------------------------
INSERT INTO `si_db`.`SI_USERS` (`PK_USER`, `FK_official`, `FK_email`, `password`) VALUES ('123', '123', 'ld.ramirezch14@gmail.com', SHA2('123', 256));
INSERT INTO `si_db`.`SI_USERS` (`PK_USER`, `FK_official`, `FK_email`, `password`) VALUES ('50', '50', 'informatica@sanpablo.go.cr', SHA2('contra1', 256));
INSERT INTO `si_db`.`SI_USERS` (`PK_USER`, `FK_official`, `FK_email`, `password`) VALUES ('51', '51', 'controlinterno@sanpablo.go.cr', SHA2('contra2', 256));

-- -----------------------------------------------SI_USER_ROLES------------------------------------------------
INSERT INTO `si_db`.`SI_USER_ROLES` (`PK_USER_ROL`,`FK_USER`,`FK_ROL`) values (1,117040615,2);
INSERT INTO `si_db`.`SI_USER_ROLES` (`PK_USER_ROL`,`FK_USER`,`FK_ROL`) values (2,50,2);
INSERT INTO `si_db`.`SI_USER_ROLES` (`PK_USER_ROL`,`FK_USER`,`FK_ROL`) values (3,51,1);
commit;

-- -----------------------------------------------SFR------------------------------------------------
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