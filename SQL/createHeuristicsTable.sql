CREATE TABLE testHeuristics (sourceID varchar(50) NOT NULL, targetID varchar(50) NOT NULL, overload bigint);
INSERT INTO dbo.thirdHeuristics(sourceID,targetID) SELECT dbo.thirdRoad.nameID,a.nameID FROM dbo.testRoad CROSS JOIN dbo.testRoad AS a;
SELECT * FROM dbo.testHeuristics;