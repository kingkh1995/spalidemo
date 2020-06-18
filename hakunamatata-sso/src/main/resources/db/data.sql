DELETE FROM user;

INSERT INTO user (id, name, username, password, gender, age, email, app_no) VALUES
(1, '凯', 'Kai', '87c56bc9d59e16edeff921cac8779149', 'MALE', 18, 'kai@hakunamatata.com', 'app1');

INSERT INTO user (id, name, username, gender, age, email, app_no) VALUES
(2, '宇', 'Yu', 'MALE', 20, 'yu@hakunamatata.com', 'app2'),
(3, '任', 'Ren', 'MALE', 25, 'ren@hakunamatata.com', 'app1'),
(5, '静', 'Jing', 'FEMALE', 26, 'jing@hakunamatata.com', 'app1'),
(6, '龙', 'Long', 'MALE', 32, 'long@hakunamatata.com', 'app3');

INSERT INTO user (id, name, username, gender, age, email, app_no, deleted) VALUES
(4, '旭', 'Xu', 'MALE', 26, 'xu@hakunamatata.com', 'app4', 1);