# PowerShell compile and run
New-Item -ItemType Directory -Force -Path bin | Out-Null
$srcs = Get-ChildItem -Recurse -Filter *.java POS/src | ForEach-Object { $_.FullName }
javac -d bin $srcs
if ($LASTEXITCODE -ne 0) { Write-Host 'Compilation failed'; exit 1 }
Write-Host 'Running...'
java -cp bin pos.Main
