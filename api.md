# Server API

*Development Base URL:* /minrva-estimote-server/rest/v1.0/

*Production Base URL:* /rest/v1.0/
___
### Table of Contents 
- **Version Resources**
	- [Get Version Number](#get-version-number)
- **User Resources**
	- [Authenticate User](#authenticate-user)
- **Ebook Resources**
	- [Get Ebook URL](#get-ebook-url)
- **Beacons Resources**
	- [Get All Beacons](#get-all-beacons)
		- [with UUID](#get-all-beacons-with-uuid)
		- [with UUID and Major](#get-all-beacons-with-uuid-and-with-major)
	- [Get Beacon](#get-beacon)
	- [Create Beacon](#create-beacon)
	- [Update Beacon](#update-beacon)
	- [Delete Beacon](#delete-beacon)

___

## Version Resources

### Get Version Number
  **No Authorization Required** <br/>
  Returns the version number of the database used by the server.

* **URL** 

	`GET` /version

*  **URL Params**

	None

* **Data Params**

	None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{ id : 12 }`
 
* **Error Response:**

  * **Code:** 500 INTERNAL SERVER ERROR <br/>
    **Reasons** Database connection error


## User Resources

### Authenticate User
  **No Authorization Required** <br/>
  Returns a JWT token that can be used in the Authorization field of the headers of
  subsequent requests.

* **URL** 

	`POST` /user/authenticate

*  **URL Params**

	None

* **Data Params**

	**username:** the user's username <br/>
	**password:** the password corresponding to the username

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{ token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ }`
 
* **Error Response:**

  * **Code:** 400 Bad Request <br/>
    **Reasons** Bad username, password combination. The user may not exist, the wrong params may have been passed, or the password may not match. 


## Ebook Resources

### Get Ebook URL
  **No Authorization Required** <br/>
  Returns a link to the ebook copy of the book with the corresponding bib_id

* **URL** 

	`GET` /ebook/:bib_id

*  **URL Params**

	**bib_id:** the Minrva defined book id

* **Data Params**

	None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{ ebook_url: [url] }`
 
* **Error Response:**

  * **Code:** 500 INTERNAL SERVER ERROR <br/>
    **Reasons** Couldn't connect to http://vufind.carli.illinois.edu/

  * **Code:** 400 Bad Request <br/>
    **Reasons** Vufind doesn't have a record page of matching the given bibId <br/>
	OR an ebook link couldn't be parsed from Vufind


## Beacon Resources

### Get All Beacons
  **No Authorization Required** <br/>
  **Paginated features not implmented** *see: issue 30* <br/>
  
  Returns a paginated list of all beacons in the database.

* **URL** 

	`GET` /beacons

*  **URL Params**

	None

* **Data Params**

	None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
 
* **Error Response:**

  * **Code:** 500 INTERNAL SERVER ERROR <br/>
    **Reasons** Internal SQL error


### Get All Beacons with UUID
  **No Authorization Required** <br/>
  **Paginated features not implmented** *see: issue 30* <br/>
  
  Returns a paginated list of all beacons in the database that have the given UUID

* **URL** 

	`GET` /beacons/:uuid

*  **URL Params**

	**uuid:** The uuid of the desired beacons

* **Data Params**

	None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
 
* **Error Response:**

  * **Code:** 500 INTERNAL SERVER ERROR <br/>
    **Reasons** Internal SQL error


### Get All Beacons with UUID and with Major
  **No Authorization Required** <br/>
  **Paginated features not implmented** *see: issue 30* <br/>
  
  Returns a paginated list of all beacons in the database that have the given UUID and the given Major

* **URL** 

	`GET` /beacons/:uuid/:major

*  **URL Params**

	**uuid:** The uuid of the desired beacons <br/>
	**major:** The major of the desired beacons

* **Data Params**

	None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
 
* **Error Response:**

  * **Code:** 500 INTERNAL SERVER ERROR <br/>
    **Reasons** Internal SQL error


### Get Beacon
  **No Authorization Required** <br/>
  
  Returns a beacons in the database that has the given UUID and the given Major and the given Minor

* **URL** 

	`GET` /beacons/:uuid/:major/:minor

*  **URL Params**

	**uuid:** The uuid of the desired beacons <br/>
	**major:** The major of the desired beacons <br/>
	**minor:** The minor of the desired beacons

* **Data Params**

	None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
 
* **Error Response:**

  * **Code:** 500 INTERNAL SERVER ERROR <br/>
    **Reasons** Internal SQL error


### Create Beacon
  Adds a beacon to the database with the given fields. Returns whether or not the beacon was created using response codes
  
* **URL** 

	`POST` /beacons/

*  **URL Params**

	None

* **Data Params**

	**uuid:** _(required)_ The uuid of the new beacon <br/>
	**major:** _(required)_ The major of the new beacon <br/>
	**minor:** _(required)_ The minor of the new beacon <br/>
	**x:** The x location of the new beacon <br/>
	**y:** The y location of the new beacon <br/>
	**z:** The z location of the new beacon <br/>
	**description:** A description of the new beacon's placement

* **Success Response:**

  * **Code:** 201 <br />
    **Content:** {}
 
* **Error Response:**

  * **Code:** 500 INTERNAL SERVER ERROR <br/>
    **Reasons** Internal SQL error
  * **Code:** 400 BAD REQUEST <br/>
    **Reasons** Request body lacks uuid, major, minor <br/>
	OR a field is of the wrong type


### Update Beacon
  Updates a beacon in the database with the given fields. Returns whether or not the beacon was updated using response codes
  
* **URL** 

	`PUT` /beacons/:uuid/:major/:minor

*  **URL Params**

	**uuid:** The uuid of the beacon <br/>
	**major:** The major of the beacon <br/>
	**minor:** The minor of the beacon

* **Data Params**

	**x:** _(optional)_ The new x location of the beacon <br/>
	**y:** _(optional)_ The new y location of the beacon <br/>
	**z:** _(optional)_ The new z location of the beacon <br/>
	**description:** _(optional)_ The description of the location of the beacon <br/>

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** {}
 
* **Error Response:**

  * **Code:** 500 INTERNAL SERVER ERROR <br/>
    **Reasons** Internal SQL error
  * **Code:** 400 BAD REQUEST <br/>
    **Reasons** Beacon given uuid, major, minor doesn't exist<br/>
	OR an update field is of the wrong type


### Delete Beacon
  Deletes a beacon in the database with the given fields. Returns whether or not the beacon was deleted using response codes
  
* **URL** 

	`DELETE` /beacons/:uuid/:major/:minor

*  **URL Params**

	**uuid:** The uuid of the beacon <br/>
	**major:** The major of the beacon <br/>
	**minor:** The minor of the beacon

* **Data Params**

	None

* **Success Response:**

  * **Code:** 204 <br />
    **Content:** {}
 
* **Error Response:**

  * **Code:** 500 INTERNAL SERVER ERROR <br/>
    **Reasons** Internal SQL error
  * **Code:** 400 BAD REQUEST <br/>
    **Reasons** Beacon given uuid, major, minor doesn't exist<br/>
