-- MySQL dump 10.13  Distrib 5.7.23, for Linux (x86_64)
--
-- Host: localhost    Database: paint
-- ------------------------------------------------------
-- Server version	5.7.23-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `provice_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_city_provice_idx` (`provice_id`),
  CONSTRAINT `fk_city_provice` FOREIGN KEY (`provice_id`) REFERENCES `provice` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,'kermanshah',1),(2,'biston',1);
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discount`
--

DROP TABLE IF EXISTS `discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(400) DEFAULT NULL,
  `description` text,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `discount` int(8) NOT NULL,
  `start_discount` timestamp NULL DEFAULT NULL,
  `end_discount` timestamp NULL DEFAULT NULL,
  `extra_field` text,
  `type` int(3) DEFAULT NULL,
  `repeat` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discount`
--

LOCK TABLES `discount` WRITE;
/*!40000 ALTER TABLE `discount` DISABLE KEYS */;
/*!40000 ALTER TABLE `discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discount_gallery_for_user`
--

DROP TABLE IF EXISTS `discount_gallery_for_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discount_gallery_for_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `discount_id` int(11) NOT NULL,
  `gallery_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `number_user` int(5) NOT NULL,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `this_has_discount_idx` (`discount_id`),
  KEY `this_has_gallery_idx` (`gallery_id`),
  KEY `this_has_user_idx` (`user_id`),
  CONSTRAINT `this_has_discount` FOREIGN KEY (`discount_id`) REFERENCES `discount` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `this_has_gallery` FOREIGN KEY (`gallery_id`) REFERENCES `gallery` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `this_has_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discount_gallery_for_user`
--

LOCK TABLES `discount_gallery_for_user` WRITE;
/*!40000 ALTER TABLE `discount_gallery_for_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `discount_gallery_for_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gallery`
--

DROP TABLE IF EXISTS `gallery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gallery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(500) DEFAULT NULL,
  `description` text,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `udpate_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `type` int(3) NOT NULL,
  `extra_field` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gallery`
--

LOCK TABLES `gallery` WRITE;
/*!40000 ALTER TABLE `gallery` DISABLE KEYS */;
INSERT INTO `gallery` VALUES (4,'tehran2',NULL,'2018-01-27 06:56:26','2018-01-27 07:01:05',0,NULL),(6,' ش2222این یک گالری برای تست می باشد',NULL,'2018-01-27 07:00:53','2018-01-27 07:00:53',0,NULL);
/*!40000 ALTER TABLE `gallery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gallery_cost`
--

DROP TABLE IF EXISTS `gallery_cost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gallery_cost` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(250) DEFAULT NULL,
  `extra_field` text,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `type` int(3) DEFAULT NULL,
  `price` float NOT NULL,
  `term_of_validity` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gallery_cost`
--

