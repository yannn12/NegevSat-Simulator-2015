@ECHO OFF

if "%JAVA_HOME%" == "" goto noJavaHome
if not exist "%JAVA_HOME%\bin\java.exe" goto END

SET /P port=Please enter serial port name:
IF %port%=="" GOTO PortNotDefined
"%JAVA_HOME%\bin\java.exe" -jar simulator-1.1.jar -port "%port%" -p xml
goto END

:noJavaHome
@echo please set up JAVA_HOME environment variable - java should be version 8 and 32bit 
goto END

:PortNotDefined
@echo didn't enter port, will use COM1 as default
"%JAVA_HOME%\bin\java.exe" -jar simulator-1.1.jar -port COM1 -p xml
goto END

:END

