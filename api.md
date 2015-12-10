# Version Resources 
**No Authentication Required**


## `GET` /version
Get the most current version number of the database

### Returns
- An integer representing the number of edits made to the database


# Beacon Resources 
**Authentication will eventually be required**

**Description**
API calls to check version.

## `GET` /beacons
Get all beacons within the database. 

### Returns
- A list of beacons containing their uuid, major, minor, x, y, z and description


## `GET` /beacons/[uuid]
Get all beacons within the database associated with the given uuid 

### Returns
- A list of beacons containing their uuid, major, minor, x, y, z and description.
	

## `GET` /beacons/[uuid]/[major]
Get all beacons within the database associated with the given uuid and major

### Returns
- A list of beacons containing their uuid, major, minor, x, y, z and description.

	
## `GET` /beacons/[uuid]/[major]/[minor]
Get the beacon associated with the given uuid, major, and minor

### Returns
- A single beacon with corresponding uuid, major, minor, and then its x, y, z and description.


## `POST` /beacons
Create a new beacon

### Payload Parameters
- **uuid** _(required)_
- **major** _(required)_
- **minor** _(required)_
- **x**
- **y**
- **z**
- **description**

### Returns
- The status of the creation


## `PUT` /beacons/[uuid]/[major]/[minor]
Updates the beacon with the given uuid, major, and minor

### Payload Parameters
- **x**
- **y**
- **z**
- **description**

### Returns
- The status of the update


## `DELETE` /beacons/[uuid]/[major]/[minor]
Deletes the beacon with the given uuid, major, and minor

### Returns
- The status of the deletion
