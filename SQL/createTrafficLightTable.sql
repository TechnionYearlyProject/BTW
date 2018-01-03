DROP TABLE IF EXISTS dbo.mapNameTrafficLight;
CREATE TABLE mapNameTrafficLight(
nameID varchar(50) NOT NULL,
cordx smallint NOT NULL,
cordy smallint NOT NULL,
overload bigint,
PRIMARY KEY(nameID));
