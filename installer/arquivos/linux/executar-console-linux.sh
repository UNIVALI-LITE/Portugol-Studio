#!/bin/sh

caminho_script=$(readlink -f "$0")
caminho_portugol=$(dirname "$caminho_script")
caminho_aplicacao="$caminho_portugol/aplicacao"
caminho_java="$caminho_portugol/java/java-linux/bin/java"

clear
clear
clear

numero_parametros=$#

comando="'$caminho_java' -jar -Xms32m -Xmx256m -XX:MinHeapFreeRatio=5 -XX:MaxHeapFreeRatio=10 -XX:+UseG1GC -XX:+CMSClassUnloadingEnabled -Dvisualvm.display.name=Portugol-Studio portugol-console.jar"

if [ $numero_parametros -gt 0 ]; then

	if [ -f "$1" ]; then

		arquivo=$(readlink -f "$1")
	else

		arquivo="$1"
	fi

	shift

	comando="$comando '$arquivo' $@"
fi

cd "$caminho_aplicacao"
sh -c "$comando"
