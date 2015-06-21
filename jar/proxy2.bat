@ECHO OFF

if "%JAVA_HOME%" == "" goto noJavaHome
if not exist "%JAVA_HOME%\bin\java.exe" goto END

"%JAVA_HOME%\bin\java.exe" -jar proxy.jar -com COM2 -inip 192.168.43.187 -inport 4444 -outip 192.168.43.187 -outport 6666
goto END


:noJavaHome
@echo please set up JAVA_HOME environment variable - java should be version 8 and 32bit 
goto END

:END

