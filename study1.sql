-- MySQL dump 10.13  Distrib 8.0.46, for Linux (aarch64)
--
-- Host: localhost    Database: study1
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
-- Table structure for table `db_account`
--

DROP TABLE IF EXISTS `db_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `register_time` datetime DEFAULT NULL,
  `mute` tinyint DEFAULT NULL,
  `banned` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_email` (`email`) USING BTREE,
  UNIQUE KEY `unique_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_account`
--

LOCK TABLES `db_account` WRITE;
/*!40000 ALTER TABLE `db_account` DISABLE KEYS */;
INSERT INTO `db_account` VALUES (4,'test','2726722154@qq.com','$2a$10$cWOxEftz9MaMa8BXmwVacO5G17kWHsWy1GfdaZ6fljgdXTHIyI/qC','user','/avatar/ee2aaac0c27340fd95215102e90fe7d4','2026-08-07 11:34:57',0,0),(5,'user','1604802148@qq.com','$2a$10$6VjOF21i5mwPBYcy/EtN0..JwfsQThJ4b9YmpMEDiCW/tduVObfdC','admin',NULL,'2026-08-20 14:10:33',0,0);
/*!40000 ALTER TABLE `db_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_account_details`
--

DROP TABLE IF EXISTS `db_account_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_account_details` (
  `id` int NOT NULL,
  `gender` tinyint DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `qq` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `wx` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_account_details`
--

LOCK TABLES `db_account_details` WRITE;
/*!40000 ALTER TABLE `db_account_details` DISABLE KEYS */;
INSERT INTO `db_account_details` VALUES (4,1,'13111414124','13113313132','2r2r2rfdd','hello world'),(5,0,'13112131131','1313131','afaffwaf','大家好，我是萌新，求大佬带带');
/*!40000 ALTER TABLE `db_account_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_account_privacy`
--

DROP TABLE IF EXISTS `db_account_privacy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_account_privacy` (
  `id` int NOT NULL,
  `phone` tinyint DEFAULT NULL,
  `email` tinyint DEFAULT NULL,
  `wx` tinyint DEFAULT NULL,
  `qq` tinyint DEFAULT NULL,
  `gender` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_account_privacy`
--

LOCK TABLES `db_account_privacy` WRITE;
/*!40000 ALTER TABLE `db_account_privacy` DISABLE KEYS */;
INSERT INTO `db_account_privacy` VALUES (4,1,1,1,1,1),(5,1,1,1,1,1);
/*!40000 ALTER TABLE `db_account_privacy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_email_record`
--

DROP TABLE IF EXISTS `db_email_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_email_record` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_email_record`
--

LOCK TABLES `db_email_record` WRITE;
/*!40000 ALTER TABLE `db_email_record` DISABLE KEYS */;
INSERT INTO `db_email_record` VALUES (1,'2726722154@qq.com','您的邮件修改验证邮件','您好，您正在绑定新的电子邮件地址，验证码：255315，有效时间3分钟，如非本人操作，请无视。','2026-08-25 14:38:35',2),(2,'2726722154@qq.com','您的邮件修改验证邮件','您好，您正在绑定新的电子邮件地址，验证码：822865，有效时间3分钟，如非本人操作，请无视。','2026-08-25 14:40:11',2),(3,'2726722154@qq.com','您的邮件修改验证邮件','您好，您正在绑定新的电子邮件地址，验证码：178596，有效时间3分钟，如非本人操作，请无视。','2026-08-25 14:42:54',2),(4,'2726722154@qq.com','您的邮件修改验证邮件','您好，您正在绑定新的电子邮件地址，验证码：895256，有效时间3分钟，如非本人操作，请无视。','2026-08-25 14:44:58',1);
/*!40000 ALTER TABLE `db_email_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_image_store`
--

DROP TABLE IF EXISTS `db_image_store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_image_store` (
  `uid` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_image_store`
--

LOCK TABLES `db_image_store` WRITE;
/*!40000 ALTER TABLE `db_image_store` DISABLE KEYS */;
INSERT INTO `db_image_store` VALUES (4,'/cache/20260812/7e77aaaee8b649b58c601980596bcbf0','2026-08-12 15:58:30'),(4,'/cache/20260812/cbd43eb8f280453f9609263ce108ed42','2026-08-12 16:44:59'),(4,'/cache/20260812/a010fe289c784916a7a292b1f6dd0577','2026-08-12 16:45:01'),(4,'/cache/20260812/ba8b66b27c264692b92d8ccb34f3e0d7','2026-08-12 16:45:03'),(4,'/cache/20260812/df18e34b5a90434c840da1d11779b161','2026-08-12 16:45:04'),(4,'/cache/20260812/7c860fbcdeb74122b209befd89db92bb','2026-08-12 16:45:12'),(4,'/cache/20260812/70b488b8825e4abfb9b2f32ae3ef02e9','2026-08-12 16:45:32'),(4,'/cache/20260812/8a9a13c5fcfb4d78ba00d433bcff7249','2026-08-12 16:48:57'),(4,'/cache/20260812/68fdf29a306f40f280978940c8d50910','2026-08-12 16:59:01'),(4,'/cache/20260814/a701912220dc469498e107819d52e9bc','2026-08-14 04:14:23'),(4,'/cache/20260815/c4423049a5c44dc584ece136813cf65a','2026-08-15 17:28:40'),(4,'/cache/20260815/255dbb7991cc49dc853a5dd6dde36c2b','2026-08-15 17:29:49'),(4,'/cache/20260815/837903e79f3b45cfa41d2f5fab179a1d','2026-08-15 17:29:52'),(4,'/cache/20260815/9a2f1639f84e4cc9bd76920bc3c8dea1','2026-08-15 20:48:15'),(4,'/cache/20260815/262b1189ce5547b39bc467546324fc77','2026-08-15 20:52:00'),(4,'/cache/20260815/939e5e3e71bb4899bf59b135112f0ce6','2026-08-15 20:52:43'),(4,'/cache/20260819/aaded636a7a54a708bf0f0d2a541ceed','2026-08-19 15:13:06'),(4,'/cache/20260819/ce53a74f3bad470283268832c303989b','2026-08-19 15:44:44');
/*!40000 ALTER TABLE `db_image_store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_notification`
--

DROP TABLE IF EXISTS `db_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_notification` (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_notification`
--

LOCK TABLES `db_notification` WRITE;
/*!40000 ALTER TABLE `db_notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `db_notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_topic`
--

DROP TABLE IF EXISTS `db_topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_topic` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `intro` text,
  `content` text,
  `uid` int DEFAULT NULL,
  `type` int DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `top` tinyint DEFAULT '0',
  `locked` tinyint DEFAULT '0',
  `invisible` tinyint DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_topic`
--

LOCK TABLES `db_topic` WRITE;
/*!40000 ALTER TABLE `db_topic` DISABLE KEYS */;
INSERT INTO `db_topic` VALUES (2,'hello1',NULL,'{\"ops\":[{\"insert\":\"faafaf\"},{\"attributes\":{\"width\":\"177\"},\"insert\":{\"image\":\"http://localhost:8080/images/cache/20260815/c4423049a5c44dc584ece136813cf65a\"}},{\"insert\":\"\\n\"}]}',4,2,'2026-08-15 17:28:56',0,0,0),(3,'2121',NULL,'{\"ops\":[{\"attributes\":{\"width\":\"163\"},\"insert\":{\"image\":\"http://localhost:8080/images/cache/20260815/255dbb7991cc49dc853a5dd6dde36c2b\"}},{\"insert\":\"\\nafjfw\\n\"}]}',4,1,'2026-08-15 17:30:06',0,0,0),(4,'1111',NULL,'{\"ops\":[{\"insert\":\"1d1d\\n\"}]}',4,1,'2026-08-15 17:30:41',0,0,0),(5,'嘻嘻',NULL,'{\"ops\":[{\"attributes\":{\"background\":\"#ffffff\",\"color\":\"#333333\"},\"insert\":\"我在好几篇小说中都提到过一座废弃的古园，实际就是地坛。\"},{\"insert\":\"\\n\"},{\"attributes\":{\"background\":\"#ffffff\",\"color\":\"#333333\"},\"insert\":\" 许多年前旅游业还没有开展，园子荒芜冷落得如同一片野地，很少被人记起。\"},{\"insert\":\"\\n\"},{\"attributes\":{\"background\":\"#ffffff\",\"color\":\"#333333\"},\"insert\":\" 地坛离我家很近。或者说我家离地坛很近。总之，只好认为这是缘分。地坛在我出生前四百多年就座落在那儿了，而自从我的祖母年轻时带着我父亲来到北京，就一直住在离它不远的地方——五十多年间搬过几次家，可搬来搬去总是在它周围，而且是越搬离它越近了。我常觉得这中间有着宿命的味道：仿佛这古园就是为了等我，而历尽沧桑在那儿等待了四百多年。\"},{\"insert\":\"\\n\"},{\"attributes\":{\"background\":\"#ffffff\",\"color\":\"#333333\"},\"insert\":\" 它等待我出生，然后又等待我活到最狂妄的年龄上忽地残废了双腿。四百多年里，它一面剥蚀了古殿檐头浮夸的琉璃，淡褪了门壁上炫耀的朱红，坍圮了一段段高墙又散落了玉砌雕栏，祭坛四周的老柏树愈见苍幽，到处的野草荒藤也都茂盛得自在坦荡。\"},{\"insert\":\"\\n\"},{\"attributes\":{\"background\":\"#ffffff\",\"color\":\"#333333\"},\"insert\":\" 这时候想必我是该来了。十五年前的一个下午，我摇着轮椅进入园中，它为一个失魂落魄的人把一切都准备好了。那时，太阳循着亘古不变的路途正越来越大，也越红。在满园弥漫的沉静光芒中，一个人更容易看到时间，并看见自己的身影。\"},{\"insert\":\"\\n\"},{\"attributes\":{\"background\":\"#ffffff\",\"color\":\"#333333\"},\"insert\":\" 自从那个下午我无意中进了这园子，就再没长久地离开过它。\"},{\"insert\":\"\\n\"},{\"attributes\":{\"background\":\"#ffffff\",\"color\":\"#333333\"},\"insert\":\" 我一下子就理解了它的意图。正如我在一篇小说中所说的：“在人口密聚的城市里，有这样一个宁静的去处，像是上帝的苦心安排。”\"},{\"insert\":\"\\n\"},{\"attributes\":{\"background\":\"#ffffff\",\"color\":\"#333333\"},\"insert\":\" 两条腿残废后的最初几年，我找不到工作，找不到去路，忽然间几乎什么都找不到了，我就摇了轮椅总是到它那儿去，仅为着那儿是可以逃避一个世界的另一个世界。我在那篇小说中写道：“没处可去我便一天到晚耗在这园子里。跟上班下班一样，别人去上班我就摇了轮椅到这儿来。园子无人看管，上下班时间有些抄近路的人们从园中穿过，园子里活跃一阵，过后便沉寂下来。”\"},{\"insert\":\"\\n\"}]}',4,4,'2026-08-15 19:48:09',1,0,0),(7,'论坛出现超大bug赶紧修',NULL,'{\"ops\":[{\"insert\":\"嘻嘻\\n\"}]}',4,3,'2026-08-15 20:31:31',0,0,0),(10,'测试帖子',NULL,'{\"ops\":[{\"insert\":\"测试\\n\"}]}',4,1,'2026-08-16 15:07:25',1,0,0),(11,'afjaf','void\n','{\"ops\":[{\"insert\":\"void\\n\"}]}',4,3,'2026-08-16 15:38:13',0,0,0),(12,'测试','路由跳转\n一句话\n从一个页面（URL）切到另一个页面（URL）的动作，就叫路由跳转。\n就像你在 App 里点底部导航栏，从 \"首页\" 切到 \"我的\"，这个切换动作就是路由跳转。\n路由跳转\n一句话\n从一个页面（URL）切到另一个页面（URL）的动作，就叫路由跳转。\n就像你在 App 里点底部导航栏，从 \"首页\" 切到 \"我的\"，这个切换动作就是路由跳转。\n1123\n\n','{\"ops\":[{\"attributes\":{\"background\":\"rgba(0, 0, 0, 0)\",\"color\":\"rgba(255, 255, 255, 0.85)\"},\"insert\":\"路由跳转\"},{\"attributes\":{\"header\":1},\"insert\":\"\\n\"},{\"attributes\":{\"background\":\"rgba(0, 0, 0, 0)\",\"color\":\"rgba(255, 255, 255, 0.85)\"},\"insert\":\"一句话\"},{\"attributes\":{\"header\":2},\"insert\":\"\\n\"},{\"attributes\":{\"background\":\"rgba(0, 0, 0, 0)\",\"color\":\"rgba(255, 255, 255, 0.85)\",\"bold\":true},\"insert\":\"从一个页面（URL）切到另一个页面（URL）的动作，就叫路由跳转。\"},{\"insert\":\"\\n\"},{\"attributes\":{\"background\":\"rgba(0, 0, 0, 0)\",\"color\":\"rgba(255, 255, 255, 0.85)\"},\"insert\":\"就像你在 App 里点底部导航栏，从 \\\"首页\\\" 切到 \\\"我的\\\"，这个切换动作就是路由跳转。\"},{\"insert\":\"\\n路由跳转\\n一句话\\n从一个页面（URL）切到另一个页面（URL）的动作，就叫路由跳转。\\n就像你在 App 里点底部导航栏，从 \\\"首页\\\" 切到 \\\"我的\\\"，这个切换动作就是路由跳转。\\n1123\\n\"},{\"attributes\":{\"width\":\"314\"},\"insert\":{\"image\":\"http://localhost:8080/images/cache/20260819/ce53a74f3bad470283268832c303989b\"}},{\"insert\":\"\\n\"}]}',4,1,'2026-08-16 17:59:00',0,0,0),(13,'美国年轻人更习惯于一个重新崛起的中国','近年来，一些美国高校教师观察到，美国年轻人对中国的看法正在发生变化。\n据香港《南华早报》8月15日报道，莱斯大学全球事务课程教师史蒂文·刘易斯表示，他注意到，“人们对中国的看法正在变得更加积极”。\n刘易斯说：“我的学生们很清楚，他们接触到的传统媒体几乎一边倒地以负面方式描述中国，而这种报道所依据的政治利益未必与他们这一代人的利益一致。此外，他们普遍对老一辈人的动机和观念抱有一种深深的怀疑。”\n美国博主于中美身穿汉服、手持团扇 新华网\n这种怀疑不仅出现在课堂上，也延伸至社会层面。Searchlight研究所今年3月公布的一项调查显示，在18至34岁的美国人中，62%的人支持“与中国合作并改善中美关系”，29%的人则更倾向于“对抗中国”。\n国际危机组织负责美国与中国关系的高级研究及倡导顾问阿里·怀恩认为，这种变化是更广泛代际变化的一部分。\n“如今的美国年轻人更加习惯于一个重新崛起的中国。”怀恩说。\n他同时指出，经济焦虑也是一个重要因素。\n怀恩表示，由于美国年轻人对自己的经济前景更加悲观，他们更可能将与中国的战略竞争视为“对他们生活成本压力的干扰，而不是造成这种压力的原因”。\n怀恩还强调，年轻人更倾向于使用数字媒体平台，“这些平台能够让他们比传统媒体更加深入地了解中国人的日常生活”。刘易斯也将态度转变很大程度上归因于社交媒体曝光，以及对传统报道方式的深度不信任。他指出，许多美国学生从未去过中国，在多年负面媒体渲染后，他们越来越多地转向社交媒体，了解中国人的日常生活。“很多学生都表示，他们对前往中国、在中国旅行和生活感到非常惊讶，也很感兴趣。”\n视觉中国\n另一名受访者怀尔德也认为，社交媒体为美国年轻人提供了一扇了解中国城市日常生活的窗口，这是此前几代美国人所没有的。但他同时声称，这种变化也可能造成所谓的“认知盲区”，怀尔德认为，由于有关“中国人权”和“言论自由限制”的报道有限，社交媒体可能塑造出一个“过于正面”的中国形象。\n不过，整体影响不容忽视：中国正越来越成为美国年轻人亲身接触和感知的对象，而不再只是一个由别人告诉他们的国家。怀尔德说，越来越多的美国学生从小就开始学习中文，许多人还接触过中国学生或华裔美国人。“他们觉得这些人很有礼貌，也很友好。”他说。\n怀尔德还提到了美国年轻人对美国制度的“失望”，并指出，年轻美国人“并不认为美国的制度比中国的制度好多少”。\n随着这些学生进入政府、国会、智库和国家安全机构，问题在于他们的观点是否会随之进入决策圈。怀恩认为，这种转变可能会体现在有志于从事外交政策工作的专业人士观点中，但他也提醒，任何持久改变都会很缓慢，可能需要在多个总统任期内逐步显现。\n怀恩说，当前机构共识也会给特朗普继任者带来压力，迫使其对华“强硬”，与此同时中美争端不断扩大，国会也在持续批评现行中国政策。目前，“许多政策制定者对中美关系的解读，与相当一部分年轻美国人的看法之间存在明显鸿沟”，但随着新一代在华盛顿担任更有影响力的角色，这一鸿沟可能逐渐缩小。\n但这一代人是否真能重塑对华政策，既取决于未来事态发展，也取决于他们当下的信念。怀恩指出，如果中美紧张升级，无论是白宫采取更强硬的对华路线，还是双方爆发严重安全危机，年轻美国人的观点可能变得更加强硬。近年来，一些美国高校教师观察到，美国年轻人对中国的看法正在发生变化。\n据香港《南华早报》8月15日报道，莱斯大学全球事务课程教师史蒂文·刘易斯表示，他注意到，“人们对中国的看法正在变得更加积极”。\n刘易斯说：“我的学生们很清楚，他们接触到的传统媒体几乎一边倒地以负面方式描述中国，而这种报道所依据的政治利益未必与他们这一代人的利益一致。此外，他们普遍对老一辈人的动机和观念抱有一种深深的怀疑。”\n美国博主于中美身穿汉服、手持团扇 新华网\n这种怀疑不仅出现在课堂上，也延伸至社会层面。Searchlight研究所今年3月公布的一项调查显示，在18至34岁的美国人中，62%的人支持“与中国合作并改善中美关系”，29%的人则更倾向于“对抗中国”。\n国际危机组织负责美国与中国关系的高级研究及倡导顾问阿里·怀恩认为，这种变化是更广泛代际变化的一部分。\n“如今的美国年轻人更加习惯于一个重新崛起的中国。”怀恩说。\n他同时指出，经济焦虑也是一个重要因素。\n怀恩表示，由于美国年轻人对自己的经济前景更加悲观，他们更可能将与中国的战略竞争视为“对他们生活成本压力的干扰，而不是造成这种压力的原因”。\n怀恩还强调，年轻人更倾向于使用数字媒体平台，“这些平台能够让他们比传统媒体更加深入地了解中国人的日常生活”。刘易斯也将态度转变很大程度上归因于社交媒体曝光，以及对传统报道方式的深度不信任。他指出，许多美国学生从未去过中国，在多年负面媒体渲染后，他们越来越多地转向社交媒体，了解中国人的日常生活。“很多学生都表示，他们对前往中国、在中国旅行和生活感到非常惊讶，也很感兴趣。”\n视觉中国\n另一名受访者怀尔德也认为，社交媒体为美国年轻人提供了一扇了解中国城市日常生活的窗口，这是此前几代美国人所没有的。但他同时声称，这种变化也可能造成所谓的“认知盲区”，怀尔德认为，由于有关“中国人权”和“言论自由限制”的报道有限，社交媒体可能塑造出一个“过于正面”的中国形象。\n不过，整体影响不容忽视：中国正越来越成为美国年轻人亲身接触和感知的对象，而不再只是一个由别人告诉他们的国家。怀尔德说，越来越多的美国学生从小就开始学习中文，许多人还接触过中国学生或华裔美国人。“他们觉得这些人很有礼貌，也很友好。”他说。\n怀尔德还提到了美国年轻人对美国制度的“失望”，并指出，年轻美国人“并不认为美国的制度比中国的制度好多少”。\n随着这些学生进入政府、国会、智库和国家安全机构，问题在于他们的观点是否会随之进入决策圈。怀恩认为，这种转变可能会体现在有志于从事外交政策工作的专业人士观点中，但他也提醒，任何持久改变都会很缓慢，可能需要在多个总统任期内逐步显现。\n怀恩说，当前机构共识也会给特朗普继任者带来压力，迫使其对华“强硬”，与此同时中美争端不断扩大，国会也在持续批评现行中国政策。目前，“许多政策制定者对中美关系的解读，与相当一部分年轻美国人的看法之间存在明显鸿沟”，但随着新一代在华盛顿担任更有影响力的角色，这一鸿沟可能逐渐缩小。\n但这一代人是否真能重塑对华政策，既取决于未来事态发展，也取决于他们当下的信念。怀恩指出，如果中美紧张升级，无论是白宫采取更强硬的对华路线，还是双方爆发严重安全危机，年轻美国人的观点可能变得更加强硬。\n','{\"ops\":[{\"insert\":\"近年来，一些美国高校教师观察到，美国年轻人对中国的看法正在发生变化。\\n据香港《南华早报》8月15日报道，莱斯大学全球事务课程教师史蒂文·刘易斯表示，他注意到，“人们对中国的看法正在变得更加积极”。\\n刘易斯说：“我的学生们很清楚，他们接触到的传统媒体几乎一边倒地以负面方式描述中国，而这种报道所依据的政治利益未必与他们这一代人的利益一致。此外，他们普遍对老一辈人的动机和观念抱有一种深深的怀疑。”\"},{\"attributes\":{\"width\":\"377\"},\"insert\":{\"image\":\"https://k.sinaimg.cn/n/sinakd20260816s/107/w1024h683/20260816/c92c-d6ac9346fe98cd2a7636d9b677cfcfdf.png/w700d1q75cms.jpg?by=cms_fixed_width\"}},{\"insert\":\"\\n美国博主于中美身穿汉服、手持团扇 新华网\\n这种怀疑不仅出现在课堂上，也延伸至社会层面。Searchlight研究所今年3月公布的一项调查显示，在18至34岁的美国人中，62%的人支持“与中国合作并改善中美关系”，29%的人则更倾向于“对抗中国”。\\n国际危机组织负责美国与中国关系的高级研究及倡导顾问阿里·怀恩认为，这种变化是更广泛代际变化的一部分。\\n“如今的美国年轻人更加习惯于一个重新崛起的中国。”怀恩说。\\n他同时指出，经济焦虑也是一个重要因素。\\n怀恩表示，由于美国年轻人对自己的经济前景更加悲观，他们更可能将与中国的战略竞争视为“对他们生活成本压力的干扰，而不是造成这种压力的原因”。\\n怀恩还强调，年轻人更倾向于使用数字媒体平台，“这些平台能够让他们比传统媒体更加深入地了解中国人的日常生活”。刘易斯也将态度转变很大程度上归因于社交媒体曝光，以及对传统报道方式的深度不信任。他指出，许多美国学生从未去过中国，在多年负面媒体渲染后，他们越来越多地转向社交媒体，了解中国人的日常生活。“很多学生都表示，他们对前往中国、在中国旅行和生活感到非常惊讶，也很感兴趣。”\"},{\"attributes\":{\"width\":\"326\"},\"insert\":{\"image\":\"https://k.sinaimg.cn/n/sinakd20260816s/214/w2048h1366/20260816/c0af-1f5a0cf468ca1f0c0f700642b96d20f9.jpg/w700d1q75cms.jpg?by=cms_fixed_width\"}},{\"insert\":\"\\n视觉中国\\n另一名受访者怀尔德也认为，社交媒体为美国年轻人提供了一扇了解中国城市日常生活的窗口，这是此前几代美国人所没有的。但他同时声称，这种变化也可能造成所谓的“认知盲区”，怀尔德认为，由于有关“中国人权”和“言论自由限制”的报道有限，社交媒体可能塑造出一个“过于正面”的中国形象。\\n不过，整体影响不容忽视：中国正越来越成为美国年轻人亲身接触和感知的对象，而不再只是一个由别人告诉他们的国家。怀尔德说，越来越多的美国学生从小就开始学习中文，许多人还接触过中国学生或华裔美国人。“他们觉得这些人很有礼貌，也很友好。”他说。\\n怀尔德还提到了美国年轻人对美国制度的“失望”，并指出，年轻美国人“并不认为美国的制度比中国的制度好多少”。\\n随着这些学生进入政府、国会、智库和国家安全机构，问题在于他们的观点是否会随之进入决策圈。怀恩认为，这种转变可能会体现在有志于从事外交政策工作的专业人士观点中，但他也提醒，任何持久改变都会很缓慢，可能需要在多个总统任期内逐步显现。\\n怀恩说，当前机构共识也会给特朗普继任者带来压力，迫使其对华“强硬”，与此同时中美争端不断扩大，国会也在持续批评现行中国政策。目前，“许多政策制定者对中美关系的解读，与相当一部分年轻美国人的看法之间存在明显鸿沟”，但随着新一代在华盛顿担任更有影响力的角色，这一鸿沟可能逐渐缩小。\\n但这一代人是否真能重塑对华政策，既取决于未来事态发展，也取决于他们当下的信念。怀恩指出，如果中美紧张升级，无论是白宫采取更强硬的对华路线，还是双方爆发严重安全危机，年轻美国人的观点可能变得更加强硬。近年来，一些美国高校教师观察到，美国年轻人对中国的看法正在发生变化。\\n据香港《南华早报》8月15日报道，莱斯大学全球事务课程教师史蒂文·刘易斯表示，他注意到，“人们对中国的看法正在变得更加积极”。\\n刘易斯说：“我的学生们很清楚，他们接触到的传统媒体几乎一边倒地以负面方式描述中国，而这种报道所依据的政治利益未必与他们这一代人的利益一致。此外，他们普遍对老一辈人的动机和观念抱有一种深深的怀疑。”\\n美国博主于中美身穿汉服、手持团扇 新华网\\n这种怀疑不仅出现在课堂上，也延伸至社会层面。Searchlight研究所今年3月公布的一项调查显示，在18至34岁的美国人中，62%的人支持“与中国合作并改善中美关系”，29%的人则更倾向于“对抗中国”。\\n国际危机组织负责美国与中国关系的高级研究及倡导顾问阿里·怀恩认为，这种变化是更广泛代际变化的一部分。\\n“如今的美国年轻人更加习惯于一个重新崛起的中国。”怀恩说。\\n他同时指出，经济焦虑也是一个重要因素。\\n怀恩表示，由于美国年轻人对自己的经济前景更加悲观，他们更可能将与中国的战略竞争视为“对他们生活成本压力的干扰，而不是造成这种压力的原因”。\\n怀恩还强调，年轻人更倾向于使用数字媒体平台，“这些平台能够让他们比传统媒体更加深入地了解中国人的日常生活”。刘易斯也将态度转变很大程度上归因于社交媒体曝光，以及对传统报道方式的深度不信任。他指出，许多美国学生从未去过中国，在多年负面媒体渲染后，他们越来越多地转向社交媒体，了解中国人的日常生活。“很多学生都表示，他们对前往中国、在中国旅行和生活感到非常惊讶，也很感兴趣。”\\n视觉中国\\n另一名受访者怀尔德也认为，社交媒体为美国年轻人提供了一扇了解中国城市日常生活的窗口，这是此前几代美国人所没有的。但他同时声称，这种变化也可能造成所谓的“认知盲区”，怀尔德认为，由于有关“中国人权”和“言论自由限制”的报道有限，社交媒体可能塑造出一个“过于正面”的中国形象。\\n不过，整体影响不容忽视：中国正越来越成为美国年轻人亲身接触和感知的对象，而不再只是一个由别人告诉他们的国家。怀尔德说，越来越多的美国学生从小就开始学习中文，许多人还接触过中国学生或华裔美国人。“他们觉得这些人很有礼貌，也很友好。”他说。\\n怀尔德还提到了美国年轻人对美国制度的“失望”，并指出，年轻美国人“并不认为美国的制度比中国的制度好多少”。\\n随着这些学生进入政府、国会、智库和国家安全机构，问题在于他们的观点是否会随之进入决策圈。怀恩认为，这种转变可能会体现在有志于从事外交政策工作的专业人士观点中，但他也提醒，任何持久改变都会很缓慢，可能需要在多个总统任期内逐步显现。\\n怀恩说，当前机构共识也会给特朗普继任者带来压力，迫使其对华“强硬”，与此同时中美争端不断扩大，国会也在持续批评现行中国政策。目前，“许多政策制定者对中美关系的解读，与相当一部分年轻美国人的看法之间存在明显鸿沟”，但随着新一代在华盛顿担任更有影响力的角色，这一鸿沟可能逐渐缩小。\\n但这一代人是否真能重塑对华政策，既取决于未来事态发展，也取决于他们当下的信念。怀恩指出，如果中美紧张升级，无论是白宫采取更强硬的对华路线，还是双方爆发严重安全危机，年轻美国人的观点可能变得更加强硬。\\n\"}]}',4,1,'2026-08-16 18:21:07',0,0,0),(14,'新人报道，请多指教','大家好，我今天第一次注册，请问需要注意什么\n','{\"ops\":[{\"insert\":\"大家好，我今天第一次注册，请问需要注意什么\\n\"}]}',5,1,'2026-08-20 14:13:13',0,0,0),(18,'干翻广应科百事通',NULL,'{\"ops\":[{\"insert\":\"能不能干翻广应科百事通干翻广应科百事通\\n\"}]}',4,3,'2026-08-21 17:45:42',0,1,1),(22,'123信息','hhh信息肌肤回复哈j j\n\n','{\"ops\":[{\"insert\":\"hhh信息肌肤回复哈j j\\n\\n\"}]}',5,2,'2026-08-30 14:57:27',0,0,0);
/*!40000 ALTER TABLE `db_topic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_topic_comment`
--

DROP TABLE IF EXISTS `db_topic_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_topic_comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int DEFAULT NULL,
  `tid` int DEFAULT NULL,
  `content` text,
  `time` datetime DEFAULT NULL,
  `quote` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_topic_comment`
--

LOCK TABLES `db_topic_comment` WRITE;
/*!40000 ALTER TABLE `db_topic_comment` DISABLE KEYS */;
INSERT INTO `db_topic_comment` VALUES (1,4,12,'{\"ops\":[{\"insert\":\"我是一条评论\\n\"}]}','2026-08-19 16:57:29',-1),(2,4,12,'{\"ops\":[{\"insert\":\"我是一条评论\\n\"}]}','2026-08-19 16:58:09',-1),(5,4,11,'{\"ops\":[{\"insert\":\"111\\n\"}]}','2026-08-19 17:04:26',-1),(6,4,12,'{\"ops\":[{\"insert\":\"111\\n\"}]}','2026-08-19 18:45:44',-1),(7,4,12,'{\"ops\":[{\"insert\":\"afewfw\\n\"}]}','2026-08-19 18:51:12',-1),(8,4,12,'{\"ops\":[{\"insert\":\"fewafwaf\\n\"}]}','2026-08-19 19:00:24',-1),(9,4,12,'{\"ops\":[{\"insert\":\"fasfefe\\n\"}]}','2026-08-19 19:00:36',-1),(10,4,12,'{\"ops\":[{\"insert\":\"afewfw\\n\"}]}','2026-08-19 18:51:12',-1),(14,4,12,'{\"ops\":[{\"insert\":\"dad\\n\"}]}','2026-08-19 19:17:31',-1),(15,4,12,'{\"ops\":[{\"insert\":\"fasfefe\\n\"}]}','2026-08-19 19:00:36',-1),(20,4,12,'{\"ops\":[{\"insert\":\"压力的压力\\n\"}]}','2026-08-20 13:34:35',19),(21,4,12,'{\"ops\":[{\"insert\":\"压力的压力的压力\\n\"}]}','2026-08-20 13:34:48',20),(22,4,11,'{\"ops\":[{\"insert\":\"我是楼中楼\\n\"}]}','2026-08-20 14:01:14',-1),(23,4,11,'{\"ops\":[{\"insert\":\"1111\\n\"}]}','2026-08-20 14:01:19',22),(24,4,14,'{\"ops\":[{\"insert\":\"wc，牛逼\\n\"}]}','2026-08-20 14:47:22',-1),(26,5,14,'{\"ops\":[{\"insert\":\"真的厉害\\n\"}]}','2026-08-20 14:48:26',24),(33,4,15,'{\"ops\":[{\"insert\":\"lbl\\n\"}]}','2026-08-20 16:45:40',-1),(34,5,15,'{\"ops\":[{\"insert\":\"666\\n\"}]}','2026-08-20 16:45:50',33);
/*!40000 ALTER TABLE `db_topic_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_topic_interact_collect`
--

DROP TABLE IF EXISTS `db_topic_interact_collect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_topic_interact_collect` (
  `tid` int DEFAULT NULL,
  `uid` int DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  UNIQUE KEY `tid_uid_collect` (`tid`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_topic_interact_collect`
--

LOCK TABLES `db_topic_interact_collect` WRITE;
/*!40000 ALTER TABLE `db_topic_interact_collect` DISABLE KEYS */;
INSERT INTO `db_topic_interact_collect` VALUES (12,4,'2026-08-18 17:54:31'),(11,4,'2026-08-18 17:54:49'),(13,5,'2026-08-20 14:38:02'),(16,4,'2026-08-20 16:57:29');
/*!40000 ALTER TABLE `db_topic_interact_collect` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_topic_interact_like`
--

DROP TABLE IF EXISTS `db_topic_interact_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_topic_interact_like` (
  `tid` int DEFAULT NULL,
  `uid` int DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  UNIQUE KEY `tid_uid_like` (`tid`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_topic_interact_like`
--

LOCK TABLES `db_topic_interact_like` WRITE;
/*!40000 ALTER TABLE `db_topic_interact_like` DISABLE KEYS */;
INSERT INTO `db_topic_interact_like` VALUES (11,4,'2026-08-18 17:07:48'),(13,4,'2026-08-18 17:08:57'),(13,5,'2026-08-20 14:38:01'),(14,4,'2026-08-20 16:32:38'),(9,4,'2026-08-20 16:32:43'),(15,4,'2026-08-20 16:44:10'),(16,4,'2026-08-20 16:57:28');
/*!40000 ALTER TABLE `db_topic_interact_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_topic_type`
--

DROP TABLE IF EXISTS `db_topic_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_topic_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1459175426 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_topic_type`
--

LOCK TABLES `db_topic_type` WRITE;
/*!40000 ALTER TABLE `db_topic_type` DISABLE KEYS */;
INSERT INTO `db_topic_type` VALUES (1,'日常闲聊','在这里分享你的各种日常','#1E90FF'),(2,'真诚交友','在校园里寻找与自己志同道合的朋友','#CE1EFF'),(3,'问题反馈','反馈你在校园里遇到的问题','#E07373'),(4,'恋爱官宣','向大家展示你的恋爱成果了','#E0CE73');
/*!40000 ALTER TABLE `db_topic_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'study1'
--

--
-- Dumping routines for database 'study1'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-30 17:11:20
