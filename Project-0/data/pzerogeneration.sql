DROP DATABASE IF EXISTS pzero;
CREATE DATABASE pzero;

# Breaking table into smaller update to avoid circular dependency - run alter statement at the end
CREATE TABLE customer(
	customer_id INT PRIMARY KEY AUTO_INCREMENT,
	credential_id INT,
	first_name VARCHAR(50),
	last_name VARCHAR(50),
	address1 VARCHAR(50),
	address2 VARCHAR(50),
	city VARCHAR(20),
	state VARCHAR(13),
	zip_code VARCHAR(10),
	email VARCHAR(50)
);

CREATE TABLE credential (
	credential_id INT PRIMARY KEY AUTO_INCREMENT,
	customer_id INT,
	username VARCHAR(30),
	password VARCHAR(50),
	CONSTRAINT customer_id_fk FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
);


CREATE TABLE account (
	account_id INT PRIMARY KEY AUTO_INCREMENT,
	customer_id INT,
	balance DECIMAL (10, 2),
	type VARCHAR(20),
	CONSTRAINT customer_id_fk_acct FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
);


CREATE TABLE transaction (
	transaction_id INT PRIMARY KEY AUTO_INCREMENT,
	account_id INT,
	amount DOUBLE,
	source INT,
	destination INT,
	date TIMESTAMP,
	CONSTRAINT account_id_trans_fk FOREIGN KEY (account_id) REFERENCES account (account_id)
);

ALTER TABLE customer ADD CONSTRAINT FOREIGN KEY (credential_id) REFERENCES credential (credential_id);

# Extra
ALTER TABLE account MODIFY balance DECIMAL (10, 2);
ALTER TABLE pzero.account MODIFY COLUMN balance DECIMAL(10,2);


# Test
INSERT INTO account (customer_id, balance, type) VALUES (2, 200.00, "savings");
DELETE FROM account WHERE (account_id > 0)

# Let customer  #1 be all the edge cases
INSERT INTO account (customer_id, balance, type) VALUES (1, 0.00, "savings");
INSERT INTO account (customer_id, balance, type) VALUES (1, -200.23, "savings");
INSERT INTO account (customer_id, balance, type) VALUES (1, 99999999, "savings");
INSERT INTO account (customer_id, balance, type) VALUES (1, 0230.01, "savings");


