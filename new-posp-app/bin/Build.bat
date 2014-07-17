@echo off
echo [INFO] Build ruide posp Server.

cd %~dp0
cd ..

call mvn clean
call mvn compile -Dmaven.test.skip=true
call mvn jar:jar
call mvn assembly:single

cd bin
pause