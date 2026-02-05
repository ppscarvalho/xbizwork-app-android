# Script para limpar build travado no Windows
Write-Host "🧹 Limpando build travado..." -ForegroundColor Cyan

# 1. Matar processos Java/Gradle que podem estar travando arquivos
Write-Host "`n1️⃣ Matando processos Java/Gradle..." -ForegroundColor Yellow
Get-Process -Name "java", "kotlin-compiler-daemon", "gradle*" -ErrorAction SilentlyContinue | Stop-Process -Force -ErrorAction SilentlyContinue
Start-Sleep -Seconds 2

# 2. Limpar pastas de build
Write-Host "`n2️⃣ Removendo pastas de build..." -ForegroundColor Yellow
$buildDirs = @(
    ".\app\build",
    ".\build",
    ".\.gradle"
)

foreach ($dir in $buildDirs) {
    if (Test-Path $dir) {
        Write-Host "   Removendo: $dir" -ForegroundColor Gray
        Remove-Item -Path $dir -Recurse -Force -ErrorAction SilentlyContinue
    }
}

# 3. Limpar cache do Gradle global (opcional)
Write-Host "`n3️⃣ Limpando cache Gradle..." -ForegroundColor Yellow
$gradleCacheDir = "$env:USERPROFILE\.gradle\caches"
if (Test-Path $gradleCacheDir) {
    Write-Host "   Limpando: $gradleCacheDir\modules-2" -ForegroundColor Gray
    Remove-Item -Path "$gradleCacheDir\modules-2\files-*" -Recurse -Force -ErrorAction SilentlyContinue
}

Write-Host "`n✅ Limpeza concluída!" -ForegroundColor Green
Write-Host "`n4️⃣ Reconstruindo projeto..." -ForegroundColor Yellow

# 4. Rebuild do projeto
.\gradlew.bat clean assembleDebug --no-daemon

Write-Host "`n🎉 Processo finalizado!" -ForegroundColor Green
