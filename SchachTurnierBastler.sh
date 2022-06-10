#!/bin/bash
#
SCRIPTPATH=$(cd `dirname $0` && pwd)
echo $SCRIPTPATH
for filename in ${SCRIPTPATH}/lib/*.jar
 do CLASSPATH=${CLASSPATH}:${filename}
done
echo $CLASSPATH
export CLASSPATH
java -Xmx2048M -Xms1280M org.myoggradio.stb.Main
java pack.Pause
exit
