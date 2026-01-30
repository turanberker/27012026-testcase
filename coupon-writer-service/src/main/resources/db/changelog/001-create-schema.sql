--liquibase formatted sql

--changeset berker:create-coupon-schema
CREATE SCHEMA IF NOT EXISTS coupon;