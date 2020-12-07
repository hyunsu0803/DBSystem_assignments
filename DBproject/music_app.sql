-- MariaDB dump 10.17  Distrib 10.5.6-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: music_app
-- ------------------------------------------------------
-- Server version	10.5.6-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `music_app`
--

/*!40000 DROP DATABASE IF EXISTS `music_app`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `music_app` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `music_app`;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `name` varchar(20) NOT NULL,
  `ssn` char(13) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `adminIdx` char(8) NOT NULL,
  `password` varchar(12) NOT NULL,
  PRIMARY KEY (`adminIdx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES ('HyunsooKim','0008031234567',21,'F','12345678','000803');
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `album`
--

DROP TABLE IF EXISTS `album`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `album` (
  `name` varchar(30) NOT NULL,
  `publisher` varchar(20) DEFAULT NULL,
  `albumIdx` char(8) NOT NULL,
  `releaseDate` date DEFAULT NULL,
  `artistNum` char(8) NOT NULL,
  `admNum` char(8) NOT NULL DEFAULT '12345678',
  PRIMARY KEY (`albumIdx`),
  UNIQUE KEY `UNQ` (`name`,`artistNum`),
  KEY `ARTISTFK` (`artistNum`),
  KEY `ADMFK` (`admNum`),
  CONSTRAINT `ADMFK` FOREIGN KEY (`admNum`) REFERENCES `administrator` (`adminIdx`),
  CONSTRAINT `ARTISTFK` FOREIGN KEY (`artistNum`) REFERENCES `artist` (`artistIdx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `album`
--

LOCK TABLES `album` WRITE;
/*!40000 ALTER TABLE `album` DISABLE KEYS */;
INSERT INTO `album` VALUES ('NONSTOP','sony music','17682679','2020-04-27','66465893','12345678'),('Remember Me',NULL,'17928617',NULL,'66465893','12345678'),('SHOWER',NULL,'28840672',NULL,'47748634','12345678'),('PINK OCEAN','sony music','65039191','2016-03-28','66465893','12345678'),('Hands on me','genie music','77225912','2017-06-07','13746009','12345678'),('Bon Voyage','sony music','81357695','2020-09-07','64165129','12345678'),('TO BE ONE','genie music','92508373','2017-08-07','81498486','12345678');
/*!40000 ALTER TABLE `album` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artist`
--

DROP TABLE IF EXISTS `artist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `artist` (
  `name` varchar(10) NOT NULL,
  `artistIdx` char(8) NOT NULL,
  PRIMARY KEY (`artistIdx`),
  UNIQUE KEY `UNIQUENAME` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artist`
--

LOCK TABLES `artist` WRITE;
/*!40000 ALTER TABLE `artist` DISABLE KEYS */;
INSERT INTO `artist` VALUES ('BanHana','13781620'),('Chungha','13746009'),('IOI','47748634'),('Mimi','77018395'),('omygirl','66465893'),('wannaone','81498486'),('YooA','64165129');
/*!40000 ALTER TABLE `artist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `artist_album_songs`
--

DROP TABLE IF EXISTS `artist_album_songs`;
/*!50001 DROP VIEW IF EXISTS `artist_album_songs`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `artist_album_songs` (
  `artistName` tinyint NOT NULL,
  `artistIdx` tinyint NOT NULL,
  `albumName` tinyint NOT NULL,
  `albumIdx` tinyint NOT NULL,
  `songName` tinyint NOT NULL,
  `songIdx` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `bookmark`
--

DROP TABLE IF EXISTS `bookmark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bookmark` (
  `BeLiked` char(8) NOT NULL,
  `Likes` char(8) NOT NULL,
  PRIMARY KEY (`BeLiked`,`Likes`),
  KEY `THEYFK` (`Likes`),
  CONSTRAINT `THEMFK` FOREIGN KEY (`BeLiked`) REFERENCES `members` (`memberIdx`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `THEYFK` FOREIGN KEY (`Likes`) REFERENCES `members` (`memberIdx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookmark`
--

LOCK TABLES `bookmark` WRITE;
/*!40000 ALTER TABLE `bookmark` DISABLE KEYS */;
INSERT INTO `bookmark` VALUES ('32738059','54416152');
/*!40000 ALTER TABLE `bookmark` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `follow`
--

DROP TABLE IF EXISTS `follow`;
/*!50001 DROP VIEW IF EXISTS `follow`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `follow` (
  `theyID` tinyint NOT NULL,
  `theyIdx` tinyint NOT NULL,
  `themID` tinyint NOT NULL,
  `themIdx` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genre` (
  `genreInfo` varchar(20) NOT NULL,
  `songNum` char(8) NOT NULL,
  PRIMARY KEY (`genreInfo`,`songNum`),
  KEY `SONGFK4` (`songNum`),
  CONSTRAINT `SONGFK4` FOREIGN KEY (`songNum`) REFERENCES `song` (`songIdx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
INSERT INTO `genre` VALUES ('dance pop','50492522');
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `included`
--

DROP TABLE IF EXISTS `included`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `included` (
  `songNum` char(8) NOT NULL,
  `creatorNum` char(8) NOT NULL,
  `PLName` varchar(20) NOT NULL,
  PRIMARY KEY (`songNum`,`creatorNum`,`PLName`),
  KEY `PLFK2` (`PLName`),
  KEY `CREATORFK` (`creatorNum`),
  CONSTRAINT `CREATORFK` FOREIGN KEY (`creatorNum`) REFERENCES `playlist` (`creatorNum`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `PLFK2` FOREIGN KEY (`PLName`) REFERENCES `playlist` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `SONGFK2` FOREIGN KEY (`songNum`) REFERENCES `song` (`songIdx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `included`
--

LOCK TABLES `included` WRITE;
/*!40000 ALTER TABLE `included` DISABLE KEYS */;
INSERT INTO `included` VALUES ('20919916','32738059','nonstoppli'),('20919916','54416152','omgPlay'),('75692470','54416152','omgPlay'),('80393690','54416152','omgPlay'),('88927389','32738059','nonstoppli'),('88927389','54416152','omgPlay'),('95609774','32738059','omypli');
/*!40000 ALTER TABLE `included` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `memberof`
--

DROP TABLE IF EXISTS `memberof`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `memberof` (
  `groupNum` char(8) NOT NULL,
  `memberNum` char(8) NOT NULL,
  PRIMARY KEY (`groupNum`,`memberNum`),
  KEY `MEMBERPK` (`memberNum`),
  CONSTRAINT `GROUPFK` FOREIGN KEY (`groupNum`) REFERENCES `artist` (`artistIdx`),
  CONSTRAINT `MEMBERPK` FOREIGN KEY (`memberNum`) REFERENCES `artist` (`artistIdx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `memberof`
--

LOCK TABLES `memberof` WRITE;
/*!40000 ALTER TABLE `memberof` DISABLE KEYS */;
INSERT INTO `memberof` VALUES ('66465893','13781620'),('66465893','64165129'),('66465893','77018395');
/*!40000 ALTER TABLE `memberof` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `members` (
  `name` varchar(20) NOT NULL,
  `ssn` char(13) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `ID` varchar(20) NOT NULL,
  `comment` text DEFAULT NULL,
  `password` varchar(12) NOT NULL,
  `admNum` char(8) NOT NULL DEFAULT '12345678',
  `memberIdx` char(8) NOT NULL,
  `sex` char(1) DEFAULT NULL,
  PRIMARY KEY (`memberIdx`),
  UNIQUE KEY `ID` (`ID`),
  UNIQUE KEY `ssn` (`ssn`),
  KEY `admNum` (`admNum`),
  CONSTRAINT `members_ibfk_1` FOREIGN KEY (`admNum`) REFERENCES `administrator` (`adminIdx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES ('hyunsoo kim','0008031234567',21,'hyunsoo','','000803','12345678','32738059','F'),('Gustn Kim','0008037654321',15,'gustn','Hi~!','0803','12345678','54416152','M');
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlist`
--

DROP TABLE IF EXISTS `playlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `playlist` (
  `name` varchar(20) NOT NULL,
  `public` tinyint(1) DEFAULT 1,
  `creatorNum` char(8) NOT NULL,
  PRIMARY KEY (`name`,`creatorNum`),
  KEY `CREATORFK2` (`creatorNum`),
  CONSTRAINT `CREATORFK2` FOREIGN KEY (`creatorNum`) REFERENCES `members` (`memberIdx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlist`
--

LOCK TABLES `playlist` WRITE;
/*!40000 ALTER TABLE `playlist` DISABLE KEYS */;
INSERT INTO `playlist` VALUES ('nonstoppli',1,'32738059'),('omgPlay',1,'54416152'),('omypli',1,'32738059');
/*!40000 ALTER TABLE `playlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `playlist_include_songs`
--

DROP TABLE IF EXISTS `playlist_include_songs`;
/*!50001 DROP VIEW IF EXISTS `playlist_include_songs`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `playlist_include_songs` (
  `PLName` tinyint NOT NULL,
  `creatorIdx` tinyint NOT NULL,
  `songName` tinyint NOT NULL,
  `songIdx` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `playsong`
--

DROP TABLE IF EXISTS `playsong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `playsong` (
  `listenerNum` char(8) NOT NULL,
  `songNum` char(8) NOT NULL,
  `playtime` int(20) NOT NULL,
  PRIMARY KEY (`listenerNum`,`songNum`),
  KEY `SONGFK` (`songNum`),
  CONSTRAINT `LSTNRFK2` FOREIGN KEY (`listenerNum`) REFERENCES `members` (`memberIdx`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `SONGFK` FOREIGN KEY (`songNum`) REFERENCES `song` (`songIdx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playsong`
--

LOCK TABLES `playsong` WRITE;
/*!40000 ALTER TABLE `playsong` DISABLE KEYS */;
INSERT INTO `playsong` VALUES ('32738059','20919916',1),('32738059','36427857',1),('32738059','38194693',3),('32738059','57899843',2),('32738059','88927389',1),('32738059','95609774',1),('54416152','20919916',3),('54416152','71593284',1),('54416152','75692470',1),('54416152','80393690',1),('54416152','88927389',3),('54416152','95609774',1);
/*!40000 ALTER TABLE `playsong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `song`
--

DROP TABLE IF EXISTS `song`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `song` (
  `name` varchar(100) NOT NULL,
  `lyrics` text DEFAULT NULL,
  `ageLimit` int(11) DEFAULT 0,
  `songIdx` char(8) NOT NULL,
  `songwriter` text DEFAULT NULL,
  `albumNum` char(8) NOT NULL,
  PRIMARY KEY (`songIdx`),
  KEY `ALBUMFK2` (`albumNum`),
  CONSTRAINT `ALBUMFK2` FOREIGN KEY (`albumNum`) REFERENCES `album` (`albumIdx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `song`
--

LOCK TABLES `song` WRITE;
/*!40000 ALTER TABLE `song` DISABLE KEYS */;
INSERT INTO `song` VALUES ('dolphin',NULL,0,'20919916',NULL,'17682679'),('knock knock',NULL,0,'27113242',NULL,'65039191'),('firework',NULL,0,'28019501',NULL,'17928617'),('one step two step',NULL,0,'30711344',NULL,'65039191'),('burn it up',NULL,0,'36427857',NULL,'92508373'),('energetic',NULL,0,'38194693',NULL,'92508373'),('why dont you know','why dont you know?',0,'50492522','oreo','77225912'),('B612',NULL,0,'51034349',NULL,'65039191'),('wanna be',NULL,0,'57899843',NULL,'92508373'),('make a wish',NULL,0,'71593284',NULL,'77225912'),('liar liar',NULL,0,'75692470',NULL,'65039191'),('shower','.',0,'79671853','.','28840672'),('twilight',NULL,0,'80393690',NULL,'17928617'),('nonstop',NULL,0,'88927389',NULL,'17682679'),('Bon voyage','.',0,'95609774','','81357695'),('I found love',NULL,0,'98126503',NULL,'65039191');
/*!40000 ALTER TABLE `song` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `music_app`
--

USE `music_app`;

--
-- Final view structure for view `artist_album_songs`
--

/*!50001 DROP TABLE IF EXISTS `artist_album_songs`*/;
/*!50001 DROP VIEW IF EXISTS `artist_album_songs`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `artist_album_songs` AS select `artist`.`name` AS `artistName`,`artist`.`artistIdx` AS `artistIdx`,`album`.`name` AS `albumName`,`album`.`albumIdx` AS `albumIdx`,`song`.`name` AS `songName`,`song`.`songIdx` AS `songIdx` from ((`artist` join `album`) join `song`) where `artist`.`artistIdx` = `album`.`artistNum` and `album`.`albumIdx` = `song`.`albumNum` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `follow`
--

/*!50001 DROP TABLE IF EXISTS `follow`*/;
/*!50001 DROP VIEW IF EXISTS `follow`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `follow` AS select `they`.`ID` AS `theyID`,`they`.`memberIdx` AS `theyIdx`,`them`.`ID` AS `themID`,`them`.`memberIdx` AS `themIdx` from ((`members` `they` join `bookmark` `b`) join `members` `them`) where `they`.`memberIdx` = `b`.`Likes` and `them`.`memberIdx` = `b`.`BeLiked` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `playlist_include_songs`
--

/*!50001 DROP TABLE IF EXISTS `playlist_include_songs`*/;
/*!50001 DROP VIEW IF EXISTS `playlist_include_songs`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `playlist_include_songs` AS select `p`.`name` AS `PLName`,`p`.`creatorNum` AS `creatorIdx`,`s`.`name` AS `songName`,`s`.`songIdx` AS `songIdx` from ((`playlist` `p` join `included` `i`) join `song` `s`) where `p`.`creatorNum` = `i`.`creatorNum` and `p`.`name` = `i`.`PLName` and `i`.`songNum` = `s`.`songIdx` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-07 22:16:14
