-- MySQL dump 10.13  Distrib 5.6.11, for Win64 (x86_64)
--
-- Host: localhost    Database: familybookz
-- ------------------------------------------------------
-- Server version	5.6.11

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
-- Table structure for table `feed`
--

DROP TABLE IF EXISTS `feed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feed` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` int(10) unsigned NOT NULL DEFAULT '0',
  `content` varchar(5000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `images` varchar(1000) DEFAULT NULL,
  `title` varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `url` varchar(1000) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `owner_id` int(10) unsigned NOT NULL DEFAULT '0',
  `owner_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `owner_avatar` varchar(200) NOT NULL DEFAULT '',
  `description` varchar(2000) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `website` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feed`
--

LOCK TABLES `feed` WRITE;
/*!40000 ALTER TABLE `feed` DISABLE KEYS */;
INSERT INTO `feed` VALUES (26,0,'Con Tuấn','/images/feed/1445740434007.png',NULL,NULL,'2015-10-30 09:10:28','0000-00-00 00:00:00',8,'Quoc Tuan Trinh','/images/person/tuan.png',NULL,NULL),(27,0,'Bố Trung đi làm cả tuần','/images/feed/1445740680495.png',NULL,NULL,'2015-10-30 09:10:28','0000-00-00 00:00:00',10,'Gia Han Trinh','/images/person/giahan.png',NULL,NULL),(28,0,'Love my family','/images/feed/1445740866628.png',NULL,NULL,'2015-10-30 09:10:28','0000-00-00 00:00:00',2,'Văn Kien Trinh','/images/person/kien.png',NULL,NULL),(29,0,'hello','/images/feed/1445744165564.png',NULL,NULL,'2015-10-30 09:10:28','0000-00-00 00:00:00',2,'Văn Kien Trinh','/images/person/kien.png',NULL,NULL),(30,0,'Love you guys!','/images/feed/1445963638887.png,/images/feed/1445963638892.png,/images/feed/1445963638895.png',NULL,NULL,'2015-10-30 09:10:28','0000-00-00 00:00:00',2,'Văn Kien Trinh','/images/person/kien.png',NULL,NULL),(31,0,'','/images/feed/1445964037313.png',NULL,NULL,'2015-10-30 09:10:28','0000-00-00 00:00:00',2,'Văn Kien Trinh','/images/person/kien.png',NULL,NULL),(32,0,'','/images/feed/1445964037864.png',NULL,NULL,'2015-10-30 09:10:28','0000-00-00 00:00:00',2,'Văn Kien Trinh','/images/person/kien.png',NULL,NULL),(35,1,'http://news.zing.vn/Viet-Nam-ra-tuyen-bo-ve-viec-tau-My-tuan-tra-o-Bien-Dong-post594916.html','http://img.v3.news.zdn.vn/w660/Uploaded/sotnzj/2015_10_29/haibinh.jpg','Việt Nam ra tuyên bố về việc tàu Mỹ tuần tra ở Biển Đông','http://news.zing.vn/Viet-Nam-ra-tuyen-bo-ve-viec-tau-My-tuan-tra-o-Bien-Dong-post594916.html','2015-10-29 17:22:13','0000-00-00 00:00:00',2,'Văn Kien Trinh','/images/person/kien.png','Người phát ngôn Bộ Ngoại giao nêu rõ Việt Nam có chủ quyền đối với quần đảo Hoàng Sa và Trường Sa nhưng tôn trọng quyền tự do hàng hải, hàng không của các nước khi đi qua khu vực.',NULL),(36,1,'','http://c1.f25.img.vnecdn.net/2015/10/30/trungnguyen-1446192041_490x294.jpg','Người tiêu dùng Đông Nam Á chuộng fastfood nội địa - VnExpress Kinh Doanh','http://kinhdoanh.vnexpress.net/tin-tuc/quoc-te/nguoi-tieu-dung-dong-nam-a-chuong-fastfood-noi-dia-3304460.html','2015-10-30 09:30:51','0000-00-00 00:00:00',2,'Văn Kien Trinh','/images/person/kien.png','Tại Việt Nam, Thái Lan, Philippines và Indonesia, các chuỗi nhà hàng - cà phê trong nước đều tỏ ra lấn át trước những thương hiệu đến từ Mỹ.','VnExpress'),(37,1,'Cuối cùng cũng có http://news.zing.vn/Viet-Nam-ra-tuyen-bo-ve-viec-tau-My-tuan-tra-o-Bien-Dong-post594916.html có còn hơn không.','http://img.v3.news.zdn.vn/w660/Uploaded/sotnzj/2015_10_29/haibinh.jpg','Việt Nam ra tuyên bố về việc tàu Mỹ tuần tra ở Biển Đông','http://news.zing.vn/Viet-Nam-ra-tuyen-bo-ve-viec-tau-My-tuan-tra-o-Bien-Dong-post594916.html','2015-10-30 09:49:46','0000-00-00 00:00:00',2,'Văn Kien Trinh','/images/person/kien.png','Người phát ngôn Bộ Ngoại giao nêu rõ Việt Nam có chủ quyền đối với quần đảo Hoàng Sa và Trường Sa nhưng tôn trọng quyền tự do hàng hải, hàng không của các nước khi đi qua khu vực.',NULL),(38,1,'Cuối cùng cũng có ','http://img.v3.news.zdn.vn/w660/Uploaded/sotnzj/2015_10_29/haibinh.jpg','Việt Nam ra tuyên bố về việc tàu Mỹ tuần tra ở Biển Đông','http://news.zing.vn/Viet-Nam-ra-tuyen-bo-ve-viec-tau-My-tuan-tra-o-Bien-Dong-post594916.html','2015-10-30 09:50:26','0000-00-00 00:00:00',2,'Văn Kien Trinh','/images/person/kien.png','Người phát ngôn Bộ Ngoại giao nêu rõ Việt Nam có chủ quyền đối với quần đảo Hoàng Sa và Trường Sa nhưng tôn trọng quyền tự do hàng hải, hàng không của các nước khi đi qua khu vực.',NULL),(39,1,'TQ bá đạo <a href=\'#\' onclick=\"openLink(\'http://vnexpress.net/tin-tuc/the-gioi/khu-truc-ham-trung-quoc-dien-tap-doi-khang-tren-bien-dong-3304495.html\')\">http://vnexpress.net/tin-tuc/the-gioi/khu-truc-ham-trung-quo ... quá','http://l.f30.img.vnecdn.net/2015/10/30/x-1446195336_490x294.jpg','Khu trục hạm Trung Quốc diễn tập đối kháng trên Biển Đông - VnExpress','http://vnexpress.net/tin-tuc/the-gioi/khu-truc-ham-trung-quoc-dien-tap-doi-khang-tren-bien-dong-3304495.html','2015-10-30 10:23:27','0000-00-00 00:00:00',2,'Văn Kien Trinh','/images/person/kien.png','Tàu chiến thuộc Hạm đội Nam Hải đến một khu vực không tiết lộ trên Biển Đông diễn tập đối kháng.','Tin nhanh VnExpress');
/*!40000 ALTER TABLE `feed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `surname` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `image` varchar(100) NOT NULL DEFAULT '',
  `gender` tinyint(1) NOT NULL DEFAULT '0',
  `email` varchar(100) DEFAULT NULL,
  `phonenum` varchar(45) DEFAULT NULL,
  `mom` int(10) unsigned DEFAULT NULL,
  `dad` int(10) unsigned DEFAULT NULL,
  `wifeorhusband_ids` varchar(200) DEFAULT NULL,
  `facebook_id` varchar(200) DEFAULT NULL,
  `access_token` varchar(1000) DEFAULT NULL,
  `born_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `passed_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `username` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `owner_id` int(10) unsigned DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
