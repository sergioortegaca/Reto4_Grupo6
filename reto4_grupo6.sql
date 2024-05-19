-- phpMyAdmin SQL Dump
-- version 5.1.1deb5ubuntu1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 17-05-2024 a las 12:15:41
-- Versión del servidor: 8.0.36-0ubuntu0.22.04.1
-- Versión de PHP: 8.1.2-1ubuntu2.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `reto4_grupo6`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Album`
--

CREATE TABLE `Album` (
  `IDAlbum` int NOT NULL,
  `Titulo` varchar(30) NOT NULL,
  `Ano` date NOT NULL,
  `Genero` varchar(30) NOT NULL,
  `Imagen` varchar(99) DEFAULT NULL,
  `IDMusico` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Album`
--

INSERT INTO `Album` (`IDAlbum`, `Titulo`, `Ano`, `Genero`, `Imagen`, `IDMusico`) VALUES
(1, 'Moonlight922', '2020-01-10', 'Trap/RnB', '/src/img/moonlight922.jpg', 1),
(2, 'Maracucho Bueno Muere Chiquito', '2018-03-16', 'Rap', '/src/img/maracuchobuenomuerechiquito.jpg', 1),
(29, 'pruebaaaaaaa', '2024-05-08', 'asdgsdfg', 'sdfhsdfg', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Audio`
--

CREATE TABLE `Audio` (
  `IDAudio` int NOT NULL,
  `Nombre` varchar(30) NOT NULL,
  `Duracion` time NOT NULL,
  `Imagen` varchar(99) DEFAULT NULL,
  `Tipo` enum('Podcast','Cancion') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Audio`
--

INSERT INTO `Audio` (`IDAudio`, `Nombre`, `Duracion`, `Imagen`, `Tipo`) VALUES
(2, 'The Wild Project', '01:14:13', '/src/img/TWP.jpg', 'Podcast'),
(3, 'Maracucho Bueno Muere Chiquito', '00:04:18', '/src/img/maracuchobuenomuerechiquito.jpg', 'Cancion'),
(4, 'Te Enamoraste de un G', '00:04:46', '/src/img/maracuchobuenomuerechiquito.jpg', 'Cancion'),
(5, 'Forbes', '00:02:58', '/src/img/maracuchobuenomuerechiquito.jpg', 'Cancion'),
(6, 'Cuenta Conmigo', '00:03:32', '/src/img/maracuchobuenomuerechiquito.jpg', 'Cancion'),
(7, 'Coquito la Pieza', '00:03:08', '/src/img/maracuchobuenomuerechiquito.jpg', 'Cancion'),
(8, 'De Ayer para Hoy', '00:04:44', '/src/img/maracuchobuenomuerechiquito.jpg', 'Cancion'),
(9, 'Majalulo', '00:03:52', '/src/img/maracuchobuenomuerechiquito.jpg', 'Cancion'),
(10, 'Tokyo Drift', '00:02:47', '/src/img/maracuchobuenomuerechiquito.jpg', 'Cancion'),
(11, 'Un Fleje (Wayne Chiquitito)', '00:01:37', '/src/img/maracuchobuenomuerechiquito.jpg', 'Cancion'),
(12, 'Chivatos', '00:02:47', '/src/img/maracuchobuenomuerechiquito.jpg', 'Cancion'),
(13, 'Todos Esos Golfos', '00:04:09', '/src/img/maracuchobuenomuerechiquito.jpg', 'Cancion'),
(14, 'Ahí Fuera (No Hay Reglas)', '00:03:58', '/src/img/maracuchobuenomuerechiquito.jpg', 'Cancion'),
(15, 'Único', '00:05:56', '/src/img/maracuchobuenomuerechiquito.jpg', 'Cancion'),
(16, 'Bandida', '00:03:02', '/src/img/maracuchobuenomuerechiquito.jpg', 'Cancion'),
(17, 'No Hay Perdón', '00:04:16', '/src/img/maracuchobuenomuerechiquito.jpg', 'Cancion'),
(18, '922 928', '00:00:51', '/src/img/moonlight922.jpg', 'Cancion'),
(19, 'Mi Isla 10k', '00:02:58', '/src/img/moonlight922.jpg', 'Cancion'),
(20, 'Vvs', '00:02:48', '/src/img/moonlight922.jpg', 'Cancion'),
(21, 'Ojitos Aguaos', '00:03:18', '/src/img/moonlight922.jpg', 'Cancion'),
(22, 'Lo Pues Intentar', '00:03:19', '/src/img/moonlight922.jpg', 'Cancion'),
(23, 'Balaperdida', '00:03:22', '/src/img/moonlight922.jpg', 'Cancion'),
(24, 'Mina el Hammani', '00:03:17', '/src/img/moonlight922.jpg', 'Cancion'),
(25, 'Slow Mo', '00:03:14', '/src/img/moonlight922.jpg', 'Cancion'),
(26, 'Tentaciones', '00:03:58', '/src/img/moonlight922.jpg', 'Cancion'),
(27, 'Cuando Estoy Mal', '00:02:32', '/src/img/moonlight922.jpg', 'Cancion'),
(28, 'Moonlight', '00:03:13', '/src/img/moonlight922.jpg', 'Cancion'),
(29, 'En Bajo Perfil', '00:04:13', '/src/img/moonlight922.jpg', 'Cancion'),
(30, 'Doble Tick Azul', '00:03:43', '/src/img/moonlight922.jpg', 'Cancion');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Cancion`
--

CREATE TABLE `Cancion` (
  `IDAudio` int NOT NULL,
  `IDAlbum` int NOT NULL,
  `ArtistasInvitados` varchar(99) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Cancion`
--

INSERT INTO `Cancion` (`IDAudio`, `IDAlbum`, `ArtistasInvitados`) VALUES
(3, 2, NULL),
(4, 2, NULL),
(5, 2, NULL),
(6, 2, NULL),
(7, 2, NULL),
(8, 2, NULL),
(9, 2, NULL),
(10, 2, NULL),
(11, 2, NULL),
(12, 2, NULL),
(13, 2, NULL),
(14, 2, NULL),
(15, 2, NULL),
(16, 2, NULL),
(17, 2, NULL),
(18, 1, NULL),
(19, 1, NULL),
(20, 1, NULL),
(21, 1, NULL),
(22, 1, NULL),
(23, 1, NULL),
(24, 1, NULL),
(25, 1, NULL),
(26, 1, NULL),
(27, 1, NULL),
(28, 1, NULL),
(29, 1, NULL),
(30, 1, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Cliente`
--

CREATE TABLE `Cliente` (
  `IDCliente` int NOT NULL,
  `Nombre` varchar(30) NOT NULL,
  `Apellido` varchar(30) NOT NULL,
  `Usuario` varchar(30) NOT NULL,
  `Contrasena` varchar(30) NOT NULL,
  `FechaNacimiento` date NOT NULL,
  `FechaRegistro` date NOT NULL,
  `Tipo` enum('Free','Premium') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Cliente`
--

INSERT INTO `Cliente` (`IDCliente`, `Nombre`, `Apellido`, `Usuario`, `Contrasena`, `FechaNacimiento`, `FechaRegistro`, `Tipo`) VALUES
(1, 'Ibai', 'Zaballa', 'Furaik', '123456789abc', '2003-01-01', '2024-04-18', 'Premium'),
(2, 'a', 'a', 'a', 'a', '2000-01-01', '2000-01-01', 'Premium'),
(3, 'Eder', 'Pereda', 'nenefresko', 'a', '2012-01-01', '2024-05-09', 'Free'),
(34, 'Enaitz', 'Familiar', 'enaitz', 'a', '2005-12-25', '2024-05-17', 'Free');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Estadisticas`
--

CREATE TABLE `Estadisticas` (
  `IDAudio` int NOT NULL,
  `NumeroReproducciones` int DEFAULT NULL,
  `NumeroMeGustas` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Estadisticas`
--

INSERT INTO `Estadisticas` (`IDAudio`, `NumeroReproducciones`, `NumeroMeGustas`) VALUES
(2, 11, 8),
(3, 0, 0),
(4, 23, 12),
(5, 2, 0),
(6, 80, 67),
(7, 0, 0),
(8, 0, 0),
(9, 0, 0),
(10, 0, 0),
(11, 0, 0),
(21, 14, 4),
(30, 9, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Gustos`
--

CREATE TABLE `Gustos` (
  `IDCliente` int NOT NULL,
  `IDAudio` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Musico`
--

CREATE TABLE `Musico` (
  `IDMusico` int NOT NULL,
  `NombreArtistico` varchar(30) NOT NULL,
  `Imagen` varchar(99) DEFAULT NULL,
  `Caracteristica` enum('Solista','Grupo') DEFAULT NULL,
  `Descripcion` varchar(999) DEFAULT NULL,
  `AnoActivo` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Musico`
--

INSERT INTO `Musico` (`IDMusico`, `NombreArtistico`, `Imagen`, `Caracteristica`, `Descripcion`, `AnoActivo`) VALUES
(1, 'Cruz Cafune', '/src/img/cruzcafune.jpg', 'Solista', 'Carlos Bruñas Zamorin (Tenerife, 25 de junio de 1993) de nombre artistico Cruz Cafune, es un cantante y rapero español. ', 2012),
(2, 'El Cigala', '/src/img/elcigala.jpg', 'Grupo', 'Diego Ramon Jimenez Salazar, ​ mas conocido por su nombre artistico Diego el Cigala, es un cantaor de flamenco español de etnia gitana y nacionalidad dominicana.', 1987),
(3, 'Bengo', '/src/img/bengo.jpg', 'Solista', 'Bengo es el futuro de la escena musical vasca. Con su explosiva mezcla de pop y musica urbana, el artista busca abrir un espacio y ocuparlo. “Bizirik” es su nuevo EP que presenta en una extensa gira nacional.', 2016);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `musicos_grupo`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `musicos_grupo` (
`IDMusico` int
,`NombreArtistico` varchar(30)
,`Imagen` varchar(99)
,`Caracteristica` enum('Solista','Grupo')
,`Descripcion` varchar(999)
,`AnoActivo` int
);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `musicos_solitas`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `musicos_solitas` (
`IDMusico` int
,`NombreArtistico` varchar(30)
,`Imagen` varchar(99)
,`Caracteristica` enum('Solista','Grupo')
,`Descripcion` varchar(999)
,`AnoActivo` int
);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Playlist`
--

CREATE TABLE `Playlist` (
  `IDList` int NOT NULL,
  `Titulo` varchar(30) NOT NULL,
  `FechaCreacion` date NOT NULL,
  `IDCliente` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Playlist`
--

INSERT INTO `Playlist` (`IDList`, `Titulo`, `FechaCreacion`, `IDCliente`) VALUES
(1, 'Favoritos', '2024-04-18', 1),
(2, 'Favoritos', '2024-05-15', 2),
(3, 'Favoritos', '2024-05-15', 3),
(4, 'Favoritos', '2024-05-17', 34),
(5, 'Rhythmicity Top Songs', '2024-05-15', 3),
(6, 'Mis canciones favoritas', '2024-05-17', 2),
(7, 'Sergio Favs', '2024-05-17', 2),
(26, 'Verano 2022', '2024-05-17', 34);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `PlaylistCanciones`
--

CREATE TABLE `PlaylistCanciones` (
  `IDAudio` int NOT NULL,
  `IDList` int NOT NULL,
  `FechaPlaylistCancion` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `PlaylistCanciones`
--

INSERT INTO `PlaylistCanciones` (`IDAudio`, `IDList`, `FechaPlaylistCancion`) VALUES
(4, 3, '2024-05-16'),
(7, 2, '2024-05-17'),
(7, 7, '2024-05-17'),
(8, 26, '2024-05-17'),
(9, 2, '2024-05-16'),
(11, 2, '2024-05-16'),
(12, 2, '2024-05-17'),
(12, 4, '2024-05-17'),
(13, 2, '2024-05-16'),
(13, 4, '2024-05-17'),
(14, 4, '2024-05-17'),
(18, 1, '2024-05-16'),
(20, 6, '2024-05-17'),
(20, 26, '2024-05-17'),
(21, 5, '2024-05-17'),
(21, 7, '2024-05-17'),
(21, 26, '2024-05-17'),
(22, 26, '2024-05-17'),
(23, 2, '2024-05-17'),
(24, 2, '2024-05-16'),
(25, 2, '2024-05-16'),
(26, 3, '2024-05-16'),
(27, 4, '2024-05-17'),
(27, 26, '2024-05-17');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Podcast`
--

CREATE TABLE `Podcast` (
  `IDAudio` int NOT NULL,
  `Colaboradores` varchar(99) DEFAULT NULL,
  `IDPodcaster` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Podcast`
--

INSERT INTO `Podcast` (`IDAudio`, `Colaboradores`, `IDPodcaster`) VALUES
(2, 'Papa Giorgio', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Podcaster`
--

CREATE TABLE `Podcaster` (
  `IDPodcaster` int NOT NULL,
  `NombreArtistico` varchar(30) NOT NULL,
  `Imagen` varchar(99) DEFAULT NULL,
  `Descripcion` varchar(999) NOT NULL,
  `AnoActivo` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Podcaster`
--

INSERT INTO `Podcaster` (`IDPodcaster`, `NombreArtistico`, `Imagen`, `Descripcion`, `AnoActivo`) VALUES
(1, 'Jordi Wild', '/src/img/jordiwild.jpg', 'Jorge Carrillo de Albornoz Torres, conocido como Jordi Wild o Giorgio, es un youtuber, podcaster y celebridad de internet español. Su canal de YouTube, El Rincón de Giorgio, fue creado en marzo de 2013 y actualmente tiene más de 11 millones de suscriptores', 2013);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Premium`
--

CREATE TABLE `Premium` (
  `IDCliente` int NOT NULL,
  `FechaCaducidad` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `tipo_cancion`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `tipo_cancion` (
`IDAudio` int
,`Nombre` varchar(30)
,`Duracion` time
,`Imagen` varchar(99)
,`Tipo` enum('Podcast','Cancion')
);

-- --------------------------------------------------------

--
-- Estructura para la vista `musicos_grupo`
--
DROP TABLE IF EXISTS `musicos_grupo`;

CREATE ALGORITHM=UNDEFINED DEFINER=`grupo06`@`localhost` SQL SECURITY DEFINER VIEW `musicos_grupo`  AS SELECT `Musico`.`IDMusico` AS `IDMusico`, `Musico`.`NombreArtistico` AS `NombreArtistico`, `Musico`.`Imagen` AS `Imagen`, `Musico`.`Caracteristica` AS `Caracteristica`, `Musico`.`Descripcion` AS `Descripcion`, `Musico`.`AnoActivo` AS `AnoActivo` FROM `Musico` WHERE (`Musico`.`Caracteristica` = 'Grupo') ;

-- --------------------------------------------------------

--
-- Estructura para la vista `musicos_solitas`
--
DROP TABLE IF EXISTS `musicos_solitas`;

CREATE ALGORITHM=UNDEFINED DEFINER=`grupo06`@`localhost` SQL SECURITY DEFINER VIEW `musicos_solitas`  AS SELECT `Musico`.`IDMusico` AS `IDMusico`, `Musico`.`NombreArtistico` AS `NombreArtistico`, `Musico`.`Imagen` AS `Imagen`, `Musico`.`Caracteristica` AS `Caracteristica`, `Musico`.`Descripcion` AS `Descripcion`, `Musico`.`AnoActivo` AS `AnoActivo` FROM `Musico` WHERE (`Musico`.`Caracteristica` = 'Solista') ;

-- --------------------------------------------------------

--
-- Estructura para la vista `tipo_cancion`
--
DROP TABLE IF EXISTS `tipo_cancion`;

CREATE ALGORITHM=UNDEFINED DEFINER=`grupo06`@`localhost` SQL SECURITY DEFINER VIEW `tipo_cancion`  AS SELECT `Audio`.`IDAudio` AS `IDAudio`, `Audio`.`Nombre` AS `Nombre`, `Audio`.`Duracion` AS `Duracion`, `Audio`.`Imagen` AS `Imagen`, `Audio`.`Tipo` AS `Tipo` FROM `Audio` WHERE (`Audio`.`Tipo` = 'Cancion') ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `Album`
--
ALTER TABLE `Album`
  ADD PRIMARY KEY (`IDAlbum`),
  ADD UNIQUE KEY `Titulo` (`Titulo`),
  ADD KEY `IDMusico` (`IDMusico`);

--
-- Indices de la tabla `Audio`
--
ALTER TABLE `Audio`
  ADD PRIMARY KEY (`IDAudio`);

--
-- Indices de la tabla `Cancion`
--
ALTER TABLE `Cancion`
  ADD PRIMARY KEY (`IDAudio`),
  ADD KEY `Cancion_ibfk_2` (`IDAlbum`);

--
-- Indices de la tabla `Cliente`
--
ALTER TABLE `Cliente`
  ADD PRIMARY KEY (`IDCliente`),
  ADD UNIQUE KEY `Usuario` (`Usuario`);

--
-- Indices de la tabla `Estadisticas`
--
ALTER TABLE `Estadisticas`
  ADD PRIMARY KEY (`IDAudio`);

--
-- Indices de la tabla `Gustos`
--
ALTER TABLE `Gustos`
  ADD PRIMARY KEY (`IDCliente`,`IDAudio`),
  ADD KEY `Gustos_ibfk_2` (`IDAudio`);

--
-- Indices de la tabla `Musico`
--
ALTER TABLE `Musico`
  ADD PRIMARY KEY (`IDMusico`),
  ADD UNIQUE KEY `NombreArtistico` (`NombreArtistico`);

--
-- Indices de la tabla `Playlist`
--
ALTER TABLE `Playlist`
  ADD PRIMARY KEY (`IDList`),
  ADD KEY `Playlist_ibfk_1` (`IDCliente`);

--
-- Indices de la tabla `PlaylistCanciones`
--
ALTER TABLE `PlaylistCanciones`
  ADD PRIMARY KEY (`IDAudio`,`IDList`),
  ADD KEY `PlaylistCanciones_ibfk_2` (`IDList`);

--
-- Indices de la tabla `Podcast`
--
ALTER TABLE `Podcast`
  ADD PRIMARY KEY (`IDAudio`),
  ADD KEY `Podcast_ibfk_2` (`IDPodcaster`);

--
-- Indices de la tabla `Podcaster`
--
ALTER TABLE `Podcaster`
  ADD PRIMARY KEY (`IDPodcaster`),
  ADD UNIQUE KEY `NombreArtistico` (`NombreArtistico`);

--
-- Indices de la tabla `Premium`
--
ALTER TABLE `Premium`
  ADD PRIMARY KEY (`IDCliente`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `Album`
--
ALTER TABLE `Album`
  MODIFY `IDAlbum` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT de la tabla `Audio`
--
ALTER TABLE `Audio`
  MODIFY `IDAudio` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT de la tabla `Cliente`
--
ALTER TABLE `Cliente`
  MODIFY `IDCliente` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT de la tabla `Musico`
--
ALTER TABLE `Musico`
  MODIFY `IDMusico` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `Playlist`
--
ALTER TABLE `Playlist`
  MODIFY `IDList` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT de la tabla `Podcaster`
--
ALTER TABLE `Podcaster`
  MODIFY `IDPodcaster` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `Album`
--
ALTER TABLE `Album`
  ADD CONSTRAINT `Album_ibfk_1` FOREIGN KEY (`IDMusico`) REFERENCES `Musico` (`IDMusico`);

--
-- Filtros para la tabla `Cancion`
--
ALTER TABLE `Cancion`
  ADD CONSTRAINT `Cancion_ibfk_1` FOREIGN KEY (`IDAudio`) REFERENCES `Audio` (`IDAudio`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Cancion_ibfk_2` FOREIGN KEY (`IDAlbum`) REFERENCES `Album` (`IDAlbum`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `Estadisticas`
--
ALTER TABLE `Estadisticas`
  ADD CONSTRAINT `Estadisticas_ibfk_1` FOREIGN KEY (`IDAudio`) REFERENCES `Audio` (`IDAudio`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `Gustos`
--
ALTER TABLE `Gustos`
  ADD CONSTRAINT `Gustos_ibfk_1` FOREIGN KEY (`IDCliente`) REFERENCES `Cliente` (`IDCliente`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Gustos_ibfk_2` FOREIGN KEY (`IDAudio`) REFERENCES `Audio` (`IDAudio`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `Playlist`
--
ALTER TABLE `Playlist`
  ADD CONSTRAINT `Playlist_ibfk_1` FOREIGN KEY (`IDCliente`) REFERENCES `Cliente` (`IDCliente`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `PlaylistCanciones`
--
ALTER TABLE `PlaylistCanciones`
  ADD CONSTRAINT `PlaylistCanciones_ibfk_1` FOREIGN KEY (`IDAudio`) REFERENCES `Audio` (`IDAudio`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `PlaylistCanciones_ibfk_2` FOREIGN KEY (`IDList`) REFERENCES `Playlist` (`IDList`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `Podcast`
--
ALTER TABLE `Podcast`
  ADD CONSTRAINT `Podcast_ibfk_1` FOREIGN KEY (`IDAudio`) REFERENCES `Audio` (`IDAudio`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Podcast_ibfk_2` FOREIGN KEY (`IDPodcaster`) REFERENCES `Podcaster` (`IDPodcaster`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `Premium`
--
ALTER TABLE `Premium`
  ADD CONSTRAINT `Premium_ibfk_1` FOREIGN KEY (`IDCliente`) REFERENCES `Cliente` (`IDCliente`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
