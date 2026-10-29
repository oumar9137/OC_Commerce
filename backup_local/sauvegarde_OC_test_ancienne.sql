-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: gestion_commerce
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `achat`
--

DROP TABLE IF EXISTS `achat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `achat` (
  `id_achat` int NOT NULL AUTO_INCREMENT,
  `date_achat` date NOT NULL,
  `id_fournisseur` int NOT NULL,
  `id_utilisateur` int NOT NULL,
  PRIMARY KEY (`id_achat`),
  KEY `fk_achat_fournisseur` (`id_fournisseur`),
  KEY `fk_achat_utilisateur` (`id_utilisateur`),
  CONSTRAINT `fk_achat_fournisseur` FOREIGN KEY (`id_fournisseur`) REFERENCES `fournisseur` (`id_fournisseur`),
  CONSTRAINT `fk_achat_utilisateur` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id_utilisateur`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `achat`
--

LOCK TABLES `achat` WRITE;
/*!40000 ALTER TABLE `achat` DISABLE KEYS */;
INSERT INTO `achat` VALUES (1,'2026-09-25',1,1),(2,'2026-09-25',2,1),(3,'2026-09-25',3,1),(4,'2026-09-25',1,1),(5,'2026-09-25',1,1),(6,'2026-09-25',1,1),(7,'2026-09-25',7,1),(9,'2026-09-25',7,1),(10,'2026-09-25',1,1),(11,'2026-09-25',1,1);
/*!40000 ALTER TABLE `achat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categorie`
--

DROP TABLE IF EXISTS `categorie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorie` (
  `id_categorie` int NOT NULL AUTO_INCREMENT,
  `libelle_categorie` varchar(100) NOT NULL,
  PRIMARY KEY (`id_categorie`),
  UNIQUE KEY `libelle_categorie` (`libelle_categorie`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorie`
--

LOCK TABLES `categorie` WRITE;
/*!40000 ALTER TABLE `categorie` DISABLE KEYS */;
INSERT INTO `categorie` VALUES (3,'Accessoire informatique'),(7,'Composant Informatique'),(5,'Imprimante'),(8,'Logiciel'),(1,'Ordinateur'),(6,'Reseau'),(4,'Stockage'),(2,'Téléphone');
/*!40000 ALTER TABLE `categorie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `id_client` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) DEFAULT NULL,
  `telephone` varchar(30) DEFAULT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_client`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (3,'Manjant','Camara','79898978','Mamaribougou'),(4,'Camara','Fadima','75040755','Kaye'),(5,'Fatouma','Berthe','76549089','KAYE'),(6,'Adama','Camara','90908989','Bamako'),(7,'Mouhamed Camara','Camara',NULL,NULL),(8,'Mamadou','Barry',NULL,NULL),(9,'Fatim','Berthe',NULL,NULL);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detail_achat`
--

DROP TABLE IF EXISTS `detail_achat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detail_achat` (
  `id_detail_achat` int NOT NULL AUTO_INCREMENT,
  `id_achat` int NOT NULL,
  `id_produit` int NOT NULL,
  `quantite` int NOT NULL,
  `prix_achat` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id_detail_achat`),
  KEY `fk_detait_achat_achat` (`id_achat`),
  KEY `fk_detail_achat_produit` (`id_produit`),
  CONSTRAINT `fk_detail_achat_produit` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id_produit`),
  CONSTRAINT `fk_detait_achat_achat` FOREIGN KEY (`id_achat`) REFERENCES `achat` (`id_achat`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detail_achat`
--

LOCK TABLES `detail_achat` WRITE;
/*!40000 ALTER TABLE `detail_achat` DISABLE KEYS */;
INSERT INTO `detail_achat` VALUES (1,1,3,15,120000.00),(2,2,1,5,4000.00),(3,3,1,2,300.00),(4,4,3,5,100000.00),(5,5,3,1,90000.00),(6,6,3,6,120000.00),(7,7,6,15000,20000.00),(8,9,8,12,25000.00),(9,10,4,1,50000.00),(10,11,3,5,100000.00);
/*!40000 ALTER TABLE `detail_achat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detail_vente`
--

DROP TABLE IF EXISTS `detail_vente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detail_vente` (
  `id_detail_vente` int NOT NULL AUTO_INCREMENT,
  `id_vente` int NOT NULL,
  `id_produit` int NOT NULL,
  `quantite` int NOT NULL,
  `prix_unitaire` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id_detail_vente`),
  KEY `fk_detait_vente_vente` (`id_vente`),
  KEY `fk_detail_vente_produit` (`id_produit`),
  CONSTRAINT `fk_detail_vente_produit` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id_produit`),
  CONSTRAINT `fk_detait_vente_vente` FOREIGN KEY (`id_vente`) REFERENCES `vente` (`id_vente`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detail_vente`
--

LOCK TABLES `detail_vente` WRITE;
/*!40000 ALTER TABLE `detail_vente` DISABLE KEYS */;
INSERT INTO `detail_vente` VALUES (1,1,3,5,100000.00),(2,2,1,1,5000.00),(3,3,4,1,50000.00),(4,4,3,15,150000.00),(5,5,7,5,25000.00),(6,6,9,10,10000.00),(7,7,3,5,150000.00),(8,8,1,9,5000.00),(9,9,7,10,5000.00),(10,10,3,10,10000000.00),(11,11,9,30,300000.00),(12,12,7,5,3000000.00),(13,13,6,20,50000000.00),(14,14,7,10,300000.00),(15,15,3,1,150000.00),(16,16,9,1,10000.00),(17,17,3,5,150000.00),(18,18,9,5,10000.00),(19,19,9,4,10000.00),(20,20,3,2,150000.00),(21,21,10,1,5000.00),(22,22,10,5,5000.00);
/*!40000 ALTER TABLE `detail_vente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fournisseur`
--

DROP TABLE IF EXISTS `fournisseur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fournisseur` (
  `id_fournisseur` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(150) NOT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `telephone` varchar(50) DEFAULT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_fournisseur`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fournisseur`
--

LOCK TABLES `fournisseur` WRITE;
/*!40000 ALTER TABLE `fournisseur` DISABLE KEYS */;
INSERT INTO `fournisseur` VALUES (1,'Camara','Bamba','66940492','Kita'),(2,'Camara','Fotoumata','96884457','Sebenikoro'),(3,'Camara','Fadima','75040755','Mamaribougou'),(5,'Oumar','Traore','90789876','Bamako'),(6,'Mamady','Camara','79602041',NULL),(7,'Mandjan','Camara','6757867',NULL),(8,'Madou','Traore','90789876','Bamako');
/*!40000 ALTER TABLE `fournisseur` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produit`
--

DROP TABLE IF EXISTS `produit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produit` (
  `id_produit` int NOT NULL AUTO_INCREMENT,
  `designation` varchar(100) NOT NULL,
  `prix_achat` decimal(10,2) NOT NULL,
  `prix_vente` decimal(10,2) NOT NULL,
  `stock` int NOT NULL DEFAULT '0',
  `seuil_minimal` int NOT NULL DEFAULT '0',
  `id_categorie` int NOT NULL,
  PRIMARY KEY (`id_produit`),
  KEY `fk_produit_categorie` (`id_categorie`),
  CONSTRAINT `fk_produit_categorie` FOREIGN KEY (`id_categorie`) REFERENCES `categorie` (`id_categorie`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produit`
--

LOCK TABLES `produit` WRITE;
/*!40000 ALTER TABLE `produit` DISABLE KEYS */;
INSERT INTO `produit` VALUES (1,'Charger',3000.00,5000.00,0,1,3),(3,'HP',100000.00,150000.00,32,1,1),(4,'TECNO A20',50000.00,120000.00,15,0,2),(6,'Baterie',5000.00,7000.00,0,5,1),(7,'Logiel',100000.00,150000.00,0,5,1),(8,'Imprimante',15000.00,25000.00,20,5,1),(9,'Disque',5000.00,10000.00,0,0,3),(10,'Sourie',5000.00,10000.00,2994,5,7);
/*!40000 ALTER TABLE `produit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utilisateur` (
  `id_utilisateur` int NOT NULL AUTO_INCREMENT,
  `nom_utilisateur` varchar(100) NOT NULL,
  `mot_de_passe` varchar(255) NOT NULL,
  `profil` varchar(50) NOT NULL,
  PRIMARY KEY (`id_utilisateur`),
  UNIQUE KEY `nom_utilisateur` (`nom_utilisateur`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utilisateur`
--

LOCK TABLES `utilisateur` WRITE;
/*!40000 ALTER TABLE `utilisateur` DISABLE KEYS */;
INSERT INTO `utilisateur` VALUES (1,'admin','9137','Admin');
/*!40000 ALTER TABLE `utilisateur` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vente`
--

DROP TABLE IF EXISTS `vente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vente` (
  `id_vente` int NOT NULL AUTO_INCREMENT,
  `date_vente` date NOT NULL,
  `id_client` int NOT NULL,
  `id_utilisateur` int NOT NULL,
  PRIMARY KEY (`id_vente`),
  KEY `fk_vente_client` (`id_client`),
  KEY `fk_vente_utilisateur` (`id_utilisateur`),
  CONSTRAINT `fk_vente_client` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_client`),
  CONSTRAINT `fk_vente_utilisateur` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id_utilisateur`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vente`
--

LOCK TABLES `vente` WRITE;
/*!40000 ALTER TABLE `vente` DISABLE KEYS */;
INSERT INTO `vente` VALUES (1,'2026-09-25',6,1),(2,'2026-09-25',4,1),(3,'2026-09-26',3,1),(4,'2026-09-26',8,1),(5,'2026-09-26',9,1),(6,'2026-09-26',6,1),(7,'2026-09-26',3,1),(8,'2026-09-26',3,1),(9,'2026-09-26',5,1),(10,'2026-09-26',6,1),(11,'2026-09-26',6,1),(12,'2026-09-26',6,1),(13,'2026-09-26',6,1),(14,'2026-09-26',6,1),(15,'2026-09-26',6,1),(16,'2026-09-26',6,1),(17,'2026-09-26',4,1),(18,'2026-09-26',6,1),(19,'2026-09-26',5,1),(20,'2026-09-26',3,1),(21,'2026-09-26',4,1),(22,'2026-09-26',6,1);
/*!40000 ALTER TABLE `vente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-09-29 11:23:10
