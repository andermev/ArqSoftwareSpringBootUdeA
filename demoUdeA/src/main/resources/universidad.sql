-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         5.7.18-log - MySQL Community Server (GPL)
-- SO del servidor:              Win64
-- HeidiSQL Versión:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para universidad
CREATE DATABASE IF NOT EXISTS `universidad` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `universidad`;

-- Volcando estructura para tabla universidad.aula
CREATE TABLE IF NOT EXISTS `aula` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo_aula` varchar(255) DEFAULT NULL,
  `ubicacion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla universidad.aula: ~5 rows (aproximadamente)
/*!40000 ALTER TABLE `aula` DISABLE KEYS */;
INSERT INTO `aula` (`id`, `tipo_aula`, `ubicacion`) VALUES
	(1, 'PRESENCIAL', 'BLOQUE ING SALA LIS'),
	(2, 'PRESENCIAL', 'DRAI'),
	(3, 'PRESENCIAL', 'BLOQUE ING 19-304'),
	(4, 'PRESENCIAL', 'BLOQUE ING 21-216'),
	(5, 'VIRTUAL', 'PLATAFORMA WIZIQ');
/*!40000 ALTER TABLE `aula` ENABLE KEYS */;

-- Volcando estructura para tabla universidad.estudiante
CREATE TABLE IF NOT EXISTS `estudiante` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombres` varchar(255) DEFAULT NULL,
  `apellidos` varchar(255) DEFAULT NULL,
  `carrera` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla universidad.estudiante: ~6 rows (aproximadamente)
/*!40000 ALTER TABLE `estudiante` DISABLE KEYS */;
INSERT INTO `estudiante` (`id`, `nombres`, `apellidos`, `carrera`) VALUES
	(3, 'Ana Maria', 'Agudelo Cadavid', 'ING SISTEMAS'),
	(5, 'Harold', 'Castañeda', 'ING SISTEMAS'),
	(6, 'Juan', 'Andrade', 'ING SISTEMAS'),
	(7, 'Yeison', 'Jimenez', 'ING SISTEMAS'),
	(8, 'Alexander', 'Uribe', 'ING SISTEMAS'),
	(9, 'Sara', 'Gonzales', 'ING SISTEMAS');
/*!40000 ALTER TABLE `estudiante` ENABLE KEYS */;

-- Volcando estructura para tabla universidad.estudiante_materia
CREATE TABLE IF NOT EXISTS `estudiante_materia` (
  `estudiante_id` int(11) NOT NULL,
  `materia_id` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `fk_estudiante_materia` (`estudiante_id`),
  KEY `fk_materia_estudiante` (`materia_id`),
  CONSTRAINT `fk_estudiante_materia` FOREIGN KEY (`estudiante_id`) REFERENCES `estudiante` (`id`),
  CONSTRAINT `fk_materia_estudiante` FOREIGN KEY (`materia_id`) REFERENCES `materia` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla universidad.estudiante_materia: ~7 rows (aproximadamente)
/*!40000 ALTER TABLE `estudiante_materia` DISABLE KEYS */;
INSERT INTO `estudiante_materia` (`estudiante_id`, `materia_id`, `id`) VALUES
	(3, 1, 1),
	(3, 2, 2),
	(5, 1, 3),
	(5, 2, 4),
	(6, 3, 5),
	(7, 4, 6),
	(8, 1, 7);
/*!40000 ALTER TABLE `estudiante_materia` ENABLE KEYS */;

-- Volcando estructura para tabla universidad.materia
CREATE TABLE IF NOT EXISTS `materia` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `hora_inicio` time DEFAULT NULL,
  `hora_fin` time DEFAULT NULL,
  `dias` varchar(50) DEFAULT NULL,
  `aula_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_materia_aula_id` (`aula_id`),
  CONSTRAINT `fk_materia_aula_id` FOREIGN KEY (`aula_id`) REFERENCES `aula` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla universidad.materia: ~4 rows (aproximadamente)
/*!40000 ALTER TABLE `materia` DISABLE KEYS */;
INSERT INTO `materia` (`id`, `nombre`, `hora_inicio`, `hora_fin`, `dias`, `aula_id`) VALUES
	(1, 'ARQ DE SOFTWARE', '12:00:00', '14:00:00', 'MIERCOLES', 5),
	(2, 'ARQ DE SOFTWARE', '10:00:00', '12:00:00', 'SABADOS', 5),
	(3, 'BASE DE DATOS', '14:00:00', '16:00:00', 'LUNES', 1),
	(4, 'BASE DE DATOS', '14:00:00', '16:00:00', 'JUEVES', 1);
/*!40000 ALTER TABLE `materia` ENABLE KEYS */;

-- Volcando estructura para tabla universidad.profesor
CREATE TABLE IF NOT EXISTS `profesor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombres` varchar(255) DEFAULT NULL,
  `apellidos` varchar(255) DEFAULT NULL,
  `carrera` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla universidad.profesor: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `profesor` DISABLE KEYS */;
INSERT INTO `profesor` (`id`, `nombres`, `apellidos`, `carrera`) VALUES
	(1, 'Anderson', 'Mejia', 'ING SISTEMAS'),
	(2, 'Diego', 'Botia', 'ING SISTEMAS');
/*!40000 ALTER TABLE `profesor` ENABLE KEYS */;

-- Volcando estructura para tabla universidad.profesor_materia
CREATE TABLE IF NOT EXISTS `profesor_materia` (
  `profesor_id` int(11) NOT NULL,
  `materia_id` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `fk_profesor_materia` (`profesor_id`),
  KEY `fk_materia_profesor` (`materia_id`),
  CONSTRAINT `fk_materia_profesor` FOREIGN KEY (`materia_id`) REFERENCES `materia` (`id`),
  CONSTRAINT `fk_profesor_materia` FOREIGN KEY (`profesor_id`) REFERENCES `profesor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla universidad.profesor_materia: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `profesor_materia` DISABLE KEYS */;
INSERT INTO `profesor_materia` (`profesor_id`, `materia_id`, `id`) VALUES
	(1, 1, 1),
	(1, 2, 2),
	(2, 3, 3);
/*!40000 ALTER TABLE `profesor_materia` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
