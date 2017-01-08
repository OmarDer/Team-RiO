-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 08, 2017 at 02:46 AM
-- Server version: 5.5.38
-- PHP Version: 5.3.10-1ubuntu3.25

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `weather_forecasting`
--
CREATE DATABASE `weather_forecasting` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `weather_forecasting`;

-- --------------------------------------------------------

--
-- Table structure for table `gradovi`
--

CREATE TABLE IF NOT EXISTS `gradovi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ime` varchar(50) NOT NULL,
  `longitude` double NOT NULL,
  `latitude` double NOT NULL,
  `veci_centar` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=33 ;

--
-- Dumping data for table `gradovi`
--

INSERT INTO `gradovi` (`id`, `ime`, `longitude`, `latitude`, `veci_centar`) VALUES
(1, 'Sarajevo', 18.4130763, 43.8562586, b'1'),
(2, 'Zenica', 17.9077432, 44.2034392, b'1'),
(3, 'Tuzla', 18.6734688, 44.53746109999999, b'1'),
(4, 'Mostar', 17.8077578, 43.34377480000001, b'1'),
(5, 'Banja Luka', 17.191, 44.7721811, b'1'),
(6, 'Bihac', 15.8685645, 44.8119628, b'1'),
(7, 'Trebinje', 18.3502638, 42.7081585, b'1'),
(8, 'Visegrad', 19.2934518, 43.78344990000001, b'1'),
(9, 'Doboj', 18.0602921, 44.8004192, b'1'),
(10, 'Bugojno', 17.4498275, 44.0562486, b'1'),
(11, 'Livno', 17.0076892, 43.8249858, b'1'),
(12, 'Sanski Most', 16.664612, 44.7639581, b'0'),
(13, 'Travnik', 17.6566916, 44.2272107, b'0'),
(14, 'Konjic', 17.9590172, 43.6536202, b'0'),
(15, 'Neum', 17.6185727, 42.9230437, b'0'),
(16, 'Jablanica', 17.760399, 43.6577331, b'0'),
(17, 'Mrkonjic Grad', 17.0868199, 44.4186959, b'0'),
(18, 'Drvar', 16.3812914, 44.371114, b'0'),
(19, 'Gradacac', 18.425887, 44.8775521, b'0'),
(20, 'Bijeljina', 19.2150224, 44.75695109999999, b'0'),
(21, 'Gorazde', 18.9748537, 43.6685443, b'0'),
(22, 'Foca', 18.7772645, 43.505478, b'0'),
(23, 'Stolac', 17.9592754, 43.08515149999999, b'0'),
(24, 'Ljubinje', 18.07765, 42.95546059999999, b'0'),
(25, 'Derventa', 17.9070412, 44.9764185, b'0'),
(26, 'Gradiska', 17.2544232, 45.1465976, b'0'),
(27, 'Kakanj', 18.1178262, 44.128014, b'0'),
(28, 'Vlasenica', 18.9418196, 44.17997740000001, b'0'),
(29, 'Visoko', 18.1798837, 43.98876509999999, b'0'),
(30, 'Fojnica', 17.8966344, 43.96382879999999, b'0'),
(31, 'Srebrenica', 19.2997218, 44.1039762, b'0'),
(32, 'Velika Kladusa', 15.8065454, 45.1838373, b'0');

-- --------------------------------------------------------

--
-- Table structure for table `gradovi_prognoze`
--

CREATE TABLE IF NOT EXISTS `gradovi_prognoze` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prognoza_id` int(11) NOT NULL,
  `grad_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `prognoza_id` (`prognoza_id`),
  KEY `grad_id` (`grad_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=16 ;

--
-- Dumping data for table `gradovi_prognoze`
--

INSERT INTO `gradovi_prognoze` (`id`, `prognoza_id`, `grad_id`) VALUES
(1, 1, 1),
(2, 2, 1),
(3, 3, 1),
(4, 4, 1),
(5, 5, 1),
(6, 6, 1),
(7, 7, 1),
(8, 8, 1),
(9, 9, 1),
(10, 10, 1),
(11, 11, 1),
(12, 12, 1),
(13, 13, 1),
(14, 14, 1),
(15, 15, 1);

-- --------------------------------------------------------

--
-- Table structure for table `historija_prognoze`
--

CREATE TABLE IF NOT EXISTS `historija_prognoze` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vrijeme` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `temp` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `pritisak` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `brzina_vjetra` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `vlaznost_zraka` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `datum` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=16 ;

--
-- Dumping data for table `historija_prognoze`
--

INSERT INTO `historija_prognoze` (`id`, `vrijeme`, `temp`, `pritisak`, `brzina_vjetra`, `vlaznost_zraka`, `datum`) VALUES
(1, 'Partly cloudy', '6', '1028', '5', '80', '2016-12-25 00:00:00'),
(2, 'Light drizzle', '4', '1029', '5', '87', '2016-12-26 00:00:00'),
(3, 'Patchy heavy snow', '3', '1032', '7', '87', '2016-12-27 00:00:00'),
(4, 'Moderate or heavy snow showers', '0', '1032', '17', '88', '2016-12-28 00:00:00'),
(5, 'Heavy snow', '-2', '1035', '15', '88', '2016-12-29 00:00:00'),
(6, 'Moderate snow', '-3', '1038', '9', '86', '2016-12-30 00:00:00'),
(7, 'Partly cloudy', '1', '1034', '3', '73', '2016-12-31 00:00:00'),
(8, 'Overcast', '2', '1026', '4', '78', '2017-01-01 00:00:00'),
(9, 'Mist', '3', '1022', '8', '88', '2017-01-02 00:00:00'),
(10, 'Heavy snow', '-2', '1022', '7', '98', '2017-01-03 00:00:00'),
(11, 'Fog', '2', '1015', '7', '97', '2017-01-04 00:00:00'),
(12, 'Heavy snow', '-2', '1011', '12', '98', '2017-01-05 00:00:00'),
(13, 'Heavy snow', '-11', '1023', '17', '97', '2017-01-06 00:00:00'),
(14, 'Moderate snow', '-14', '1030', '9', '97', '2017-01-07 00:00:00'),
(15, 'Freezing fog', '-10', '1028', '5', '92', '2017-01-08 00:00:00');

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
