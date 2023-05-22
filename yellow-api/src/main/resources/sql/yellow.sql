/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50712
Source Host           : localhost:3306
Source Database       : yellow

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2023-05-18 14:52:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------

-- ----------------------------
-- Table structure for sys_detail_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_detail_log`;
CREATE TABLE `sys_detail_log` (
  `log_id` int(11) NOT NULL COMMENT '日志id',
  `log_type` int(11) NOT NULL COMMENT '日志类型：\r\n1-登录日志 \r\n2-操作日志',
  `detail` text COMMENT '日志详情',
  PRIMARY KEY (`log_id`,`log_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_detail_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `dict_type` varchar(100) NOT NULL COMMENT '字典类型',
  `dict_name` varchar(255) NOT NULL COMMENT '字典名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `sort` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `create_by` varchar(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型';

-- ----------------------------
-- Records of sys_dictionary
-- ----------------------------
INSERT INTO `sys_dictionary` VALUES ('1', 'gender', '性别', '', '0', '1067246875800000001', '2022-07-18 10:19:07', '1067246875800000001', '2022-07-18 10:19:07');
INSERT INTO `sys_dictionary` VALUES ('2', 'notice_type', '站内通知-类型', '', '1', '1067246875800000001', '2022-07-18 10:19:07', '1067246875800000001', '2022-07-18 10:19:07');
INSERT INTO `sys_dictionary` VALUES ('3', 'test', '测试', '', '0', '1067246875800000001', '2022-07-18 13:18:32', '1067246875800000001', '2022-07-18 13:18:32');

