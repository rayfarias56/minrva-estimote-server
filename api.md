# Version Resources

**Description**

Return data pertaining to a beacon's list version and its corresponding beacons.

**Should Require Authentication**


## `GET` /versions
Gets a list of all version ids sorted by date. The version id listed twice (once with a negative date)
will be the one currently in production. 

## `PUT` /versions
Create a new version to store beacon ids in. 

### Returns
- Returns status and result of action

## `GET` /versions/production
Gets the version id that is currently being used in production.

## `GET` /versions/{versionId}
Gets information tied to a specific versionId

### Parameters
- **versionId** _(required)_ - Id of version

### Returns
- date of version
- production status of version

## `GET` /versions/{versionId}/beacons
Gets a list of beacons

### Parameters
- **versionId** _(required)_ - Id of version

### Returns
- an unsorted list of beacons given by UUID,Major,Minor and an x, y, z position

## `POST` /versions/{versionId}/beacons
Creates a new beacon.

###Parameters
- **versionId** _(required)_ - Id of version
- **request body** _(required)_ - Included in this body is:
	- 'beaconId' - UUID,Major,Minor of the beacon to 

## `GET` /versions/{versionId}/beacons/{beaconId}
Returns the location of a beacon

###Parameters
- **versionId** _(required)_ - Id of version
- **beaconId** _(required)_ - beacon Id given by UUID,Major,Minor

### Returns
- The x, y, z location of the requested beacon.

## `PUT` /versions/{versionId}/beacons/{beaconId}
Updates the location of a beacon

###Parameters
- **versionId** _(required)_ - Id of version
- **beaconId** _(required)_ - beacon Id given by UUID,Major,Minor
- **request body** _(required)_ - Included in this body is:
	- 'x' - The x coordinate of this beacon
	- 'y' - The y coordinate of this beacon
	- 'z' - The z coordinate of this beacon
	
### Returns
- Returns status and result of action

## `DELETE` /versions/{versionId}/beacons/{beaconId}
Deletes a beacon.

###Parameters
- **versionId** _(required)_ - Id of version
- **beaconId** _(required)_ - beacon Id of beacon to delete given by UUID,Major,Minor
	
### Returns
- Returns status and result of action



