/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50712
Source Host           : localhost:3306
Source Database       : yellow_cad

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2022-08-12 15:24:08
*/
CREATE DATABASE IF NOT EXISTS yellow_cad CHARACTER SET utf8;

USE yellow_cad;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for authority_operation
-- ----------------------------
DROP TABLE IF EXISTS `authority_operation`;
CREATE TABLE `authority_operation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表唯一标识',
  `operation_name` varchar(128) DEFAULT NULL COMMENT '权限操作名称',
  `operation_role_no` varchar(32) DEFAULT NULL COMMENT '关联的角色编号',
  `operation_url` varchar(128) DEFAULT NULL COMMENT '可访问的url地址',
  `operation_api` varchar(128) DEFAULT NULL COMMENT '可执行的API接口',
  `operation_status` int(11) DEFAULT '1' COMMENT '权限状态 0:失效；1：正常',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='**权限操作表 ';

-- ----------------------------
-- Records of authority_operation
-- ----------------------------
INSERT INTO `authority_operation` VALUES ('1', '巡检部门列表', '10002', null, '/epoi/pollDept/list', '1', '10000032', '2022-02-23 14:08:31', null, '2022-04-01 17:15:02');
INSERT INTO `authority_operation` VALUES ('2', '查询列表', '10002', null, '/ocmb/oc-game-paly-info/getGamePalyInfoUTg', '1', '10000032', '2022-02-23 14:08:57', null, '2022-02-23 14:10:27');
INSERT INTO `authority_operation` VALUES ('3', '查看详情', '10002', null, '/ocmb/oc-game-user-relation/getUserGamePalyInfo', '1', '10000032', '2022-02-23 14:09:13', null, '2022-02-23 14:10:28');
INSERT INTO `authority_operation` VALUES ('4', '登陆', '10003', null, '/ocmb/oc-user/getUserInfo', '1', '10000032', '2022-02-23 14:10:25', null, '2022-02-23 14:10:42');
INSERT INTO `authority_operation` VALUES ('5', '所属项目下拉框', '10004', null, '/equipment/projectSelectList', '1', null, '2022-05-31 11:09:26', null, '2022-05-31 11:09:26');
INSERT INTO `authority_operation` VALUES ('6', '设施编组列表', '10004', null, '/facility/group/list/{type}', '1', null, '2022-05-31 11:09:26', null, '2022-05-31 11:09:26');
INSERT INTO `authority_operation` VALUES ('7', '查询设备配置', '10004', null, '/config/equipment/selectList', '1', null, '2022-05-31 11:09:26', null, '2022-05-31 11:09:26');
INSERT INTO `authority_operation` VALUES ('8', '列表', '10004', null, '/equipment/page', '1', null, '2022-05-31 11:09:26', null, '2022-05-31 11:09:26');
INSERT INTO `authority_operation` VALUES ('9', '修改', '10004', null, '/equipment/edit', '1', null, '2022-05-31 11:09:26', null, '2022-05-31 11:09:26');
INSERT INTO `authority_operation` VALUES ('10', '增加', '10004', null, '/equipment/add', '1', null, '2022-05-31 11:09:26', null, '2022-05-31 11:09:26');
INSERT INTO `authority_operation` VALUES ('11', '删除', '10004', null, '/equipment/del', '1', null, '2022-05-31 11:09:26', null, '2022-05-31 11:09:26');
INSERT INTO `authority_operation` VALUES ('12', '设备导入', '10004', null, '/equipment/import', '1', null, '2022-05-31 11:09:26', null, '2022-05-31 11:09:26');
INSERT INTO `authority_operation` VALUES ('13', '设备导出', '10004', null, '/equipment/export', '1', null, '2022-05-31 11:09:26', null, '2022-05-31 11:09:26');
INSERT INTO `authority_operation` VALUES ('14', '行政区域下拉框', '10004', null, '/equipment/areaSelectList', '1', null, '2022-05-31 11:09:26', null, '2022-05-31 11:09:26');
INSERT INTO `authority_operation` VALUES ('15', '所属项目下拉框', '10004', null, '/equipment/projectSelectList', '1', null, '2022-05-31 11:09:26', null, '2022-05-31 11:09:26');
INSERT INTO `authority_operation` VALUES ('16', '设备状态下拉框', '10004', null, '/equipment/statusSelectList', '1', null, '2022-05-31 11:09:26', null, '2022-05-31 11:09:26');
INSERT INTO `authority_operation` VALUES ('17', '详情-管养-事件列表', '10004', null, '/equipment/detail/list/{page}/{size}', '1', null, '2022-05-31 11:09:26', null, '2022-05-31 11:09:26');
INSERT INTO `authority_operation` VALUES ('18', '巡检部门下拉框', '10004', null, '/pollDept/selectList', '1', null, '2022-05-31 11:09:26', null, '2022-05-31 11:09:26');
INSERT INTO `authority_operation` VALUES ('19', '设施编组列表', '10006', null, '/facility/group/list/{type}', '1', null, '2022-05-31 11:09:26', null, '2022-05-31 11:09:26');
INSERT INTO `authority_operation` VALUES ('20', '查询机柜', '10006', null, '/equipment/cabinet/selectList', '1', null, '2022-05-31 11:09:26', null, '2022-05-31 11:09:26');
INSERT INTO `authority_operation` VALUES ('21', '查询设备配置', '10006', null, '/config/equipment/selectList', '1', null, '2022-05-31 11:09:26', null, '2022-05-31 11:09:26');
INSERT INTO `authority_operation` VALUES ('22', '修改', '10006', null, '/equipment/cabinet/edit', '1', null, '2022-05-31 11:09:26', null, '2022-05-31 11:09:26');
INSERT INTO `authority_operation` VALUES ('23', '增加', '10006', null, '/equipment/cabinet/add', '1', null, '2022-05-31 11:09:26', null, '2022-05-31 11:09:26');
INSERT INTO `authority_operation` VALUES ('24', '删除机柜信息', '10006', null, '/equipment/cabinet/del/{id}', '1', null, '2022-05-31 11:09:26', null, '2022-05-31 11:09:26');
INSERT INTO `authority_operation` VALUES ('25', '详情-管养-事件列表', '10006', null, '/equipment/detail/list/{page}/{size}', '1', null, '2022-05-31 11:09:26', null, '2022-05-31 11:09:26');
INSERT INTO `authority_operation` VALUES ('26', '巡检部门下拉框', '10006', null, '/pollDept/selectList', '1', null, '2022-05-31 11:09:26', null, '2022-05-31 11:09:26');

-- ----------------------------
-- Table structure for authority_role
-- ----------------------------
DROP TABLE IF EXISTS `authority_role`;
CREATE TABLE `authority_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表唯一标识',
  `role_no` varchar(32) NOT NULL COMMENT '角色编号',
  `role_name` varchar(128) DEFAULT NULL COMMENT '角色名称',
  `role_status` int(11) DEFAULT '1' COMMENT '角色状态 0:异常、1:正常、2:逻辑删除',
  `role_type` int(11) DEFAULT '99' COMMENT '角色类型 101:最大操作权限；\n99:受权限限制；',
  `role_belonging` varchar(32) DEFAULT NULL COMMENT '角色所属 所属的上级角色编号',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='**权限角色表 ';

