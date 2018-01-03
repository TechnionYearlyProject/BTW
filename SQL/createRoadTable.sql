DROP TABLE IF EXISTS dbo.mapNameRoad;
CREATE TABLE mapNameRoad(
nameID varchar(50) NOT NULL,
cord1x smallint NOT NULL,
cord1y smallint NOT NULL,
cord2x smallint NOT NULL,
cord2y smallint NOT NULL,
length int,
secStart smallint,
secEnd smallint,
overload long,
PRIMARY KEY(nameID));