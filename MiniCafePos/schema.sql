CREATE DATABASE IF NOT EXISTS pksdb;
USE pksdb;
CREATE TABLE member
(
    `no`    INT            NOT NULL    AUTO_INCREMENT COMMENT '회원번호', 
    `name`  VARCHAR(45)    NULL        COMMENT '회원이름', 
    `tel`   VARCHAR(50)    NULL        COMMENT '회원전화번호', 
    PRIMARY KEY (no)
);

ALTER TABLE member COMMENT '회원 테이블';