INSERT INTO `profile` VALUES (1,'Văn Bình','Trịnh','/images/person/binh.png',1,NULL,NULL,0,39,'30',NULL,NULL,'2015-10-25 02:29:01','2015-10-10 08:38:30','binhtv','123456',NULL,'0000-00-00 00:00:00'),(2,'Văn Kien','Trinh','/images/person/kien.png',1,NULL,NULL,30,1,NULL,NULL,NULL,'2015-10-23 16:55:41','2015-10-10 08:38:30','kientv','123456',NULL,'0000-00-00 00:00:00'),(3,'Hữu Trung','Trinh','/images/person/trung.png',1,NULL,NULL,30,1,NULL,NULL,NULL,'2015-10-23 16:56:32','2015-10-10 08:38:30','trungh','123456',NULL,'0000-00-00 00:00:00'),(4,'Thị Phương','Trinh','/images/person/phuong.png',0,NULL,NULL,30,1,NULL,NULL,NULL,'2015-10-23 16:56:54','2015-10-10 08:38:30','phuongtt','123456',NULL,'0000-00-00 00:00:00'),(5,'Nguyệt Minh','Pham','/images/person/minh.png',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2015-10-11 10:49:04','2015-10-10 08:38:30','minhp','123456',NULL,'0000-00-00 00:00:00'),(6,'Ngoc','Huỳnh','/images/person/ngoc.png',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2015-10-11 10:49:04','2015-10-10 08:38:30','ngoch','123456',NULL,'0000-00-00 00:00:00'),(7,'Giang','Phan','/images/person/giang.png',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2015-10-23 16:36:19','2015-10-10 08:38:30','giangp','123456',NULL,'0000-00-00 00:00:00'),(8,'Quoc Tuan','Trinh','/images/person/tuan.png',1,NULL,NULL,0,2,NULL,NULL,NULL,'2015-10-23 16:59:28','2015-10-10 08:38:30','qtuan','123456',NULL,'0000-00-00 00:00:00'),(9,'Hai Bach','Trinh','/images/person/bach.png',1,NULL,NULL,0,2,NULL,NULL,NULL,'2015-10-23 16:59:58','2015-10-10 08:38:30','hbach','123456',NULL,'0000-00-00 00:00:00'),(10,'Gia Han','Trinh','/images/person/giahan.png',0,NULL,NULL,0,3,NULL,NULL,NULL,'2015-10-23 17:01:48','2015-10-10 08:38:30','ghan','123456',NULL,'0000-00-00 00:00:00'),(11,'Gia Bao','Trinh','/images/person/giabao.png',1,NULL,NULL,0,3,NULL,NULL,NULL,'2015-10-23 17:02:09','2015-10-10 08:38:30','gbao','123456',NULL,'0000-00-00 00:00:00'),(12,'Hai Long','Phan','/images/person/co.png',1,NULL,NULL,4,0,NULL,NULL,NULL,'2015-10-23 16:57:51','2015-10-10 08:38:30','longp','123456',NULL,'0000-00-00 00:00:00'),(13,'Tuan con 1','Trinh','/images/person/tuan.png',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2015-10-11 10:49:04','2015-10-10 08:38:30','tuancon1','123456',NULL,'0000-00-00 00:00:00'),(14,'tuan con 2','Trinh','/images/person/tuan.png',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2015-10-11 10:49:04','2015-10-10 08:38:30','tuancon2','123456',NULL,'0000-00-00 00:00:00'),(15,'bach con 1','trinh','/images/person/bach.png',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2015-10-11 10:49:04','2015-10-10 08:38:30','bachcon1','123456',NULL,'0000-00-00 00:00:00'),(16,'bach con 2','Trinh','/images/person/bach.png',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2015-10-11 10:49:04','2015-10-10 08:38:30','bachcon2','123456',NULL,'0000-00-00 00:00:00'),(30,'Thi Huong ','Nguyen','/images/person/huong.png',0,NULL,NULL,0,0,NULL,NULL,NULL,'2015-10-23 16:46:33','0000-00-00 00:00:00','huong','123456',NULL,'0000-00-00 00:00:00'),(31,'Điệp','Trinh','/images/tmp/1445687360855.png',1,'na',NULL,0,0,'',NULL,NULL,'2015-10-23 17:00:00','2015-10-24 11:49:25','diep','123456',0,'0000-00-00 00:00:00'),(32,'Trien','Trinh','/images/tmp/1445687695905.png',1,'na',NULL,0,0,'',NULL,NULL,'2015-10-23 17:00:00','2015-10-24 11:54:55','trien','123456',0,'0000-00-00 00:00:00'),(33,'Hiệp','Trinh','/images/tmp/1445701765643.png',1,'na',NULL,0,0,'',NULL,NULL,'2015-10-23 17:00:00','2015-10-24 15:49:26','hiep','123456',0,'0000-00-00 00:00:00'),(34,'Duy','Trinh','/images/tmp/1445701889377.png',1,'na',NULL,0,0,'',NULL,NULL,'2015-10-23 17:00:00','2015-10-24 15:51:29','duy','123456',0,'0000-00-00 00:00:00'),(35,'Thịnh','Trịnh','/images/tmp/1445702393979.png',1,'na',NULL,0,0,'',NULL,NULL,'2015-10-23 17:00:00','2015-10-24 15:59:53','thinh','123456',0,'0000-00-00 00:00:00'),(36,'Khoáng','Trịnh','/images/tmp/1445702504010.png',1,'',NULL,0,39,'',NULL,NULL,'2015-10-25 02:28:43','2015-10-24 16:01:44','khoang','123456',0,'0000-00-00 00:00:00'),(37,'Mây','Trịnh','/images/tmp/1445702609998.png',1,'',NULL,0,39,'',NULL,NULL,'2015-10-25 02:28:29','2015-10-24 16:03:30','may','123456',0,'0000-00-00 00:00:00'),(38,'Thị Minh','Trịnh','/images/tmp/1445702933760.png',1,'',NULL,0,39,'',NULL,NULL,'2015-10-25 02:28:17','2015-10-24 16:08:54','minhtt','123456',0,'0000-00-00 00:00:00'),(39,'Phóng','Trịnh','/images/tmp/1445739890392.png',1,'',NULL,0,0,'',NULL,NULL,'2015-10-24 17:00:00','2015-10-25 02:24:50','phongt','123456',0,'0000-00-00 00:00:00');
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile_feeds_timeline`
--

DROP TABLE IF EXISTS `profile_feeds_timeline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile_feeds_timeline` (
  `profile_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `feed_ids` varchar(10000) NOT NULL DEFAULT ' ',
  PRIMARY KEY (`profile_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile_feeds_timeline`
--

LOCK TABLES `profile_feeds_timeline` WRITE;
/*!40000 ALTER TABLE `profile_feeds_timeline` DISABLE KEYS */;
INSERT INTO `profile_feeds_timeline` VALUES (1,'26,27,28,29,30,31,32,34,35,36,37,38,39'),(2,'26,27,28,29,30,31,32,34,35,36,37,38,39'),(3,'26,27,28,29,30,31,32,34,35,36,37,38,39'),(4,'26,27,28,29,30,31,32,34,35,36,37,38,39'),(8,'26,27,28,29,30,31,32,34,35,36,37,38,39'),(9,'26,27,28,29,30,31,32,34,35,36,37,38,39'),(10,'26,27,28,29,30,31,32,34,35,36,37,38,39'),(11,'26,27,28,29,30,31,32,34,35,36,37,38,39'),(12,'26,27,28,29,30,31,32,34,35,36,37,38,39'),(30,'26,27,28,29,30,31,32,34,35,36,37,38,39'),(36,'28,29,30,31,32,34,35,36,37,38,39'),(37,'28,29,30,31,32,34,35,36,37,38,39'),(38,'28,29,30,31,32,34,35,36,37,38,39'),(39,'28,29,30,31,32,34,35,36,37,38,39');
/*!40000 ALTER TABLE `profile_feeds_timeline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile_ownfeed_mapping`
--

DROP TABLE IF EXISTS `profile_ownfeed_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile_ownfeed_mapping` (
  `profile_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `feed_ids` varchar(5000) NOT NULL DEFAULT '',
  PRIMARY KEY (`profile_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile_ownfeed_mapping`
--

LOCK TABLES `profile_ownfeed_mapping` WRITE;
/*!40000 ALTER TABLE `profile_ownfeed_mapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `profile_ownfeed_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relation_request`
--

DROP TABLE IF EXISTS `relation_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `relation_request` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `requesting_id` int(10) unsigned NOT NULL DEFAULT '0',
  `requested_id` int(10) unsigned NOT NULL DEFAULT '0',
  `request_msg` varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `requested_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(10) NOT NULL DEFAULT 'Requesting',
  `relation` varchar(45) NOT NULL DEFAULT '',
  `requester_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `requester_image` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relation_request`
--

LOCK TABLES `relation_request` WRITE;
/*!40000 ALTER TABLE `relation_request` DISABLE KEYS */;
INSERT INTO `relation_request` VALUES (1,10,6,'Gia Han Trinh want to add you as a mom','2015-10-10 16:44:49','requesting','mom','Gia Han','/images/person/giahan.png'),(2,1,2,'Văn Bình Trịnh want to add you as a kid','2015-10-17 09:20:01','confirmed','kid','Văn Bình','/images/person/binh.png'),(3,1,3,'Văn Bình Trịnh want to add you as a kid','2015-10-10 17:39:22','confirmed','kid','Văn Bình','/images/person/binh.png'),(4,19,4,'Huong Nguyen want to add you as a kid','2015-10-10 17:38:49','confirmed','kid','Huong','/images/person/huong.png'),(5,19,1,'Huong Nguyen want to add you as a husband','2015-10-10 17:31:25','confirmed','husband','Huong','/images/person/huong.png'),(6,3,10,'Hữu Trung Trinh want to add you as a kid','2015-10-23 17:01:48','confirmed','kid','Hữu Trung','/images/person/trung.png'),(7,3,11,'Hữu Trung Trinh want to add you as a kid','2015-10-23 17:02:11','confirmed','kid','Hữu Trung','/images/person/trung.png'),(8,2,8,'Văn Kien Trinh want to add you as a kid','2015-10-23 16:59:28','confirmed','kid','Văn Kien','/images/person/kien.png'),(9,2,9,'Văn Kien Trinh want to add you as a kid','2015-10-23 16:59:58','confirmed','kid','Văn Kien','/images/person/kien.png'),(10,2,9,'Văn Kien Trinh want to add you as a kid','2015-10-23 17:00:01','confirmed','kid','Văn Kien','/images/person/kien.png'),(11,2,9,'Văn Kien Trinh want to add you as a kid','2015-10-23 16:59:59','confirmed','kid','Văn Kien','/images/person/kien.png'),(12,2,9,'Văn Kien Trinh want to add you as a kid','2015-10-23 17:00:03','confirmed','kid','Văn Kien','/images/person/kien.png'),(13,2,9,'Văn Kien Trinh want to add you as a kid','2015-10-23 17:00:02','confirmed','kid','Văn Kien','/images/person/kien.png'),(14,2,10,'Văn Kien Trinh want to add you as a kid','2015-10-23 17:01:44','delete','kid','Văn Kien','/images/person/kien.png'),(15,2,1,'Văn Kien Trinh want to add you as a dad','2015-10-11 11:00:12','confirmed','dad','Văn Kien','/images/person/kien.png'),(16,2,1,'Văn Kien Trinh want to add you as a dad','2015-10-11 11:00:14','confirmed','dad','Văn Kien','/images/person/kien.png'),(17,2,1,'Văn Kien Trinh want to add you as a dad','2015-10-13 16:24:35','confirmed','dad','Văn Kien','/images/person/kien.png'),(18,1,4,'Văn Bình Trịnh want to add you as a kid','2015-10-13 16:34:04','confirmed','kid','Văn Bình','/images/person/binh.png'),(19,1,3,'Văn Bình Trịnh want to add you as a kid','2015-10-13 16:35:51','confirmed','kid','Văn Bình','/images/person/binh.png'),(20,1,30,'Văn Bình Trịnh want to add you as a wife','2015-10-13 16:40:08','confirmed','wife','Văn Bình','/images/person/binh.png'),(21,8,2,'Quoc Tuan Trinh want to add you as a dad','2015-10-13 16:47:35','confirmed','dad','Quoc Tuan','/images/person/tuan.png'),(22,7,12,'Giang Phan want to add you as a kid','2015-10-17 16:19:12','confirmed','kid','Giang','/images/person/giang.png'),(23,7,4,'Giang Phan want to add you as a wife','2015-10-17 16:20:32','confirmed','wife','Giang','/images/person/giang.png'),(24,1,2,'Văn Bình Trịnh want to add you as a kid','2015-10-23 16:39:10','confirmed','kid','Văn Bình','/images/person/binh.png'),(25,1,4,'Văn Bình Trịnh want to add you as a kid','2015-10-23 16:43:08','confirmed','kid','Văn Bình','/images/person/binh.png'),(26,1,3,'Văn Bình Trịnh want to add you as a kid','2015-10-23 16:43:28','confirmed','kid','Văn Bình','/images/person/binh.png'),(27,1,30,'Văn Bình Trịnh want to add you as a wife','2015-10-23 16:46:34','confirmed','wife','Văn Bình','/images/person/binh.png'),(28,30,2,'Thi Huong  Nguyen want to add you as a kid','2015-10-23 16:55:41','confirmed','kid','Thi Huong ','/images/person/huong.png'),(29,30,3,'Thi Huong  Nguyen want to add you as a kid','2015-10-23 16:56:32','confirmed','kid','Thi Huong ','/images/person/huong.png'),(30,30,4,'Thi Huong  Nguyen want to add you as a kid','2015-10-23 16:56:54','confirmed','kid','Thi Huong ','/images/person/huong.png'),(31,4,12,'Thị Phương Trinh want to add you as a kid','2015-10-23 16:57:51','confirmed','kid','Thị Phương','/images/person/phuong.png'),(32,2,8,'Văn Kien Trinh want to add you as a kid','2015-10-23 16:59:29','confirmed','kid','Văn Kien','/images/person/kien.png'),(33,2,9,'Văn Kien Trinh want to add you as a kid','2015-10-23 17:00:05','confirmed','kid','Văn Kien','/images/person/kien.png'),(34,3,10,'Hữu Trung Trinh want to add you as a kid','2015-10-23 17:01:50','confirmed','kid','Hữu Trung','/images/person/trung.png'),(35,3,11,'Hữu Trung Trinh want to add you as a kid','2015-10-23 17:02:10','confirmed','kid','Hữu Trung','/images/person/trung.png'),(36,39,1,'Phóng Trịnh want to add you as a kid','2015-10-25 02:29:01','confirmed','kid','Phóng','/images/tmp/1445739890392.png'),(37,39,38,'Phóng Trịnh want to add you as a kid','2015-10-25 02:28:17','confirmed','kid','Phóng','/images/tmp/1445739890392.png'),(38,39,37,'Phóng Trịnh want to add you as a kid','2015-10-25 02:28:30','confirmed','kid','Phóng','/images/tmp/1445739890392.png'),(39,39,36,'Phóng Trịnh want to add you as a kid','2015-10-25 02:28:43','confirmed','kid','Phóng','/images/tmp/1445739890392.png');
/*!40000 ALTER TABLE `relation_request` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-10-31 16:53:37
