@ECHO OFF
@ECHO SET CP
SET CP=%CLASSPATH%
FOR %%F IN (lib\*.jar) DO call :addcp %%F
goto extlibe
:addcp
SET CP=%CP%;%1
goto :eof
:extlibe
SET CP=%CP%;.\resources;.
SET CP
java -Xms128m -Xmx712m -XX:MaxPermSize=256m -classpath %CP% com.redcard.posp.server.Bootstrap 