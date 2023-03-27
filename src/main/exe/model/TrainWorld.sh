#!/bin/bash

# Arg 1 : Path of the list (list)
# Arg 2 : Path of the world (gmm)

EXE_DIR="./src/main/exe/model"
CFG_DIR="./src/main/exe/model/cfg"

$EXE_DIR/TrainWorld --config $CFG_DIR/TrainWorld.cfg --inputFeatureFilename $1 --mixtureFilesPath $2