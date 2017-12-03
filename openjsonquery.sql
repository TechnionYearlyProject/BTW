DECLARE @json NVARCHAR(max)
SET @json = 
'{
  "type": "FeatureCollection",
  "features": [
    {
      "type": "Feature",
      "geometry": {
        "type": "Point",
        "coordinates": [0,0]
      },
      "properties": {
        "name": "TL1"
      }
    },
    
    {
      "type": "Feature",
      "geometry": {
        "type": "LineString",
        "coordinates": [[0,0],[-3,0]]
      },
      "properties": {
        "name": "Safari St. 1-15"
      }
    },
    
    {
      "type": "Feature",
      "geometry": {
        "type": "LineString",
        "coordinates": [[0,0],[4,0]]
      },
      "properties": {
        "name": "Road1"
      }
    },
    
    {
      "type": "Feature",
      "geometry": {
        "type": "Point",
        "coordinates": [2,2]
      },
      "properties": {
        "name": "TL2"
      }
    },
    {
      "type": "Feature",
      "geometry": {
        "type": "LineString",
        "coordinates": [[0,0],[2,2]]
      },
      "properties": {
        "name": "Road2"
      }
    },
    {
      "type": "Feature",
      "geometry": {
        "type": "Point",
        "coordinates": [4,0]
      },
      "properties": {
        "name": "TL3"
      }
    },
    {
      "type": "Feature",
      "geometry": {
        "type": "Point",
        "coordinates": [2,-2]
      },
      "properties": {
        "name": "TL4"
      }
    },
    {
      "type": "Feature",
      "geometry": {
        "type": "LineString",
        "coordinates": [[4,0],[2,-2]]
      },
      "properties": {
        "name": "Road3"
      }
    },
    {
      "type": "Feature",
      "geometry": {
        "type": "LineString",
        "coordinates": [[0,0],[2,-2]]
      },
      "properties": {
        "name": "Road4"
      }
    },
    {
      "type": "Feature",
      "geometry": {
        "type": "LineString",
        "coordinates": [[8,0],[4,0]]
      },
      "properties": {
        "name": "RoadVista st 1-10"
      }
    },
	
	{
      "type": "Feature",
      "geometry": {
        "type": "LineString",
        "coordinates": [[6,0],[6,-3]]
      },
      "properties": {
        "name": "ex St. 1-4"
      }
    },
    
    {
      "type": "Feature",
      "geometry": {
        "type": "Point",
        "coordinates": [6,-3]
      },
      "properties": {
        "name": "office building",
        "address": "ex St. 5"
      }
    },
    
    {
      "type": "Feature",
      "geometry": {
        "type": "LineString",
        "coordinates": [[6,-3],[6,-5]]
      },
      "properties": {
        "name": "ex St. 5-8"
      }
    },
    
    {
      "type": "Feature",
      "geometry": {
        "type": "LineString",
        "coordinates": [[0,0],[2,2]]
      },
      "properties": {
        "name": "Road2"
      }
    },
    
    {
      "type": "Feature",
      "geometry": {
        "type": "LineString",
        "coordinates": [[2,2],[2,5]]
      },
      "properties": {
        "name": "Fire St. 1-4"
      }
    },
	
	{
      "type": "Feature",
      "geometry": {
        "type": "LineString",
        "coordinates": [[2,3.5] ,[-3,3.5]]
      },
      "properties": {
        "name": "Jungle St. 2-7"
      }
    },
    
    {
      "type": "Feature",
      "geometry": {
        "type": "LineString",
        "coordinates": [[2,-2],[2,-5]]
      },
      "properties": {
        "name": "Mozilla St. 1-9, Left"
      }
    },
    
    {
      "type": "Feature",
      "geometry": {
        "type": "LineString",
        "coordinates": [[2,-2],[2,-5]]
      },
      "properties": {
        "name": "Mozilla St. 1-9, Right"
      }
    },
    
    {
      "type": "Feature",
      "geometry": {
        "type": "LineString",
        "coordinates": [[2,-5],[2,-6]]
      },
      "properties": {
        "name": "Mozilla St. 10-11"
      }
    },
    
    {
      "type": "Feature",
      "geometry": {
        "type": "LineString",
        "coordinates": [[2,-5],[-4,-5]]
      },
      "properties": {
        "name": "Animals St. 5-8"
      }
    },
    
    {
      "type": "Feature",
      "geometry": {
        "type": "LineString",
        "coordinates": [[-4,-5],[-4,-1.5]]
      },
      "properties": {
        "name": "Animals St. 1-5"
      }
    } 
  ]
}'

SELECT * FROM OPENJSON(@json, '$.features')
WITH (
	typeoftoken varchar(20) '$.geometry.type',
	pointAx varchar(10) '$.geometry.coordinates[0][1]',
	pointAy varchar(10) '$.geometry.coordinates[0][1]',
	pointBx varchar(10) '$.geometry.coordinates[1][0]',
	pointBy varchar(10) '$.geometry.coordinates[1][1]',
	nameoftoken varchar(30) '$.properties.name',
	addressoftoken varchar(30) '$.properties.address'
	)