LOCK TABLES `gallery_cost` WRITE;
/*!40000 ALTER TABLE `gallery_cost` DISABLE KEYS */;
INSERT INTO `gallery_cost` VALUES (1,'اولین قیمت کالری',NULL,'2018-01-28 11:48:19','2018-01-28 11:48:19',NULL,2000,NULL);
/*!40000 ALTER TABLE `gallery_cost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gallery_has_gallery_cost`
--

DROP TABLE IF EXISTS `gallery_has_gallery_cost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gallery_has_gallery_cost` (
  `gallery_id` int(11) NOT NULL,
  `gallery_cost_id` int(11) NOT NULL,
  PRIMARY KEY (`gallery_id`,`gallery_cost_id`),
  KEY `this_has_gallery_cost_idx` (`gallery_cost_id`),
  CONSTRAINT `this_table_has_gallery` FOREIGN KEY (`gallery_id`) REFERENCES `gallery` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `this_table_has_gallery_cost` FOREIGN KEY (`gallery_cost_id`) REFERENCES `gallery_cost` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gallery_has_gallery_cost`
--

LOCK TABLES `gallery_has_gallery_cost` WRITE;
/*!40000 ALTER TABLE `gallery_has_gallery_cost` DISABLE KEYS */;
/*!40000 ALTER TABLE `gallery_has_gallery_cost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `judge_user`
--

DROP TABLE IF EXISTS `judge_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `judge_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `username` varchar(100) NOT NULL,
  `phone` varchar(100) NOT NULL,
  `email` varchar(150) DEFAULT NULL,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_login` timestamp NULL DEFAULT NULL,
  `user_code` varchar(150) NOT NULL,
  `password` varchar(150) DEFAULT NULL,
  `active` tinyint(1) NOT NULL,
  `delete_user` tinyint(1) NOT NULL,
  `gender` int(1) DEFAULT NULL,
  `type_user` int(3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `user_code_UNIQUE` (`user_code`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `judge_user`
--

LOCK TABLES `judge_user` WRITE;
/*!40000 ALTER TABLE `judge_user` DISABLE KEYS */;
INSERT INTO `judge_user` VALUES (5,NULL,NULL,'959595953','09187156889','959593@uahh.co','2018-01-30 11:42:30','2018-07-16 07:27:42','2018-07-16 07:27:42','80d86291-d488-45e8-b255-487b305a3962','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92',1,0,NULL,0),(7,NULL,NULL,'09187156889','09187156889','09187156889@uahh.co','2018-01-30 11:50:39','2018-02-03 09:19:05','2018-01-30 12:26:59','f3242646-aff2-4f29-b513-fda83681f1e0','e54da2b7051d2c813d5ca3ecf27e1fbb4f55c7862ca44005c860450a34dd55fc',1,0,NULL,0),(8,NULL,NULL,'091871568898','09187156889','09187156887@uahh.co','2018-02-03 08:58:07','2018-02-03 09:02:43','2018-02-03 08:58:07','2b7c32f5-c9a6-4fb8-a9a5-211b6c0cc254','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92',1,0,NULL,2),(9,NULL,NULL,'091871568823','09187156889','0918715682289@uahh.co','2018-02-03 09:06:27','2018-02-03 09:21:26','2018-02-03 09:06:27','95bea6fa-6864-4690-be32-fc6975a8177d','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92',1,1,NULL,2);
/*!40000 ALTER TABLE `judge_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `judgment`
--

DROP TABLE IF EXISTS `judgment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `judgment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `score` float NOT NULL,
  `question_id` int(11) NOT NULL,
  `racing_id` int(11) NOT NULL,
  `participants_id` int(11) NOT NULL,
  `judge_user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_judgment_question1_idx` (`question_id`),
  KEY `fk_judgment_racing1_idx` (`racing_id`),
  KEY `fk_judgment_participants1_idx` (`participants_id`),
  KEY `fk_judgment_judge_user1_idx` (`judge_user_id`),
  CONSTRAINT `fk_judgment_judge_user1` FOREIGN KEY (`judge_user_id`) REFERENCES `judge_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_judgment_participants1` FOREIGN KEY (`participants_id`) REFERENCES `participants` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_judgment_question1` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_judgment_racing1` FOREIGN KEY (`racing_id`) REFERENCES `racing` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `judgment`
--

LOCK TABLES `judgment` WRITE;
/*!40000 ALTER TABLE `judgment` DISABLE KEYS */;
/*!40000 ALTER TABLE `judgment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `participants`
--

DROP TABLE IF EXISTS `participants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `participants` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `age` int(3) DEFAULT NULL,
  `url` text,
  `address` varchar(500) DEFAULT NULL,
  `total_score` int(11) DEFAULT NULL,
  `is_selective` tinyint(1) NOT NULL,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL,
  `racing_id` int(11) DEFAULT NULL,
  `city_id` int(11) DEFAULT NULL,
  `birth_year` int(4) DEFAULT NULL,
  `birth_month` int(2) DEFAULT NULL,
  `birth_day` int(2) DEFAULT NULL,
  `gender` varchar(6) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `tag` varchar(100) DEFAULT NULL,
  `desciption` varchar(1000) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `display_time` varchar(50) DEFAULT NULL,
  `condition_type` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_participants_user1_idx` (`user_id`),
  KEY `fk_participants_racing1_idx` (`racing_id`),
  KEY `fk_participants_city1_idx` (`city_id`),
  CONSTRAINT `fk_participants_city1` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_participants_racing1` FOREIGN KEY (`racing_id`) REFERENCES `racing` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_participants_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participants`
--

LOCK TABLES `participants` WRITE;
/*!40000 ALTER TABLE `participants` DISABLE KEYS */;
INSERT INTO `participants` VALUES (16,'علی','خاکساری',NULL,'http://185.211.56.72:8080/img/2/1d2b4b82-cfb2-4c5e-b39d-0cde7c5e2f94.png',NULL,NULL,0,'2018-04-18 12:53:40','2018-04-18 12:53:40',5,2,NULL,1395,12,21,'boy','کرمانشاه','هفته سلامت','این اولین عکس برای مسابقه هفته سلامت تست می شود','دریافت شد',NULL,NULL),(17,'علی','خاکساری',NULL,'http://185.211.56.72:8080/img/2/d35ef64f-ca85-41f8-8d8a-2ea860591bea.png',NULL,NULL,0,'2018-04-18 13:09:05','2018-04-18 13:09:05',5,2,NULL,1395,12,21,'boy','کرمانشاه','هفته سلامت','دومین نقاشی هفته سلامت','دریافت شد',NULL,NULL),(18,'علی','خاکساری',NULL,'http://185.211.56.72:8080/img/2/bdd1c152-402a-41c6-9379-c9624aeb4f65.png',NULL,NULL,0,'2018-04-18 14:19:14','2018-04-18 14:19:14',5,2,NULL,1395,12,21,'boy','کرمانشاه','هفته سلامت','برای محسن','دریافت شد',NULL,NULL),(19,'زهرا','سهام',NULL,'http://185.211.56.72:8080/img/2/72644c79-5d65-427b-9331-63062172730c.png',NULL,NULL,0,'2018-04-21 04:33:48','2018-04-21 04:33:48',7,2,NULL,80,7,12,'girl','شیراز','تست درسا','این تصویر برای تست اپلیکیشن دنیای نقاشی است','دریافت شد',NULL,NULL),(20,'زهرا','سهام',NULL,'http://185.211.56.72:8080/img/2/c4358d7f-0f4b-4b2d-9994-42f87ebcb4c8.png',NULL,NULL,0,'2018-04-21 04:53:00','2018-04-21 04:53:00',7,2,NULL,80,7,12,'girl','','','','دریافت شد',NULL,NULL),(21,'زهرا','سهام',NULL,'http://185.211.56.72:8080/img/2/eafa5293-bc08-4b1b-bdf3-930e756408d0.png',NULL,NULL,0,'2018-04-21 04:55:36','2018-04-21 04:55:36',7,2,NULL,80,7,12,'girl','','','','دریافت شد',NULL,NULL),(22,'زهرا','سهام',NULL,'http://185.211.56.72:8080/img/2/22cc7844-0993-4d86-b49e-a949aed7cd5a.png',NULL,NULL,0,'2018-04-21 05:25:14','2018-04-21 05:25:14',7,2,NULL,80,7,12,'girl','','','','دریافت شد',NULL,NULL),(23,'امیر','رضایی',NULL,'http://185.211.56.72:8080/img/2/322d4fb1-3aaf-4c77-924a-0722a9b1d005.png',NULL,NULL,0,'2018-04-21 09:12:05','2018-04-21 09:12:05',9,2,NULL,89,10,28,'boy','تهران','فرزند','زیارت','دریافت شد',NULL,NULL),(24,'علی','عزیزی',NULL,'http://185.211.56.72:8080/img/2/6fec6a6b-58d0-49e2-a455-2792cdb19855.png',NULL,NULL,0,'2018-04-21 13:47:39','2018-04-21 13:47:39',10,2,NULL,90,9,28,'boy','تهران','کوه','ابالبالبع','دریافت شد',NULL,NULL),(25,'محمد','کاظمی فرد',NULL,'http://185.211.56.72:8080/img/2/2463c3d5-849c-4e5e-b26c-ed21ec98b605.png',NULL,NULL,0,'2018-04-23 05:21:31','2018-04-23 05:21:31',3,2,NULL,1361,4,22,'boy','کرمانشاه','عمس خوشگل','عکس','دریافت شد',NULL,NULL),(26,'g','f',NULL,'http://185.211.56.72:8080/img/2/2f0c6bd5-5bf1-4862-9fec-d73fbda70567.jpg',NULL,NULL,0,'2018-04-24 12:20:40','2018-04-24 12:20:40',11,2,NULL,1351,3,3,'boy','ب','ل','لل','دریافت شد',NULL,NULL),(27,'12test','test2',NULL,'http://185.211.56.72:8080/img/2/afcd4a7c-0140-4329-9c73-e097142f9dfa.png',NULL,NULL,0,'2018-04-25 06:01:41','2018-04-25 06:01:41',7,2,NULL,89,7,12,'girl','tehran','test test','ydhdfhdgn','دریافت شد',NULL,NULL),(28,'ه','غ',NULL,'http://185.211.56.72:8080/img/2/d1b8f788-3899-438c-8af3-df83da866835.png',NULL,NULL,0,'2018-04-25 06:10:39','2018-04-25 06:10:39',11,2,NULL,3,2,2,'boy','کرمانشا','ببی','یی','دریافت شد',NULL,NULL),(29,'ه','غ',NULL,'http://185.211.56.72:8080/img/2/40260a6b-e25f-4747-a470-7d5bb43168b7.png',NULL,NULL,0,'2018-04-25 06:11:34','2018-04-25 06:11:34',11,2,NULL,3,2,2,'boy','ه','ی','ی','دریافت شد',NULL,NULL),(30,'ددو','دد',NULL,'http://185.211.56.72:8080/img/2/d581f137-54af-4a4b-be97-57ef34a64259.png',NULL,NULL,0,'2018-04-25 06:15:23','2018-04-25 06:15:23',12,2,NULL,1235,5,5,'boy','تهران','خط خطی','برلا','دریافت شد',NULL,NULL),(31,'ه','غ',NULL,'http://185.211.56.72:8080/img/2/ac68ad2c-4f68-40b5-9db6-be0a64dd19be.png',NULL,NULL,0,'2018-04-25 06:17:35','2018-04-25 06:17:35',11,2,NULL,3,2,2,'boy','ف','کوه-اب','ف','دریافت شد',NULL,NULL),(32,'12test','test2',NULL,'http://185.211.56.72:8080/img/2/819410e9-0d29-4902-b8da-24f4efbce3f9.png',NULL,NULL,0,'2018-04-25 06:19:05','2018-04-25 06:19:05',7,2,NULL,89,7,12,'girl','تست','تست تست','تیلببلییبلبیز','دریافت شد',NULL,NULL),(33,'12test','test2',NULL,'http://185.211.56.72:8080/img/2/608934f6-7c5d-487d-9ed9-97d26ee24807.png',NULL,NULL,0,'2018-04-25 06:19:31','2018-04-25 06:19:31',7,2,NULL,89,7,12,'girl','test','test test','yrhhdbvdv','دریافت شد',NULL,NULL),(34,'ددو','دد',NULL,'http://185.211.56.72:8080/img/2/be49e670-fcf2-43f4-8ac4-4981488ccfe6.png',NULL,NULL,0,'2018-04-25 06:24:56','2018-04-25 06:24:56',12,2,NULL,1235,5,5,'boy','قلبد','لبد','زدببد','دریافت شد',NULL,NULL),(35,'ددو','دد',NULL,'http://185.211.56.72:8080/img/2/5ee5de0d-1a1b-4593-aabd-aff9a7c43d4a.png',NULL,NULL,0,'2018-04-25 06:25:10','2018-04-25 06:25:10',12,2,NULL,1235,5,5,'boy','فدبد','بدبدف','یریریر','دریافت شد',NULL,NULL),(36,'ددو','دد',NULL,'http://185.211.56.72:8080/img/2/e0433771-6528-4159-9a38-8096e2d1ea28.png',NULL,NULL,0,'2018-04-25 06:25:24','2018-04-25 06:25:24',12,2,NULL,1235,5,5,'boy','برفد','فلاعا','زریرقل','دریافت شد',NULL,NULL),(37,'ددو','دد',NULL,'http://185.211.56.72:8080/img/2/d1abac2e-05fb-4ecb-9627-565542ece8fa.png',NULL,NULL,0,'2018-04-25 06:25:40','2018-04-25 06:25:40',12,2,NULL,1235,5,5,'boy','ع۴لفد','بدفلفا','بییرا','دریافت شد',NULL,NULL),(38,'ه','غ',NULL,'http://185.211.56.72:8080/img/2/afa7d013-32da-4be6-af2f-112989abbdf5.png',NULL,NULL,0,'2018-04-25 06:26:31','2018-04-25 06:26:31',11,2,NULL,3,2,2,'boy','','','','دریافت شد',NULL,NULL),(39,'12test','test2',NULL,'http://185.211.56.72:8080/img/2/1a12c919-4ce6-4a50-aadc-8a82949b0ab8.png',NULL,NULL,0,'2018-04-25 06:30:46','2018-04-25 06:30:46',7,2,NULL,89,7,12,'girl','tsrs','tedgf rewsfg','rdgdsghh','دریافت شد',NULL,NULL),(40,'test','test1',NULL,'http://185.211.56.72:8080/img/2/c2d036fb-3216-406a-bb7b-699204de29d6.png',NULL,NULL,0,'2018-04-25 06:44:14','2018-04-25 06:44:14',7,2,NULL,70,7,2,'boy','تهران','سلامت','یلیثلفثلتب','دریافت شد',NULL,NULL),(41,'لل','عل',NULL,'http://185.211.56.72:8080/img/2/d2bc160a-b2c7-410f-93ee-36a751e178fa.png',NULL,NULL,0,'2018-04-28 13:43:02','2018-04-28 13:43:02',12,2,NULL,258,12,31,'boy','مممزز','یی','ززز','دریافت شد',NULL,NULL),(42,'علی','امیری',NULL,'http://185.211.56.72:8080/img/2/32f8c0e9-e731-4e17-bc21-9c03ec2b2340.png',NULL,NULL,0,'2018-04-28 14:39:36','2018-04-28 14:39:36',9,2,NULL,91,12,28,'boy','تهران','تهران','سلام','دریافت شد',NULL,NULL),(43,'علی','امیری',NULL,'http://185.211.56.72:8080/img/2/6d24dc44-2793-4a6a-ab23-576bb7d8a267.jpg',NULL,NULL,0,'2018-04-28 14:45:45','2018-04-28 14:45:45',9,2,NULL,91,12,28,'boy','سلام','تهران','تست','دریافت شد',NULL,NULL),(44,'علی','امیری',NULL,'http://185.211.56.72:8080/img/2/43d0658b-4766-49d2-904d-be16bf8e2e38.jpg',NULL,NULL,0,'2018-04-28 14:47:56','2018-04-28 14:47:56',9,2,NULL,91,12,28,'boy','تهرا','التنلبلو','تابورزبود','دریافت شد',NULL,NULL),(45,'محمد','کاظمی فرد',NULL,'http://185.211.56.72:8080/img/2/917476d4-a9aa-40de-b0a2-bde892f5c461.png',NULL,NULL,0,'2018-04-29 10:59:13','2018-04-29 10:59:13',6,2,NULL,1361,4,22,'boy','d','d','d','دریافت شد',NULL,NULL),(46,'غ','ع',NULL,'http://185.211.56.72:8080/img/2/0bd562f4-f1f9-4baa-b0ae-77d8190be753.png',NULL,NULL,0,'2018-04-29 11:15:07','2018-04-29 11:15:07',11,2,NULL,1,1,1,'boy','ه','ا','ه','دریافت شد',NULL,NULL),(47,'غ','ع',NULL,'http://185.211.56.72:8080/img/2/8a07b30a-343d-4f84-87af-edd827128135.png',NULL,NULL,0,'2018-04-29 11:15:21','2018-04-29 11:15:21',11,2,NULL,1,1,1,'boy','ه','ا','خ','دریافت شد',NULL,NULL),(48,'علی','امیری',NULL,'http://185.211.56.72:8080/img/2/1d427018-583f-4a4f-a90e-972c555d8ab2.jpg',NULL,NULL,0,'2018-04-30 06:56:15','2018-04-30 06:56:15',9,2,NULL,91,9,25,'boy','تهران','کار','جهت تست است','دریافت شد',NULL,NULL),(49,'علی','امیری',NULL,'http://185.211.56.72:8080/img/2/73244a81-f61b-43f0-ab98-e3f5769a3d70.jpg',NULL,NULL,0,'2018-04-30 06:59:17','2018-04-30 06:59:17',9,2,NULL,91,9,25,'boy','اتددی','ویتویتی','ایتدبوب','دریافت شد',NULL,NULL),(50,'تا','دت',NULL,'http://185.211.56.72:8080/img/2/82e640f6-8eaf-46fb-be40-ea03752998ff.jpg',NULL,NULL,0,'2018-04-30 07:05:07','2018-04-30 07:05:07',12,2,NULL,899,12,31,'boy','اا','دد','رر','دریافت شد',NULL,NULL),(51,'تا','دت',NULL,'http://185.211.56.72:8080/img/2/e5462afa-7840-49c4-86a0-fea986f1335d.jpg',NULL,NULL,0,'2018-04-30 07:06:13','2018-04-30 07:06:13',12,2,NULL,899,12,31,'boy','او','داا','او','دریافت شد',NULL,NULL),(52,'محمد','کاظمی فرد',NULL,'http://185.211.56.72:8080/img/2/c6fa104d-a9a7-4f02-87cd-ad1a49a4041b.png',NULL,NULL,0,'2018-04-30 07:08:18','2018-04-30 07:08:18',6,2,NULL,1361,4,22,'boy','0','0','0','دریافت شد',NULL,NULL),(53,'تا','دت',NULL,'http://185.211.56.72:8080/img/2/8db698b4-cfe7-48f8-ae13-0823d6b64478.jpg',NULL,NULL,0,'2018-04-30 07:22:26','2018-04-30 07:22:26',12,2,NULL,899,12,31,'boy','ات','لد','دد','دریافت شد',NULL,NULL),(54,'ع','ع',NULL,'http://185.211.56.72:8080/img/2/e2126899-5290-4a3c-8b3c-6259c54f112d.jpg',NULL,NULL,0,'2018-04-30 07:32:18','2018-04-30 07:32:18',11,2,NULL,2,2,2,'boy','ی','ف','ف','دریافت شد',NULL,NULL),(55,'d','d',NULL,'http://185.211.56.72:8080/img/2/c9ed05bc-2b61-4403-8f9d-e57660775a2e.jpg',NULL,NULL,0,'2018-04-30 11:54:10','2018-04-30 11:54:10',11,2,NULL,1,1,1,'boy','s','s','w','دریافت شد',NULL,NULL),(56,'علی','امیری',NULL,'http://185.211.56.72:8080/img/2/04d4dc9a-18df-42ed-899c-59b5ec6a2b26.jpg',NULL,NULL,0,'2018-04-30 12:13:17','2018-04-30 12:13:17',9,2,NULL,91,9,28,'boy','تهرد','کوه','تبخلتلبتبلل','دریافت شد',NULL,NULL),(57,'علی','امیری',NULL,'http://185.211.56.72:8080/img/2/f8a6c47e-6902-46dd-a531-0feda41d6854.jpg',NULL,NULL,0,'2018-04-30 13:49:24','2018-04-30 13:49:24',9,2,NULL,91,9,28,'boy','تهران','کوهستان','تصویر یک بز در کوهستان های اطراف تهران','دریافت شد',NULL,NULL),(58,'خ','خ',NULL,'http://185.211.56.72:8080/img/2/c9dd8264-422b-4a7b-b536-0f3ec3b5d3a7.jpg',NULL,NULL,0,'2018-05-01 08:05:25','2018-05-01 08:05:25',11,2,NULL,3,3,3,'girl','ه','ه','ه','دریافت شد',NULL,NULL),(59,'خ','خ',NULL,'http://185.211.56.72:8080/img/2/ef84e24d-bda4-4346-a592-264063e6de7a.png',NULL,NULL,0,'2018-05-01 08:07:31','2018-05-01 08:07:31',11,2,NULL,3,3,3,'girl','ی','ی','ی','دریافت شد',NULL,NULL),(60,'t','r',NULL,'http://185.211.56.72:8080/img/2/9038bd63-54e2-463e-a98d-7a43f9e76d98.jpg',NULL,NULL,0,'2018-05-01 12:28:04','2018-05-01 12:28:04',11,2,NULL,1,1,1,'boy','u','y','y','دریافت شد',NULL,NULL),(61,'شیما','سیفی',NULL,'http://185.211.56.72:8080/img/2/f46b7588-1e75-41f5-97e5-05e448b09b28.jpg',NULL,NULL,0,'2018-05-01 13:55:09','2018-05-01 13:55:09',16,2,NULL,90,6,2,'girl','کرمانشاه','هوای بارانی','هوا امروز بارانی بود','دریافت شد',NULL,NULL),(62,'شیما','سیفی',NULL,'http://185.211.56.72:8080/img/2/2d91cd9a-34d9-4d43-8db7-4a19f77c9e53.jpg',NULL,NULL,0,'2018-05-01 13:56:31','2018-05-01 13:56:31',16,2,NULL,90,6,2,'girl','کرمانشاه','هواب آفتابی','هوا امروز آفتابی است','دریافت شد',NULL,NULL),(63,'شیما','سیفی',NULL,'http://185.211.56.72:8080/img/2/2b670329-9600-47f1-a9fb-89866a0c7fd0.jpg',NULL,NULL,0,'2018-05-01 14:08:52','2018-05-01 14:08:52',16,2,NULL,90,6,2,'girl','کرمانشاه','هوای ابری','هوا ابری است','دریافت شد',NULL,NULL),(64,'علی','زارعی',NULL,'http://185.211.56.72:8080/img/2/cfa72f9f-b848-439a-a0f6-098b449fecab.jpg',NULL,NULL,0,'2018-05-01 18:02:24','2018-05-01 18:02:24',9,2,NULL,93,9,28,'boy','تهران','ترتا','لخلتنتر','دریافت شد',NULL,NULL),(65,'w','e',NULL,'http://185.211.56.72:8080/img/2/88b59ece-e440-4223-817e-1008817f9896.png',NULL,NULL,0,'2018-05-02 03:55:47','2018-05-02 03:55:47',11,2,NULL,1,1,1,'boy','ع','ا','ع','دریافت شد',NULL,NULL),(66,'ات','دا',NULL,'http://185.211.56.72:8080/img/2/1a7fea04-fed4-4df4-b9c4-6e1c3c0cb6df.png',NULL,NULL,0,'2018-05-02 20:20:23','2018-05-02 20:20:23',12,2,NULL,5564,12,25,'girl','بب','زز','زز','دریافت شد',NULL,NULL),(67,'ات','دا',NULL,'http://185.211.56.72:8080/img/2/d6507fd2-505c-4d90-a57e-c7d85d4d3618.png',NULL,NULL,0,'2018-05-02 20:20:45','2018-05-19 06:23:07',12,2,NULL,5564,12,25,'girl','دد','دد','ددد','بررسی شد',NULL,NULL),(68,'ات','دا',NULL,'http://185.211.56.72:8080/img/2/d5de7117-4e28-4916-8ad3-a41db0e0a818.png',NULL,NULL,0,'2018-05-02 20:20:59','2018-05-19 06:32:09',12,2,NULL,5564,12,25,'girl','رد','دد','دد','بررسی شد',NULL,NULL),(69,'ات','دا',NULL,'http://185.211.56.72:8080/img/2/c04bb905-87da-46e8-ab2d-fbb420190545.jpg',NULL,NULL,0,'2018-05-02 20:26:51','2018-05-02 20:26:51',12,2,NULL,5564,12,25,'girl','ها','وو','دو','دریافت شد',NULL,NULL),(70,'حسین','قنبری',NULL,'http://185.211.56.72:8080/img/2/9a913613-0959-4e33-bc1f-24fb995dc773.png',NULL,NULL,0,'2018-05-03 07:27:53','2018-05-03 07:27:53',3,2,NULL,1366,12,20,'boy','کرمانشاه','نقاش','تلاش','دریافت شد',NULL,NULL),(71,'حسین','قنبری',NULL,'http://185.211.56.72:8080/img/2/55e78fae-e0ae-4193-840d-911b65230de9.jpg',NULL,NULL,0,'2018-05-03 07:29:39','2018-05-03 07:29:39',3,2,NULL,1366,12,20,'boy','کرمانشاه','نقاشی','نقاشی','دریافت شد',NULL,NULL),(72,'حسین','قنبری',NULL,'http://185.211.56.72:8080/img/2/1c7b4005-fcf3-4fae-85a5-ebaa6fe05fab.png',NULL,NULL,0,'2018-05-03 07:31:19','2018-05-03 07:31:19',3,2,NULL,1366,12,20,'boy','تت','نن','نپ','دریافت شد',NULL,NULL),(73,'حسین','قنبری',NULL,'http://185.211.56.72:8080/img/2/b03a79b5-93f8-441f-933f-5e595ebba3d6.jpg',NULL,NULL,0,'2018-05-03 07:33:01','2018-05-03 07:33:01',3,2,NULL,1366,12,20,'boy','تت','هدن','ندت','دریافت شد',NULL,NULL),(74,'تتن','اهذذذ',NULL,'http://185.211.56.72:8080/img/2/e5ae6990-5b7b-4cbf-bab1-e02e3f8c2db3.png',NULL,NULL,0,'2018-05-03 08:52:43','2018-05-03 08:52:43',3,2,NULL,666,6,6,'boy','نن','نهن','تااد','دریافت شد',NULL,NULL),(75,'تت','پپنم',NULL,'http://185.211.56.72:8080/img/2/87afa03e-bf1b-4285-92fe-84505206cc6f.png',NULL,NULL,0,'2018-05-03 09:11:52','2018-05-03 09:11:52',3,2,NULL,66,12,31,'boy','تت','نه','ننن','دریافت شد',NULL,NULL),(76,'رر','اد',NULL,'http://185.211.56.72:8080/img/2/bd83631c-92af-4bb8-b401-362f45a979fa.png',NULL,NULL,0,'2018-05-03 18:27:21','2018-05-03 18:27:21',12,2,NULL,888,12,31,'girl','لد','بر','رر','دریافت شد',NULL,NULL),(77,'رر','اد',NULL,'http://185.211.56.72:8080/img/2/a2f6db38-e97f-4588-af7c-b6dd38bf19ad.jpg',NULL,NULL,0,'2018-05-03 18:28:22','2018-05-03 18:28:22',12,2,NULL,888,12,31,'girl','لا','دد','دد','دریافت شد',NULL,NULL),(78,'رر','اد',NULL,'http://185.211.56.72:8080/img/2/04418220-2299-421d-a996-1a048301770b.jpg',NULL,NULL,0,'2018-05-03 18:28:53','2018-05-03 18:28:53',12,2,NULL,888,12,31,'girl','دد','اا','دد','دریافت شد',NULL,NULL),(79,'رر','اد',NULL,'http://185.211.56.72:8080/img/2/dda0ebc4-18f9-4e80-b6ea-b952ec8476a6.jpg',NULL,NULL,0,'2018-05-03 18:29:50','2018-05-03 18:29:50',12,2,NULL,888,12,31,'girl','لا','وا','وو','دریافت شد',NULL,NULL),(80,'حسین','قنبری',NULL,'http://185.211.56.72:8080/img/2/e5e8ed4d-d702-462d-bf05-1cfdf388688f.jpg',NULL,NULL,0,'2018-05-03 20:01:24','2018-05-03 20:01:24',3,2,NULL,66,12,12,'boy','کرمانشاه','کرمانشاه','کرمانشاه','دریافت شد',NULL,NULL),(81,'رر','اد',NULL,'http://185.211.56.72:8080/img/2/13edbb07-97a1-4168-9451-c1e753503dfa.jpg',NULL,NULL,0,'2018-05-03 20:04:43','2018-05-03 20:04:43',12,2,NULL,888,12,31,'girl','خح','کن','نم','دریافت شد',NULL,NULL),(82,'حسین','قنبری',NULL,'http://185.211.56.72:8080/img/2/d44ad7b0-648d-4300-93ba-1ab1b5344d3a.jpg',NULL,NULL,0,'2018-05-03 20:05:31','2018-05-03 20:05:31',3,2,NULL,66,12,12,'boy','کرمانشاه','کرمانشاه','کرمانشاه','دریافت شد',NULL,NULL),(83,'رر','اد',NULL,'http://185.211.56.72:8080/img/2/4f1b859d-e663-4cbe-af94-2a1a6d1b808e.jpg',NULL,NULL,0,'2018-05-05 05:24:40','2018-05-05 05:24:40',12,2,NULL,888,12,31,'girl','bh','hh','bb','دریافت شد',NULL,NULL),(84,'رر','اد',NULL,'http://185.211.56.72:8080/img/2/2401ee29-61ac-4695-8e88-ad041ea0693a.jpg',NULL,NULL,0,'2018-05-05 05:25:39','2018-05-05 05:25:39',12,2,NULL,888,12,31,'girl','bh','bh','bb','دریافت شد',NULL,NULL),(85,'رر','اد',NULL,'http://185.211.56.72:8080/img/2/70a2aab9-1ca6-421d-b91b-d5ccd9a3f15a.png',NULL,NULL,0,'2018-05-05 05:29:36','2018-05-05 05:29:36',12,2,NULL,888,12,31,'girl','rf','fcv','fcc','دریافت شد',NULL,NULL),(86,'رر','اد',NULL,'http://185.211.56.72:8080/img/2/22455bb3-2624-4875-834c-efd1e38b1780.jpg',NULL,NULL,0,'2018-05-05 05:37:29','2018-05-05 05:37:29',12,2,NULL,888,12,31,'girl','6','6','6','دریافت شد',NULL,NULL),(87,'jik','hj',NULL,'http://185.211.56.72:8080/img/2/82beb87c-7958-4b6d-bb99-37046f12c73f.jpg',NULL,NULL,0,'2018-05-05 05:38:58','2018-05-05 05:38:58',12,2,NULL,8996,8,9,'boy','z','z','f','دریافت شد',NULL,NULL),(88,'w','e',NULL,'http://185.211.56.72:8080/img/2/8ce721dd-8209-4436-a14b-e4aa58f0d85f.jpg',NULL,NULL,0,'2018-05-05 05:41:33','2018-05-05 05:41:33',11,2,NULL,1,1,1,'boy','ع','ه','ه','دریافت شد',NULL,NULL),(89,'نیی','ننی',NULL,'http://185.211.56.72:8080/img/2/dc34084f-cba0-46ad-9aa2-4e19f10baeef.jpg',NULL,NULL,0,'2018-05-05 05:46:06','2018-05-05 05:46:06',3,2,NULL,99,12,31,'boy','نت','ته','ته','دریافت شد',NULL,NULL),(90,'سارا','احمدی',NULL,'http://185.211.56.72:8080/img/2/9eb724f9-d3da-4b0e-b45e-4b49bbd4edc0.jpg',NULL,NULL,0,'2018-05-05 05:52:26','2018-05-05 05:52:26',12,2,NULL,1385,12,31,'girl','تهران','ناپسند','لاو','دریافت شد',NULL,NULL),(91,'سارا','احمدی',NULL,'http://185.211.56.72:8080/img/2/fd831a44-196a-40eb-9a38-3e97c5392620.jpg',NULL,NULL,0,'2018-05-05 05:53:01','2018-05-05 05:53:01',12,2,NULL,1385,12,31,'girl','تیت','تبتت','تیت','دریافت شد',NULL,NULL),(92,'q','q',NULL,'http://185.211.56.72:8080/img/2/d48d5518-9c22-4f56-bf92-79f0850e2956.jpg',NULL,NULL,0,'2018-05-05 06:33:13','2018-05-05 06:33:13',11,2,NULL,1,1,1,'boy','y','y','y','دریافت شد',NULL,NULL),(93,'فریبا','نوری',NULL,'http://185.211.56.72:8080/img/2/c5cdab50-03f9-4e2d-97a5-6171111429f3.jpg',NULL,NULL,0,'2018-05-05 06:42:09','2018-05-05 06:42:09',16,2,NULL,71,4,4,'boy','تت','تت','تحح','دریافت شد',NULL,NULL),(94,'امیر','امیری',NULL,'http://185.211.56.72:8080/img/2/bc622daa-58b4-47ec-86de-be5187eb88b8.png',NULL,NULL,0,'2018-05-08 06:40:10','2018-05-08 06:40:10',9,2,NULL,1398,12,13,'boy','تهران','نماز','آموزش نماز به کودکان','دریافت شد',NULL,NULL),(95,'امیر','امیری',NULL,'http://185.211.56.72:8080/img/2/96b39f03-ed83-4b62-b848-5e0a81279d2f.jpg',NULL,NULL,0,'2018-05-08 06:44:02','2018-05-08 06:44:02',9,2,NULL,1398,12,13,'boy','تهران','کار','نمایی از میز محل کار','دریافت شد',NULL,NULL),(96,'ع','ا',NULL,'http://185.211.56.72:8080/img/2/a0092a36-2fce-4573-a845-a7cb00d979b3.jpg',NULL,NULL,0,'2018-05-12 09:18:08','2018-05-12 09:18:08',11,2,NULL,2,3,2,'boy','غ','ح','ه','دریافت شد',NULL,NULL),(97,'ایمانس','خاکساری',NULL,'http://185.211.56.72:8080/img/2/8d76ae5e-b33e-4939-a0d1-82453a294ef6.png',NULL,NULL,0,'2018-05-26 10:31:49','2018-05-26 10:31:49',3,2,NULL,1,9,17,'boy','dgffdgfg','dfgdfgd','dfgdfgd','دریافت شد','2008-12-12',0),(98,'test','test',NULL,'http://185.211.56.72:8080/img/2/d2f91549-99d2-4e2c-9421-44d8166b5c41.png',NULL,NULL,0,'2018-06-03 11:44:54','2018-06-03 11:44:54',25,2,NULL,1389,5,5,'boy','shiraz','test tesf','gsgdghfs','دریافت شد',NULL,0),(99,'یاسمن','کشاورزی',NULL,'http://185.211.56.72:8080/img/2/24829304-7322-4ac6-96b3-fcaff6e9842a.jpg',NULL,NULL,0,'2018-06-03 19:00:56','2018-06-03 19:00:56',26,2,NULL,1387,3,30,'girl','شیراز','گل زیبا','من با اپلیکیشن های شرکت درسا با فضای مجازی پاک برای کودکان آشنا شدم','دریافت شد',NULL,0),(100,'تست','درسا',NULL,'http://185.211.56.72:8080/img/2/909514ba-da79-4d29-b395-943e7770005b.jpg',NULL,NULL,0,'2018-06-03 21:04:17','2018-06-03 21:04:17',25,2,NULL,1389,8,2,'girl','تست','تست درسا','أین تصویر صرفا جهت تست اپلیکیشن بارگذاری شده است.','دریافت شد',NULL,0),(101,'سینا','ثابت',NULL,'http://185.211.56.72:8080/img/2/a584939e-b2a8-4845-9164-0bf6add43656.jpg',NULL,NULL,0,'2018-06-04 09:01:55','2018-06-04 09:01:55',29,2,NULL,1389,9,8,'boy','شیراز','آدمک و جوجه','با استفاده از اپلیکیشن دنیای نقاشی می توانم نقاشی های زیبایی بکشم.','دریافت شد',NULL,0),(102,'test','test22',NULL,'http://185.211.56.72:8080/img/2/7c57af8c-27d9-458e-89a2-5ab9dadc4840.jpg',NULL,NULL,0,'2018-06-09 12:28:55','2018-06-09 12:28:55',30,2,NULL,89,2,12,'boy','tsst','test','gdgdghd','دریافت شد',NULL,0),(103,'test','test22',NULL,'http://185.211.56.72:8080/img/2/d54320e2-39c1-4be1-ac1c-1266339dcb92.jpg',NULL,NULL,0,'2018-06-09 12:29:35','2018-06-09 12:29:35',30,2,NULL,89,2,12,'boy','شیراز','تست نقاشی','تست نقاشی','دریافت شد',NULL,0),(104,'تست','درسا',NULL,'http://185.211.56.72:8080/img/2/eb1e758e-5164-40b6-905e-817694b22e3c.jpg',NULL,NULL,0,'2018-06-11 06:14:22','2018-06-11 06:14:22',30,2,NULL,89,2,22,'boy','تست','تست درسا','خثخصخصحسحینسح','دریافت شد',NULL,0),(105,'تست','درسا22',NULL,'http://185.211.56.72:8080/img/2/0b0629c9-7eef-41bd-96b3-fe6479691018.jpg',NULL,NULL,0,'2018-06-11 06:30:21','2018-06-11 06:30:21',31,2,NULL,88,7,2,'boy','تست','تست درسا','تستستستتستیت','دریافت شد',NULL,0),(106,'ایمانس','خاکساری',NULL,'http://185.211.56.72:8080/img/2/7a12bb3e-1520-453b-94a0-5b93768d4332.png',NULL,NULL,0,'2018-06-13 09:23:37','2018-06-13 09:23:37',3,2,NULL,1,9,17,'boy','dgffdgfg','dfgdfgd','dfgdfgd','دریافت شد','2008-12-12',0),(107,'حسین','قنبری',NULL,'http://185.211.56.72:8080/img/2/388f7090-5628-43b7-8bb0-87e9eebe9d74.png',NULL,NULL,0,'2018-06-13 10:02:56','2018-06-13 10:02:56',3,2,NULL,1366,12,20,'boy','کرمانشاه','خط خطی','تدد','دریافت شد',NULL,0),(108,'مومو','تا',NULL,'http://185.211.56.72:8080/img/2/15abf7fc-563d-437e-88ce-666161510ce9.png',NULL,NULL,1,'2018-06-13 11:28:41','2018-06-13 12:10:40',12,2,NULL,889,12,31,'boy','تهدان','ااف','بلا','دریافت شد',NULL,1),(109,'مینا','ن',NULL,'http://185.211.56.72:8080/img/2/175355bb-aee9-45d8-a781-5274b73bceca.png',NULL,NULL,0,'2018-06-13 15:46:50','2018-06-13 15:46:50',33,2,NULL,1584,12,25,'boy','تهران','بهداشت','لا','دریافت شد',NULL,0),(110,'حسین','قنبری',NULL,'http://185.211.56.72:8080/img/2/2eb62771-b6bf-4f93-97a8-cd727c6e44aa.png',NULL,NULL,0,'2018-06-13 16:06:02','2018-06-13 16:06:02',3,2,NULL,66,12,20,'boy','کرمانشاه','خط خطی','خط خطی','دریافت شد',NULL,0),(111,'حسین','قنبری',NULL,'http://185.211.56.72:8080/img/2/b6a508fb-6dd4-48b5-b5b5-6b3d9643e6df.png',NULL,NULL,0,'2018-06-13 18:00:15','2018-06-13 18:00:15',3,2,NULL,66,12,20,'boy','کرمانشاه','خط خطی','خط خطی','دریافت شد',NULL,0),(112,'حسین','قنبری',NULL,'http://185.211.56.72:8080/img/2/77c42ffd-37bd-4f32-9a1d-55ca2f9629e6.png',NULL,NULL,0,'2018-06-13 18:03:29','2018-06-13 18:03:29',3,2,NULL,66,12,20,'boy','کرمانشاه','خط خطی','خط خطی','دریافت شد',NULL,0),(113,'7','8',NULL,'http://185.211.56.72:8080/img/2/f8608dd9-3e2e-4e79-95d2-fcae384803c0.png',NULL,NULL,0,'2018-06-17 10:38:55','2018-06-17 10:38:55',5,2,NULL,8,8,8,'boy','0','0','0','دریافت شد',NULL,0),(114,'تست۳۳','تست۲',NULL,'http://185.211.56.72:8080/img/2/09937d52-d2d1-4dd9-b609-6cafea3c4636.png',NULL,NULL,0,'2018-06-20 06:47:42','2018-06-20 06:47:42',30,2,NULL,89,12,22,'boy','شیراز','دخترزیبا','قتتثتثتستتس','دریافت شد',NULL,0);
/*!40000 ALTER TABLE `participants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_irancel`
--

DROP TABLE IF EXISTS `payment_irancel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_irancel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(1000) DEFAULT NULL,
  `msisdn` varchar(45) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `kind` varchar(1000) DEFAULT NULL,
  `startTimeMillis` bigint(100) DEFAULT NULL,
  `priceCurrencyCode` varchar(1000) DEFAULT NULL,
  `expiryTimeMillis` bigint(100) DEFAULT NULL,
  `autoRenewing` tinyint(1) DEFAULT NULL,
  `priceAmountMircos` bigint(100) DEFAULT NULL,
  `countryCode` varchar(1000) DEFAULT NULL,
  `developerPayload` varchar(1000) DEFAULT NULL,
  `paymentState` bigint(100) DEFAULT NULL,
  `cancelReason` bigint(100) DEFAULT NULL,
  `todayPayment` bigint(100) DEFAULT NULL,
  `cancelChannel` varchar(1000) DEFAULT NULL,
  `totalPayment` bigint(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idpayment_irancel_UNIQUE` (`id`),
  KEY `fk_payment_irancel_by_user_idx` (`user_id`),
  CONSTRAINT `fk_payment_irancel_by_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_irancel`
--

LOCK TABLES `payment_irancel` WRITE;
/*!40000 ALTER TABLE `payment_irancel` DISABLE KEYS */;
INSERT INTO `payment_irancel` VALUES (1,'sdalkfj;alkjfkasj','09187156889',3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,'sdalkfj;alkjfkasj','09187156889',3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `payment_irancel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `picture`
--

DROP TABLE IF EXISTS `picture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `picture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(500) DEFAULT NULL,
  `url_thumbnail` varchar(400) DEFAULT NULL,
  `url_main` varchar(400) DEFAULT NULL,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `gallery_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `picture_has_gallery_idx` (`gallery_id`),
  CONSTRAINT `picture_has_gallery` FOREIGN KEY (`gallery_id`) REFERENCES `gallery` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `picture`
--

LOCK TABLES `picture` WRITE;
/*!40000 ALTER TABLE `picture` DISABLE KEYS */;
INSERT INTO `picture` VALUES (5,'4545','http://localhost:8080/img/4beb74bb-2d42-4853-9860-a99a5301419e/db95dbbe-92dd-4bc6-94cf-d8fd0fca8ad4.jpeg','45454545324564564','2018-01-27 09:01:34','2018-01-27 10:21:40',4),(11,NULL,NULL,'http://asanbesan.com:8080/img/4beb74bb-2d42-4853-9860-a99a5301419e/eb681f84-e7e3-4f20-8371-8018e26a79cc.jpeg','2018-01-27 10:10:53','2018-01-27 10:10:53',4),(12,NULL,NULL,'http://localhost:8080/img/4beb74bb-2d42-4853-9860-a99a5301419e/9a4f756d-7aa1-4394-8c2e-e11acb5dd1a9.jpeg','2018-01-27 10:12:25','2018-01-27 10:12:25',4),(13,NULL,'http://localhost:8080/img/4beb74bb-2d42-4853-9860-a99a5301419e/3e7fc96d-d354-4dae-b068-d48d77f4552a.jpeg',NULL,'2018-01-27 10:13:18','2018-01-27 10:13:18',4);
/*!40000 ALTER TABLE `picture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_code` varchar(45) NOT NULL,
  `service_code` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `price` varchar(45) DEFAULT NULL,
  `url` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `product_code_UNIQUE` (`product_code`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'2210','3003','subscribe','خرید تمام محصولات گالری','300','http://localhost:8080/newFiles.zip'),(3,'22115522210','3003','subscribe','خرید تمام محصولات گالری','300','http://185.211.56.75:8080//c0edd0da-5d4b-48f3-95fb-476e0d4c78aa.jpeg'),(4,'22115210','3003','subscribe','خرید تمام محصولات گالری','300','http://localhost:8080/fa49eced-7460-4076-8108-9dbf0ce4ec4c.jpeg'),(5,'5555','3003','subscribe','خرید تمام محصولات گالری','300','http://localhost:8080/4a3e357d-ebf2-4ea3-8e43-f415b1bc3c21.jpeg'),(6,'666666','3003','subscribe','خرید تمام محصولات گالری','300','http://localhost:8080/img/bf1f0edd-27c2-49f5-8f99-d47ee14000f7.jpeg'),(7,'666','3003','subscribe','خرید تمام محصولات گالری','300','http://localhost:8080/img/8441f192-dbf0-47c4-9ff4-768ac647c32e.WW23JJ31ZRtjlOPM5BEV'),(8,'66776','3003','subscribe','خرید تمام محصولات گالری','300','http://localhost:8080/file/9d8bc0a1-41dc-4326-af10-3100484dc7d3.zip'),(9,'888','3003','subscribe','خرید تمام محصولات گالری','300','http://localhost:8080/file/501acf9e-f850-48ff-925e-f79ab41e37c2.zip');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `provice`
--

DROP TABLE IF EXISTS `provice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `provice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provice`
--

LOCK TABLES `provice` WRITE;
/*!40000 ALTER TABLE `provice` DISABLE KEYS */;
INSERT INTO `provice` VALUES (1,'kermanshah'),(3,'karj');
/*!40000 ALTER TABLE `provice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(1000) NOT NULL,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (2,'karj','2018-01-28 10:19:02','2018-01-28 10:19:02');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `racing`
--

DROP TABLE IF EXISTS `racing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `racing` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end` timestamp NULL DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `description` text,
  `extra_field` text,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `url` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `racing`
--

LOCK TABLES `racing` WRITE;
/*!40000 ALTER TABLE `racing` DISABLE KEYS */;
INSERT INTO `racing` VALUES (2,'2018-04-18 12:35:15','2012-04-23 18:25:44','هفته سلامت',NULL,NULL,'2018-01-27 11:18:59','2018-04-18 12:35:15',NULL);
/*!40000 ALTER TABLE `racing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scale_question_for_racing`
--

DROP TABLE IF EXISTS `scale_question_for_racing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scale_question_for_racing` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `scale` float NOT NULL DEFAULT '0',
  `question_id` int(11) NOT NULL,
  `racing_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_scale_question_for_racing_question1_idx` (`question_id`),
  KEY `fk_scale_question_for_racing_racing1_idx` (`racing_id`),
  CONSTRAINT `fk_scale_question_for_racing_question1` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_scale_question_for_racing_racing1` FOREIGN KEY (`racing_id`) REFERENCES `racing` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scale_question_for_racing`
--

LOCK TABLES `scale_question_for_racing` WRITE;
/*!40000 ALTER TABLE `scale_question_for_racing` DISABLE KEYS */;
INSERT INTO `scale_question_for_racing` VALUES (1,12,2,2),(3,13,2,2);
/*!40000 ALTER TABLE `scale_question_for_racing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(100) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `email` varchar(100) DEFAULT NULL,
  `extra_field` text,
  `confirm_code` varchar(45) DEFAULT NULL,
  `last_login` timestamp NULL DEFAULT NULL,
  `time_create_code_confrim` timestamp NULL DEFAULT NULL,
  `active` tinyint(1) NOT NULL,
  `delete_user` tinyint(1) NOT NULL,
  `status` int(3) DEFAULT NULL,
  `first_name` varchar(150) DEFAULT NULL,
  `last_name` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (3,'4beb74bb-2d42-4853-9860-a99a5301419e','09187156889','2017-12-18 08:39:19','2018-06-13 16:05:15',NULL,NULL,'8052',NULL,'2018-06-13 16:05:15',1,0,NULL,NULL,NULL),(5,'b5107da9-34b3-4356-9d3d-d31a654f253a','09374671016','2018-03-07 07:22:46','2018-05-27 08:42:18',NULL,NULL,'80',NULL,'2018-05-27 08:42:18',1,0,NULL,NULL,NULL),(6,'3c5ce00c-75e7-4efa-992c-013ea8623696','09183306035','2018-04-18 20:42:44','2018-04-29 09:30:03',NULL,NULL,'5098',NULL,'2018-04-29 09:30:03',1,0,NULL,NULL,NULL),(7,'b112cf87-8c3f-4a81-a15e-c5785274b197','09190754657','2018-04-21 04:09:12','2018-05-07 10:17:00',NULL,NULL,'1684',NULL,'2018-05-07 10:17:00',1,0,NULL,NULL,NULL),(8,'e8532eb4-1f1b-4c22-845d-aff8aa189128','09196976733','2018-04-21 09:08:34','2018-04-21 09:08:34',NULL,NULL,'9330',NULL,'2018-04-21 09:08:34',1,0,NULL,NULL,NULL),(9,'c1bec638-2d95-4758-95bf-a853442604e8','09120036616','2018-04-21 09:10:38','2018-05-05 05:52:08',NULL,NULL,'1830',NULL,'2018-05-05 05:52:08',1,0,NULL,NULL,NULL),(10,'74634f71-49af-4025-939b-5038327ff8ee','09360982545','2018-04-21 13:46:06','2018-04-21 13:46:06',NULL,NULL,'139',NULL,'2018-04-21 13:46:06',1,0,NULL,NULL,NULL),(11,'c8cc25e5-4db8-4fea-abe4-b215fb58ded0','09185575861','2018-04-23 06:47:07','2018-07-04 10:50:02',NULL,NULL,'2625',NULL,'2018-07-04 10:50:02',1,0,NULL,NULL,NULL),(12,'ac6a4964-cdf3-4a94-9da6-535b1ea95fa2','09015414596','2018-04-25 06:14:10','2018-06-22 20:45:15',NULL,NULL,'7823',NULL,'2018-06-22 20:45:15',1,0,NULL,NULL,NULL),(13,'2a1cc6bc-a8cf-41ed-9052-744bc8ccb4e6','09183399619','2018-04-27 15:19:50','2018-04-27 15:19:50',NULL,NULL,'6995',NULL,'2018-04-27 15:19:50',1,0,NULL,NULL,NULL),(14,'76b37e56-82b8-4959-978a-8dcc892115ef','9185575861','2018-04-30 06:00:34','2018-04-30 06:00:34',NULL,NULL,'7330',NULL,'2018-04-30 06:00:34',1,0,NULL,NULL,NULL),(15,'ae4eedd5-fa11-4414-b369-7a3d9c9bdd93','09120031666','2018-04-30 12:11:05','2018-04-30 12:11:05',NULL,NULL,'2691',NULL,'2018-04-30 12:11:05',1,0,NULL,NULL,NULL),(16,'3bfaa7b6-49c9-4bdb-8a79-b4bba411c5ac','09186733229','2018-05-01 13:52:05','2018-06-28 07:16:44',NULL,NULL,'8241',NULL,'2018-06-28 07:16:44',1,0,NULL,NULL,NULL),(17,'e1edc498-b62b-4372-9146-7c8f5da8757f','09151221165','2018-05-13 16:01:39','2018-05-13 16:01:39',NULL,NULL,'6682',NULL,'2018-05-13 16:01:39',1,0,NULL,NULL,NULL),(18,'e159870b-c35f-429b-a1ee-728cbd0a2083','988888587000009854488965580','2018-05-23 06:57:59','2018-05-23 07:05:00',NULL,NULL,'5999',NULL,'2018-05-23 07:05:00',1,0,NULL,NULL,NULL),(19,'de0a11c9-8d22-4431-9a99-e1c1ded40397','09015414576','2018-05-23 07:34:19','2018-05-23 07:34:19',NULL,NULL,'4632',NULL,'2018-05-23 07:34:19',1,0,NULL,NULL,NULL),(20,'a024254f-a7bb-4dc4-a9d0-ea222ad81f3e','67876898','2018-05-23 07:35:47','2018-05-23 07:35:47',NULL,NULL,'7068',NULL,'2018-05-23 07:35:47',1,0,NULL,NULL,NULL),(21,'f55d556d-15c8-47bd-b7e0-1fdae910f2cd','09187556889','2018-05-26 10:29:02','2018-05-26 10:29:02',NULL,NULL,'7627',NULL,'2018-05-26 10:29:02',1,0,NULL,NULL,NULL),(22,'b09eb8c4-8046-4d1f-af20-b9569e7cab3f','0934671016','2018-05-27 08:40:14','2018-05-27 08:40:14',NULL,NULL,'8826',NULL,'2018-05-27 08:40:14',1,0,NULL,NULL,NULL),(23,'36a9792e-228f-4c27-ac0a-c096fed3903c','09105414596','2018-05-30 07:44:05','2018-05-30 07:44:48',NULL,NULL,'3390',NULL,'2018-05-30 07:44:48',1,0,NULL,NULL,NULL),(24,'a52ac538-c7ca-4487-a0fb-5663e3e05fb9','09144619484','2018-05-30 18:54:24','2018-05-30 19:04:07',NULL,NULL,'1885',NULL,'2018-05-30 19:04:07',1,0,NULL,NULL,NULL),(25,'7e5c6d19-cb92-4921-870f-8a788f8b6351','09335683209','2018-06-03 11:43:19','2018-06-03 18:56:15',NULL,NULL,'9005',NULL,'2018-06-03 18:56:15',1,0,NULL,NULL,NULL),(26,'5f0efab1-6544-475e-94bc-f96d8736cc02','09129171367','2018-06-03 18:49:30','2018-06-03 18:50:59',NULL,NULL,'5161',NULL,'2018-06-03 18:50:59',1,0,NULL,NULL,NULL),(27,'7ea5aa5c-f199-4881-96ad-f4bdf0929515','09389542466','2018-06-04 08:47:26','2018-06-04 08:47:26',NULL,NULL,'3334',NULL,'2018-06-04 08:47:26',1,0,NULL,NULL,NULL),(28,'7b0cf05f-8981-48b9-880e-1cb00ae0f1a5','09173010243','2018-06-04 08:50:00','2018-06-04 08:50:00',NULL,NULL,'1721',NULL,'2018-06-04 08:50:00',1,0,NULL,NULL,NULL),(29,'05b1bf31-9921-4fae-94f3-8b271515d63f','09171082094','2018-06-04 08:51:30','2018-06-04 08:51:30',NULL,NULL,'8626',NULL,'2018-06-04 08:51:30',1,0,NULL,NULL,NULL),(30,'843bcb9b-706c-49f7-a0aa-e8a72811ae38','09018747922','2018-06-09 12:26:07','2018-06-20 06:37:25',NULL,NULL,'5504',NULL,'2018-06-20 06:37:25',1,0,NULL,NULL,NULL),(31,'e3810f33-dc6a-4933-93d4-3f85d79e23d8','09102471587','2018-06-11 06:28:22','2018-06-11 06:28:22',NULL,NULL,'373',NULL,'2018-06-11 06:28:22',1,0,NULL,NULL,NULL),(32,'16c6537a-082b-4820-bb5a-4212fc341d10','0905414596','2018-06-13 12:27:07','2018-06-13 12:27:07',NULL,NULL,'5535',NULL,'2018-06-13 12:27:07',1,0,NULL,NULL,NULL),(33,'4da48804-3057-4e3e-b336-187726f516e6','09183318929','2018-06-13 15:45:22','2018-06-13 15:45:22',NULL,NULL,'8081',NULL,'2018-06-13 15:45:21',1,0,NULL,NULL,NULL),(34,'1d466ceb-534d-40bf-b814-02b9c45623df','0','2018-06-20 12:19:10','2018-06-20 12:19:10',NULL,NULL,'3072',NULL,'2018-06-20 12:19:10',1,0,NULL,NULL,NULL),(35,'9c170b28-b4f4-4861-88a9-619241afcd3d','09353377517','2018-06-22 19:13:09','2018-06-22 19:13:09',NULL,NULL,'4914',NULL,'2018-06-22 19:13:09',1,0,NULL,NULL,NULL),(36,'9dbc435d-e549-4693-aa0e-df1363ddb91d','09127132556','2018-07-03 06:07:33','2018-07-03 06:07:33',NULL,NULL,'3644',NULL,'2018-07-03 06:07:33',1,0,NULL,NULL,NULL),(37,'046b1fcf-67dd-40ec-ba2c-9f6dcf8a7e61','09383813709','2018-07-08 03:14:30','2018-07-08 03:14:30',NULL,NULL,'6476',NULL,'2018-07-08 03:14:30',1,0,NULL,NULL,NULL),(38,'e33ed82b-f7fa-41ca-93d2-6e0f41cf1b05','09104557881','2018-07-09 17:01:15','2018-07-09 17:01:22',NULL,NULL,'7226',NULL,'2018-07-09 17:01:22',1,0,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_gallery`
--

DROP TABLE IF EXISTS `user_has_gallery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_gallery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `gallery_id` int(11) NOT NULL,
  `type` int(3) DEFAULT NULL,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `user_gallery_has_user_idx` (`user_id`),
  KEY `user_gallery_has_gallery_idx` (`gallery_id`),
  CONSTRAINT `user_gallery_has_gallery` FOREIGN KEY (`gallery_id`) REFERENCES `gallery` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_gallery_has_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_gallery`
--

LOCK TABLES `user_has_gallery` WRITE;
/*!40000 ALTER TABLE `user_has_gallery` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_has_gallery` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-08-01 17:10:19
