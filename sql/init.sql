-- ============================================
-- AI智能食材管理系统 - 数据库初始化脚本
-- 数据库: MySQL 8.0+
-- ============================================

CREATE DATABASE IF NOT EXISTS ai_ingredient DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ai_ingredient;

-- 用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    display_name VARCHAR(100),
    role VARCHAR(20) DEFAULT 'USER' COMMENT 'ADMIN/USER',
    phone VARCHAR(20),
    email VARCHAR(100),
    enabled BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 食材分类表
CREATE TABLE IF NOT EXISTS ingredient_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(200),
    image_url VARCHAR(500),
    sort_order INT DEFAULT 0,
    enabled BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 供应商表
CREATE TABLE IF NOT EXISTS suppliers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    contact_person VARCHAR(50),
    phone VARCHAR(20),
    address VARCHAR(200),
    remark VARCHAR(500),
    enabled BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 食材表
CREATE TABLE IF NOT EXISTS ingredients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    barcode VARCHAR(200),
    unit VARCHAR(50) COMMENT 'kg/g/L/个/箱',
    stock_quantity DOUBLE DEFAULT 0,
    min_stock_quantity DOUBLE DEFAULT 0,
    unit_price DOUBLE DEFAULT 0,
    production_date DATE,
    expiry_date DATE,
    storage_method VARCHAR(50) COMMENT '冷藏/冷冻/常温',
    image_url VARCHAR(500),
    remark VARCHAR(500),
    category_id BIGINT,
    supplier_id BIGINT,
    status INT DEFAULT 1 COMMENT '1正常 2预警 3过期',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES ingredient_categories(id) ON DELETE SET NULL,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 采购订单表
CREATE TABLE IF NOT EXISTS purchase_orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50),
    supplier_id BIGINT,
    ingredient_id BIGINT,
    quantity DOUBLE DEFAULT 0,
    total_price DOUBLE DEFAULT 0,
    status VARCHAR(50) DEFAULT 'PENDING' COMMENT 'PENDING/COMPLETED/CANCELLED',
    remark VARCHAR(500),
    order_date DATE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE SET NULL,
    FOREIGN KEY (ingredient_id) REFERENCES ingredients(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 消耗记录表
CREATE TABLE IF NOT EXISTS consumption_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ingredient_id BIGINT,
    quantity DOUBLE DEFAULT 0,
    type VARCHAR(50) COMMENT 'CONSUME/WASTE',
    remark VARCHAR(500),
    record_date DATE,
    user_id BIGINT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ingredient_id) REFERENCES ingredients(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- AI对话记录表
CREATE TABLE IF NOT EXISTS ai_conversations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    user_message TEXT,
    ai_response TEXT,
    type VARCHAR(50) COMMENT 'CHAT/ARTICLE/IMAGE/ANALYSIS',
    title VARCHAR(200),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- AI生成内容表
CREATE TABLE IF NOT EXISTS ai_generated_contents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    title VARCHAR(100),
    content_type VARCHAR(50) COMMENT 'ARTICLE/IMAGE/ANALYSIS',
    content TEXT,
    image_url VARCHAR(500),
    remark VARCHAR(500),
    favorite BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入默认管理员
INSERT INTO users (username, password, display_name, role) VALUES
('admin', '\\\.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '系统管理员', 'ADMIN');

-- 插入默认分类
INSERT INTO ingredient_categories (name, description, sort_order) VALUES
('蔬菜类', '各类新鲜蔬菜', 1),
('水果类', '各类新鲜水果', 2),
('肉类', '猪肉、牛肉、羊肉等', 3),
('禽类', '鸡、鸭、鹅等', 4),
('水产类', '鱼、虾、蟹等', 5),
('蛋奶类', '鸡蛋、牛奶等', 6),
('调味品', '油盐酱醋等', 7),
('粮食类', '米面粮油等', 8),
('冻品', '冷冻食品', 9),
('其他', '其他食材', 10);