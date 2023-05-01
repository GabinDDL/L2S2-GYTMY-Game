#!/bin/bash

EXE_DIR="./src/main/exe/model"
CFG_DIR="./src/main/exe/model/cfg"

$EXE_DIR/TrainWorld --config $CFG_DIR/TrainWorld.cfg

exit 0