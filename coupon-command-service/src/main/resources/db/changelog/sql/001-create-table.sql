CREATE TABLE coupon (
    code                VARCHAR(255) PRIMARY KEY,

    coupon_type         VARCHAR(50) NOT NULL,
    discont_type        VARCHAR(50) NOT NULL,

    discount_amount     NUMERIC(10, 2) NOT NULL,

    expiry_date         DATE NOT NULL,

    max_usage           BIGINT NOT NULL,
    total_used_count    BIGINT NOT NULL DEFAULT 0
);



CREATE TABLE coupon_type_log (
    coupon_type   VARCHAR(50) PRIMARY KEY,
    created_count BIGINT NOT NULL DEFAULT 0,
    used_count    BIGINT NOT NULL DEFAULT 0
);


CREATE TABLE IF NOT EXISTS coupon_user (
    id           BIGSERIAL PRIMARY KEY,

    coupon_code  VARCHAR(50) NOT NULL,
    user_id      VARCHAR(255) NOT NULL,

    created_date TIMESTAMP,
    used_date    TIMESTAMP
);

CREATE INDEX idx_coupon_user_active ON coupon_user (coupon_code) WHERE used_date IS NULL;

ALTER TABLE coupon_user ADD CONSTRAINT fk_coupon_user_coupon FOREIGN KEY (coupon_code) REFERENCES coupon (code);

CREATE TABLE IF NOT EXISTS uploaded_file (
    file_name VARCHAR(255) PRIMARY KEY,
    status    VARCHAR(50) NOT NULL DEFAULT 'NEW'
);

CREATE SEQUENCE IF NOT EXISTS coupon_user_seq
    START WITH 1
    INCREMENT BY 50
    MINVALUE 1
    CACHE 50;
