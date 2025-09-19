@echo off
REM Compile and run for Windows CMD

if not exist bin mkdir bin

REM Compile all .java files under src
javac -d bin src\pos\*.java src\pos\model\*.java src\pos\service\*.java src\pos\discount\*.java src\pos\ui\*.java src\pos\ui\tui\*.java src\pos\ui\gui\*.java

if %errorlevel% neq 0 (
  echo Compilation failed.
  pause
  exit /b 1
)

echo Running...
java -cp bin pos.Main
pause
