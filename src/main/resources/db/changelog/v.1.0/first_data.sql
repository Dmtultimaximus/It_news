-- MySQL dump 10.13  Distrib 8.0.21, for Linux (x86_64)
--
-- Host: localhost    Database: db_example
-- ------------------------------------------------------
-- Server version	8.0.21-0ubuntu0.20.04.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comment_entity`
--

DROP TABLE IF EXISTS `comment_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_entity` (
  `comment_id` bigint NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `likes` varchar(255) DEFAULT NULL,
  `news` bigint DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FK7u6osru73338guaca8ukops8l` (`user_id`),
  CONSTRAINT `FK7u6osru73338guaca8ukops8l` FOREIGN KEY (`user_id`) REFERENCES `user_entity` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_entity`
--

LOCK TABLES `comment_entity` WRITE;
/*!40000 ALTER TABLE `comment_entity` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1),(1),(1),(1),(1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news_entity`
--

DROP TABLE IF EXISTS `news_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `news_entity` (
  `news_id` bigint NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `newsname` varchar(255) DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `url_img` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`news_id`),
  KEY `FKtljlj49s46ae7r3vj1gfqb9fh` (`user_id`),
  CONSTRAINT `FKtljlj49s46ae7r3vj1gfqb9fh` FOREIGN KEY (`user_id`) REFERENCES `user_entity` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news_entity`
--

LOCK TABLES `news_entity` WRITE;
/*!40000 ALTER TABLE `news_entity` DISABLE KEYS */;
/*!40000 ALTER TABLE `news_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiry_date` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_entity_user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKks5iqpcw1827vx017qpymtv2d` (`user_entity_user_id`),
  CONSTRAINT `FKks5iqpcw1827vx017qpymtv2d` FOREIGN KEY (`user_entity_user_id`) REFERENCES `user_entity` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_entity`
--

DROP TABLE IF EXISTS `user_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_entity` (
  `user_id` bigint NOT NULL,
  `created` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_entity`
--

LOCK TABLES `user_entity` WRITE;
/*!40000 ALTER TABLE `user_entity` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_like_comment_entity`
--

DROP TABLE IF EXISTS `user_like_comment_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_like_comment_entity` (
  `like_id` bigint NOT NULL,
  `were_added_like` bigint DEFAULT NULL,
  `who_added_like` bigint DEFAULT NULL,
  PRIMARY KEY (`like_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_like_comment_entity`
--

LOCK TABLES `user_like_comment_entity` WRITE;
/*!40000 ALTER TABLE `user_like_comment_entity` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_like_comment_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_raiting_news_entity`
--

DROP TABLE IF EXISTS `user_raiting_news_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_raiting_news_entity` (
  `rating_id` bigint NOT NULL,
  `where_added_rating` bigint DEFAULT NULL,
  `who_added_rating` bigint DEFAULT NULL,
  PRIMARY KEY (`rating_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_raiting_news_entity`
--

LOCK TABLES `user_raiting_news_entity` WRITE;
/*!40000 ALTER TABLE `user_raiting_news_entity` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_raiting_news_entity` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-30 12:34:03
