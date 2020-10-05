@echo off
zip.vbs %~dp0pack\Anniversary_Edition %~dp0Anniversary_Edition.zip
move %~dp0Anniversary_Edition.zip %appdata%\.minecraft\resourcepacks