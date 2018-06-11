# By The Way
  [![Build Status](https://travis-ci.org/TechnionYearlyProject/BTW.svg?branch=master)](https://travis-ci.org/TechnionYearlyProject/BTW)
  [![codecov](https://codecov.io/gh/TechnionYearlyProject/BTW/branch/master/graph/badge.svg)](https://codecov.io/gh/TechnionYearlyProject/BTW)

# Download instructions for new developer:

* make sure you have:
* jdk 1.8, or a more advanced version of the jdk, if not, download here: http://www.oracle.com/technetwork/java/javase/downloads/index.html
* apache maven, if not, download here: https://maven.apache.org/download.cgi
* an IDE that enables to run java code, and supports maven projects, we suggest Jetbrain's Intellij IDE, which you can download here: https://www.jetbrains.com/idea/download/#section=windows

To run the project:
1. open the pom.xml file, located in the BTW-Project folder, as a maven project.
2. go to the BTW.java file located in the src folder of the BTW-Application module, and run it.

# Having Troubles?

Maybe you'll find the solution here:

* Question: I get an error of "java: package javafx.application does not exist."
* Answer: check, if you have <Java SDK root>/jre/lib/ext/jfxrt.jar on your classpath under Project Structure -> SDKs -> 1.x -> Classpath? If not, that could be why. Try adding it and see if that fixes your issue. If you have it go to File -> Settings -> Build, Execution, Deployment -> Compiler -> Java Compiler and make sure you have the same version of 1.x next to every module in the table.
