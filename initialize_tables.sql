CREATE TABLE Versions (id BIGINT PRIMARY KEY);

INSERT INTO  Versions VALUES (1); 

CREATE TABLE Beacons (uuid varchar(38) NOT NULL,
                      major SMALLINT NOT NULL,
                      minor SMALLINT NOT NULL,
                      x REAL NOT NULL,
                      y REAL NOT NULL,
                      z REAL NOT NULL,
                      description TEXT,
                      PRIMARY KEY(uuid, major, minor));

DELIMITER //
CREATE PROCEDURE incrementVersion()
BEGIN
    UPDATE Versions SET id = id + 1;
END //
DELIMITER ;