-- ----------------------------
-- Records of authority_role
-- ----------------------------
INSERT INTO `authority_role` VALUES ('1', '10001', '超级管理员', '1', '101', null, '10000032', '2022-02-23 14:00:56', null, '2022-04-11 17:09:15');
INSERT INTO `authority_role` VALUES ('2', '10002', '管理员', '1', '99', null, '10000032', '2022-02-23 14:04:25', null, '2022-04-11 17:10:26');
INSERT INTO `authority_role` VALUES ('3', '10003', '东方有线', '1', '99', null, '10000032', '2022-02-23 14:05:06', null, '2022-04-11 17:10:39');
INSERT INTO `authority_role` VALUES ('4', '10004', '交警总队', '1', '99', null, null, '2022-04-11 17:09:47', null, '2022-04-11 17:09:47');
INSERT INTO `authority_role` VALUES ('5', '10005', '宝康电子', '1', '99', null, null, '2022-04-11 17:10:00', null, '2022-04-11 17:10:00');
INSERT INTO `authority_role` VALUES ('7', '10006', '机房运维', '1', '99', null, null, '2022-05-31 13:58:59', null, '2022-05-31 13:58:59');

-- ----------------------------
-- Table structure for authority_role_relationship
-- ----------------------------
DROP TABLE IF EXISTS `authority_role_relationship`;
CREATE TABLE `authority_role_relationship` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表唯一标识',
  `user_no` varchar(32) NOT NULL COMMENT '用户编号',
  `role_no` varchar(32) NOT NULL COMMENT '角色编号',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8 COMMENT='**角色用户关系表 ';

