#!/bin/sh

caminho_script=$(readlink -f "$0")
caminho_portugol=$(dirname "$caminho_script")
caminho_java="$caminho_portugol/java/java-linux/bin/java"

cd "$caminho_portugol"

"$caminho_java" -jar -server -Dvisualvm.display.name=Portugol-Studio -Xms32m -Xmx256m -XX:MinHeapFreeRatio=5 -XX:MaxHeapFreeRatio=10 -XX:+UseG1GC -XX:+CMSClassUnloadingEnabled aplicacao/portugol-studio.jar "$@"
