-- MySQL Workbench Forward Engineering
-- -----------------------------------------------------
-- Schema sigcd
-- -----------------------------------------------------

CREATE SCHEMA IF NOT EXISTS `sigcd` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `sigcd` ;

-- -----------------------------------------------------
-- Table `sigcd`.`Solicitante`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `sigcd`.`Solicitante` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `cedula` VARCHAR(45) NOT NULL,
    `nombre` VARCHAR(45) NOT NULL,
    `primerApellido` VARCHAR(45) NOT NULL,
    `segundoApellido` VARCHAR(45) NOT NULL,
    `fechaNacimiento` VARCHAR(45) NOT NULL,
    `edad` INT NOT NULL,
    `telefonoHabitacion` VARCHAR(45),
    `telefonoCelular` VARCHAR(45) NOT NULL,
    `correoElectronico` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARACTER SET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

-- -----------------------------------------------------
-- Table `sigcd`.`Direccion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sigcd`.`Direccion` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `distrito` VARCHAR(45) NOT NULL,
    `barrio` VARCHAR(45) NOT NULL,
    `direccionExacta` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARACTER SET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

-- -----------------------------------------------------
-- Table `sigcd`.`Estudiante`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `sigcd`.`Estudiante` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `cedula` VARCHAR(45) NOT NULL,
    `nombre` VARCHAR(45) NOT NULL,
    `primerApellido` VARCHAR(45) NOT NULL,
    `segundoApellido` VARCHAR(45) NOT NULL,
    `fechaNacimiento` VARCHAR(45) NOT NULL,
    `edad` INT NOT NULL,
    `gradoAcademico` INT NOT NULL,
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARACTER SET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

-- -----------------------------------------------------
-- Table `sigcd`.`GradoAcademico`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `sigcd`.`GradoAcademico` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `nivel` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARACTER SET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

-- -----------------------------------------------------
-- Table `sigcd`.`Estado`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `sigcd`.`Estado` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `estado` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARACTER SET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

-- -----------------------------------------------------
-- Table `sigcd`.`AyudaTemporal`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `sigcd`.`AyudaTemporal` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `idSolicitante` INT NOT NULL,
    `fechaCreacion` TIMESTAMP NOT NULL,
    `claveRecuperacion` VARCHAR(45) NOT NULL,
    `motivoAyuda` VARCHAR(500) NOT NULL,
    `idDireccion` INT NOT NULL,
    `idEstado` INT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `solicitanteAtFk` FOREIGN KEY (`idSolicitante`)
        REFERENCES `sigcd`.`solicitante` (`id`),
    CONSTRAINT `direccion_fk` FOREIGN KEY (`idDireccion`)
        REFERENCES `sigcd`.`direccion` (`id`),
    CONSTRAINT `estadoAtFk` FOREIGN KEY (`idEstado`)
        REFERENCES `sigcd`.`estado` (`id`)
)  ENGINE=INNODB DEFAULT CHARACTER SET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

-- -----------------------------------------------------
-- Table `sigcd`.`BecaAcademica`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `sigcd`.`BecaAcademica` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `idSolicitante` INT NOT NULL,
    `fechaCreacion` TIMESTAMP NOT NULL,
    `claveRecuperacion` VARCHAR(45) NOT NULL,
    `idEstudiante` INT NOT NULL,
    `idDireccion` INT NOT NULL,
    `idEstado` INT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `solicitanteBaFk` FOREIGN KEY (`idSolicitante`)
        REFERENCES `sigcd`.`solicitante` (`id`),
    CONSTRAINT `estudianteFk` FOREIGN KEY (`idEstudiante`)
        REFERENCES `sigcd`.`estudiante` (`id`),
    CONSTRAINT `direccionfk` FOREIGN KEY (`idDireccion`)
        REFERENCES `sigcd`.`direccion` (`id`),
    CONSTRAINT `estadoBaFk` FOREIGN KEY (`idEstado`)
        REFERENCES `sigcd`.`estado` (`id`)
)  ENGINE=INNODB DEFAULT CHARACTER SET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

INSERT INTO estado VALUES(1,"En proceso");
INSERT INTO estado VALUES(2,"Aceptada");
INSERT INTO estado VALUES(3,"Denegada");

INSERT INTO gradoAcademico values(1,"Primer grado");
INSERT INTO gradoAcademico values(2,"Segundo grado");
INSERT INTO gradoAcademico values(3,"Tercer grado");
INSERT INTO gradoAcademico values(4,"Cuarto grado");
INSERT INTO gradoAcademico values(5,"Quinto grado");
INSERT INTO gradoAcademico values(6,"Sexto grado");
INSERT INTO gradoAcademico values(7,"Septimo año");
INSERT INTO gradoAcademico values(8,"Octavo año");
INSERT INTO gradoAcademico values(9,"Noveno año");
INSERT INTO gradoAcademico values(10,"Decimo año");
INSERT INTO gradoAcademico values(11,"Undecimo año");
INSERT INTO gradoAcademico values(12,"Duodecimo año");