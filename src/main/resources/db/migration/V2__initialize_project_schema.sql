CREATE Table IF NOT EXISTS `res_countries`(
    title VARCHAR(25) Unique NOT NULL,
    code VARCHAR(25) NOT NULL,
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    createdDate DATETIME NOT NULL DEFAULT now(),
    lastModifiedDate DATETIME DEFAULT NULL DEFAULT now(),
    createdBy BIGINT NOT NULL DEFAULT 0,
    lastModifiedBy BIGINT DEFAULT NULL,
    CONSTRAINT UC_Country UNIQUE (title, code)
);

CREATE Table IF NOT EXISTS `res_locations`(
    countryId BIGINT NOT NULL,
    city VARCHAR(25) NOT NULL,
    `address` VARCHAR(50) NOT NULL,
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    createdDate DATETIME NOT NULL DEFAULT now(),
    lastModifiedDate DATETIME DEFAULT NULL DEFAULT now(),
    createdBy BIGINT NOT NULL DEFAULT 0,
    lastModifiedBy BIGINT DEFAULT NULL,
    CONSTRAINT FK_CountryLocation FOREIGN KEY (countryId) REFERENCES `res_countries`(id)
);

CREATE TABLE IF NOT EXISTS `res_warehouses`(
    title VARCHAR(25) NOT NULL,
    locationId BIGINT NOT NULL,
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    createdDate DATETIME NOT NULL DEFAULT now(),
    lastModifiedDate DATETIME DEFAULT NULL DEFAULT now(),
    createdBy BIGINT NOT NULL DEFAULT 0,
    lastModifiedBy BIGINT DEFAULT NULL,
    CONSTRAINT FK_LocationWarehouse FOREIGN KEY (locationId) REFERENCES `res_locations`(id)
)