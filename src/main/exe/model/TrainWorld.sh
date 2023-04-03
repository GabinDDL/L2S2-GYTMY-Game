#!/bin/bash

LIST_PATH=$1
GMM_PATH=$2

EXE_DIR="./src/main/exe/model"
CFG_DIR="./src/main/exe/model/cfg"

$EXE_DIR/TrainWorld --config $CFG_DIR/TrainWorld.cfg --inputFeatureFilename $LIST_PATH --mixtureFilesPath $GMM_PATH

exit 0