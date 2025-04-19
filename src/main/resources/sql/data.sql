INSERT INTO users VALUES ('keith', '{noop}keithpw', NULL,NULL,NULL);
INSERT INTO user_roles(username, role) VALUES ('keith', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('keith', 'ROLE_ADMIN');

INSERT INTO users VALUES ('john', '{noop}johnpw', NULL,NULL,NULL);
INSERT INTO user_roles(username, role) VALUES ('john', 'ROLE_ADMIN');

INSERT INTO users VALUES ('mary', '{noop}marypw', NULL,NULL,NULL);
INSERT INTO user_roles(username, role) VALUES ('mary', 'ROLE_USER');