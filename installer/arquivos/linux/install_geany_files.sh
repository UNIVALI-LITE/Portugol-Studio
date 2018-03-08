#!/bin/sh
insertLine=`grep -n '\[Extensions\]' /usr/share/geany/filetype_extensions.conf | awk -F ":" '{print $1}'`
insertLine=$(($insertLine + 1))
awk 'NR=='$insertLine'{print "Portugol20=*.por;"}7' /usr/share/geany/filetype_extensions.conf > /usr/share/geany/tmppsconf
mv /usr/share/geany/tmppsconf /usr/share/geany/filetype_extensions.conf
