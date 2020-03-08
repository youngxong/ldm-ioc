-- 表
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `accountno` varchar(255) DEFAULT NULL,
  `money` decimal(20,4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- 数据
INSERT INTO `dev`.`account` (`id`, `name`, `accountno`, `money`) VALUES ('1', 'zhangsan', '600320005698', '1600.0000');
INSERT INTO `dev`.`account` (`id`, `name`, `accountno`, `money`) VALUES ('2', 'lisi', '632548899551', '2200.0000');
