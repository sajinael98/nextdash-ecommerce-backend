CREATE TABLE IF NOT EXISTS `res_countries` (
    `title` VARCHAR(25) UNIQUE NOT NULL,
    `code` VARCHAR(25) NOT NULL,
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `status` TINYINT  DEFAULT 0,
    `createdDate` DATETIME NOT NULL DEFAULT NOW(),
    `lastModifiedDate` DATETIME DEFAULT NOW(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL,
    CONSTRAINT `UC_Country` UNIQUE (`title`, `code`)
);

CREATE TABLE IF NOT EXISTS `res_locations` (
    `countryId` BIGINT NOT NULL,
    `country` VARCHAR(25),
    `city` VARCHAR(25) NOT NULL,
    `address` VARCHAR(50) NOT NULL,
    `title` VARCHAR(255),
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `status` TINYINT ,
    `createdDate` DATETIME NOT NULL DEFAULT NOW(),
    `lastModifiedDate` DATETIME DEFAULT NOW(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL,
    CONSTRAINT `FK_Country_Location` FOREIGN KEY (`countryId`) REFERENCES `res_countries`(`id`)
);

CREATE TABLE IF NOT EXISTS `res_warehouses` (
    `title` VARCHAR(25) NOT NULL,
    `locationId` BIGINT NOT NULL,
    `location` VARCHAR(255),
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `status` TINYINT ,
    `createdDate` DATETIME NOT NULL DEFAULT NOW(),
    `lastModifiedDate` DATETIME DEFAULT NOW(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL,
    CONSTRAINT `FK_Location_Warehouse` FOREIGN KEY (`locationId`) REFERENCES `res_locations`(`id`)
);

CREATE TABLE IF NOT EXISTS `res_items` (
    `enabled` TINYINT(1),
    `title` VARCHAR(50) NOT NULL,
    `description` TEXT,
    `hasSubItems` TINYINT(1),
    `templateId` BIGINT,
    `template` VARCHAR(25),
    `published` TINYINT(1),
    `publishedDate` Date,
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `status` TINYINT ,
    `createdDate` DATETIME NOT NULL DEFAULT NOW(),
    `lastModifiedDate` DATETIME DEFAULT NOW(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL,
    CONSTRAINT `FK_Item_Template` FOREIGN KEY (`templateId`) REFERENCES `res_items`(`id`)
);

CREATE TABLE IF NOT EXISTS `res_variants` (
    `title` VARCHAR(25) NOT NULL,
    `type` VARCHAR(25) NOT NULL,
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `status` TINYINT ,
    `createdDate` DATETIME NOT NULL DEFAULT NOW(),
    `lastModifiedDate` DATETIME DEFAULT NOW(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `res_variant_values` (
    `label` VARCHAR(25) NOT NULL,
    `value` VARCHAR(25) NOT NULL,
    `variantId` BIGINT NOT NULL,
    CONSTRAINT `FK_Variant_Value` FOREIGN KEY (`variantId`) REFERENCES `res_variants`(`id`),
    PRIMARY KEY(`variantId`, `label`)
);

CREATE TABLE IF NOT EXISTS `res_item_variants` (
    `itemId` BIGINT NOT NULL,
    `variantId` BIGINT NOT NULL,
    `variant` VARCHAR(25),
    `value` VARCHAR(25),
    FOREIGN KEY (`itemId`) REFERENCES `res_items`(`id`),
    FOREIGN KEY (`variantId`) REFERENCES `res_variants`(`id`),
    PRIMARY KEY (`itemId`, `variantId`)
);

CREATE TABLE IF NOT EXISTS `res_uoms`(
    `uom` VARCHAR(25) NOT NULL,
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `status` TINYINT ,
    `createdDate` DATETIME NOT NULL DEFAULT NOW(),
    `lastModifiedDate` DATETIME DEFAULT NOW(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `res_item_uoms` (
    `itemId` BIGINT NOT NULL,
    `uomId` BIGINT NOT NULL,
    `uom` VARCHAR(25),
    `value` FLOAT,
    FOREIGN KEY (`itemId`) REFERENCES `res_items`(`id`),
    FOREIGN KEY (`uomId`) REFERENCES `res_uoms`(`id`),
    PRIMARY KEY (`itemId`, `uomId`)
);

CREATE TABLE if NOT EXISTS `res_purchase_transactions`(
    `transactionType` VARCHAR(25) NOT NULL,
    `transactionDate` DATETIME NOT NULL,
    `warehouseId` BIGINT NOT NULL,
    `total` FLOAT DEFAULT 0,
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `status` TINYINT  DEFAULT 0,
    `createdDate` DATETIME NOT NULL DEFAULT NOW(),
    `lastModifiedDate` DATETIME DEFAULT NOW(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL,
    FOREIGN KEY (`warehouseId`) REFERENCES `res_warehouses`(`id`)
);

CREATE TABLE if NOT EXISTS `res_purchase_transaction_items`(
    `voucherId` BIGINT NOT NULL,
    `itemId` BIGINT NOT NULL,
    `uomId` BIGINT NOT NULL,
    `price` FLOAT NOT NULL,
    `qty` FLOAT NOT NULL,
    `uomFactor` FLOAT NOT NULL,
    `total` FLOAT DEFAULT 0,
    FOREIGN KEY (`voucherId`) REFERENCES `res_purchase_transactions`(`id`),
    FOREIGN KEY (`uomId`) REFERENCES `res_uoms`(`id`),
    FOREIGN KEY (`itemId`) REFERENCES `res_items`(`id`)
);

CREATE TABLE IF NOT EXISTS `res_item_prices`(
    `itemId` BIGINT NOT NULL,
    `uomId` BIGINT NOT NULL,
    `price` FLOAT NOT NULL,
    `price_type` VARCHAR(25) NOT NULL,
    `start_selling_date` DATE,
    `stop_selling_date` DATE,
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `status` TINYINT ,
    `createdDate` DATETIME NOT NULL DEFAULT NOW(),
    `lastModifiedDate` DATETIME DEFAULT NOW(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL,
    FOREIGN KEY (`itemId`) REFERENCES `res_items`(`id`),
    FOREIGN KEY (`uomId`) REFERENCES `res_uoms`(`id`)
);

CREATE TABLE IF NOT EXISTS `res_stock_rooms`(
    `itemId` BIGINT NOT NULL,
    `uomId` BIGINT NOT NULL,
    `warehouseId` BIGINT NOT NULL,
    `currentQty` FLOAT DEFAULT 0,
    `orderedQty` FLOAT DEFAULT 0,
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `status` TINYINT ,
    `createdDate` DATETIME NOT NULL DEFAULT NOW(),
    `lastModifiedDate` DATETIME DEFAULT NOW(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL,
    FOREIGN KEY (`itemId`) REFERENCES `res_items`(`id`),
    FOREIGN KEY (`uomId`) REFERENCES `res_uoms`(`id`),
    FOREIGN KEY (`warehouseId`) REFERENCES `res_warehouses`(`id`)
);

CREATE TABLE IF NOT EXISTS `res_stock_room_logs`(
    `stockRoomId` BIGINT NOT NULL,
    `transactionId` BIGINT NOT NULL,
    `transactionDate` DATETIME NOT NULL,
    `transactionType` VARCHAR(25) NOT NULL,
    `qty` FLOAT DEFAULT 0,
    FOREIGN KEY (`stockRoomId`) REFERENCES `res_stock_rooms`(`id`)

);