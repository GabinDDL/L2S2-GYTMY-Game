#!/bin/bash

NBR_ARG=2

if [ $# -ne $NBR_ARG ]; then
    exit 1
fi

if ! [ -f $1 ]; then
    exit 1
fi

if ! [ -d $2 ]; then
    exit 1
fi

LIST_PATH=$1
DATA_PATH=$2

EXE_DIR="./src/main/exe/model"
PRM_DIR="./src/main/exe/model/prm"
PRM_EXT=".prm"
SPH_EXT=".wav"
OPTS="-F pcm16 -p 17 -f 16000 -e"


for i in `cat $LIST_PATH`;do
	$EXE_DIR/sfbcep $OPTS $DATA_PATH/$i$SPH_EXT $PRM_DIR/$i$PRM_EXT
done

exit 0
