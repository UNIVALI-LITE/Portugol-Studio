@ECHO OFF
SETLOCAL

CLS

SET caminho_script=%~dp0
SET caminho_script=%caminho_script:~0,-1%
SET caminho_portugol=%caminho_script%
SET caminho_aplicacao=%caminho_portugol%\aplicacao
SET caminho_java=%caminho_portugol%\java\java-windows\bin\java.exe

SET arquivo=
SET comando="%caminho_java%" -server -Xms32m -Xmx256m -XX:MinHeapFreeRatio=5 -XX:MaxHeapFreeRatio=10 -XX:+UseG1GC -XX:+CMSClassUnloadingEnabled -Dvisualvm.display.name=Portugol-Studio -jar portugol-console.jar

IF NOT "%~1"=="" (
	
	PUSHD .
	CD /d %~dp0
	SET comando=%comando% "%~f1"
	POPD
	SHIFT
)

SET parametros=

:PROCESSAR_PROXIMO_PARAMETRO
IF "%1"=="" GOTO FIM_PROCESSAR_PROXIMO_PARAMETRO

	SET parametros=%parametros% %1
	SHIFT
	GOTO PROCESSAR_PROXIMO_PARAMETRO

:FIM_PROCESSAR_PROXIMO_PARAMETRO

SET comando=%comando% %parametros%

PUSHD .
CD /d "%caminho_aplicacao%"
%comando%
POPD

ENDLOCAL
