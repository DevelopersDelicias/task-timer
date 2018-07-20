# Task Timer

This is a very simple Swing Application that is used to help to have a timer that you can start, pause and stop.

## Project Setup

### Maven Setup
1. Download maven from: https://maven.apache.org/download.cgi
2. Unzip content in a folder that you want, e.g. (C:\Software\)
3. Create a new environment variable ```MVN_HOME``` and set the value with the system path to maven (e.g. C:\Software\apache-maven-3.5.4)
4. Append ```%MVN_HOME%\bin``` to the ```PATH``` variable at the end
5. Open a console window or terminal and type ```mvn -v``` to verify Maven version
6. Go to your project folder and execute a new console window inside the folder, and type ```mvn clean install```
7. Maven will run a build and download all the dependencies required, run the tests, and package the application

#### Troubleshooting
1. Problem: Error finding Java compiler
    Solution: Check the Path variables is not having multiple entries to Java compiler (different versions)

### Eclipse

1. Import an Existing Maven Project
2. Select Task Timer project in "C:\Users\[git-username]\projects\task-timer"
3. Once Task timer is imported build the project and check for build errors.
4. Go to Run> Run Configurations> Maven Build> New Run Configuration.
5. Creating and running the task timer run configuration should allow you to run the JUnit test cases.
