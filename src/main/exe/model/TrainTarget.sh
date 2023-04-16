#!/bin/bash

NBR_ARG=1

if [ $# -ne $NBR_ARG ]; then
    exit 1
fi

if ! [ -f $1 ]; then
    exit 1
fi

NDX_PATH=$1
GMM_PATH=$2

EXE_DIR="./src/main/exe/model"
CFG_DIR="./src/main/exe/model/cfg"

$EXE_DIR/TrainTarget --config $CFG_DIR/TrainTarget.cfg --targetIdList $NDX_PATH

exit 0