-- ----------------------------
-- Records of authority_role_relationship
-- ----------------------------
INSERT INTO `authority_role_relationship` VALUES ('2', 'adminjj02', '10001', '10000032', '2022-02-23 14:11:06');
INSERT INTO `authority_role_relationship` VALUES ('3', 'zhangsan', '10002', '10000032', '2022-02-23 14:13:15');
INSERT INTO `authority_role_relationship` VALUES ('6', 'wwww', '10001', '', '2022-04-18 10:25:50');
INSERT INTO `authority_role_relationship` VALUES ('7', 'zkzmm', '10001', '', '2022-04-19 09:17:41');
INSERT INTO `authority_role_relationship` VALUES ('8', 'wwwww', '10001', '', '2022-04-19 09:51:52');
INSERT INTO `authority_role_relationship` VALUES ('9', 'zkzmm', '10001', '', '2022-04-19 10:54:46');
INSERT INTO `authority_role_relationship` VALUES ('10', 'afasdfaddf', '10004', '', '2022-04-19 10:58:37');
INSERT INTO `authority_role_relationship` VALUES ('11', 'zkzmm', '10001', '', '2022-04-19 11:06:11');
INSERT INTO `authority_role_relationship` VALUES ('13', 'ceshi002', '10001', '', '2022-04-19 11:08:41');
INSERT INTO `authority_role_relationship` VALUES ('14', 'test', '10001', '', '2022-04-21 09:10:57');
INSERT INTO `authority_role_relationship` VALUES ('16', '465654645654', '10002', 'adminjj02', '2022-04-21 14:17:49');
INSERT INTO `authority_role_relationship` VALUES ('19', '测试帐号删除', '10001', 'adminjj02', '2022-04-21 15:32:16');
INSERT INTO `authority_role_relationship` VALUES ('20', '测试帐号删', '10001', 'adminjj02', '2022-04-21 15:36:58');
INSERT INTO `authority_role_relationship` VALUES ('21', 'te11', '10002', 'adminjj02', '2022-04-21 16:51:18');
INSERT INTO `authority_role_relationship` VALUES ('29', '帐号密码重置', '10001', 'adminjj02', '2022-04-22 11:13:02');
INSERT INTO `authority_role_relationship` VALUES ('30', '测试用户新增', '10001', 'adminjj02', '2022-04-22 11:17:55');
INSERT INTO `authority_role_relationship` VALUES ('31', '15738383930', '10001', 'ceshi002', '2022-04-22 11:22:28');
INSERT INTO `authority_role_relationship` VALUES ('33', 'ceshi003', '10001', 'adminjj02', '2022-04-22 15:30:27');
INSERT INTO `authority_role_relationship` VALUES ('35', 'zhouhao', '10001', 'zktest', '2022-05-06 16:50:56');
INSERT INTO `authority_role_relationship` VALUES ('38', 'yangql', '10001', 'adminjj02', '2022-05-18 10:55:27');
INSERT INTO `authority_role_relationship` VALUES ('39', 'petit', '10001', 'zktest', '2022-05-18 11:02:50');
INSERT INTO `authority_role_relationship` VALUES ('40', 'adminjj02', '10001', 'zktest', '2022-05-18 11:04:41');
INSERT INTO `authority_role_relationship` VALUES ('76', 'zktest', '10001', 'zktest', '2022-06-06 14:27:59');
INSERT INTO `authority_role_relationship` VALUES ('78', 'lizn', '10001', 'yangql', '2022-06-06 14:29:12');
INSERT INTO `authority_role_relationship` VALUES ('81', 'yangql22', '10004', 'yangql', '2022-06-06 14:41:12');
INSERT INTO `authority_role_relationship` VALUES ('86', 'jjzd', '10004', 'zktest', '2022-06-07 14:56:38');
INSERT INTO `authority_role_relationship` VALUES ('87', 'jfyw', '10006', 'zktest', '2022-06-07 14:56:53');
INSERT INTO `authority_role_relationship` VALUES ('89', 'ceshi001', '10001', 'zktest', '2022-06-07 16:47:56');
INSERT INTO `authority_role_relationship` VALUES ('90', 'adminjj01', '10001', 'yangql', '2022-06-07 16:52:01');
INSERT INTO `authority_role_relationship` VALUES ('92', 'admin002', '10001', 'zktest', '2022-06-08 14:00:51');

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
) ENGINE=InnoDB AUTO_INCREMENT=48476 DEFAULT CHARSET=utf8 COMMENT='系统登录日志';

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------

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
   `time` bigint(20) DEFAULT NULL COMMENT '请求耗时（毫秒）',
   `params` varchar(1000) DEFAULT NULL COMMENT '请求参数',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59305 DEFAULT CHARSET=utf8 COMMENT='系统操作日志';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` varchar(40) NOT NULL COMMENT '账号',
  `passwd` varchar(32) DEFAULT NULL COMMENT '密码',
  `user_name` varchar(16) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `enable_status` int(11) DEFAULT '1' COMMENT '启用状态（0：停用，1：启用）',
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
  `create_id` varchar(40) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` varchar(40) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `delete_status` varchar(32) NOT NULL DEFAULT '0' COMMENT '删除状态（0：未删除 1:已删除）',
  PRIMARY KEY (`user_id`,`delete_status`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('111111', 'FE16B940E865845E55A4946249FCEB9E', 'gh', '15738383933', '0', '', 'ceshi002', '2022-04-21 14:23:41', 'zktest', '2022-06-06 15:33:06', null, '1');
INSERT INTO `sys_user` VALUES ('admin002', '790A66D29F2A84BE89990E4553A90AC8', '王二', '18601618495', '0', '', 'zktest', '2022-06-08 14:00:39', 'zktest', '2022-06-08 14:00:51', null, '0');
INSERT INTO `sys_user` VALUES ('adminjj01', 'f5d6bcf3d1714badc8d0b93937325f6c', 'adminjj01', '18601618493', '1', '', 'zktest', '2022-05-18 13:38:13', 'yangql', '2022-06-07 16:52:01', null, '0');
INSERT INTO `sys_user` VALUES ('ceshi001', '6B67E5596F91B9B773895D03C1DC2C5C', '测试', '17717031912', '0', '', '', '2022-04-19 11:06:56', 'zktest', '2022-06-07 16:47:56', null, '0');
INSERT INTO `sys_user` VALUES ('jfyw', '62c125ac4b9ed7659edc5caa4bad267f', '机房运维', '17717031913', '1', '', null, '2022-05-31 13:58:59', 'zktest', '2022-06-07 14:56:53', null, '0');
INSERT INTO `sys_user` VALUES ('jjzd', '62c125ac4b9ed7659edc5caa4bad267f', '刘队', '17717031915', '1', '', null, '2022-05-31 13:58:59', 'zktest', '2022-06-07 14:56:38', null, '0');
INSERT INTO `sys_user` VALUES ('lizn', '705270001C103C31268BCF33CA05C6E8', '李祖能', '15618000536', '1', '', 'zktest', '2022-05-24 09:02:57', 'yangql', '2022-06-06 14:29:12', null, '0');
INSERT INTO `sys_user` VALUES ('petit', 'A9D223AFE0B9F86A5C099503E743633B', '李', '18601720144', '1', '', 'adminjj01', '2022-04-21 14:20:30', 'zktest', '2022-05-18 11:02:50', null, '0');
INSERT INTO `sys_user` VALUES ('yangql', 'C7FE5413BC6FE8AB0E11D0D907F53175', 'YYDS', '18551212349', '1', '', 'adminjj02', '2022-05-18 10:55:27', 'adminjj02', '2022-05-18 10:55:27', null, '0');
INSERT INTO `sys_user` VALUES ('yangql111', 'C7FE5413BC6FE8AB0E11D0D907F53175', 'yql', '18551212344', '1', '', 'yangql', '2022-06-06 11:23:22', 'yangql', '2022-06-06 15:27:29', null, '1');
INSERT INTO `sys_user` VALUES ('yangql2', 'C7FE5413BC6FE8AB0E11D0D907F53175', '222', '18551212348', '1', '', 'yangql', '2022-06-06 10:20:42', 'yangql', '2022-06-06 10:20:50', null, '1');
INSERT INTO `sys_user` VALUES ('yangql22', 'f5d6bcf3d1714badc8d0b93937325f6c', '2222', '13222222222', '1', '', 'yangql', '2022-06-06 13:04:00', 'yangql', '2022-06-06 14:41:12', null, '0');
INSERT INTO `sys_user` VALUES ('yyydd', '9A805C23977A363064361A92CC8A76A5', '对对对', '18551212308', '1', 'vuevip@16c.com', 'yangql', '2022-06-06 14:42:07', 'yangql', '2022-06-06 15:31:19', null, '1');
INSERT INTO `sys_user` VALUES ('zhouhao', '063708547e5cd57aec58af95a7dc87da', '那个男人', '15738383930', '1', '', '', '2022-04-15 16:39:43', 'zktest', '2022-05-06 16:50:56', null, '0');
INSERT INTO `sys_user` VALUES ('zktest', 'f5d6bcf3d1714badc8d0b93937325f6c', '张凯', '17717031916', '1', '', '', '2022-04-21 09:54:28', 'zktest', '2022-06-06 14:27:59', null, '0');
INSERT INTO `sys_user` VALUES ('zktestsss', '2719B12C214F91E72025C529BB0A6063', '张琪', '11111111111', '1', '', 'zktest', '2022-06-06 11:22:45', 'zktest', '2022-06-06 11:23:08', null, '1');

-- ----------------------------
-- Table structure for sys_verify_code
-- ----------------------------
DROP TABLE IF EXISTS `sys_verify_code`;
CREATE TABLE `sys_verify_code` (
  `code_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(255) DEFAULT NULL COMMENT '验证码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`code_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3026 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='验证码';

-- ----------------------------
-- Records of sys_verify_code
-- ----------------------------
