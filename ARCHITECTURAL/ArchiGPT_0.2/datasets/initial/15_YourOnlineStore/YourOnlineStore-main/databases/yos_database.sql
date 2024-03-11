-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Dic 10, 2022 alle 12:21
-- Versione del server: 10.4.22-MariaDB
-- Versione PHP: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";
CREATE DATABASE IF NOT EXISTS yos_database;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `yos_database`
--
-- create the databases
-- --------------------------------------------------------

--
-- Struttura della tabella `product`
--

CREATE TABLE yos_database.`product` (
  `product_id` int(11) NOT NULL,
  `cost` float DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `disponibility` int(11) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO yos_database.`product` (`product_id`, `cost`, `description`, `image_url`, `product_name`, `disponibility`) VALUES
(1, 6.5, 'Pizza molto buona', 'https://th.bing.com/th/id/OIP.ftH-auP-grYS19fiNNQMdgHaFS?w=264&h=189&c=7&r=0&o=5&dpr=1.25&pid=1.7', 'Pizza', 20),
(2, 4, 'Kebab turco', 'https://th.bing.com/th/id/OIP.9MwgD7WitBQTP3qSaC6qGwHaHa?w=180&h=180&c=7&r=0&o=5&dpr=1.25&pid=1.7', 'Kebab', 14),
(3, 15, 'Piatto di pasta carbonara', 'https://th.bing.com/th/id/OIP.YLRgZ2i9mHrq5qz5jHJDOAHaE8?w=241&h=180&c=7&r=0&o=5&dpr=1.25&pid=1.7', 'Pasta Carbonara', 10),
(4, 120, 'Felpa Ralph Lauren', 'https://th.bing.com/th/id/OIP.L8l3ruKV_LZHGk3B8adZlgHaHa?w=195&h=195&c=7&r=0&o=5&dpr=1.25&pid=1.7', 'Felpa Ralph Lauren', 5),
(5, 50, 'Nike Air Force 1', 'https://th.bing.com/th/id/OIP.Z5Xt_CJtd_rIkKvnDYfUBgHaEU?w=310&h=180&c=7&r=0&o=5&dpr=1.25&pid=1.7', 'Nike Air Force 1', 50),
(6, 25, 'Maglietta Levi\'s', 'https://th.bing.com/th/id/OIP.RFHQmqUCh0DRA0IzjFiUZgHaHa?w=208&h=208&c=7&r=0&o=5&dpr=1.25&pid=1.7', 'Maglietta Levi\'s', 20);
--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `product`
--
ALTER TABLE yos_database.`product`
  ADD PRIMARY KEY (`product_id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `product`
--
ALTER TABLE yos_database.`product`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
