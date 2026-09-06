# ============================================================================
#  Tito el Monito Ahorcado - Build de distribucion (release)
# ----------------------------------------------------------------------------
#  Genera, en orden:
#    1. Fat JAR ejecutable (mvn clean package con shade-plugin)
#    2. icono.ico (multitamano, desde src/main/resources/ui/icono.png)
#    3. App-image portable (jpackage app-image: EXE nativo + JRE recortado)
#    4. ZIP portable (app-image comprimida)
#    5. Instalador EXE personalizado (jpackage exe, sin WiX)
#
#  Requisitos: JDK 21 (jpackage/jlink/javac), Maven, PowerShell 5.1+
#
#  Nota: archivo en ASCII puro. Las tildes del vendor se construyen con
#  [char]0xE1 (a) y [char]0xE9 (e) para evitar dependencias de codificacion.
#  Los comandos externos se invocan SIEMPRE de forma directa (sin splatting
#  de arrays), por compatibilidad con PowerShell 5.1.
# ============================================================================
$ErrorActionPreference = "Stop"

$Root   = Split-Path -Parent $PSScriptRoot
$Target = Join-Path $Root "target"
$Dist   = Join-Path $Target "dist"
$Stage  = Join-Path $Target "jpackage-input"
$Tools  = Join-Path $Root "tools"
$IconPng = Join-Path $Root "src\main\resources\ui\icono.png"

# --- Parametros del release ---------------------------------------------------
$AppName     = "Tito el Monito Ahorcado"
$Version     = "1.0.0-beta"
$AppVer      = "1.0.0"
$Vendor      = "Ad" + [char]0xE1 + "n Cort" + [char]0xE9 + "s"
$Description = "Ahorcado interactivo con identidad visual de boceto, economia de Lapices, power-ups, logros y ranking."
$Copyright   = "Copyright (c) 2026 " + $Vendor + ". Licencia GPL-3."
$AboutUrl    = "https://github.com/adancortes-ing/tito-el-monito-ahorcado"
$UpgradeUuid = "3f0a8b2e-5c41-4f7a-9b0d-6e8c2a9d1f4e"
$JarName     = "Tito_El_Monito_Ahorcado-${Version}.jar"
$Modules     = "java.base,java.desktop,java.sql,java.logging"

# --- Localizacion de JDK ---------------------------------------------------------
$JdkBin = $env:JAVA_HOME
if (-not $JdkBin -or -not (Test-Path (Join-Path $JdkBin "javac.exe"))) {
    $Candidato = "C:\Program Files\Java\jdk-21.0.10\bin"
    if (Test-Path (Join-Path $Candidato "javac.exe")) { $JdkBin = $Candidato }
}
if (-not $JdkBin) {
    $JavacPath = Get-Command javac -ErrorAction SilentlyContinue
    if ($JavacPath) { $JdkBin = Split-Path -Parent $JavacPath.Path }
}
if (-not $JdkBin) { throw "No se encontro un JDK con javac. Defina JAVA_HOME." }

$Jpkg  = Join-Path $JdkBin "jpackage.exe"
$Javac = Join-Path $JdkBin "javac.exe"
$Java  = Join-Path $JdkBin "java.exe"
if (-not (Test-Path $Jpkg)) { throw "No se encontro jpackage en: $Jpkg" }

# --- 1/5 Fat JAR -------------------------------------------------------------------
Write-Host ""
Write-Host "=== 1/5 Fat JAR (mvn clean package) ==="
mvn clean package -q
if ($LASTEXITCODE -ne 0) { throw "Fallo: mvn clean package" }

# --- 2/5 icono.ico -----------------------------------------------------------------
New-Item -ItemType Directory -Force -Path $Dist | Out-Null
$ToolsClasses = Join-Path $Target "tools-classes"
New-Item -ItemType Directory -Force -Path $ToolsClasses | Out-Null
$Ico = Join-Path $Dist "icono.ico"

