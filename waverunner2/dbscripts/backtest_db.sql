
	
CREATE TABLE `tb_backtest`
	(`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`name` varchar(64),
	`stock` varchar(64),
	`type` varchar(64),
    `start_date` varchar(64),
	`end_date` varchar(64),
	`buy_amount` decimal(10,4),
	`sell_amount` decimal(10,4),
	`profit_limit` decimal(10,4),
	`trailing_stop_percent` decimal(10,4),
	`algorithum` varchar(128) NOT NULL,
    `money_spent`decimal(10,4),
    `total_value` decimal(10,4),
	`is_active` bit(1) DEFAULT 1,
	`is_archive` bit(1) DEFAULT 0,
	`is_locked` bit(1) DEFAULT 0,
	`lockowner_id` bigint(20) DEFAULT NULL,
	`modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`lock_time` datetime,
	`version` bigint(20) NOT NULL DEFAULT 0,
	PRIMARY KEY (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_unicode_ci;
