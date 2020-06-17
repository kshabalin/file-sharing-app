CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  email VARCHAR(250) NOT NULL,
  password varchar(512) NOT NULL,
  enabled BOOL NOT NULL DEFAULT TRUE,
  UNIQUE KEY email (email)
);

CREATE TABLE IF NOT EXISTS authorities (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  authority VARCHAR(250) NOT NULL,
  username VARCHAR(250) NOT NULL
);

CREATE TABLE IF NOT EXISTS files (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  user_id INT NOT NULL,
  FOREIGN KEY(user_id) REFERENCES users(id),
  name VARCHAR(250) NOT NULL
);

CREATE TABLE IF NOT EXISTS shared_files (
  user_id INT NOT NULL,
  FOREIGN KEY(user_id) REFERENCES users(id),
  file_id INT NOT NULL,
  FOREIGN KEY(file_id) REFERENCES files(id)
);