CREATE TABLE `user` (
  `id` bigint(13) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `account` varchar(25) NOT NULL DEFAULT '' COMMENT '帐号',
  `pwd` varchar(20) NOT NULL DEFAULT '' COMMENT '密码',
  `nickname` varchar(25) NOT NULL DEFAULT '' COMMENT '昵称',
  `exp` bigint(13) NOT NULL DEFAULT '0' COMMENT '经验',
  `tel` varchar(11) NOT NULL DEFAULT '' COMMENT '手机',
  `avatar` varchar(40) NOT NULL DEFAULT '' COMMENT '头像',
  `status` int(4) NOT NULL DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

