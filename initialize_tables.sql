CREATE TABLE Versions (id BIGINT PRIMARY KEY);

INSERT INTO  Versions VALUES (1);

CREATE TABLE Beacons (uuid VARCHAR(54) NOT NULL,
                      major INT NOT NULL,
                      minor INT NOT NULL,
                      x REAL NOT NULL,
                      y REAL NOT NULL,
                      z REAL NOT NULL,
                      description TEXT,
                      PRIMARY KEY(uuid, major, minor));

CREATE TABLE Users (id MEDIUMINT NOT NULL AUTO_INCREMENT,
                    username VARCHAR(16) unique,
                    password VARCHAR(64),
                    PRIMARY KEY (id));

DELIMITER //
CREATE PROCEDURE incrementVersion()
BEGIN
    UPDATE Versions SET id = id + 1;
END //
DELIMITER ;