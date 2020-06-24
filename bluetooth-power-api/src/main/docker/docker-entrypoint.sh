#!/bin/sh
# 文件分隔符必须为LF
echo "A total of $# parameters."
echo "The parameters is: $*."

JAVA_OPTS_SCRIPT="-XX:+HeapDumpOnOutOfMemoryError -Djava.awt.headless=true"

#设置默认JVM初始内存和最大内存
Xms=100m
Xmx=1000m
while getopts ":s:x:" opt
do
  case $opt in
    s)
      Xms=$OPTARG
      ;;
    x)
      Xmx=$OPTARG
      ;;
    ?)
      "Invalid option: -$OPTARG"
      ;;
  esac
done

s=${Xms%?}
x=${Xmx%?}
if [ $x -lt $s ] ; then
  Xmx=$Xms
fi

## Use the Hotspot garbage-first collector.
#JAVA_OPTS="$JAVA_OPTS -Xmx8000m -Xms100m -XX:+UseG1GC"
JAVA_OPTS="$JAVA_OPTS -Xmx$Xmx -Xms$Xms -XX:+UseG1GC"

## Have the JVM do less remembered set work during STW, instead
## preferring concurrent GC. Reduces p99.9 latency.
JAVA_OPTS="$JAVA_OPTS -XX:G1RSetUpdatingPauseTimePercent=5"

## Main G1GC tunable: lowering the pause target will lower throughput and vise versa.
## 200ms is the JVM default and lowest viable setting
## 1000ms increases throughput. Keep it smaller than the timeouts.
JAVA_OPTS="$JAVA_OPTS -XX:MaxGCPauseMillis=500"
#
JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCDetails"
JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCDateStamps"
JAVA_OPTS="$JAVA_OPTS -XX:+PrintHeapAtGC"
JAVA_OPTS="$JAVA_OPTS -XX:+PrintTenuringDistribution"
JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCApplicationStoppedTime"
JAVA_OPTS="$JAVA_OPTS -XX:+PrintPromotionFailure"
#JAVA_OPTS="$JAVA_OPTS -XX:PrintFLSStatistics=1"
JAVA_OPTS="$JAVA_OPTS -Xloggc:$LICENSE_HOME/logs/gc.log"
JAVA_OPTS="$JAVA_OPTS -XX:+UseGCLogFileRotation"
JAVA_OPTS="$JAVA_OPTS -XX:NumberOfGCLogFiles=10"
JAVA_OPTS="$JAVA_OPTS -XX:GCLogFileSize=10M"

java $JAVA_OPTS $JAVA_OPTS_SCRIPT -jar @project.build.finalName@.jar