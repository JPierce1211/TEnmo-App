BEGIN TRANSACTION;

DROP TABLE IF EXISTS tenmo_user, account, personal_info, transfer_records;

DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id;

-- Sequence to start user_id values at 1001 instead of 1
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tenmo_user 
(
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

-- Sequence to start account_id values at 2001 instead of 1
-- Note: Use similar sequences with unique starting values for additional tables
CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;

CREATE TABLE account 
(
	account_id int NOT NULL DEFAULT nextval('seq_account_id'),
	user_id int NOT NULL,
	balance decimal(13, 2) NOT NULL,
	CONSTRAINT PK_account PRIMARY KEY (account_id),
	CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
);

CREATE TABLE transfer_type
(
	type_id int NOT NULL,
	type_name varchar(6) NOT NULL,
	CONSTRAINT PK_type PRIMARY KEY (type_id),
	--CONSTRAINT FK_type FOREIGN KEY (type_id) REFERENCES transfer (transfer_type),
	CONSTRAINT uq_type_id UNIQUE (type_id)
);

CREATE TABLE transfer_status
(
	status_id int NOT NULL,
	status_name varchar(7) NOT NULL,
	CONSTRAINT PK_status PRIMARY KEY (status_id),
	--CONSTRAINT FK_status FOREIGN KEY (status_id) REFERENCES transfer (transfer_status),
	CONSTRAINT uq_status_id UNIQUE (status_id)
);

CREATE TABLE transfer
(
	transfer_id serial,
	from_id int NOT NULL,
	to_id int NOT NULL,
	amt int NOT NULL,
	dot date NOT NULL,
	transfer_type int NOT NULL,  
	transfer_status int NOT NULL, 
	CONSTRAINT unique_transfer_id UNIQUE (transfer_id),
	CONSTRAINT PK_transfer_id PRIMARY KEY (transfer_id),
	CONSTRAINT FK_from_id FOREIGN KEY (from_id) REFERENCES tenmo_user (user_id),
	CONSTRAINT FK_to_id FOREIGN KEY (to_id) REFERENCES tenmo_user (user_id),
	CONSTRAINT FK_trans_type FOREIGN KEY (transfer_type) REFERENCES transfer_type (type_id),
	CONSTRAINT FK_trans_status FOREIGN KEY (transfer_status) REFERENCES transfer_status (status_id)
	--CONSTRAINT unique_trans_id UNIQUE (transfer_id)
);

COMMIT;
