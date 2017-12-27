CREATE TABLE mapNameTrafficLight(
nameID varchar(50) NOT NULL,
cordx smallint NOT NULL,
cordy smallint NOT NULL,
overload varchar(50),
PRIMARY KEY(nameID));

CREATE TABLE mapNamePlace(
nameID varchar(50) NOT NULL,
street varchar(50) NOT NULL,
cord1x smallint NOT NULL,
cord2x smallint NOT NULL,
cord3x smallint NOT NULL,
cord4x smallint NOT NULL,
cord1y smallint NOT NULL,
cord2y smallint NOT NULL,
cord3y smallint NOT NULL,
cord4y smallint NOT NULL,
PRIMARY KEY(nameID));

CREATE TABLE mapNameRoad(
nameID varchar(50) NOT NULL,
cord1x smallint NOT NULL,
cord1y smallint NOT NULL,
cord2x smallint NOT NULL,
cord2y smallint NOT NULL,
length int,
secStart smallint,
secEnd smallint,
overload varchar(50),
PRIMARY KEY(nameID));

DECLARE @json NVARCHAR(max)
SET @json = 
'{
  "type": "FeatureCollection",
  "features": [
    {
      "type": "Feature",
      "geometry": {
        "type": "Point",
        "coordinates": [0,0],
		"name": "T245-bb-aa",
		"overload": "b45"
      }
    },
		
	{
      "type": "Feature",
      "geometry": {
        "type": "Point",
        "coordinates": [3,0],
		"name": "T246-cc-bb",
		"overload": "b47"
      }
    },
	
    {
      "type": "Feature",
      "geometry": {
        "type": "LineString",
        "coordinates": [[0,0],[-3,0]]
      },
      "properties": {
        "name": "aa",
		"sector_start": "1",
		"sector_end": "10",
		"length": 3,
		"overload": "a56"
      }
    },
	
    {
      "type": "Feature",
      "geometry": {
        "type": "LineString",
        "coordinates": [[0,0],[0,3]]
      },
      "properties": {
        "name": "bb",
		"sector_start": "1",
		"sector_end": "10",
		"length": 3,
		"overload": "a57"
      }
    },  
    {
      "type": "Feature",
      "geometry": {
        "type": "LineString",
        "coordinates": [[0,3],[3,3]]
      },
      "properties": {
        "name": "bb",
		"sector_start": "1",
		"sector_end": "10",
		"length": 3,
		"overload": "a56"
      }
    },	
    {
      "type": "Feature",
      "geometry": {
        "type": "Poligon",
        "coordinates": [[0,0],[4,0],[4,1],[1,0]]
      },
      "properties": {
        "name": "MailWay",
		"street": "street chukchuk"
      }
    }
    
  ]
}'


INSERT INTO dbo.mapNameTrafficLight (nameID, cordx,cordy, overload) SELECT nameID,cordx,cordy,overload FROM OPENJSON(@json, '$.features')
WITH (
	typeoftoken varchar(50) '$.geometry.type',
	nameID varchar(50) '$.geometry.name',
	cordx smallint '$.geometry.coordinates[0]',
	cordy smallint '$.geometry.coordinates[1]',
	overload varchar(5) '$.geometry.overload'
	) WHERE (typeoftoken = 'Point');
INSERT INTO dbo.mapNamePlace (nameID, street, cord1x, cord2x, cord3x, cord4x, cord1y, cord2y, cord3y, cord4y) SELECT nameID, street, cord1x, cord2x, cord3x, cord4x, cord1y, cord2y, cord3y, cord4y FROM OPENJSON(@json, '$.features')
WITH (
	typeoftoken varchar(50) '$.geometry.type',
	nameID varchar(50) '$.properties.name',
	street varchar(50) '$.properties.street',
	cord1x smallint '$.geometry.coordinates[0][0]',
	cord2x smallint '$.geometry.coordinates[1][0]',
	cord3x smallint '$.geometry.coordinates[2][0]',
	cord4x smallint '$.geometry.coordinates[3][0]',
	cord1y smallint '$.geometry.coordinates[0][1]',
	cord2y smallint '$.geometry.coordinates[1][1]',
	cord3y smallint '$.geometry.coordinates[2][1]',
	cord4y smallint '$.geometry.coordinates[3][1]'
	) WHERE (typeoftoken = 'Poligon');
INSERT INTO dbo.mapNameRoad (nameID,cord1x,cord1y,cord2x,cord2y,length,secStart,secEnd,overload) SELECT nameID, cord1x, cord1y, cord2x, cord2y, length, secStart, secEnd, overload FROM OPENJSON(@json, '$.features')
WITH (
	typeoftoken varchar(50) '$.geometry.type',
	nameID varchar(30) '$.properties.name',
	cord1x smallint '$.geometry.coordinates[0][0]',
	cord1y smallint '$.geometry.coordinates[0][1]',
	cord2x smallint '$.geometry.coordinates[1][0]',
	cord2y smallint '$.geometry.coordinates[1][1]',	
	length int '$.properties.length',
	secStart smallint '$.properties.secStart',
	secEnd smallint '$.properties.secEnd',
	overload varchar(50) '$.properties.overload'
	) WHERE (typeoftoken = 'LineString');	
