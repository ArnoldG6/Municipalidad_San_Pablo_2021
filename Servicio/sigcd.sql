-- MySQL Workbench Forward Engineering
-- -----------------------------------------------------
-- Schema sigcd
-- -----------------------------------------------------

CREATE SCHEMA IF NOT EXISTS `sigcd` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE si_db ;

-- -----------------------------------------------------
-- Table `Solicitante`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `Solicitante` (
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
-- Table `Direccion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Direccion` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `distrito` VARCHAR(45) NOT NULL,
    `barrio` VARCHAR(45) NOT NULL,
    `direccionExacta` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARACTER SET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

-- -----------------------------------------------------
-- Table `Estudiante`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `Estudiante` (
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
-- Table `GradoAcademico`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `GradoAcademico` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `nivel` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARACTER SET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

-- -----------------------------------------------------
-- Table `Estado`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `Estado` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `estado` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARACTER SET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

-- -----------------------------------------------------
-- Table `AyudaTemporal`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `AyudaTemporal` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `idSolicitante` INT NOT NULL,
    `fechaCreacion` TIMESTAMP NOT NULL,
    `claveRecuperacion` VARCHAR(45) NOT NULL,
    `motivoAyuda` VARCHAR(500) NOT NULL,
    `idDireccion` INT NOT NULL,
    `idEstado` INT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `solicitanteAtFk` FOREIGN KEY (`idSolicitante`)
        REFERENCES `solicitante` (`id`),
    CONSTRAINT `direccion_fk` FOREIGN KEY (`idDireccion`)
        REFERENCES `direccion` (`id`),
    CONSTRAINT `estadoAtFk` FOREIGN KEY (`idEstado`)
        REFERENCES `estado` (`id`)
)  ENGINE=INNODB DEFAULT CHARACTER SET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;

-- -----------------------------------------------------
-- Table `BecaAcademica`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `BecaAcademica` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `idSolicitante` INT NOT NULL,
    `fechaCreacion` TIMESTAMP NOT NULL,
    `claveRecuperacion` VARCHAR(45) NOT NULL,
    `idEstudiante` INT NOT NULL,
    `idDireccion` INT NOT NULL,
    `idEstado` INT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `solicitanteBaFk` FOREIGN KEY (`idSolicitante`)
        REFERENCES `solicitante` (`id`),
    CONSTRAINT `estudianteFk` FOREIGN KEY (`idEstudiante`)
        REFERENCES `estudiante` (`id`),
    CONSTRAINT `direccionfk` FOREIGN KEY (`idDireccion`)
        REFERENCES `direccion` (`id`),
    CONSTRAINT `estadoBaFk` FOREIGN KEY (`idEstado`)
        REFERENCES `estado` (`id`)
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