Write-Host ""
Write-Host "=== 2/5 icono.ico (compilar PngToIco) ==="
& $Javac -d $ToolsClasses (Join-Path $Tools "PngToIco.java")
if ($LASTEXITCODE -ne 0) { throw "Fallo: compilar PngToIco" }

Write-Host ""
Write-Host "=== 2/5 icono.ico (conversion) ==="
& $Java -cp $ToolsClasses PngToIco $IconPng $Ico
if ($LASTEXITCODE -ne 0) { throw "Fallo: PngToIco" }

# --- 3/5 App-image portable ------------------------------------------------------------
New-Item -ItemType Directory -Force -Path $Stage | Out-Null
Copy-Item -Force (Join-Path $Target $JarName) $Stage
$AppImageDest = Join-Path $Dist $AppName
if (Test-Path $AppImageDest) { Remove-Item -Recurse -Force $AppImageDest }

Write-Host ""
Write-Host "=== 3/5 App-image (jpackage) ==="
& $Jpkg --type app-image --name $AppName --app-version $AppVer --vendor $Vendor --description $Description --copyright $Copyright --icon $Ico --input $Stage --main-jar $JarName --add-modules $Modules --dest $Dist
if ($LASTEXITCODE -ne 0) { throw "Fallo: jpackage app-image" }

# --- 4/5 ZIP portable --------------------------------------------------------------------
$Zip = Join-Path $Dist "Tito-El-Monito-Ahorcado-${Version}-portable.zip"
Remove-Item -Force $Zip -ErrorAction SilentlyContinue
Write-Host ""
Write-Host "=== 4/5 ZIP portable ==="
Compress-Archive -Path $AppImageDest -DestinationPath $Zip -Force

# --- Localizacion de WiX (requerido por jpackage para el instalador EXE) --------------
$WiXHome = $env:WIX_HOME
if (-not $WiXHome) {
    $WiXCandidatos = @(
        "C:\Users\adanc\AppData\Local\WiX3",
        "C:\Program Files (x86)\WiX Toolset 3.11\bin",
        "C:\Program Files\WiX Toolset 3.14\bin"
    )
    foreach ($c in $WiXCandidatos) {
        if (Test-Path (Join-Path $c "light.exe")) { $WiXHome = $c; break }
    }
}
if ($WiXHome -and (Test-Path (Join-Path $WiXHome "light.exe"))) {
    $env:PATH = "$WiXHome;$env:PATH"
    Write-Host "WiX encontrado en: $WiXHome"
} else {
    Write-Host "ADVERTENCIA: WiX no encontrado; el paso 5/5 (instalador EXE) fallara."
}

# --- 5/5 Instalador EXE ---------------------------------------------------------------------
Write-Host ""
Write-Host "=== 5/5 Instalador EXE (jpackage) ==="
& $Jpkg --type exe --name $AppName --app-version $AppVer --vendor $Vendor --description $Description --copyright $Copyright --about-url $AboutUrl --icon $Ico --input $Stage --main-jar $JarName --add-modules $Modules --win-menu --win-menu-group $AppName --win-upgrade-uuid $UpgradeUuid --license-file (Join-Path $Root "LICENSE") --dest $Dist
if ($LASTEXITCODE -ne 0) { throw "Fallo: jpackage exe" }

$SetupRaw = Join-Path $Dist "${AppName}-${AppVer}.exe"
$SetupFinal = Join-Path $Dist "Tito-El-Monito-Ahorcado-${Version}-setup.exe"
if (Test-Path $SetupRaw) { Move-Item -Force $SetupRaw $SetupFinal }

# --- Resumen -------------------------------------------------------------------------------------
Write-Host ""
Write-Host "============================================="
Write-Host "  BUILD COMPLETADO - Distribucion v$Version"
Write-Host "============================================="
Get-ChildItem $Dist | Select-Object Name, @{N='Tamano (MB)';E={[math]::Round($_.Length/1MB,2)}} | Format-Table -AutoSize