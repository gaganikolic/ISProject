CREATE DATABASE  IF NOT EXISTS `podsistem3` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `podsistem3`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: podsistem3
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `gledanje`
--

DROP TABLE IF EXISTS `gledanje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gledanje` (
  `IdGledanje` int NOT NULL AUTO_INCREMENT,
  `DatumVremePocetkaGledanja` datetime NOT NULL,
  `SekundeNastavljanja` int DEFAULT NULL,
  `SekundeOdgledane` int DEFAULT NULL,
  `IdVideo` int NOT NULL,
  `KorisnikEmail` varchar(45) NOT NULL,
  PRIMARY KEY (`IdGledanje`),
  KEY `FK_IdVideo_Gledanje_idx` (`IdVideo`),
  KEY `FK_KorisnikEmail_Gledanje_idx` (`KorisnikEmail`),
  CONSTRAINT `FK_IdVideo_Gledanje` FOREIGN KEY (`IdVideo`) REFERENCES `video` (`IdVideo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_KorisnikEmail_Gledanje` FOREIGN KEY (`KorisnikEmail`) REFERENCES `korisnik` (`Email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gledanje`
--

LOCK TABLES `gledanje` WRITE;
/*!40000 ALTER TABLE `gledanje` DISABLE KEYS */;
INSERT INTO `gledanje` VALUES (1,'2024-07-07 14:25:01',20,20,1,'marko.stancevic'),(2,'2024-07-07 15:05:50',20,20,1,'marko.stancevic'),(3,'2024-07-07 15:38:23',50,50,2,'nadja.matulovic'),(4,'2024-07-07 15:38:53',40,40,3,'dragan.nik');
/*!40000 ALTER TABLE `gledanje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik` (
  `Ime` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Godiste` varchar(45) NOT NULL,
  `Pol` varchar(45) NOT NULL,
  `IdMesto` int NOT NULL,
  PRIMARY KEY (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES ('Dragan','dragan.nik','2003','M',2),('Ana','lazarevic.ana','2002','Z',3),('Marko','marko.stancevic','2001','M',1),('Nadja','nadja.matulovic','2001','Z',5);
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ocena`
--

DROP TABLE IF EXISTS `ocena`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ocena` (
  `IdOcena` int NOT NULL AUTO_INCREMENT,
  `Ocena` int NOT NULL,
  `DatumVremeOcenjivanja` datetime NOT NULL,
  `IdVideo` int NOT NULL,
  `KorisnikEmail` varchar(45) NOT NULL,
  PRIMARY KEY (`IdOcena`),
  KEY `FK_IdVideo_Ocena_idx` (`IdVideo`),
  KEY `FK_KorisnikEmail_Ocena_idx` (`KorisnikEmail`),
  CONSTRAINT `FK_IdVideo_Ocena` FOREIGN KEY (`IdVideo`) REFERENCES `video` (`IdVideo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_KorisnikEmail_Ocena` FOREIGN KEY (`KorisnikEmail`) REFERENCES `korisnik` (`Email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ocena`
--

LOCK TABLES `ocena` WRITE;
/*!40000 ALTER TABLE `ocena` DISABLE KEYS */;
INSERT INTO `ocena` VALUES (1,5,'2024-07-07 03:04:33',1,'marko.stancevic'),(3,5,'2024-07-07 03:08:02',2,'dragan.nik'),(4,5,'2024-07-07 03:08:32',3,'dragan.nik'),(5,5,'2024-07-07 03:09:42',1,'lazarevic.ana'),(6,4,'2024-07-07 03:09:58',2,'marko.stancevic'),(7,4,'2024-07-07 03:10:11',3,'lazarevic.ana'),(8,5,'2024-07-07 15:40:11',5,'dragan.nik');
/*!40000 ALTER TABLE `ocena` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paket`
--

DROP TABLE IF EXISTS `paket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paket` (
  `IdPaket` int NOT NULL AUTO_INCREMENT,
  `TrenutnaCena` int NOT NULL,
  PRIMARY KEY (`IdPaket`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paket`
--

LOCK TABLES `paket` WRITE;
/*!40000 ALTER TABLE `paket` DISABLE KEYS */;
INSERT INTO `paket` VALUES (1,1000),(2,1500),(3,2000),(4,2500);
/*!40000 ALTER TABLE `paket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pretplata`
--

DROP TABLE IF EXISTS `pretplata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pretplata` (
  `IdPretplata` int NOT NULL AUTO_INCREMENT,
  `Trajanje` int NOT NULL,
  `DatumVremePocetka` datetime NOT NULL,
  `Cena` int NOT NULL,
  `IdPaket` int NOT NULL,
  `KorisnikEmail` varchar(45) NOT NULL,
  PRIMARY KEY (`IdPretplata`),
  KEY `FK_KorisnikEmail_Pretplata_idx` (`KorisnikEmail`),
  KEY `FK_IdPaket_Pretplata_idx` (`IdPaket`),
  CONSTRAINT `FK_IdPaket_Pretplata` FOREIGN KEY (`IdPaket`) REFERENCES `paket` (`IdPaket`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_KorisnikEmail_Pretplata` FOREIGN KEY (`KorisnikEmail`) REFERENCES `korisnik` (`Email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pretplata`
--

LOCK TABLES `pretplata` WRITE;
/*!40000 ALTER TABLE `pretplata` DISABLE KEYS */;
INSERT INTO `pretplata` VALUES (1,30,'2024-07-07 03:02:22',1000,1,'dragan.nik'),(2,30,'2024-07-07 03:02:42',1500,2,'lazarevic.ana'),(3,30,'2024-07-07 03:02:55',2000,3,'marko.stancevic'),(4,30,'2024-07-07 15:36:35',2500,4,'nadja.matulovic');
/*!40000 ALTER TABLE `pretplata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `video`
--

DROP TABLE IF EXISTS `video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video` (
  `IdVideo` int NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(45) NOT NULL,
  `Trajanje` int NOT NULL,
  `DatumVremePostavljanja` datetime NOT NULL,
  `VlasnikEmail` varchar(45) NOT NULL,
  PRIMARY KEY (`IdVideo`),
  KEY `FK_IdKorisnik_Video_idx` (`VlasnikEmail`),
  CONSTRAINT `FK_IdKorisnik_Video` FOREIGN KEY (`VlasnikEmail`) REFERENCES `korisnik` (`Email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video`
--

LOCK TABLES `video` WRITE;
/*!40000 ALTER TABLE `video` DISABLE KEYS */;
INSERT INTO `video` VALUES (1,'Spajdermen',100,'2024-07-07 02:55:39','dragan.nik'),(2,'Supermen',110,'2024-07-07 02:56:24','lazarevic.ana'),(3,'Betmen',110,'2024-07-07 02:56:40','marko.stancevic'),(5,'Kapetan Amerika',120,'2024-07-07 15:35:07','nadja.matulovic');
/*!40000 ALTER TABLE `video` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-07 15:48:30
