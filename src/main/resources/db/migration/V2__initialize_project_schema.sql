-- ===================== Countries =====================
CREATE TABLE IF NOT EXISTS `res_countries` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(25) UNIQUE NOT NULL,
    `code` VARCHAR(25) NOT NULL,
    `status` TINYINT DEFAULT 0,
    `createdDate` DATETIME NOT NULL DEFAULT NOW(),
    `lastModifiedDate` DATETIME DEFAULT NOW(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL,
    CONSTRAINT `UC_Country` UNIQUE (`title`, `code`)
);

-- ===================== Locations =====================
CREATE TABLE IF NOT EXISTS `res_locations` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `countryId` BIGINT NOT NULL,
    `country` VARCHAR(25),
    `city` VARCHAR(25) NOT NULL,
    `address` VARCHAR(50) NOT NULL,
    `title` VARCHAR(255),
    `status` TINYINT DEFAULT 0,
    `createdDate` DATETIME NOT NULL DEFAULT NOW(),
    `lastModifiedDate` DATETIME DEFAULT NOW(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL,
    CONSTRAINT `FK_Country_Location` FOREIGN KEY (`countryId`) REFERENCES `res_countries`(`id`)
);

-- ===================== Warehouses =====================
CREATE TABLE IF NOT EXISTS `res_warehouses` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(25) NOT NULL,
    `locationId` BIGINT NOT NULL,
    `location` VARCHAR(255),
    `status` TINYINT DEFAULT 0,
    `createdDate` DATETIME NOT NULL DEFAULT NOW(),
    `lastModifiedDate` DATETIME DEFAULT NOW(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL,
    CONSTRAINT `FK_Location_Warehouse` FOREIGN KEY (`locationId`) REFERENCES `res_locations`(`id`)
);

-- ===================== Items =====================
CREATE TABLE IF NOT EXISTS `res_items` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `enabled` TINYINT(1),
    `title` VARCHAR(50) NOT NULL,
    `description` TEXT,
    `hasSubItems` TINYINT(1),
    `templateId` BIGINT,
    `template` VARCHAR(25),
    `published` TINYINT(1),
    `publishedDate` DATE,
    `status` TINYINT DEFAULT 0,
    `createdDate` DATETIME NOT NULL DEFAULT NOW(),
    `lastModifiedDate` DATETIME DEFAULT NOW(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL,
    CONSTRAINT `FK_Item_Template` FOREIGN KEY (`templateId`) REFERENCES `res_items`(`id`)
);

-- ===================== Variants =====================
CREATE TABLE IF NOT EXISTS `res_variants` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(25) NOT NULL,
    `type` VARCHAR(25) NOT NULL,
    `status` TINYINT DEFAULT 0,
    `createdDate` DATETIME NOT NULL DEFAULT NOW(),
    `lastModifiedDate` DATETIME DEFAULT NOW(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL
);

-- ===================== Variant Values =====================
CREATE TABLE IF NOT EXISTS `res_variant_values` (
    `variantId` BIGINT NOT NULL,
    `label` VARCHAR(25) NOT NULL,
    `value` VARCHAR(25) NOT NULL,
    PRIMARY KEY (`variantId`, `label`),
    CONSTRAINT `FK_Variant_Value` FOREIGN KEY (`variantId`) REFERENCES `res_variants`(`id`)
);

-- ===================== Item Variants =====================
CREATE TABLE IF NOT EXISTS `res_item_variants` (
    `itemId` BIGINT NOT NULL,
    `variantId` BIGINT NOT NULL,
    `variant` VARCHAR(25),
    `value` VARCHAR(25),
    PRIMARY KEY (`itemId`, `variantId`),
    FOREIGN KEY (`itemId`) REFERENCES `res_items`(`id`),
    FOREIGN KEY (`variantId`) REFERENCES `res_variants`(`id`)
);

-- ===================== UOMs =====================
CREATE TABLE IF NOT EXISTS `res_uoms` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `uom` VARCHAR(25) NOT NULL,
    `status` TINYINT DEFAULT 0,
    `createdDate` DATETIME NOT NULL DEFAULT NOW(),
    `lastModifiedDate` DATETIME DEFAULT NOW(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL
);

