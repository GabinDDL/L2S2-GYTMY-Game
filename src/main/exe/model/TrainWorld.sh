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
GMM_PATH=$2

EXE_DIR="./src/main/exe/model"
CFG_DIR="./src/main/exe/model/cfg"

$EXE_DIR/TrainWorld --config $CFG_DIR/TrainWorld.cfg --inputFeatureFilename $LIST_PATH --mixtureFilesPath $GMM_PATH

exit 0