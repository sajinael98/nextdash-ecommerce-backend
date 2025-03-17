CREATE Table IF NOT EXISTS `res_sysusers` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(25),
    lastName VARCHAR(25),
    email VARCHAR(255) UNIQUE,
    password TEXT,
    username TEXT,
    profileImage TEXT,
    createdDate DATETIME NOT NULL DEFAULT now(),
    lastModifiedDate DATETIME DEFAULT NULL DEFAULT now(),
    createdBy BIGINT NOT NULL DEFAULT 0,
    lastModifiedBy BIGINT DEFAULT NULL
);

CREATE Table IF NOT EXISTS `res_roles` (
    id BIGINT AUTO_INCREMENT,
    `enabled` TINYINT(1),
    role VARCHAR(25) UNIQUE,
    createdDate DATETIME NOT NULL DEFAULT now(),
    lastModifiedDate DATETIME DEFAULT NULL DEFAULT now(),
    createdBy BIGINT NOT NULL DEFAULT 0,
    lastModifiedBy BIGINT DEFAULT NULL,
    PRIMARY KEY(id, role)
);

CREATE Table IF NOT EXISTS `res_user_roles` (
    userId BIGINT NOT NULL,
    `roleId` BIGINT NOT NULL,
    FOREIGN KEY (userId) REFERENCES `res_sysusers` (id),
    FOREIGN KEY (`roleId`) REFERENCES `res_roles` (id)
);

CREATE Table IF NOT EXISTS `res_role_permissions` (
    `resource` VARCHAR(25),
    `create_r` TINYINT(1),
    `read_r` TINYINT(1),
    `update_r` TINYINT(1),
    `delete_r` TINYINT(1),
    `roleId` BIGINT,
    `role` VARCHAR(25),
    FOREIGN KEY (`roleId`) REFERENCES `res_roles` (`id`)
);

-- CREATE INDEX idx_roles_role ON res_roles(role);

CREATE Table IF NOT EXISTS `res_tokens` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    token TEXT,
    expired TINYINT(1),
    revoked TINYINT(1),
    userId BIGINT,
    createdDate DATETIME NOT NULL DEFAULT now(),
    lastModifiedDate DATETIME DEFAULT NULL DEFAULT now(),
    createdBy BIGINT NOT NULL DEFAULT 0,
    lastModifiedBy BIGINT DEFAULT NULL,
    FOREIGN KEY (userId) REFERENCES `res_sysusers` (id)
);

CREATE TABLE IF NOT EXISTS `res_files` (
    id SERIAL PRIMARY KEY,
    fileName VARCHAR(255) NOT NULL UNIQUE,
    fileType VARCHAR(50) NOT NULL,
    fileSize BIGINT NOT NULL,
    createdDate DATETIME NOT NULL DEFAULT now(),
    lastModifiedDate DATETIME DEFAULT NULL DEFAULT now(),
    createdBy BIGINT NOT NULL DEFAULT 0,
    lastModifiedBy BIGINT DEFAULT NULL,
    uploadDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `res_audit_logs` (
    Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(25) NOT NULL,
    resource VARCHAR(255) NOT NULL,
    resourceId VARCHAR(255),
    action VARCHAR(255) NOT NULL,
    createdBy BIGINT,
    createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `data` Text ,
    previousData Text
);

CREATE Table `res_demos` (
    Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    createdDate DATETIME NOT NULL DEFAULT now(),
    lastModifiedDate DATETIME DEFAULT NULL DEFAULT now(),
    createdBy BIGINT NOT NULL DEFAULT 0,
    lastModifiedBy BIGINT DEFAULT NULL,
    `role` BIGINT,
    FOREIGN KEY (`role`) REFERENCES `res_roles` (id)
);