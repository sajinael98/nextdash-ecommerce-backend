-- ===================== Users =====================
CREATE TABLE IF NOT EXISTS `res_sysusers` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `status` TINYINT DEFAULT 0 CHECK (`status` IN (0, 1)),
    `firstName` VARCHAR(25) NOT NULL,
    `lastName` VARCHAR(25) NOT NULL,
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `password` TEXT NOT NULL,
    `username` VARCHAR(25) NOT NULL,
    `profileImage` TEXT,
    `createdDate` DATETIME NOT NULL DEFAULT now(),
    `lastModifiedDate` DATETIME DEFAULT now(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL
);

CREATE INDEX idx_sysusers_email ON `res_sysusers`(`email`);

CREATE INDEX idx_sysusers_username ON `res_sysusers`(`username`);

-- ===================== Roles =====================
CREATE TABLE IF NOT EXISTS `res_roles` (
    `id` BIGINT AUTO_INCREMENT,
    `status` TINYINT DEFAULT 0 CHECK (`status` IN (0, 1)),
    `enabled` TINYINT(1) DEFAULT 1 CHECK (`enabled` IN (0, 1)),
    `role` VARCHAR(25) NOT NULL UNIQUE,
    `createdDate` DATETIME NOT NULL DEFAULT now(),
    `lastModifiedDate` DATETIME DEFAULT now(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL,
    PRIMARY KEY(`id`, `role`)
);

CREATE INDEX idx_roles_enabled ON `res_roles`(`enabled`);

-- ===================== User-Roles Relation =====================
CREATE TABLE IF NOT EXISTS `res_user_roles` (
    `userId` BIGINT NOT NULL,
    `roleId` BIGINT NOT NULL,
    `role` VARCHAR(25),
    FOREIGN KEY (`userId`) REFERENCES `res_sysusers` (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`roleId`) REFERENCES `res_roles` (`id`) ON DELETE CASCADE,
    PRIMARY KEY (`userId`, `roleId`)
);

CREATE INDEX idx_user_roles_userId ON `res_user_roles`(`userId`);

CREATE INDEX idx_user_roles_roleId ON `res_user_roles`(`roleId`);

-- ===================== Role Permissions =====================
CREATE TABLE IF NOT EXISTS `res_role_permissions` (
    `resource` VARCHAR(25) NOT NULL,
    `create_r` TINYINT(1) DEFAULT 0 CHECK (`create_r` IN (0, 1)),
    `read_r` TINYINT(1) DEFAULT 0 CHECK (`read_r` IN (0, 1)),
    `update_r` TINYINT(1) DEFAULT 0 CHECK (`update_r` IN (0, 1)),
    `delete_r` TINYINT(1) DEFAULT 0 CHECK (`delete_r` IN (0, 1)),
    `roleId` BIGINT NOT NULL,
    `role` VARCHAR(25),
    FOREIGN KEY (`roleId`) REFERENCES `res_roles` (`id`) ON DELETE CASCADE
);

CREATE INDEX idx_role_permissions_roleId ON `res_role_permissions`(`roleId`);

-- ===================== Tokens =====================
CREATE TABLE IF NOT EXISTS `res_tokens` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `status` TINYINT DEFAULT 0 CHECK (`status` IN (0, 1)),
    `token` TEXT NOT NULL,
    `expired` TINYINT(1) DEFAULT 0 CHECK (`expired` IN (0, 1)),
    `revoked` TINYINT(1) DEFAULT 0 CHECK (`revoked` IN (0, 1)),
    `userId` BIGINT NOT NULL,
    `createdDate` DATETIME NOT NULL DEFAULT now(),
    `lastModifiedDate` DATETIME DEFAULT now(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL,
    FOREIGN KEY (`userId`) REFERENCES `res_sysusers` (`id`) ON DELETE CASCADE
);

CREATE INDEX idx_tokens_userId ON `res_tokens`(`userId`);

-- ===================== Files =====================
CREATE TABLE IF NOT EXISTS `res_files` (
    `id` SERIAL PRIMARY KEY,
    `status` TINYINT DEFAULT 0 CHECK (`status` IN (0, 1)),
    `fileName` VARCHAR(255) NOT NULL UNIQUE,
    `fileType` VARCHAR(50) NOT NULL,
    `fileSize` BIGINT NOT NULL CHECK (`fileSize` >= 0),
    `createdDate` DATETIME NOT NULL DEFAULT now(),
    `lastModifiedDate` DATETIME DEFAULT now(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL,
    `uploadDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_files_fileType ON `res_files`(`fileType`);

-- ===================== Audit Logs =====================
CREATE TABLE IF NOT EXISTS `res_audit_logs` (
    `Id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `status` TINYINT DEFAULT 0 CHECK (`status` IN (0, 1)),
    `username` VARCHAR(25) NOT NULL,
    `resource` VARCHAR(255) NOT NULL,
    `resourceId` VARCHAR(255),
    `action` VARCHAR(255) NOT NULL,
    `createdBy` BIGINT,
    `createdDate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `data` TEXT,
    `previousData` TEXT
);

CREATE INDEX idx_audit_logs_resource ON `res_audit_logs`(`resource`);

-- ===================== Demo =====================
CREATE TABLE IF NOT EXISTS `res_demos` (
    `Id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `status` TINYINT DEFAULT 0 CHECK (`status` IN (0, 1)),
    `createdDate` DATETIME NOT NULL DEFAULT now(),
    `lastModifiedDate` DATETIME DEFAULT now(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL,
    `role` BIGINT,
    FOREIGN KEY (`role`) REFERENCES `res_roles` (`id`) ON DELETE
    SET
        NULL
);

-- ===================== Settings =====================
CREATE TABLE IF NOT EXISTS `res_settings` (
    `title` VARCHAR(25) NOT NULL UNIQUE,
    `data` JSON NOT NULL,
    `Id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `status` TINYINT DEFAULT 0 CHECK (`status` IN (0, 1)),
    `createdDate` DATETIME NOT NULL DEFAULT now(),
    `lastModifiedDate` DATETIME DEFAULT now(),
    `createdBy` BIGINT NOT NULL DEFAULT 0,
    `lastModifiedBy` BIGINT DEFAULT NULL
);

CREATE INDEX idx_settings_title ON `res_settings`(`title`);