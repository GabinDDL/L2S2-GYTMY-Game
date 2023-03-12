#!/bin/bash

if [ $# != "2" ]; then
echo "usage: $0 liste <type Param>"
exit 1
fi

SPRO_BIN_DIR="."
# SPH_DIR="./data"
# PRM_DIR="./prm"
PRM_EXT=".prm"
SPH_EXT=".wav"
echo $SPRO_BIN_DIR

if [ "$2" == "12MFCC" ]; then #12MFCC
	OPTS="-F pcm16 -p 12 -m -f 16000"
fi
if [ "$2" == "12LFCC" ]; then #12LFCC
	OPTS="-F pcm16 -p 12 -f 16000"
fi
if [ "$2" == "13MFCC" ]; then #12MFCC+Energy
	OPTS="-F pcm16 -p 12 -m -f 16000 -e"
fi
if [ "$2" == "13LFCC" ]; then #12LFCC+Energy
	OPTS="-F pcm16 -p 12 -f 16000 -e"
fi
if [ "$2" == "26MFCC" ]; then #12MFCC+Energy+Delta
	OPTS="-F pcm16 -p 12 -m -f 16000 -e -D"
fi
if [ "$2" == "26LFCC" ]; then #12LFCC+Energy+Delta
	OPTS="-F pcm16 -p 12 -f 16000 -e -D"
fi
if [ "$2" == "39MFCC" ]; then #12MFCC+Energy+Delta+DeltaDelta
	OPTS="-F pcm16 -p 12 -m -f 16000 -e -D -A"
fi
if [ "$2" == "39LFCC" ]; then #12LFCC+Energy+Delta+DeltaDelta
	OPTS="-F pcm16 -p 12 -f 16000 -e -D -A"
fi
if [ "$2" == "20MFCC" ]; then #20MFCC
	OPTS="-F pcm16 -p 20 -m -f 16000"
fi
if [ "$2" == "20LFCC" ]; then #20LFCC
	OPTS="-F pcm16 -p 20 -f 16000"
fi
if [ "$2" == "21MFCC" ]; then #20MFCC+Energy
	OPTS="-F pcm16 -p 20 -m -f 16000 -e"
fi
if [ "$2" == "21LFCC" ]; then #20LFCC+Energy
	OPTS="-F pcm16 -p 20 -f 16000 -e"
fi

for i in `cat $1`;do

	./sfbcep $OPTS $3/$i$SPH_EXT $4/$i$PRM_EXT
	
	
done
