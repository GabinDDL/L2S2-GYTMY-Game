#!/bin/bash

# Arg 1 : Path of the list (list)
# Arg 2 : Path of the world (gmm)

./TrainWorld --config ./cfg/TrainWorld.cfg --inputFeatureFilename $1 --mixtureFilesPath $2