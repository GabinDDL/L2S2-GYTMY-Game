#!/bin/bash

NBR_ARG=1

if [ $# -ne $NBR_ARG ]; then
    exit 1
fi

if ! [ -f $1 ]; then
    exit 1
fi

LIST_PATH=$1

EXE_DIR="./src/main/exe/model"
CFG_DIR="./src/main/exe/model/cfg"

$EXE_DIR/NormFeat --config $CFG_DIR/NormFeat.cfg --inputFeatureFilename $LIST_PATH

exit 0