-- ----------------------------
-- Table structure for sys_dictionary_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary_data`;
CREATE TABLE `sys_dictionary_data` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `dict_type_id` bigint(20) NOT NULL COMMENT '字典类型ID',
  `dict_label` varchar(255) NOT NULL COMMENT '字典标签',
  `dict_value` varchar(255) DEFAULT NULL COMMENT '字典值',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `sort` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `create_by` varchar(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dict_type_value` (`dict_type_id`,`dict_value`),
  KEY `idx_sort` (`sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典数据';

-- ----------------------------
-- Records of sys_dictionary_data
-- ----------------------------
INSERT INTO `sys_dictionary_data` VALUES ('1', '1', '男', '0', '', '0', '1067246875800000001', '2022-07-18 10:19:07', '1067246875800000001', '2022-07-18 10:19:07');
INSERT INTO `sys_dictionary_data` VALUES ('2', '1', '女', '1', '', '1', '1067246875800000001', '2022-07-18 10:19:07', '1067246875800000001', '2022-07-18 10:19:07');
INSERT INTO `sys_dictionary_data` VALUES ('3', '1', '保密', '2', '', '2', '1067246875800000001', '2022-07-18 10:19:07', '1067246875800000001', '2022-07-18 10:19:07');
INSERT INTO `sys_dictionary_data` VALUES ('4', '2', '公告', '0', '', '0', '1067246875800000001', '2022-07-18 10:19:07', '1067246875800000001', '2022-07-18 10:19:07');
INSERT INTO `sys_dictionary_data` VALUES ('5', '2', '会议', '1', '', '1', '1067246875800000001', '2022-07-18 10:19:07', '1067246875800000001', '2022-07-18 10:19:07');
INSERT INTO `sys_dictionary_data` VALUES ('6', '2', '其他', '2', '', '2', '1067246875800000001', '2022-07-18 10:19:07', '1067246875800000001', '2022-07-18 10:19:07');

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `username` varchar(20) DEFAULT '' COMMENT '用户账号',
  `ip` varchar(50) DEFAULT '' COMMENT '登录IP地址',
  `address` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` varchar(10) DEFAULT '成功' COMMENT '登录状态（成功/ 失败）',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48804 DEFAULT CHARSET=utf8 COMMENT='系统登录日志';

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `auth_code` varchar(200) DEFAULT NULL COMMENT '权限代码',
  `menu_name` varchar(20) NOT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `type` int(1) NOT NULL COMMENT '菜单类型 0-目录 1-菜单 2-按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父菜单id (0-一级菜单）',
  `is_hidden` int(1) NOT NULL DEFAULT '0' COMMENT '是否隐藏 1-是 0-否',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 1-是 0-否',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=185 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统菜单/权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', null, '系统管理', null, '0', 'system', '0', '0', '0', '0', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('2', null, '用户管理', 'sys/user', '1', 'admin', '1', '1', '0', '0', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('3', null, '角色管理', 'sys/role', '1', 'role', '2', '1', '0', '0', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('4', null, '菜单管理', 'sys/menu', '1', 'menu', '3', '1', '0', '0', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('5', null, '接口文档', 'http://localhost:11100/yellow/doc.html#/home', '1', 'editor', '4', '1', '0', '0', null, null, '2023-05-16 16:56:49', 'zhouhao');
INSERT INTO `sys_menu` VALUES ('15', 'sys:user:list,sys:user:info', '查看', null, '2', null, '0', '2', '0', '0', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('16', 'sys:user:save', '新增', null, '2', null, '0', '2', '0', '0', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('17', 'sys:user:update', '修改', null, '2', null, '0', '2', '0', '0', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('18', 'sys:user:delete', '删除', null, '2', null, '0', '2', '0', '0', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('19', 'sys:role:list,sys:role:info,sys:role:select', '查看', null, '2', null, '0', '3', '0', '0', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('20', 'sys:role:save,sys:menu:list', '新增', null, '2', null, '0', '3', '0', '0', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('21', 'sys:role:update,sys:menu:list', '修改', null, '2', null, '0', '3', '0', '0', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('22', 'sys:role:delete', '删除', null, '2', null, '0', '3', '0', '0', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('23', 'sys:menu:list,sys:menu:info', '查看', null, '2', null, '0', '4', '0', '0', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('24', 'sys:menu:save,sys:menu:select', '新增', null, '2', null, '0', '4', '0', '0', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('25', 'sys:menu:update,sys:menu:select', '修改', null, '2', null, '0', '4', '0', '0', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('26', 'sys:menu:delete', '删除', null, '2', null, '0', '4', '0', '0', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('27', '', '字典管理', 'sys/dict-type', '1', 'config', '6', '1', '0', '0', null, null, '2023-05-17 16:19:07', 'zhouhao');
INSERT INTO `sys_menu` VALUES ('29', 'sys:log:list', '系统日志', 'sys/log', '1', 'log', '7', '1', '0', '0', null, null, '2023-05-17 09:36:59', 'zhouhao');
INSERT INTO `sys_menu` VALUES ('165', '', 'test', null, '0', 'daohang', '1', '0', '0', '0', '2023-05-12 11:06:17', 'zhouhao', '2023-05-12 11:06:17', 'zhouhao');
INSERT INTO `sys_menu` VALUES ('167', '', 'test001', 'test/test001', '1', 'role', '0', '165', '0', '0', '2023-05-12 11:07:49', 'zhouhao', '2023-05-12 11:09:01', 'zhouhao');
INSERT INTO `sys_menu` VALUES ('168', 'test:test001:test', 'test', null, '2', '', '0', '167', '0', '1', '2023-05-12 11:08:14', 'zhouhao', '2023-05-12 11:09:40', 'zhouhao');
INSERT INTO `sys_menu` VALUES ('169', '', 'test002', 'test/test002', '1', 'shezhi', '0', '165', '0', '0', '2023-05-12 11:14:01', 'zhouhao', '2023-05-12 11:14:01', 'zhouhao');
INSERT INTO `sys_menu` VALUES ('170', 'testtest', 'test', '', '2', '', '0', '169', '0', '0', '2023-05-12 11:14:23', 'zhouhao', '2023-05-12 11:14:33', 'zhouhao');
INSERT INTO `sys_menu` VALUES ('171', 'sys:user:reset', '重置密码', '', '2', '', '0', '2', '0', '0', '2023-05-15 13:53:50', 'zhouhao', '2023-05-15 13:53:50', 'zhouhao');
INSERT INTO `sys_menu` VALUES ('172', 'sys:user:changestatus', '修改用户状态', '', '2', '', '0', '2', '0', '0', '2023-05-15 16:53:13', 'zhouhao', '2023-05-15 16:53:13', 'zhouhao');
INSERT INTO `sys_menu` VALUES ('173', 'sys:log:list', '操作日志列表', '', '2', '', '0', '29', '0', '0', '2023-05-17 10:02:51', 'zhouhao', '2023-05-17 10:02:51', 'zhouhao');
INSERT INTO `sys_menu` VALUES ('174', 'sys:log:details', '异常详情', '', '2', '', '0', '29', '0', '0', '2023-05-17 10:56:08', 'zhouhao', '2023-05-17 10:56:08', 'zhouhao');
INSERT INTO `sys_menu` VALUES ('175', 'sys:dictionary:list', '字典列表', '', '2', '', '0', '27', '0', '0', '2023-05-18 11:00:18', 'zhouhao', '2023-05-18 11:00:18', 'zhouhao');
INSERT INTO `sys_menu` VALUES ('176', 'sys:dictionary:info', '字典详情', '', '2', '', '0', '27', '0', '0', '2023-05-18 11:03:20', 'zhouhao', '2023-05-18 11:03:20', 'zhouhao');
INSERT INTO `sys_menu` VALUES ('177', 'sys:dictionary:save', '添加字典', '', '2', '', '0', '27', '0', '0', '2023-05-18 11:03:49', 'zhouhao', '2023-05-18 11:03:49', 'zhouhao');
INSERT INTO `sys_menu` VALUES ('178', 'sys:dictionary:update', '修改字典', '', '2', '', '0', '27', '0', '0', '2023-05-18 11:04:06', 'zhouhao', '2023-05-18 11:04:06', 'zhouhao');
INSERT INTO `sys_menu` VALUES ('179', 'sys:dictionary:delete', '删除字典', '', '2', '', '0', '27', '0', '0', '2023-05-18 11:04:21', 'zhouhao', '2023-05-18 11:04:21', 'zhouhao');
INSERT INTO `sys_menu` VALUES ('180', 'sys:dictionarydata:list', '字典数据列表', null, '2', '', '0', '27', '0', '0', '2023-05-18 14:47:11', 'zhouhao', '2023-05-18 14:47:11', 'zhouhao');
INSERT INTO `sys_menu` VALUES ('181', 'sys:dictionarydata:info', '字典数据详情', null, '2', '', '0', '27', '0', '0', '2023-05-18 14:47:31', 'zhouhao', '2023-05-18 14:47:31', 'zhouhao');
INSERT INTO `sys_menu` VALUES ('182', 'sys:dictionarydata:save', '字典数据保存', null, '2', '', '0', '27', '0', '0', '2023-05-18 14:47:53', 'zhouhao', '2023-05-18 14:47:53', 'zhouhao');
INSERT INTO `sys_menu` VALUES ('183', 'sys:dictionarydata:update', '字典数据修改', null, '2', '', '0', '27', '0', '0', '2023-05-18 14:48:07', 'zhouhao', '2023-05-18 14:48:07', 'zhouhao');
INSERT INTO `sys_menu` VALUES ('184', 'sys:dictionarydata:delete', '字典数据删除', null, '2', '', '0', '27', '0', '0', '2023-05-18 14:48:21', 'zhouhao', '2023-05-18 14:48:21', 'zhouhao');

-- ----------------------------
-- Table structure for sys_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operate_log`;
CREATE TABLE `sys_operate_log` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `operate_event` varchar(100) DEFAULT '' COMMENT '操作事件/内容',
  `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
  `operate_name` varchar(50) DEFAULT '' COMMENT '操作人员',
  `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
  `operate_url` varchar(255) DEFAULT NULL COMMENT '请求URL',
  `method_name` varchar(100) DEFAULT '' COMMENT '方法名称',
  `operate_status` varchar(10) DEFAULT '成功' COMMENT '操作状态（成功/ 失败）',
  `time` int(20) DEFAULT NULL COMMENT '请求耗时（毫秒）',
  `params` varchar(1000) DEFAULT NULL COMMENT '请求参数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60426 DEFAULT CHARSET=utf8 COMMENT='系统操作日志';

-- ----------------------------
-- Records of sys_operate_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(20) NOT NULL COMMENT '角色代码',
  `role_name` varchar(20) NOT NULL COMMENT '角色名称',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '角色状态：1-启用 2-禁用',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 1-是 0-否',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='系统角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'ROLE_TEST', '测试管理员', '1', '负责系统各个功能模块的测试', '0', null, null, '2023-05-12 16:52:30', 'zhouhao');
INSERT INTO `sys_role` VALUES ('5', 'ROLE_ADMIN', '管理员', '1', '', '0', null, null, '2023-05-15 09:56:16', 'zhouhao');
INSERT INTO `sys_role` VALUES ('8', 'ROLE_DEMO', '演示人员', '1', '', '0', '2023-05-15 09:47:21', 'test', '2023-05-15 09:47:21', 'test');
INSERT INTO `sys_role` VALUES ('9', 'ROLE_SYSTEM', '系统管理员', '1', '', '0', '2023-05-15 09:57:10', 'zhouhao', '2023-05-15 09:57:10', 'zhouhao');
INSERT INTO `sys_role` VALUES ('10', 'ROLE_ROLE', 'ROLE', '1', '', '1', '2023-05-15 10:32:31', 'zhouhao', '2023-05-15 10:32:31', 'zhouhao');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '1');
INSERT INTO `sys_role_menu` VALUES ('1', '2');
INSERT INTO `sys_role_menu` VALUES ('1', '3');
INSERT INTO `sys_role_menu` VALUES ('1', '15');
INSERT INTO `sys_role_menu` VALUES ('1', '16');
INSERT INTO `sys_role_menu` VALUES ('1', '17');
INSERT INTO `sys_role_menu` VALUES ('1', '18');
INSERT INTO `sys_role_menu` VALUES ('1', '19');
INSERT INTO `sys_role_menu` VALUES ('1', '20');
INSERT INTO `sys_role_menu` VALUES ('1', '165');
INSERT INTO `sys_role_menu` VALUES ('1', '167');
INSERT INTO `sys_role_menu` VALUES ('1', '169');
INSERT INTO `sys_role_menu` VALUES ('1', '170');
INSERT INTO `sys_role_menu` VALUES ('5', '1');
INSERT INTO `sys_role_menu` VALUES ('5', '2');
INSERT INTO `sys_role_menu` VALUES ('5', '3');
INSERT INTO `sys_role_menu` VALUES ('5', '4');
INSERT INTO `sys_role_menu` VALUES ('5', '15');
INSERT INTO `sys_role_menu` VALUES ('5', '16');
INSERT INTO `sys_role_menu` VALUES ('5', '17');
INSERT INTO `sys_role_menu` VALUES ('5', '18');
INSERT INTO `sys_role_menu` VALUES ('5', '19');
INSERT INTO `sys_role_menu` VALUES ('5', '20');
INSERT INTO `sys_role_menu` VALUES ('5', '21');
INSERT INTO `sys_role_menu` VALUES ('5', '22');
INSERT INTO `sys_role_menu` VALUES ('5', '23');
INSERT INTO `sys_role_menu` VALUES ('5', '24');
INSERT INTO `sys_role_menu` VALUES ('5', '25');
INSERT INTO `sys_role_menu` VALUES ('5', '26');
INSERT INTO `sys_role_menu` VALUES ('8', '1');
INSERT INTO `sys_role_menu` VALUES ('8', '2');
INSERT INTO `sys_role_menu` VALUES ('8', '3');
INSERT INTO `sys_role_menu` VALUES ('8', '15');
INSERT INTO `sys_role_menu` VALUES ('8', '19');
INSERT INTO `sys_role_menu` VALUES ('9', '1');
INSERT INTO `sys_role_menu` VALUES ('9', '2');
INSERT INTO `sys_role_menu` VALUES ('9', '3');
INSERT INTO `sys_role_menu` VALUES ('9', '4');
INSERT INTO `sys_role_menu` VALUES ('9', '15');
INSERT INTO `sys_role_menu` VALUES ('9', '16');
INSERT INTO `sys_role_menu` VALUES ('9', '17');
INSERT INTO `sys_role_menu` VALUES ('9', '18');
INSERT INTO `sys_role_menu` VALUES ('9', '19');
INSERT INTO `sys_role_menu` VALUES ('9', '20');
INSERT INTO `sys_role_menu` VALUES ('9', '21');
INSERT INTO `sys_role_menu` VALUES ('9', '22');
INSERT INTO `sys_role_menu` VALUES ('9', '23');
INSERT INTO `sys_role_menu` VALUES ('9', '24');
INSERT INTO `sys_role_menu` VALUES ('9', '25');
INSERT INTO `sys_role_menu` VALUES ('9', '26');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` char(61) NOT NULL COMMENT '密码',
  `name` varchar(10) DEFAULT '匿名玩家' COMMENT '用户昵称',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号（留作备用，后期可延伸手机号登录业务）',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后一次登录系统时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '用户状态：1-启用 2-禁用',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 1-是 0-否',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'zhouhao', '$2a$10$MFzu.UvURdfB52ZyRVGTYed1DYLwac4Nsgy4zTGkLkzH10V0D7IV6', '那个男人', null, '2023-05-18 14:48:36', '1', '', '0', null, null, '2021-05-12 16:37:49', 'zhouhao');
INSERT INTO `sys_user` VALUES ('7', 'test', '$2a$10$5R.faR.5ZuQJbR29dSu58uR450dVvXw/qNpfTz9UkgfgNyNV6LRAa', '测试管理员', '01234567891', '2021-04-22 18:57:54', '1', null, '0', null, null, '2021-04-22 18:57:54', 'test');
INSERT INTO `sys_user` VALUES ('22', 'admin', '$2a$10$dJUEt2lFwx3Bl1RPnrJU.u.rILw7Rdz1qjfBi4uGMhmWUYg6OWU3G', '超级管理员', null, '2021-05-12 16:30:00', '1', '超级管理员，只此一枚哦~', '1', '2021-04-06 10:55:13', 'zhouhao', '2021-05-12 16:30:00', 'admin');
INSERT INTO `sys_user` VALUES ('31', 'system', '$2a$10$BCl5UST4j6tNjPjZOff0AOzyRJSMNJW/lWkIOWAUz5N/alz7N/QaK', '系统管理员', null, '2021-04-09 16:09:18', '1', null, '1', '2021-04-09 15:14:38', 'zhouhao', '2021-04-09 16:09:18', 'system');
INSERT INTO `sys_user` VALUES ('32', 'yangzhiwei', '$2a$10$puHJ7tRnF./wCZN7mFyVHeq.jmiXEdfkO5Fzg9dsL4DbfpZjuh2BK', '杨智伟', null, '2021-04-15 09:30:48', '1', null, '1', '2021-04-12 13:38:59', 'zhouhao', '2021-04-15 09:30:48', 'yangzhiwei');
INSERT INTO `sys_user` VALUES ('33', 'test2', '$2a$10$jfmguJAl5AIfQD.KdcNRIePpwRIdqceuDqHDNPe8vUW0vV26QsgPS', '匿名玩家', null, '2021-04-25 09:38:28', '1', null, '1', null, null, '2021-04-25 09:38:28', 'test2');
INSERT INTO `sys_user` VALUES ('34', 'show', '$2a$10$O4IeKGEGj6cYqwVfenFuYOZ5s9cQgpvfmNuRK5oUcp.szVwhqo0wK', '演示账号', null, '2022-01-12 13:15:07', '1', null, '0', null, null, '2022-01-12 13:15:07', 'show');
INSERT INTO `sys_user` VALUES ('35', 'test001', '$2a$10$cHnJZP1QZ9DZXpXFqgBEAeVD0KPiFVmTgcRXnDd2JB8H772uZjvo2', 'test', '01234567891', null, '1', null, '1', '2023-05-12 16:19:12', 'zhouhao', '2023-05-12 16:19:12', 'zhouhao');
INSERT INTO `sys_user` VALUES ('36', 'test002', '$2a$10$Un/Y5pbKXYRnTCUu6atjJe0fKbiMURWDcTE84MgJZTFkz4UPxW/aq', 'test002', '01234567891', null, '1', null, '1', '2023-05-12 16:19:54', 'zhouhao', '2023-05-12 16:19:54', 'zhouhao');
INSERT INTO `sys_user` VALUES ('37', 'test001', '$2a$10$32PAj8shGNLdkk1RWCj6dOSv2SlJXOY7lwhqbygATPWm49/hIOUaC', 'test001', '01234567891', null, '1', null, '1', '2023-05-12 16:26:09', 'zhouhao', '2023-05-12 16:26:09', 'zhouhao');
INSERT INTO `sys_user` VALUES ('38', 'demo001', '$2a$10$ahZtngxFzvTsgAflZBIYPebfV6ehRm/T7td7pPYQkdt5LAaS8VlSm', '演示001', '01234567891', null, '2', null, '0', '2023-05-15 09:53:12', 'test', '2023-05-15 09:53:12', 'test');
INSERT INTO `sys_user` VALUES ('39', 'sys001', '$2a$10$xqU9VifbRix4qKfAt9cq/ecyHS7WLgtNgGf9za3ko8KGO1r0vuOAG', '系统001', '01234567891', null, '1', null, '0', '2023-05-15 09:57:53', 'zhouhao', '2023-05-15 09:57:53', 'zhouhao');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '4');
INSERT INTO `sys_user_role` VALUES ('7', '1');
INSERT INTO `sys_user_role` VALUES ('7', '8');
INSERT INTO `sys_user_role` VALUES ('22', '4');
INSERT INTO `sys_user_role` VALUES ('31', '5');
INSERT INTO `sys_user_role` VALUES ('32', '4');
INSERT INTO `sys_user_role` VALUES ('33', '1');
INSERT INTO `sys_user_role` VALUES ('34', '1');
INSERT INTO `sys_user_role` VALUES ('35', '1');
INSERT INTO `sys_user_role` VALUES ('36', '1');
INSERT INTO `sys_user_role` VALUES ('37', '1');
INSERT INTO `sys_user_role` VALUES ('38', '8');
INSERT INTO `sys_user_role` VALUES ('39', '9');
INSERT INTO `sys_user_role` VALUES ('40', '5');
