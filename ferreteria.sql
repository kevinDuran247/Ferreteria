-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-11-2023 a las 02:55:50
-- Versión del servidor: 10.4.27-MariaDB
-- Versión de PHP: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ferreteria`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `admin`
--

CREATE TABLE `admin` (
  `idusuario` int(11) NOT NULL,
  `usuario` varchar(100) NOT NULL,
  `contraseña` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `telefono` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `admin`
--

INSERT INTO `admin` (`idusuario`, `usuario`, `contraseña`, `email`, `telefono`) VALUES
(2, 'admin', '$2a$10$ifPwZSdvyXsYXtityMkkc.zbS15B7tXuKW606WVdmQSnKkEKecMwK', 'admin@gmail.com', '77854589');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE `categorias` (
  `idcategoria` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`idcategoria`, `nombre`) VALUES
(16, 'Herramientas electricas'),
(17, 'Fontaneria'),
(18, 'Uso cotidiano');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalleventas`
--

CREATE TABLE `detalleventas` (
  `iddetalle` int(11) NOT NULL,
  `idproducto` int(11) DEFAULT NULL,
  `idventa` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `idproducto` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `urlimagen` varchar(255) DEFAULT NULL,
  `stock` int(11) NOT NULL,
  `sminimo` int(11) NOT NULL,
  `precio` double(10,2) NOT NULL,
  `proveedor_id` int(11) DEFAULT NULL,
  `categoria_id` int(11) DEFAULT NULL
) ;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`idproducto`, `nombre`, `descripcion`, `urlimagen`, `stock`, `sminimo`, `precio`, `proveedor_id`, `categoria_id`) VALUES
(48, 'Tenaza', 'Tenaza naranja que corta alambre', 'https://res.cloudinary.com/agglobal-com/image/upload/c_scale,h_500/054814.jpg', 25, 20, 10.99, 14, 18),
(49, 'Pulidora Electrica', 'Pule bien genial', 'https://th.bing.com/th/id/R.00ddcf66c307ab36487ac952a02b2864?rik=6y13KsnBveFdzw&pid=ImgRaw&r=0', 5, 5, 59.99, 14, 16),
(50, 'Martillo', 'Martillo plano', 'https://ferremar.cl/wp-content/uploads/2020/11/MARTILLO.png', 44, 20, 4.99, 12, 18),
(52, 'Aspiradora', 'Aspira lo que sea', 'https://th.bing.com/th/id/R.a29a1227ffa97d15d5c8836b0a6f2cad?rik=aqAEQ70EFy11ZA&pid=ImgRaw&r=0', 14, 10, 59.99, 13, 16),
(65, 'Llave de agua', 'Llave de agua de bronce', 'https://th.bing.com/th/id/OIP.jW3eH0VhGM47RO2Nm9Gk6AHaHa?pid=ImgDet&rs=1', 98, 15, 3.25, 13, 17);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedores`
--

CREATE TABLE `proveedores` (
  `idproveedor` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `telefono` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `proveedores`
--

INSERT INTO `proveedores` (`idproveedor`, `nombre`, `telefono`) VALUES
(12, ' Freund', '61900274'),
(13, 'EPA EL Salvador', '24440587'),
(14, 'Trupper', '78245621');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vendedor`
--

CREATE TABLE `vendedor` (
  `Idvendedor` int(11) NOT NULL,
  `nombresApellidos` varchar(255) NOT NULL,
  `usuario` varchar(100) NOT NULL,
  `contraseña` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `vendedor`
--

INSERT INTO `vendedor` (`Idvendedor`, `nombresApellidos`, `usuario`, `contraseña`) VALUES
(1, 'Kevin Geovanni Duran Salazar', 'Vendedor', '$2a$10$P2EgafE70PDf3CCjF4jMberv5r/uwL6YeFxZcM6m9XejGa4nallUe');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `idventa` int(11) NOT NULL,
  `idvendedor` int(11) DEFAULT NULL,
  `total` decimal(10,2) NOT NULL,
  `fecha` date NOT NULL,
  `codigo` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`idusuario`);

--
-- Indices de la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`idcategoria`);

--
-- Indices de la tabla `detalleventas`
--
ALTER TABLE `detalleventas`
  ADD PRIMARY KEY (`iddetalle`),
  ADD KEY `detalleventas_ibfk_1` (`idventa`),
  ADD KEY `idproducto` (`idproducto`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`idproducto`),
  ADD KEY `proveedor_id` (`proveedor_id`),
  ADD KEY `categoria_id` (`categoria_id`);

--
-- Indices de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD PRIMARY KEY (`idproveedor`);

--
-- Indices de la tabla `vendedor`
--
ALTER TABLE `vendedor`
  ADD PRIMARY KEY (`Idvendedor`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`idventa`),
  ADD KEY `idvendedor` (`idvendedor`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `admin`
--
ALTER TABLE `admin`
  MODIFY `idusuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `idcategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de la tabla `detalleventas`
--
ALTER TABLE `detalleventas`
  MODIFY `iddetalle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=87;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `idproducto` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  MODIFY `idproveedor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `vendedor`
--
ALTER TABLE `vendedor`
  MODIFY `Idvendedor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `idventa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=62;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalleventas`
--
ALTER TABLE `detalleventas`
  ADD CONSTRAINT `detalleventas_ibfk_1` FOREIGN KEY (`idventa`) REFERENCES `ventas` (`idventa`),
  ADD CONSTRAINT `detalleventas_ibfk_2` FOREIGN KEY (`idproducto`) REFERENCES `productos` (`idproducto`) ON DELETE SET NULL;

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`proveedor_id`) REFERENCES `proveedores` (`idproveedor`),
  ADD CONSTRAINT `productos_ibfk_2` FOREIGN KEY (`categoria_id`) REFERENCES `categorias` (`idcategoria`);

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`idvendedor`) REFERENCES `vendedor` (`Idvendedor`) ON DELETE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
