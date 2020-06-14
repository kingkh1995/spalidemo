DELETE FROM user;

INSERT INTO user (id, name, gender, age, email, app_no) VALUES
(1, '凯', 'MALE', 18, 'kai@hakunamatata.com', 'app1'),
(2, '宇', 'MALE', 20, 'yu@hakunamatata.com', 'app2'),
(3, '任', 'MALE', 25, 'ren@hakunamatata.com', 'app1'),
(5, '静', 'FEMALE', 26, 'jing@hakunamatata.com', 'app1'),
(6, '龙', 'MALE', 32, 'long@hakunamatata.com', 'app3');

INSERT INTO user (id, name, gender, age, email, app_no, deleted) VALUES
(4, '旭', 'MALE', 26, 'xu@hakunamatata.com', 'app4', 1);