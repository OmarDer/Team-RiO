-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 07, 2017 at 02:25 PM
-- Server version: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `weather_forecasting`
--
CREATE DATABASE IF NOT EXISTS `weather_forecasting` DEFAULT CHARACTER SET utf8 COLLATE utf8_croatian_ci;
USE `weather_forecasting`;

-- --------------------------------------------------------

--
-- Table structure for table `gradovi`
--

CREATE TABLE `gradovi` (
  `id` int(11) NOT NULL,
  `ime` varchar(50) COLLATE utf8_croatian_ci NOT NULL,
  `longitude` double NOT NULL,
  `latitude` double NOT NULL,
  `veci_centar` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_croatian_ci;

--
-- Dumping data for table `gradovi`
--

INSERT INTO `gradovi` (`id`, `ime`, `longitude`, `latitude`, `veci_centar`) VALUES
(1, 'Sarajevo', 18.4130763, 43.8562586, b'1'),
(2, 'Zenica', 17.9077432, 44.2034392, b'1'),
(3, 'Tuzla', 18.6734688, 44.53746109999999, b'1'),
(4, 'Mostar', 17.8077578, 43.34377480000001, b'1'),
(5, 'Banja Luka', 17.191, 44.7721811, b'1');

-- --------------------------------------------------------

--
-- Table structure for table `gradovi_prognoze`
--

CREATE TABLE `gradovi_prognoze` (
  `id` int(11) NOT NULL,
  `prognoza_id` int(11) NOT NULL,
  `grad_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_croatian_ci;

--
-- Dumping data for table `gradovi_prognoze`
--

INSERT INTO `gradovi_prognoze` (`id`, `prognoza_id`, `grad_id`) VALUES
(250, 250, 1),
(251, 251, 1),
(252, 252, 1),
(253, 253, 1),
(254, 254, 1),
(255, 255, 1),
(256, 256, 1),
(257, 257, 1),
(258, 258, 1),
(259, 259, 1),
(260, 260, 1),
(261, 261, 1),
(262, 262, 1),
(263, 263, 1),
(264, 264, 1),
(265, 265, 2),
(266, 266, 2),
(267, 267, 2),
(268, 268, 2),
(269, 269, 2),
(270, 270, 2),
(271, 271, 2),
(272, 272, 2),
(273, 273, 2),
(274, 274, 2),
(275, 275, 2),
(276, 276, 2),
(277, 277, 2),
(278, 278, 2),
(279, 279, 2),
(280, 280, 3),
(281, 281, 3),
(282, 282, 3),
(283, 283, 3),
(284, 284, 3),
(285, 285, 3),
(286, 286, 3),
(287, 287, 3),
(288, 288, 3),
(289, 289, 3),
(290, 290, 3),
(291, 291, 3),
(292, 292, 3),
(293, 293, 3),
(294, 294, 3),
(295, 295, 4),
(296, 296, 4),
(297, 297, 4),
(298, 298, 4),
(299, 299, 4),
(300, 300, 4),
(301, 301, 4),
(302, 302, 4),
(303, 303, 4),
(304, 304, 4),
(305, 305, 4),
(306, 306, 4),
(307, 307, 4),
(308, 308, 4),
(309, 309, 4),
(310, 310, 5),
(311, 311, 5),
(312, 312, 5),
(313, 313, 5),
(314, 314, 5),
(315, 315, 5),
(316, 316, 5),
(317, 317, 5),
(318, 318, 5),
(319, 319, 5),
(320, 320, 5),
(321, 321, 5),
(322, 322, 5),
(323, 323, 5),
(324, 324, 5);

-- --------------------------------------------------------

--
-- Table structure for table `historija_prognoze`
--

CREATE TABLE `historija_prognoze` (
  `id` int(11) NOT NULL,
  `vrijeme` varchar(30) COLLATE utf8_croatian_ci NOT NULL,
  `temp` varchar(30) COLLATE utf8_croatian_ci NOT NULL,
  `pritisak` varchar(30) COLLATE utf8_croatian_ci NOT NULL,
  `brzina_vjetra` varchar(30) COLLATE utf8_croatian_ci NOT NULL,
  `vlaznost_zraka` varchar(30) COLLATE utf8_croatian_ci NOT NULL,
  `datum` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_croatian_ci;

--
-- Dumping data for table `historija_prognoze`
--

INSERT INTO `historija_prognoze` (`id`, `vrijeme`, `temp`, `pritisak`, `brzina_vjetra`, `vlaznost_zraka`, `datum`) VALUES
(250, 'Partly cloudy', '1', '1030', '8', '65', '2016-12-13 00:00:00'),
(251, 'Partly cloudy', '6', '1028', '6', '28', '2016-12-14 00:00:00'),
(252, 'Light snow showers', '3', '1028', '5', '86', '2016-12-15 00:00:00'),
(253, 'Patchy light snow', '2', '1034', '5', '87', '2016-12-16 00:00:00'),
(254, 'Sunny', '5', '1039', '5', '58', '2016-12-17 00:00:00'),
(255, 'Sunny', '4', '1034', '3', '51', '2016-12-18 00:00:00'),
(256, 'Sunny', '5', '1033', '4', '69', '2016-12-19 00:00:00'),
(257, 'Sunny', '6', '1031', '5', '54', '2016-12-20 00:00:00'),
(258, 'Overcast', '6', '1031', '6', '59', '2016-12-21 00:00:00'),
(259, 'Sunny', '6', '1033', '6', '43', '2016-12-22 00:00:00'),
(260, 'Sunny', '6', '1034', '5', '44', '2016-12-23 00:00:00'),
(261, 'Sunny', '8', '1031', '3', '80', '2016-12-24 00:00:00'),
(262, 'Partly cloudy', '6', '1028', '5', '80', '2016-12-25 00:00:00'),
(263, 'Patchy rain possible', '3', '1028', '5', '87', '2016-12-26 00:00:00'),
(264, 'Heavy snow', '3', '1033', '6', '91', '2016-12-27 00:00:00'),
(265, 'Sunny', '4', '1031', '6', '74', '2016-12-13 00:00:00'),
(266, 'Cloudy', '6', '1027', '5', '41', '2016-12-14 00:00:00'),
(267, 'Mist', '5', '1029', '5', '90', '2016-12-15 00:00:00'),
(268, 'Partly cloudy', '4', '1034', '5', '87', '2016-12-16 00:00:00'),
(269, 'Sunny', '7', '1039', '3', '62', '2016-12-17 00:00:00'),
(270, 'Sunny', '3', '1034', '3', '43', '2016-12-18 00:00:00'),
(271, 'Sunny', '3', '1033', '3', '63', '2016-12-19 00:00:00'),
(272, 'Sunny', '4', '1031', '3', '63', '2016-12-20 00:00:00'),
(273, 'Overcast', '5', '1032', '5', '65', '2016-12-21 00:00:00'),
(274, 'Sunny', '3', '1033', '3', '57', '2016-12-22 00:00:00'),
(275, 'Sunny', '4', '1034', '5', '51', '2016-12-23 00:00:00'),
(276, 'Sunny', '3', '1031', '2', '82', '2016-12-24 00:00:00'),
(277, 'Partly cloudy', '3', '1028', '4', '81', '2016-12-25 00:00:00'),
(278, 'Patchy rain possible', '4', '1028', '5', '83', '2016-12-26 00:00:00'),
(279, 'Patchy light rain', '5', '1033', '5', '86', '2016-12-27 00:00:00'),
(280, 'Partly cloudy', '3', '1032', '5', '61', '2016-12-13 00:00:00'),
(281, 'Cloudy', '8', '1027', '5', '33', '2016-12-14 00:00:00'),
(282, 'Light snow', '4', '1029', '4', '85', '2016-12-15 00:00:00'),
(283, 'Sunny', '4', '1036', '4', '86', '2016-12-16 00:00:00'),
(284, 'Sunny', '6', '1039', '3', '68', '2016-12-17 00:00:00'),
(285, 'Sunny', '5', '1035', '3', '51', '2016-12-18 00:00:00'),
(286, 'Sunny', '4', '1034', '4', '71', '2016-12-19 00:00:00'),
(287, 'Sunny', '3', '1033', '4', '67', '2016-12-20 00:00:00'),
(288, 'Overcast', '2', '1034', '4', '71', '2016-12-21 00:00:00'),
(289, 'Sunny', '4', '1035', '4', '70', '2016-12-22 00:00:00'),
(290, 'Sunny', '3', '1036', '4', '57', '2016-12-23 00:00:00'),
(291, 'Sunny', '6', '1032', '3', '71', '2016-12-24 00:00:00'),
(292, 'Cloudy', '6', '1028', '7', '80', '2016-12-25 00:00:00'),
(293, 'Patchy rain possible', '8', '1028', '8', '84', '2016-12-26 00:00:00'),
(294, 'Patchy rain possible', '7', '1033', '6', '85', '2016-12-27 00:00:00'),
(295, 'Sunny', '8', '1029', '10', '61', '2016-12-13 00:00:00'),
(296, 'Cloudy', '10', '1028', '4', '37', '2016-12-14 00:00:00'),
(297, 'Sunny', '10', '1027', '9', '74', '2016-12-15 00:00:00'),
(298, 'Sunny', '7', '1032', '11', '70', '2016-12-16 00:00:00'),
(299, 'Cloudy', '11', '1037', '9', '49', '2016-12-17 00:00:00'),
(300, 'Sunny', '12', '1033', '4', '39', '2016-12-18 00:00:00'),
(301, 'Overcast', '11', '1032', '5', '45', '2016-12-19 00:00:00'),
(302, 'Sunny', '12', '1029', '7', '33', '2016-12-20 00:00:00'),
(303, 'Cloudy', '12', '1029', '10', '52', '2016-12-21 00:00:00'),
(304, 'Sunny', '12', '1031', '9', '46', '2016-12-22 00:00:00'),
(305, 'Sunny', '13', '1032', '12', '47', '2016-12-23 00:00:00'),
(306, 'Sunny', '14', '1029', '9', '65', '2016-12-24 00:00:00'),
(307, 'Partly cloudy', '13', '1027', '8', '66', '2016-12-25 00:00:00'),
(308, 'Partly cloudy', '15', '1028', '7', '74', '2016-12-26 00:00:00'),
(309, 'Patchy light rain', '8', '1032', '11', '78', '2016-12-27 00:00:00'),
(310, 'Sunny', '4', '1031', '5', '77', '2016-12-13 00:00:00'),
(311, 'Partly cloudy', '9', '1027', '3', '60', '2016-12-14 00:00:00'),
(312, 'Partly cloudy', '6', '1029', '5', '92', '2016-12-15 00:00:00'),
(313, 'Partly cloudy', '4', '1035', '5', '88', '2016-12-16 00:00:00'),
(314, 'Sunny', '6', '1039', '3', '73', '2016-12-17 00:00:00'),
(315, 'Sunny', '7', '1034', '3', '52', '2016-12-18 00:00:00'),
(316, 'Sunny', '4', '1034', '4', '66', '2016-12-19 00:00:00'),
(317, 'Sunny', '3', '1032', '4', '70', '2016-12-20 00:00:00'),
(318, 'Cloudy', '2', '1033', '4', '74', '2016-12-21 00:00:00'),
(319, 'Partly cloudy', '3', '1034', '3', '74', '2016-12-22 00:00:00'),
(320, 'Sunny', '4', '1035', '2', '64', '2016-12-23 00:00:00'),
(321, 'Sunny', '6', '1031', '4', '72', '2016-12-24 00:00:00'),
(322, 'Partly cloudy', '6', '1028', '3', '79', '2016-12-25 00:00:00'),
(323, 'Partly cloudy', '9', '1028', '5', '76', '2016-12-26 00:00:00'),
(324, 'Patchy light drizzle', '5', '1033', '6', '85', '2016-12-27 00:00:00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `gradovi`
--
ALTER TABLE `gradovi`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `gradovi_prognoze`
--
ALTER TABLE `gradovi_prognoze`
  ADD PRIMARY KEY (`id`),
  ADD KEY `prognoza_id` (`prognoza_id`),
  ADD KEY `grad_id` (`grad_id`);

--
-- Indexes for table `historija_prognoze`
--
ALTER TABLE `historija_prognoze`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `gradovi`
--
ALTER TABLE `gradovi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `gradovi_prognoze`
--
ALTER TABLE `gradovi_prognoze`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=325;
--
-- AUTO_INCREMENT for table `historija_prognoze`
--
ALTER TABLE `historija_prognoze`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=325;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `gradovi_prognoze`
--
ALTER TABLE `gradovi_prognoze`
  ADD CONSTRAINT `gradovi_prognoze_ibfk_1` FOREIGN KEY (`prognoza_id`) REFERENCES `historija_prognoze` (`id`),
  ADD CONSTRAINT `gradovi_prognoze_ibfk_2` FOREIGN KEY (`grad_id`) REFERENCES `gradovi` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
