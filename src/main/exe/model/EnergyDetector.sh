#!/bin/bash

# Arg 1 : Path of the list (list)

EXE_DIR="./src/main/exe/model"
CFG_DIR="./src/main/exe/model/cfg"

$EXE_DIR/EnergyDetector --config $CFG_DIR/EnergyDetector.cfg --inputFeatureFilename $1