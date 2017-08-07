
--user table contains actual data which I want to retrive
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `userName` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(20) NOT NULL,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `userName`, `password`, `email`, `status`) VALUES
(1, 'ataur', 'ataur', 'a@b.com', 'ACTIVE'),
(2, 'munna', 'munna', 'a@b.com', 'ACTIVE'),
(3, 'sun', 'sun', 'a@b.com', 'ACTIVE');


CREATE  TABLE users_info (
	username VARCHAR(45) NOT NULL ,
	password VARCHAR(45) NOT NULL ,
	enabled TINYINT NOT NULL DEFAULT 1 ,
	PRIMARY KEY (username)
);

CREATE TABLE user_roles (
	user_role_id int(11) NOT NULL AUTO_INCREMENT,
	username varchar(45) NOT NULL,
	role varchar(45) NOT NULL,
	PRIMARY KEY (user_role_id),
	UNIQUE KEY uni_username_role (role,username),
	KEY fk_username_idx (username),
	CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username)
);

	

create table oauth_access_token (
  token_id VARCHAR(256),
  token BLOB,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication BLOB,
  refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token BLOB,
  authentication BLOB
);


INSERT INTO user(id,userName,password,email,status) VALUES ('ataur','ataur', 'a@b.com','ACTIVE');
INSERT INTO user(id,userName,password,email,status) VALUES ('munna','munna', 'a@b.com','ACTIVE');
INSERT INTO user(id,userName,password,email,status) VALUES ('sun','sun', 'a@b.com','ACTIVE');

INSERT INTO users_info(username,password,enabled) VALUES ('munna','munna', true);



INSERT INTO user_roles (username, role) VALUES ('munna', 'ROLE_USER');

INSERT INTO user_roles (username, role) VALUES ('munna', 'ROLE_ADMIN');

