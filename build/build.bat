@echo off
cd /d %~dp0
cd .
dir /s /B *.class > sources.txt
powershell -Command "(gc sources.txt) -replace 'C:\\Users\\Jared\\Documents\\Mewsubi\\Plugins\\DupeFrame\\build\\', '' | Out-File -encoding ASCII sources.txt"
jar cmf dupeframe.mf ../DupeFrame.jar plugin.yml @sources.txt
cd ..