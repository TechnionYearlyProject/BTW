DROP TABLE IF EXISTS dbo.mapNamePlace;
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