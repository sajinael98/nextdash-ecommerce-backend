CREATE Table IF NOT EXISTS `res_sysusers` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_Name VARCHAR(25),
    last_Name VARCHAR(25),
    email VARCHAR(255) UNIQUE,
    password TEXT,
    username TEXT,
    created_date DATETIME NOT NULL DEFAULT now(),
    last_modified_date DATETIME DEFAULT NULL DEFAULT now(),
    created_by BIGINT NOT NULL DEFAULT 0,
    last_modified_by BIGINT DEFAULT NULL
);

CREATE Table IF NOT EXISTS `res_roles` (
    id BIGINT AUTO_INCREMENT,
    `enabled` TINYINT(1),
    role VARCHAR(25) UNIQUE,
    created_date DATETIME NOT NULL DEFAULT now(),
    last_modified_date DATETIME DEFAULT NULL DEFAULT now(),
    created_by BIGINT NOT NULL DEFAULT 0,
    last_modified_by BIGINT DEFAULT NULL,
    PRIMARY KEY(id, role)
);

CREATE Table IF NOT EXISTS `res_user_roles` (
    user_id BIGINT NOT NULL,
    `role_id` BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES `res_sysusers` (id),
    FOREIGN KEY (`role_id`) REFERENCES `res_roles` (id)
);

CREATE Table IF NOT EXISTS `res_role_permissions` (
    `resource` VARCHAR(25),
    `create_resource` TINYINT(1),
    `read_resource` TINYINT(1),
    `update_resource` TINYINT(1),
    `delete_resource` TINYINT(1),
    `role_id` BIGINT,
    `role` VARCHAR(25),
    FOREIGN KEY (`role_id`) REFERENCES `res_roles` (`id`)
);

-- CREATE INDEX idx_roles_role ON res_roles(role);

CREATE Table IF NOT EXISTS `res_tokens` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    token TEXT,
    expired TINYINT(1),
    revoked TINYINT(1),
    user_id BIGINT,
    created_date DATETIME NOT NULL DEFAULT now(),
    last_modified_date DATETIME DEFAULT NULL DEFAULT now(),
    created_by BIGINT NOT NULL DEFAULT 0,
    last_modified_by BIGINT DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES `res_sysusers` (id)
);

CREATE TABLE IF NOT EXISTS `res_files` (
    id SERIAL PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL UNIQUE,
    file_type VARCHAR(50) NOT NULL,
    file_size BIGINT NOT NULL,
    created_date DATETIME NOT NULL DEFAULT now(),
    last_modified_date DATETIME DEFAULT NULL DEFAULT now(),
    created_by BIGINT NOT NULL DEFAULT 0,
    last_modified_by BIGINT DEFAULT NULL,
    upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `res_audit_logs` (
    Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(25) NOT NULL,
    resource VARCHAR(255) NOT NULL,
    resource_id VARCHAR(255),
    action VARCHAR(255) NOT NULL,
    created_by BIGINT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `data` Text ,
    previous_data Text
);

CREATE Table `res_demos` (
    Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_date DATETIME NOT NULL DEFAULT now(),
    last_modified_date DATETIME DEFAULT NULL DEFAULT now(),
    created_by BIGINT NOT NULL DEFAULT 0,
    last_modified_by BIGINT DEFAULT NULL,
    `role` BIGINT,
    FOREIGN KEY (`role`) REFERENCES `res_roles` (id)
);