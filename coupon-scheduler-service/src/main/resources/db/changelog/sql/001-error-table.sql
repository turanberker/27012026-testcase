CREATE TABLE coupon_error (
    id BIGSERIAL PRIMARY KEY,

    raw_line TEXT,
    error_message TEXT,
    file_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT now()
);


CREATE SEQUENCE IF NOT EXISTS coupon_error_seq
    START WITH 1
    INCREMENT BY 50
    MINVALUE 1
    CACHE 50;