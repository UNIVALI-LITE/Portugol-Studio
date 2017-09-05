#!/bin/sh

read_lnk()
{
caminho_atual="$PWD"

cd "$(dirname "$1")"
LINK=$(readlink "$(basename "$1")")

while [ "$LINK" ]; do

cd "$(dirname "$LINK")"
LINK=$(readlink "$(basename "$1")")
done

caminho_absoluto="$PWD/$(basename "$1")"
cd "$caminho_atual"

echo "$caminho_absoluto"
}

caminho_script=$(read_lnk "$0")
caminho_portugol=$(dirname "$caminho_script")
caminho_java="$caminho_portugol/java/java-mac/bin/java"


comando="'$caminho_java' -Xms32m -Xmx256m -XX:MinHeapFreeRatio=5 -XX:MaxHeapFreeRatio=10 -XX:+UseG1GC -XX:+CMSClassUnloadingEnabled -Dvisualvm.display.name=Portugol-Studio -Xdock:name=Portugol-Studio -Xdock:icon=./aplicacao/icones/mac/portugol-studio.icns -jar aplicacao/portugol-studio.jar"

cd "$caminho_portugol"

sh -c "$comando"