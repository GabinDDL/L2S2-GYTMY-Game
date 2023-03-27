#!/bin/bash

# Arg 1 : Path of the list (list)

EXE_DIR="./src/main/exe/model"
CFG_DIR="./src/main/exe/model/cfg"

$EXE_DIR/NormFeat --config $CFG_DIR/NormFeat.cfg --inputFeatureFilename $1

exit 0