DROP DATABASE IF EXISTS pzero;
CREATE DATABASE pzero;

# Breaking table into smaller update to avoid circular dependency - run alter statement at the end
CREATE TABLE pzero.customer(
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

CREATE TABLE pzero.credential (
	credential_id INT PRIMARY KEY AUTO_INCREMENT,
	customer_id INT,
	username VARCHAR(30),
	password VARCHAR(50),
	CONSTRAINT customer_id_fk FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
);


CREATE TABLE pzero.account (
	account_id INT PRIMARY KEY AUTO_INCREMENT,
	customer_id INT,
	balance DECIMAL (10, 2),
	type VARCHAR(20),
	CONSTRAINT customer_id_fk_acct FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
);


CREATE TABLE pzero.transaction (
	transaction_id INT PRIMARY KEY AUTO_INCREMENT,
	account_id INT,
	amount DECIMAL (10,2),
	type VARCHAR(10),
	source INT,
	destination INT,
	date TIMESTAMP,
	CONSTRAINT account_id_trans_fk FOREIGN KEY (account_id) REFERENCES account (account_id)
);

ALTER TABLE pzero.customer ADD CONSTRAINT FOREIGN KEY (credential_id) REFERENCES credential (credential_id);

