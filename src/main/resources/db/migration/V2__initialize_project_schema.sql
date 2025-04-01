CREATE TABLE IF NOT EXISTS `res_countries` (
    `title` VARCHAR(25) UNIQUE NOT NULL,
    `code` VARCHAR(25) NOT NULL,
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `createdDate` DATETIME NOT NULL DEFAULT NOW(),
    `lastModifiedDate` DATETIME DEFAULT NOW(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL,
    CONSTRAINT `UC_Country` UNIQUE (`title`, `code`)
);

CREATE TABLE IF NOT EXISTS `res_locations` (
    `countryId` BIGINT NOT NULL,
    `city` VARCHAR(25) NOT NULL,
    `address` VARCHAR(50) NOT NULL,
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `createdDate` DATETIME NOT NULL DEFAULT NOW(),
    `lastModifiedDate` DATETIME DEFAULT NOW(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL,
    CONSTRAINT `FK_Country_Location` FOREIGN KEY (`countryId`) REFERENCES `res_countries`(`id`)
);

CREATE TABLE IF NOT EXISTS `res_warehouses` (
    `title` VARCHAR(25) NOT NULL,
    `locationId` BIGINT NOT NULL,
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
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
    `template` BIGINT,
    `published` TINYINT(1),
    `publishedDate` Date,
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `createdDate` DATETIME NOT NULL DEFAULT NOW(),
    `lastModifiedDate` DATETIME DEFAULT NOW(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL,
    CONSTRAINT `FK_Item_Template` FOREIGN KEY (`template`) REFERENCES `res_items`(`id`)
);

CREATE TABLE IF NOT EXISTS `res_variants` (
    `title` VARCHAR(25) NOT NULL,
    `type` VARCHAR(25) NOT NULL,
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
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
    `value` VARCHAR(25),
    FOREIGN KEY (`itemId`) REFERENCES `res_items`(`id`),
    FOREIGN KEY (`variantId`) REFERENCES `res_variants`(`id`),
    PRIMARY KEY (`itemId`, `variantId`)
);