-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 01-09-2019 a las 14:24:41
-- Versión del servidor: 10.1.38-MariaDB
-- Versión de PHP: 7.1.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `basedatospersonasprueba`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personasprueba`
--

CREATE TABLE `personasprueba` (
  `nombre` varchar(100) COLLATE latin1_spanish_ci NOT NULL,
  `apellidos` varchar(100) COLLATE latin1_spanish_ci NOT NULL,
  `fechanacimiento` date NOT NULL,
  `id` int(11) NOT NULL,
  `idprofesion` int(11) NOT NULL,
  `genero` char(1) COLLATE latin1_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesionesprueba`
--

CREATE TABLE `profesionesprueba` (
  `profesion` varchar(100) COLLATE latin1_spanish_ci NOT NULL,
  `idprofesion` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

--
-- Volcado de datos para la tabla `profesionesprueba`
--

INSERT INTO `profesionesprueba` (`profesion`, `idprofesion`) VALUES
('Parado', 1),
('Profesor', 2),
('Político', 3),
('Programador', 4),
('Concejal de Ocio y Tiempo Libre', 5),
('Futbolista', 6),
('jardinero', 7);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `personasprueba`
--
ALTER TABLE `personasprueba`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idprofesion` (`idprofesion`);

--
-- Indices de la tabla `profesionesprueba`
--
ALTER TABLE `profesionesprueba`
  ADD PRIMARY KEY (`idprofesion`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `personasprueba`
--
ALTER TABLE `personasprueba`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `profesionesprueba`
--
ALTER TABLE `profesionesprueba`
  MODIFY `idprofesion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `personasprueba`
--
ALTER TABLE `personasprueba`
  ADD CONSTRAINT `personasprueba_ibfk_1` FOREIGN KEY (`idprofesion`) REFERENCES `profesionesprueba` (`idprofesion`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
