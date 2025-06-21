@echo off
echo ================= SISTEMA DE EVENTOS =================
echo.
echo Este script executara o sistema de eventos.
echo Voce podera escolher entre interface grafica ou linha de comando.
echo.
pause
cd sistema-eventos
mvn exec:java -Dexec.mainClass="com.projeto.Main"
pause