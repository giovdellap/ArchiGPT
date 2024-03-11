-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Dic 10, 2022 alle 12:20
-- Versione del server: 10.4.22-MariaDB
-- Versione PHP: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";
CREATE DATABASE IF NOT EXISTS store;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `store`
--
-- create the databases

-- --------------------------------------------------------

--
-- Struttura della tabella `order_item`
--

CREATE TABLE store.`order_item` (
  `ordine_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `amount` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `order_item`
--

INSERT INTO store.`order_item` (`ordine_id`, `product_id`, `amount`) VALUES
(1, 1, 10),
(1, 2, 4);

-- --------------------------------------------------------

--
-- Struttura della tabella `ordine`
--

CREATE TABLE store.`ordine` (
  `ordine_id` int(11) NOT NULL,
  `address` varchar(80) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `total_cost` float DEFAULT NULL,
  `utente_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `ordine`
--

INSERT INTO store.`ordine` (`ordine_id`, `address`, `date`, `total_cost`, `utente_id`) VALUES
(1, 'via Verdello, 2', NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `product`
--

CREATE TABLE store.`product` (
  `product_id` int(11) NOT NULL,
  `cost` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `product`
--

INSERT INTO store.`product` (`product_id`, `cost`) VALUES
(1, 6.5),
(2, 4),
(3, 15),
(4, 120),
(5, 50),
(6, 25);

-- --------------------------------------------------------

--
-- Struttura della tabella `utente`
--

CREATE TABLE store.`utente` (
  `utente_id` int(11) NOT NULL,
  `email` varchar(80) DEFAULT NULL,
  `is_admin` tinyint(1) DEFAULT NULL,
  `name` varchar(80) DEFAULT NULL,
  `oauth_id` varchar(80) DEFAULT NULL,
  `surname` varchar(80) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO store.`utente` (`utente_id`, `email`, `is_admin`, `name`, `oauth_id`, `surname`) VALUES
(1, 'paolo99.caruso@gmail.com', 1, 'Paolo', '113075988806136472546', 'Caruso');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `order_item`
--
ALTER TABLE store.`order_item`
  ADD PRIMARY KEY (`ordine_id`,`product_id`),
  ADD KEY `FK551losx9j75ss5d6bfsqvijna` (`product_id`);

--
-- Indici per le tabelle `ordine`
--
ALTER TABLE store.`ordine`
  ADD PRIMARY KEY (`ordine_id`),
  ADD KEY `FKdg320enjlk6khf1wx5x8o28qx` (`utente_id`);

--
-- Indici per le tabelle `product`
--
ALTER TABLE store.`product`
  ADD PRIMARY KEY (`product_id`);

--
-- Indici per le tabelle `utente`
--
ALTER TABLE store.`utente`
  ADD PRIMARY KEY (`utente_id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `ordine`
--
ALTER TABLE store.`ordine`
  MODIFY `ordine_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `utente`
--
ALTER TABLE store.`utente`
  MODIFY `utente_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `order_item`
--
ALTER TABLE store.`order_item`
  ADD CONSTRAINT `FK551losx9j75ss5d6bfsqvijna` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  ADD CONSTRAINT `FKbx1kxae27qi3680md3a6yie50` FOREIGN KEY (`ordine_id`) REFERENCES `ordine` (`ordine_id`);

--
-- Limiti per la tabella `ordine`
--
ALTER TABLE store.`ordine`
  ADD CONSTRAINT `FKdg320enjlk6khf1wx5x8o28qx` FOREIGN KEY (`utente_id`) REFERENCES `utente` (`utente_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