-- ===================== Item UOMs =====================
CREATE TABLE IF NOT EXISTS `res_item_uoms` (
    `itemId` BIGINT NOT NULL,
    `uomId` BIGINT NOT NULL,
    `uom` VARCHAR(25),
    `value` FLOAT,
    PRIMARY KEY (`itemId`, `uomId`),
    FOREIGN KEY (`itemId`) REFERENCES `res_items`(`id`),
    FOREIGN KEY (`uomId`) REFERENCES `res_uoms`(`id`)
);

-- ===================== Purchase Transactions =====================
CREATE TABLE IF NOT EXISTS `res_purchase_transactions` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `transactionType` VARCHAR(25) NOT NULL,
    `transactionDate` DATETIME NOT NULL,
    `warehouseId` BIGINT NOT NULL,
    `total` FLOAT DEFAULT 0,
    `status` TINYINT DEFAULT 0,
    `createdDate` DATETIME NOT NULL DEFAULT NOW(),
    `lastModifiedDate` DATETIME DEFAULT NOW(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL,
    FOREIGN KEY (`warehouseId`) REFERENCES `res_warehouses`(`id`)
);

-- ===================== Purchase Transaction Items =====================
CREATE TABLE IF NOT EXISTS `res_purchase_transaction_items` (
    `voucherId` BIGINT NOT NULL,
    `itemId` BIGINT NOT NULL,
    `uomId` BIGINT NOT NULL,
    `price` FLOAT NOT NULL,
    `qty` FLOAT NOT NULL,
    `uomFactor` FLOAT NOT NULL,
    `total` FLOAT DEFAULT 0,
    PRIMARY KEY (`voucherId`, `itemId`, `uomId`),
    FOREIGN KEY (`voucherId`) REFERENCES `res_purchase_transactions`(`id`),
    FOREIGN KEY (`uomId`) REFERENCES `res_uoms`(`id`),
    FOREIGN KEY (`itemId`) REFERENCES `res_items`(`id`)
);

-- ===================== Item Prices =====================
CREATE TABLE IF NOT EXISTS `res_item_prices` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `itemId` BIGINT NOT NULL,
    `uomId` BIGINT NOT NULL,
    `price` FLOAT NOT NULL,
    `price_type` VARCHAR(25) NOT NULL,
    `start_selling_date` DATE,
    `stop_selling_date` DATE,
    `status` TINYINT DEFAULT 0,
    `createdDate` DATETIME NOT NULL DEFAULT NOW(),
    `lastModifiedDate` DATETIME DEFAULT NOW(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL,
    FOREIGN KEY (`itemId`) REFERENCES `res_items`(`id`),
    FOREIGN KEY (`uomId`) REFERENCES `res_uoms`(`id`)
);

-- ===================== Stock Rooms =====================
CREATE TABLE IF NOT EXISTS `res_stock_rooms` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `itemId` BIGINT NOT NULL,
    `uomId` BIGINT NOT NULL,
    `warehouseId` BIGINT NOT NULL,
    `currentQty` FLOAT DEFAULT 0,
    `orderedQty` FLOAT DEFAULT 0,
    `status` TINYINT DEFAULT 0,
    `createdDate` DATETIME NOT NULL DEFAULT NOW(),
    `lastModifiedDate` DATETIME DEFAULT NOW(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL,
    FOREIGN KEY (`itemId`) REFERENCES `res_items`(`id`),
    FOREIGN KEY (`uomId`) REFERENCES `res_uoms`(`id`),
    FOREIGN KEY (`warehouseId`) REFERENCES `res_warehouses`(`id`)
);

-- ===================== Stock Room Logs =====================
CREATE TABLE IF NOT EXISTS `res_stock_room_logs` (
    `stockRoomId` BIGINT NOT NULL,
    `transactionId` BIGINT NOT NULL,
    `transactionDate` DATETIME NOT NULL,
    `transactionType` VARCHAR(25) NOT NULL,
    `qty` FLOAT DEFAULT 0,
    PRIMARY KEY (`stockRoomId`, `transactionId`),
    FOREIGN KEY (`stockRoomId`) REFERENCES `res_stock_rooms`(`id`)
);