-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 30-04-2020 a las 17:31:46
-- Versión del servidor: 5.7.26
-- Versión de PHP: 7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `mis_clientes`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

DROP TABLE IF EXISTS `cliente`;
CREATE TABLE IF NOT EXISTS `cliente` (
  `id` int(3) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `PROVINCIA` varchar(100) DEFAULT NULL,
  `MUNICIPIO` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`id`, `nombre`, `direccion`, `PROVINCIA`, `MUNICIPIO`) VALUES
(126, 'Frank Lloyd Wright', 'C/ Pamochamoso sn', 'Cadiz', 'Rota'),
(11, 'Marcel Breuer', 'C/ Hungria 5641', 'Gerona', 'Casteldefels'),
(36, 'Mies Van Der Rohe', 'Avda Alemania 2651', 'Santa Cruz de Tenerife', 'Adeje'),
(24, 'Cesar Portela', 'Avda. Marín 106', 'Pontevedra', 'Marín'),
(241, 'Alvaro Siza', 'Avda. Porto 16', 'Oporto', ''),
(125, 'Antonio Corrales', 'C/ Molezun 168', 'Madrid', NULL),
(48, 'Sainz de Oiza', 'C/ Bilbao 82', 'Vizcaya', 'Bilbo'),
(520, 'Santiago Calatrava', 'C/ Valencia  48', 'Valencia', NULL),
(35, 'Navarro Baldeweg', 'C/ Malasaña 82', 'Santander', NULL),
(52, 'Zaja Hadid', 'C/ Beirut 352', 'A Coruña', ''),
(26, 'Oscar Niemeyer', 'C/ Brasilia 54', 'Oviedo', ''),
(426, 'Felix Candela', 'C/ Mexico DC', 'Madrid', NULL),
(326, 'Alvar Aalto', 'C/Finlandia 654', 'Finlandia', NULL),
(528, 'Angel', 'Plaza de La Feria 36 3ºB', 'Las Palmas', 'Las Palmas de Gran Canaria');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `informedeerrores`
--

DROP TABLE IF EXISTS `informedeerrores`;
CREATE TABLE IF NOT EXISTS `informedeerrores` (
  `id` int(3) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `informedeerrores`
--

INSERT INTO `informedeerrores` (`id`, `nombre`, `direccion`) VALUES
(0, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `municipio`
--

DROP TABLE IF EXISTS `municipio`;
CREATE TABLE IF NOT EXISTS `municipio` (
  `codigopostalid` int(11) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `provincia` varchar(50) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `municipio`
--

INSERT INTO `municipio` (`codigopostalid`, `nombre`, `provincia`) VALUES
(36940, 'CANGAS DEL MORRAZO', 'Pontevedra'),
(36940, 'CANGAS DEL MORRAZO', 'Pontevedra'),
(35018, 'Almatriche', 'Las Palmas de Gran Canaria'),
(38108, 'La Laguna', 'Santa Cruz de Tenerife');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `provincia`
--

DROP TABLE IF EXISTS `provincia`;
CREATE TABLE IF NOT EXISTS `provincia` (
  `cod_provincia` int(5) NOT NULL,
  `provincia` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `provincia`
--

INSERT INTO `provincia` (`cod_provincia`, `provincia`) VALUES
(36, 'Pontevedra'),
(35, 'Las Palmas de Gran Canaria'),
(36, 'Pontevedra'),
(35, 'Las Palmas de Gran Canaria'),
(38, 'Santa Cruz de Tenerife');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
