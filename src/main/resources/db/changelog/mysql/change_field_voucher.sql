ALTER TABLE voucher DROP FOREIGN KEY voucher_ibfk_1;
ALTER TABLE voucher MODIFY COLUMN user_id CHAR(36);
ALTER TABLE voucher ADD CONSTRAINT voucher_ibfk_1
    FOREIGN KEY (user_id) REFERENCES user (id);