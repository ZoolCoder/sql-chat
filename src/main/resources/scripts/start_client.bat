@echo off
set /p ip="Enter the IP address of the server: "
set /p port="Enter the Port of the server: "
java -jar sql-chat-${project.version}.jar --other_instance %ip%:%port%
pause