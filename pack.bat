@echo off
zip.vbs %~dp0pack\Anniversary_Edition %~dp0Anniversary_Edition.zip
copy %~dp0Anniversary_Edition.zip %appdata%\.minecraft\resourcepacks