/*
Navicat MySQL Data Transfer

Source Server         : a
Source Server Version : 80030
Source Host           : localhost:3306
Source Database       : book

Target Server Type    : MYSQL
Target Server Version : 80030
File Encoding         : 65001

Date: 2024-01-04 11:36:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for books
-- ----------------------------
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '书籍编号',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '书籍名称',
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '书籍描述',
  `number` int DEFAULT NULL COMMENT '库存数量',
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '书籍图片',
  `sortid` bigint DEFAULT NULL COMMENT '书籍分类',
  `author` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '作者',
  `language` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '语言',
  `version` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '版本',
  `price` int DEFAULT NULL COMMENT '图书价格',
  `isdeleted` int DEFAULT '1' COMMENT '图书删除状态(1:存在 0:已删除)',
  `news` int DEFAULT '0',
  `passwords` varchar(255) DEFAULT NULL,
  `hot` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1397849417888346150 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of books
-- ----------------------------
INSERT INTO `books` VALUES ('1397849417888346113', 'Java从入门到精通', '95万Java程序员的选择', '2', 'http://localhost:8086/book/photo/111111111.png', '1397849417888346125', '明日科技', '', '', '34', '1', '0', null, '0');
INSERT INTO `books` VALUES ('1397849417888346114', '寒夜无声', '取材自东北真实陈年旧案，90年代下岗潮后，底层边缘人物的飘零人生', '3', 'http://localhost:8086/book/photo/222222222.png', '1397849417888346121', '吴忠全', null, '第七版', '56', '1', '0', null, '0');
INSERT INTO `books` VALUES ('1397849417888346115', '深蓝的故事', '现象级警察故事', '3', 'http://localhost:8086/book/photo/333333333.png', '1397849417888346121', '深蓝', null, null, '45', '1', '0', null, '0');
INSERT INTO `books` VALUES ('1397849417888346116', '心理学的100个基本', '人生无需大道理，懂点心理学过好每一天', '2', 'http://localhost:8086/book/photo/444444444.png', '1397849417888346124', '陈琳', null, null, '46', '1', '0', null, '0');
INSERT INTO `books` VALUES ('1397849417888346117', '大变局', '剖析王朝灭亡真相，透视大清改革历程', '3', 'http://localhost:8086/book/photo/5555555555.png', '1397849417888346123', '谌旭彬', null, null, '24', '1', '0', null, '0');
INSERT INTO `books` VALUES ('1397849417888346118', '奔跑的少年', '鼓起勇气奔跑，每个人都可以成为英雄', '3', 'http://localhost:8086/book/photo/777777777.png', '1397849417888346121', '克里斯托福·保罗', null, null, '38', '1', '0', null, '0');
INSERT INTO `books` VALUES ('1397849417888346119', '杀死一只知更鸟', '20世纪30年代，大萧条时期美国南方静谧的小镇，几桩离奇的疑案彻底打破了几个孩子平静的生活', '3', 'http://localhost:8086/book/photo/888888888.png', '1397849417888346121', '哈珀·李', null, null, '76', '1', '0', null, '0');
INSERT INTO `books` VALUES ('1397849417888346120', 'C++ Primer', '对C++基本概念和技术全面而且权威的阐述，对现代C++编程风格的强调，使本书成为C++初学者的最佳指南', '3', 'http://localhost:8086/book/photo/999999999.png', '1397849417888346125', 'Stanley B. LippmanBarbara', null, null, '45', '1', '1', null, '0');
INSERT INTO `books` VALUES ('1397849417888346121', '我越来越喜欢自己了', '停止精神内耗，享受松弛人生', '3', 'http://localhost:8086/book/photo/000000000.png', '1397849417888346140', '燕七', null, null, '23', '1', '1', null, '0');
INSERT INTO `books` VALUES ('1397849417888346122', '顿感力', '告诫现代人不要对日常生活太过敏感，“钝感力”（迟钝的能力）是非常必要的', '3', 'http://localhost:8086/book/photo/121212121.png', '1397849417888346124', '渡边淳', null, null, '44', '1', '1', null, '0');
INSERT INTO `books` VALUES ('1397849417888346124', '我与地坛', '这部作品是史铁生文学作品中,充满哲思又极为人性化的代表作之一', '3', 'http://localhost:8086/book/photo/131313131.png', '1397849417888346121', '史铁生', null, null, '56', '1', '0', null, '0');
INSERT INTO `books` VALUES ('1397849417888346125', '月亮与六便士', '是理想与现实的碰撞', '3', 'http://localhost:8086/book/photo/141414141.png', '1397849417888346121', '威廉·萨默赛特·毛姆', null, null, '34', '1', '0', null, '0');
INSERT INTO `books` VALUES ('1397849417888346126', '围城', '主要写抗战初期知识分子的群像', '3', 'http://localhost:8086/book/photo/151515151.png', '1397849417888346121', '钱钟书', null, null, '36', '1', '0', null, '0');
INSERT INTO `books` VALUES ('1397849417888346127', '往事', '描写了湘西淳朴天然的人情世道，野性自由的生命形态，以及澄澈纯净的人性', '3', 'http://localhost:8086/book/photo/6666666666.png', '1397849417888346121', '沈从文', null, null, '46', '1', '0', null, '0');
INSERT INTO `books` VALUES ('1397849417888346128', '追风筝的人', '讲述了一个富家少年与家中仆人，关于人性的背叛与救赎的故事', '3', 'http://localhost:8086/book/photo/161616161.png', '1397849417888346121', '卡勒德·胡塞尼', null, null, '25', '1', '0', null, '0');
INSERT INTO `books` VALUES ('1397849417888346129', '悲惨世界', '内容涵盖了拿破仑战争和之后的十几年的时间', '3', 'http://localhost:8086/book/photo/171717171.png', '1397849417888346121', '雨果', null, null, '78', '1', '0', null, '0');
INSERT INTO `books` VALUES ('1397849417888346130', '万历十五年', '分别写了最高统治者万历皇帝、大学士申时行、首辅张居正、模范官僚海瑞、自由派知识分子李贽、抗倭英雄戚继光等', '3', 'http://localhost:8086/book/photo/181818181.png', '1397849417888346123', '黄仁宇', null, null, '36', '1', '0', null, '0');
INSERT INTO `books` VALUES ('1397849417888346132', '规则为什么会失败', '法律管不住的人类行为暗码', '3', 'http://localhost:8086/book/photo/191919191.png', '1397849417888346124', '本雅明·范·罗伊', null, null, '48', '1', '0', null, '1');
INSERT INTO `books` VALUES ('1397849417888346133', '超会学习的大脑', '中学生必备学习法', '3', 'http://localhost:8086/book/photo/202020202.png', '1397849417888346124', '大卫 · 霍奇森', null, null, '37', '1', '0', null, '1');
INSERT INTO `books` VALUES ('1397849417888346134', '对不起我可能对人过敏', '内向不是性格的缺陷，它只是性格的一种', '3', 'http://localhost:8086/book/photo/212121212.png', '1397849417888346124', '吴冕', null, null, '36', '1', '0', null, '1');
INSERT INTO `books` VALUES ('1397849417888346140', '局外人', '形象地体现了存在主义哲学关于“荒谬”的观念', '3', 'http://localhost:8086/book/photo/232323232.png', '1397849417888346121', '阿尔贝·加缪', null, null, '29', '1', '0', null, '0');
INSERT INTO `books` VALUES ('1397849417888346149', '打胜仗', '常胜团队的成功密码', '2', 'http://localhost:8086/book/photo/e4a93a16-7799-482e-9fdf-75726cfc995d.png', '1397849417888346123', '田涛', '中文', '', '45', '1', '0', null, '0');

-- ----------------------------
-- Table structure for booksort
-- ----------------------------
DROP TABLE IF EXISTS `booksort`;
CREATE TABLE `booksort` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `name` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '图书类名',
  `description` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1397849417888346146 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of booksort
-- ----------------------------
INSERT INTO `booksort` VALUES ('1397849417888346121', '小说类', '小说是最受欢迎的书籍类型之一，它们通过创造性的叙述和人物塑造，带领我们进入一个充满想象力的世界');
INSERT INTO `booksort` VALUES ('1397849417888346122', '传记类', '传记类书籍通过记录一个人的生平经历和成就，向我们展示了人类意志和毅力的力量');
INSERT INTO `booksort` VALUES ('1397849417888346123', '历史类', '历史类书籍通过记录过去的事件和人物，帮助我们理解现在和预测未来');
INSERT INTO `booksort` VALUES ('1397849417888346124', '哲学类', '哲学类书籍通过深入探讨人类的本质、意义和价值，帮助我们理清思路并找到生活的方向');
INSERT INTO `booksort` VALUES ('1397849417888346125', '科学类', '科学类书籍通过介绍最新的科学成果和技术进步，帮助我们理解自然世界和宇宙的奥秘');
INSERT INTO `booksort` VALUES ('1397849417888346126', '诗歌类', '诗歌类书籍通过优美的语言和节奏，激发我们的情感和美感');
INSERT INTO `booksort` VALUES ('1397849417888346140', '漫画类', '漫画类书籍通过书中的图画给读者一种身临其境的感觉');

-- ----------------------------
-- Table structure for borrow
-- ----------------------------
DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '借阅id',
  `bookid` bigint NOT NULL COMMENT '图书id',
  `studentid` bigint NOT NULL COMMENT '借阅人id',
  `createtime` datetime DEFAULT NULL COMMENT '借出时间',
  `backtime` datetime DEFAULT NULL COMMENT '归还时间',
  `endtime` datetime DEFAULT NULL COMMENT '截止时间',
  `status` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '0' COMMENT '借阅状态(1:已归还 0:未归还)',
  `sback` int DEFAULT '0' COMMENT '学生归还状态(0: 未归还 1:已归还)',
  `mback` int DEFAULT '0' COMMENT '管理员确认归还状态(0: 未确认 1: 已确认)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1397849417888346151 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of borrow
-- ----------------------------
INSERT INTO `borrow` VALUES ('1397849417888346148', '1397849417888346120', '1318984489648525322', '2024-01-03 16:33:45', '2024-01-03 16:33:56', '2025-01-03 16:33:45', '1', '1', '1');
INSERT INTO `borrow` VALUES ('1397849417888346149', '1397849417888346116', '1318984489648525322', '2024-01-03 16:33:50', '2024-01-04 08:57:35', '2025-01-03 16:33:50', '0', '1', '0');
INSERT INTO `borrow` VALUES ('1397849417888346150', '1397849417888346113', '1318984489648525322', '2024-01-03 17:47:51', null, '2025-01-03 17:47:51', '0', '0', '0');

-- ----------------------------
-- Table structure for manegers
-- ----------------------------
DROP TABLE IF EXISTS `manegers`;
CREATE TABLE `manegers` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '表里的编号',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '名字',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT 'e10adc3949ba59abbe56e057f20f883e' COMMENT '密码',
  `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '电话号码',
  `sex` varchar(255) DEFAULT NULL COMMENT '性别',
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '’http://localhost:8086/book/photo/manager.jpg‘' COMMENT '头像',
  `username` varchar(255) DEFAULT NULL COMMENT '账号',
  `status` int DEFAULT '0' COMMENT '管理员使用状态',
  `passwords` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1397844263642378254 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of manegers
-- ----------------------------
INSERT INTO `manegers` VALUES ('1397844263642378242', '管理员', 'e10adc3949ba59abbe56e057f20f883e', '14837362677', '男', 'http://localhost:8086/book/photo/77086445-ce33-4717-9039-9692b504c7ff.jpg', 'admin', '1', null);
INSERT INTO `manegers` VALUES ('1397844263642378243', 'Jack', 'e10adc3949ba59abbe56e057f20f883e', '13527896378', '男', 'http://localhost:8086/book/photo/manager.jpg', 'Jack', '1', null);
INSERT INTO `manegers` VALUES ('1397844263642378244', 'Suri', 'e10adc3949ba59abbe56e057f20f883e', '15748932764', '女', 'http://localhost:8086/book/photo/manager.jpg', 'Suri', '1', null);
INSERT INTO `manegers` VALUES ('1397844263642378245', 'Tom', 'e10adc3949ba59abbe56e057f20f883e', '12784567890', '男', 'http://localhost:8086/book/photo/manager.jpg', 'Tom', '1', null);
INSERT INTO `manegers` VALUES ('1397844263642378246', 'Morgan', 'e10adc3949ba59abbe56e057f20f883e', '15927361837', '女', 'http://localhost:8086/book/photo/manager.jpg', 'Morgan', '1', null);
INSERT INTO `manegers` VALUES ('1397844263642378247', 'Lisa', 'e10adc3949ba59abbe56e057f20f883e', '15948391982', '女', 'http://localhost:8086/book/photo/manager.jpg', 'Lisa', '1', null);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` bigint NOT NULL,
  `createtime` datetime DEFAULT NULL COMMENT '发布时间',
  `content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '发布内容',
  `manegerid` bigint DEFAULT '1397844263642378242' COMMENT '发布者',
  `title` varchar(255) DEFAULT NULL COMMENT '标题'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('1740262567888760833', '2023-12-28 14:45:03', '今日开心', '1397844263642378242', null);
INSERT INTO `message` VALUES ('1740280026628849665', '2023-12-28 15:54:26', '今日开心', '1397844263642378243', null);
INSERT INTO `message` VALUES ('1740711329450921986', '2023-12-29 20:28:16', 'hahahahahahahahahahaah', '1397844263642378242', null);
INSERT INTO `message` VALUES ('1742078175013916674', '2024-01-02 14:59:38', '新年快乐', '1397844263642378242', null);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '表里的编号',
  `sex` varchar(255) DEFAULT NULL COMMENT '性别',
  `name` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '学生真实姓名',
  `password` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT 'e10adc3949ba59abbe56e057f20f883e' COMMENT '登录密码',
  `address` varchar(255) DEFAULT NULL COMMENT '读者住址',
  `status` int DEFAULT '1' COMMENT '是否禁用(1:启用 0:禁用)',
  `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '电话',
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT 'http://localhost:8086/book/photo/user.jpg' COMMENT '头像',
  `number` int DEFAULT '2' COMMENT '可借阅书的数量',
  `username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '账号名',
  `reputation` int DEFAULT '100' COMMENT '信誉值',
  `islogin` int DEFAULT '0' COMMENT '是否登录（0未登录1已登录）',
  `passwords` varchar(255) DEFAULT NULL COMMENT '确认密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1318984489648525396 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1318984489648525322', '男', 'Reader', 'e10adc3949ba59abbe56e057f20f883e', '河南省新乡市劳动街口', '1', '15928306506', 'http://localhost:8086/book/photo/5fca9ccc-e0bd-4e9d-86c6-df9274311299.jpg', '0', 'reader', '100', '0', null);
INSERT INTO `student` VALUES ('1318984489648525323', '女', '廖雪迎', '3dcd0f0bf9f79ebeb7ca5f21df1d624e', '河南省新乡市建设路8号', '1', '13838384439', 'http://localhost:8086/book/photo/user.jpg', '2', '李雪迎', '100', '0', null);
INSERT INTO `student` VALUES ('1318984489648525324', '男', '张三丰', '1180ed749fbad65783dc6d5775822bc5', '河南省新乡市成华大道', '1', '13847204735', 'http://localhost:8086/book/photo/user.jpg', '2', '张三丰', '100', '0', null);
INSERT INTO `student` VALUES ('1318984489648525325', '男', '王井', '3941b7a12b2b62449e65bec1ca2e8375', '河南省新乡市南苑北路', '1', '16723896452', 'http://localhost:8086/book/photo/user.jpg', '2', '王井', '100', '0', null);
INSERT INTO `student` VALUES ('1318984489648525345', '男', '赵小六', 'ff98cdbfdbf90edc897f1a190d7e2074', '河南省新乡市王府井路', '1', '17623894765', 'http://localhost:8086/book/photo/user.jpg', '2', '赵小六', '100', '0', null);
INSERT INTO `student` VALUES ('1318984489648525355', '女', '李一', 'd2e6fb1c22da59492f0ded58b4f9c2de', '河南省郑州市老城区23号', '1', '16427893412', 'http://localhost:8086/book/photo/user.jpg', '2', '李一', '100', '0', null);
