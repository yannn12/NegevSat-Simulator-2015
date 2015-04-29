@ECHO OFF

if "%JAVA_HOME%" == "" goto noJavaHome
if not exist "%JAVA_HOME%\bin\java.exe" goto END

"%JAVA_HOME%\bin\java.exe" -jar simulator-1.1.jar -port COM1 -p binary
goto END


:noJavaHome
@echo please set up JAVA_HOME environment variable - java should be version 8 and 32bit 
goto END

:END

