#!/bin/sh
removeLine=`grep -n 'Portugol20=\*.por;' /usr/share/geany/filetype_extensions.conf | awk -F ":" '{print $1}'`
awk 'NR!~/^('$removeLine')$/' /usr/share/geany/filetype_extensions.conf > /usr/share/geany/tmppsconf
mv /usr/share/geany/tmppsconf /usr/share/geany/filetype_extensions.conf
rm /usr/share/geany/filetypes.Portugol20.conf
