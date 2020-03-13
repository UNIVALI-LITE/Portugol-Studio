@ECHO OFF

SETLOCAL ENABLEDELAYEDEXPANSION

CLS

SET caminho_script=%~dp0
SET caminho_script=%caminho_script:~0,-1%
SET caminho_portugol=%caminho_script%
SET caminho_java=%caminho_portugol%\java\java-windows\bin\javaw.exe

SET comando="%caminho_java%" -server -Xms32m -Xmx256m -XX:MinHeapFreeRatio=5 -XX:MaxHeapFreeRatio=10 -XX:+UseG1GC -XX:+CMSClassUnloadingEnabled -Dvisualvm.display.name=Portugol-Studio -jar aplicacao\portugol-studio.jar
				
SET parametros=

:PROCESSAR_PROXIMO_PARAMETRO
IF NOT "%~1"=="" (	

	PUSHD .
	CD /d "%caminho_script%"
	SET parametro=%~f1
	POPD	
		
	IF EXIST "!parametro!" (
			
		SET parametros=%parametros% "!parametro!"
			
	) ELSE (

		SET parametros=%parametros% %1
	)
	
	SHIFT
	GOTO PROCESSAR_PROXIMO_PARAMETRO
)

SET comando=%comando%%parametros% 

PUSHD .
CD /d "%caminho_portugol%"
%comando%
POPD

ENDLOCAL
