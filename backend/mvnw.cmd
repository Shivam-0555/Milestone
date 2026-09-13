@echo off
setlocal

set MAVEN_HOME=
set WRAPPER_PROPERTIES=.mvn\wrapper\maven-wrapper.properties

rem Try to find Maven distribution
for /f "tokens=2 delims==" %%a in ('findstr "distributionUrl" "%WRAPPER_PROPERTIES%"') do set DIST_URL=%%a
set DIST_URL=%DIST_URL:\=%

rem Check if Maven is already downloaded
set MAVEN_USER_HOME=%USERPROFILE%\.m2\wrapper\dists
if not exist "%MAVEN_USER_HOME%" mkdir "%MAVEN_USER_HOME%"

rem Extract version from URL
set MAVEN_VERSION=3.9.6
set MAVEN_DIR=%MAVEN_USER_HOME%\apache-maven-%MAVEN_VERSION%

if not exist "%MAVEN_DIR%\bin\mvn.cmd" (
    echo Downloading Apache Maven %MAVEN_VERSION%...
    set MAVEN_ZIP=%MAVEN_USER_HOME%\apache-maven-%MAVEN_VERSION%-bin.zip
    powershell -Command "Invoke-WebRequest -Uri 'https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/%MAVEN_VERSION%/apache-maven-%MAVEN_VERSION%-bin.zip' -OutFile '%MAVEN_USER_HOME%\apache-maven-%MAVEN_VERSION%-bin.zip' -UseBasicParsing"
    powershell -Command "Expand-Archive -Path '%MAVEN_USER_HOME%\apache-maven-%MAVEN_VERSION%-bin.zip' -DestinationPath '%MAVEN_USER_HOME%' -Force"
    del "%MAVEN_USER_HOME%\apache-maven-%MAVEN_VERSION%-bin.zip" 2>nul
)

"%MAVEN_DIR%\bin\mvn.cmd" %*
endlocal
