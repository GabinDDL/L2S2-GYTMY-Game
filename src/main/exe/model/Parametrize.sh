#!/bin/bash

# Arg 1 : Path of the list (list)

SPH_DIR="./data"
PRM_DIR="./prm"
PRM_EXT=".prm"
SPH_EXT=".wav"
OPTS="-F pcm16 -p 17 -f 16000 -e"

for i in `cat $1`;do
	./sfbcep $OPTS $SPH_DIR/$i$SPH_EXT $PRM_DIR/$i$PRM_EXT
done
