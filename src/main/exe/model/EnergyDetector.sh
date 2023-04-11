#!/bin/bash

NBR_ARG=1

if [ $# -ne $NBR_ARG ]; then
    echo "test1"
    exit 1
fi

if ! [ -f $1 ]; then
echo "test2"
    exit 1
fi

LIST_PATH=$1

EXE_DIR="./src/main/exe/model"
CFG_DIR="./src/main/exe/model/cfg"

$EXE_DIR/EnergyDetector --config $CFG_DIR/EnergyDetector.cfg --inputFeatureFilename $LIST_PATH

exit 0