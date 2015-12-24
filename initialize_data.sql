
SET @uuid = "c8236aad-c8bb-4a39-99dd-f48ed66d64fb";
INSERT INTO beacons (uuid, major, minor, x, y, z, description) VALUES 
    (@uuid, 21, 14, 00.34, 03.45, 12.54, "Under the east stairs"),
    (@uuid, 21, 28, 03.34, 03.86, 08.45, "On the top back corner of case 15"),
    (@uuid, 21, 33, 30.45, 05.35, 05.98, "Over the west entryway"),
    (@uuid, 50, 56, 00.23, 13.70, 46.70, "On the top front corner of case 7"),
    (@uuid, 50, 23, 03.65, 03.34, 03.34, "On the inner east wall of the courtyard");

SET @uuid = "9efde5f4-e059-4240-93d0-2e9e2fcfb19b";
INSERT INTO beacons (uuid, major, minor, x, y, z, description) VALUES 
    (@uuid, 32, 00, 00.23, 54.34, 02.65, ""),
    (@uuid, 32, 23, 03.23, 03.99, 07.01, "On the second top shelf of case 3"),
    (@uuid, 32, 37, 22.54, 05.00, 05.00, "Above the ceiling tile in the southwest corner"),
    (@uuid, 32, 44, 11.23, 11.34, 11.87, ""),
    (@uuid, 02, 12, 00.00, 02.23, 11.11,"On the top front corner of case 7"),
    (@uuid, 02, 56, 03.32, 03.53, 03.23, "Over the checkout desk"),
    (@uuid, 02, 93, 32.54, 05.22, 02.43, "");