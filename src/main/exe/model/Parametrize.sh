#!/bin/bash

# Arg 1 : Path of the list (list)
# Arg 2 : Path of data

EXE_DIR="./src/main/exe/model"
PRM_DIR="./src/main/exe/model/prm"
PRM_EXT=".prm"
SPH_EXT=".wav"
OPTS="-F pcm16 -p 17 -f 16000 -e"


for i in `cat $1`;do
	$EXE_DIR/sfbcep $OPTS $2/$i$SPH_EXT $PRM_DIR/$i$PRM_EXT
done

exit 0
