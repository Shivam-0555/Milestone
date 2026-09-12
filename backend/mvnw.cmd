@echo off
setlocal
set MAVEN_WRAPPER_VERSION=0.5.6
set WRAPPER_JAR=.mvn\wrapper\maven-wrapper.jar
set WRAPPER_URL=https://repo.maven.apache.org/maven2/io/takari/maven-wrapper/%MAVEN_WRAPPER_VERSION%/maven-wrapper-%MAVEN_WRAPPER_VERSION%.jar

if not exist "%WRAPPER_JAR%" (
  echo Downloading Maven Wrapper...\r
  md .mvn\wrapper
  powershell -Command "Invoke-WebRequest -Uri %WRAPPER_URL% -OutFile %WRAPPER_JAR%"
)

java -jar "%WRAPPER_JAR%" %*
endlocal
