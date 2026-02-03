CREATE TABLE log (
    id BIGSERIAL PRIMARY KEY,
    service_name VARCHAR(255) NOT NULL,
    class_name VARCHAR(255) NOT NULL,
    method_name VARCHAR(255) NOT NULL,
    arguments_json   TEXT ,
    result_json  TEXT,
    duration_ms BIGINT,
    success BOOLEAN,
    exception_message TEXT,
    log_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE SEQUENCE IF NOT EXISTS log_seq
    START WITH 1
    INCREMENT BY 50
    MINVALUE 1
    CACHE 50;