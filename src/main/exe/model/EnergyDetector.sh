#!/bin/bash

LIST_PATH=$1

EXE_DIR="./src/main/exe/model"
CFG_DIR="./src/main/exe/model/cfg"

$EXE_DIR/EnergyDetector --config $CFG_DIR/EnergyDetector.cfg --inputFeatureFilename $LIST_PATH

exit 0