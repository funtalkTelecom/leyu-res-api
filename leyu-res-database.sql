/*
SQLyog Professional v12.09 (64 bit)
MySQL - 5.6.44 : Database - leyudb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `sequence` */

DROP TABLE IF EXISTS `sequence`;

CREATE TABLE `sequence` (
  `name` varchar(50) NOT NULL COMMENT '序列的名字',
  `current_value` int(11) NOT NULL COMMENT '序列的当前值',
  `increment` int(11) NOT NULL DEFAULT '1' COMMENT '序列的自增值',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sequence` */

insert  into `sequence`(`name`,`current_value`,`increment`) values ('leyu_seq_5',10031,1),('leyu_seq_8',10001017,1),('rbac_serial',100344,1);

/*Table structure for table `tb_permission` */

DROP TABLE IF EXISTS `tb_permission`;

CREATE TABLE `tb_permission` (
  `id` int(20) NOT NULL COMMENT 'id为6位(123456)时,12为上级菜单,34是当前菜单,56为当前菜单内的操作权限',
  `type` int(8) DEFAULT NULL COMMENT '1:菜单 2:页面内部操作权限',
  `pid` int(20) DEFAULT NULL COMMENT 'id=0时pid=null,其余时候不能为null',
  `name` varchar(50) DEFAULT '',
  `code` varchar(100) DEFAULT NULL COMMENT '唯一限制;页面内部操作权限,type=2,前端通过此字段匹配',
  `icon` varchar(300) DEFAULT '',
  `url` varchar(100) DEFAULT '',
  `seq` int(8) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/*Data for the table `tb_permission` */

insert  into `tb_permission`(`id`,`type`,`pid`,`name`,`code`,`icon`,`url`,`seq`) values (0,1,NULL,'菜单管理',NULL,'','',1),(10,1,0,'首页',NULL,'','',1),(50,1,0,'设置',NULL,'','',5),(100100,2,10,'后台首页',NULL,'el-icon-menu','index',1),(100201,3,100100,'添加','index_add','','',1),(100202,3,100100,'修改','index_edit','','',2),(500500,2,50,'用户管理',NULL,'el-icon-setting','set_user',1),(500600,2,50,'角色管理',NULL,'el-icon-setting','set_role',2),(500700,2,50,'权限管理',NULL,'el-icon-setting','set_right',3);

/*Table structure for table `tb_role` */

DROP TABLE IF EXISTS `tb_role`;

CREATE TABLE `tb_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `status` int(2) DEFAULT '1',
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10001018 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/*Data for the table `tb_role` */

insert  into `tb_role`(`id`,`name`,`status`,`remark`) values (10002,'管理员',1,NULL),(10003,'财务',1,NULL),(10004,'测试',1,NULL),(10005,'测试',1,NULL),(10006,'测试',1,NULL),(10013,'运营',1,NULL),(10001017,'销售',1,NULL);

/*Table structure for table `tb_role_permission` */

DROP TABLE IF EXISTS `tb_role_permission`;

CREATE TABLE `tb_role_permission` (
  `id` int(11) NOT NULL,
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/*Data for the table `tb_role_permission` */

insert  into `tb_role_permission`(`id`,`role_id`,`permission_id`) values (100169,10003,50),(100174,10003,500500),(100175,10003,500600),(100176,10003,500700),(100177,10003,0),(100254,10002,10),(100255,10002,100100),(100257,10002,100201),(100258,10002,100202),(100272,10002,500500),(100273,10002,500600),(100274,10002,500700),(100275,10002,0),(100276,10002,50),(100281,10013,50),(100286,10013,500500),(100287,10013,500600),(100288,10013,500700),(100289,10013,0),(100317,10001017,100100),(100318,10001017,100202),(100321,10001017,0),(100322,10001017,10),(100336,10004,50),(100341,10004,500500),(100342,10004,500600),(100343,10004,500700),(100344,10004,0);

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(2) DEFAULT '1' COMMENT '1后台管理用户  2前端普通用户 3合伙人 4代理商',
  `level_id` int(2) DEFAULT '1' COMMENT '用户等级',
  `login_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '',
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '密码',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '姓名',
  `sex` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '性别',
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '电话',
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '邮箱',
  `nick_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '昵称',
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '头像',
  `province` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '省份',
  `city` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '地市',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `idcard` varchar(18) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '身份证号码',
  `idcard_face` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '身份证正面',
  `idcard_back` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '身份证背面',
  `last_login_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最近一次登录时间',
  `status` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '状态：0禁用1启用',
  `from_id` int(11) DEFAULT NULL COMMENT '首次通过那个用户打开的',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10010007 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;

/*Data for the table `tb_user` */

insert  into `tb_user`(`id`,`type`,`level_id`,`login_name`,`password`,`name`,`sex`,`phone`,`email`,`nick_name`,`avatar`,`province`,`city`,`create_time`,`idcard`,`idcard_face`,`idcard_back`,`last_login_time`,`status`,`from_id`) values (10001008,1,1,'leyutest5','7d1768610d00708805b9bd2848d2b014','ceshi5','','19611601230','19611601230@qq.com','','','','','2020-09-27 11:40:18','','','','0000-00-00 00:00:00','1',NULL),(10001010,1,1,'admin1234','','乐语管理员','','18611600002','18611600001@qq.com','','','','','2020-09-28 13:42:50','','','','0000-00-00 00:00:00','1',NULL),(10001011,1,1,'leyutest1234','7883a77048b6205f8dc18c79e044cd82','测试1','','18616122000','18616122000@qq.com','','','','','2020-09-28 13:50:33','','','','0000-00-00 00:00:00','1',NULL),(10001012,1,1,'admin1014','5a1760628ea739e61d9bb798b50542d5','测试1014','','18611602000','18611602000@qq.com','','','','','2020-10-14 11:05:02','','','','0000-00-00 00:00:00','1',NULL),(10001013,1,1,'admin101401','5a1760628ea739e61d9bb798b50542d5','乐语101401','','18611602000','18611602000@qq.com','','','','','2020-10-14 11:48:20','','','','0000-00-00 00:00:00','1',NULL),(10010002,1,1,'admin','21232f297a57a5a743894a0e4a801fc3','管理员','','18965902603','','','https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1958268120,1792157865&fm=26&gp=0.jpg','','','2020-07-07 16:48:03','','','','0000-00-00 00:00:00','1',NULL),(10010003,1,1,'admin123','21232f297a57a5a743894a0e4a801fc3','测试小赵','','18965902603','18965902603@qq.com','','https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1958268120,1792157865&fm=26&gp=0.jpg','','','2020-07-07 16:48:03','','','','0000-00-00 00:00:00','1',NULL);

/*Table structure for table `tb_user_permission` */

DROP TABLE IF EXISTS `tb_user_permission`;

CREATE TABLE `tb_user_permission` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '鐢ㄦ埛id',
  `permission` int(11) DEFAULT NULL COMMENT '鏉冮檺id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/*Data for the table `tb_user_permission` */

insert  into `tb_user_permission`(`id`,`user_id`,`permission`) values (28499,22011,90000),(28500,22011,91100),(28501,22011,91101),(28502,22011,91102),(28503,22011,70000),(28504,22011,70600),(28505,22011,70602),(28506,22011,70601),(28507,22011,70604),(28508,22011,70603),(28509,22011,70606),(28510,22011,70605),(28511,22011,70500),(28512,22011,70501),(28513,22011,70503),(28514,22011,70502),(28515,22011,70200),(28516,22011,70204),(28517,22011,70203),(28518,22011,70202),(28519,22011,70201),(28520,22011,70100),(28521,22011,70104),(28522,22011,70101),(28523,22011,70103),(28524,22011,70102),(28525,22011,70300),(28526,22011,70303),(28527,22011,70302),(28528,22011,70305),(28529,22011,70304),(28530,22011,70301),(28531,22011,71100),(28532,22011,71101),(28533,22011,71102),(28534,22011,70900),(28535,22011,70901),(28536,22011,70903),(28537,22011,70902),(96837,22207,70000),(96838,22207,70100),(96839,22207,70105),(96840,22207,70104),(96841,22207,70101),(96842,22207,70103),(96843,22207,70102),(97838,1,90000),(97839,1,91200),(97840,1,91201),(97841,1,91204),(97842,1,91202),(97843,1,91203),(97844,1,90200),(97845,1,90201),(97846,1,90203),(97847,1,90001),(97848,1,90100),(97849,1,90101),(97850,1,91100),(97851,1,91101),(97852,1,91102),(97853,1,90400),(97854,1,90402),(97855,1,90401),(97856,1,90404),(97857,1,90403),(97858,1,90300),(97859,1,90301),(97860,1,90303),(97861,1,90302),(97862,1,90305),(97863,1,90304),(97864,1,90500),(97865,1,90501),(97866,1,90503),(97867,1,90502),(97868,1,90504),(97869,1,80000),(97870,1,80800),(97871,1,50000),(97872,1,80400),(97873,1,80403),(97874,1,80402),(97875,1,80401),(97876,1,60000),(97877,1,60900),(97878,1,2018082802),(97879,1,60902),(97880,1,60901),(97881,1,60200),(97882,1,60201),(97883,1,60100),(97884,1,60101),(97885,1,70000),(97886,1,70600),(97887,1,70611),(97888,1,70610),(97889,1,70602),(97890,1,70601),(97891,1,70612),(97892,1,70608),(97893,1,70618),(97894,1,70609),(97895,1,70604),(97896,1,70615),(97897,1,70603),(97898,1,70606),(97899,1,70605),(97900,1,70500),(97901,1,70501),(97902,1,70503),(97903,1,70502),(97904,1,70800),(97905,1,70802),(97906,1,70801),(97907,1,70700),(97908,1,70701),(97909,1,70703),(97910,1,70702),(97911,1,70200),(97912,1,70204),(97913,1,70203),(97914,1,70205),(97915,1,70202),(97916,1,70201),(97917,1,70100),(97918,1,70105),(97919,1,70104),(97920,1,70101),(97921,1,70103),(97922,1,70102),(97923,1,71200),(97924,1,71204),(97925,1,71202),(97926,1,71203),(97927,1,71201),(97928,1,70300),(97929,1,70303),(97930,1,70302),(97931,1,70305),(97932,1,70304),(97933,1,70301),(97934,1,71300),(97935,1,71303),(97936,1,71304),(97937,1,71301),(97938,1,71302),(97939,1,71100),(97940,1,71101),(97941,1,71102),(97942,1,70900),(97943,1,70901),(97944,1,70903),(97945,1,70902);

/*Table structure for table `tb_user_role` */

DROP TABLE IF EXISTS `tb_user_role`;

CREATE TABLE `tb_user_role` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni1` (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/*Data for the table `tb_user_role` */

insert  into `tb_user_role`(`id`,`user_id`,`role_id`,`remark`) values (100181,10001013,10003,NULL),(100293,10010003,10003,NULL),(100294,10010003,10013,NULL),(100295,10010003,10005,NULL),(10001000,10010002,10002,NULL);

/* Function  structure for function  `currval` */

/*!50003 DROP FUNCTION IF EXISTS `currval` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` FUNCTION `currval`(seq_name VARCHAR(50)) RETURNS int(11)
BEGIN 
     DECLARE VALUE INTEGER; 
     SET VALUE = 0; 
     SELECT current_value INTO VALUE 
          FROM sequence 
          WHERE NAME = seq_name; 
     RETURN VALUE; 
END */$$
DELIMITER ;

/* Function  structure for function  `nextval` */

/*!50003 DROP FUNCTION IF EXISTS `nextval` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` FUNCTION `nextval`(seq_name VARCHAR(50)) RETURNS int(11)
    DETERMINISTIC
BEGIN 
     UPDATE sequence 
          SET current_value = current_value + increment 
          WHERE NAME = seq_name; 
     RETURN currval(seq_name); 
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
