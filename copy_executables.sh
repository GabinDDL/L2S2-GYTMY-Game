#!/bin/bash

MODEL_DIR="src/main/exe/model"
COMPARISON_DIR="src/main/exe/comparaison"

installation_correct=true

function copy_LIA_RAL(){
    local LIA_RAL_PATH=$1
    # Test if the path contains the LIA_RAL directory
    if [ ! -d "$LIA_RAL_PATH" ]; then
        echo "The path you entered does not contain the LIA_RAL directory."
        exit 1
    fi
    
    (cp $LIA_RAL_PATH/bin/EnergyDetector $MODEL_DIR && echo  "EnergyDetector copied") || (echo "EnergyDetector not copied" && installation_correct=false)
    (cp $LIA_RAL_PATH/bin/NormFeat $MODEL_DIR && echo  "NormFeat copied") || (echo "NormFeat not copied" && installation_correct=false)
    (cp $LIA_RAL_PATH/bin/TrainWorld $MODEL_DIR && echo  "TrainWorld copied") || (echo "TrainWorld not copied" && installation_correct=false)
    (cp $LIA_RAL_PATH/bin/TrainTarget $MODEL_DIR && echo  "TrainTarget copied") || (echo "TrainTarget not copied" && installation_correct=false)
    (cp $LIA_RAL_PATH/bin/ComputeTest $COMPARISON_DIR && echo  "ComputeTest copied") || (echo "ComputeTest not copied" && installation_correct=false)
    
}

function copy_SPro(){
    local SPRO_PATH=$1
    # Test if the path contains the SPro directory
    if [ ! -d "$SPRO_PATH" ]; then
        echo "The path you entered does not contain the SPro directory."
        exit 1
    fi
    
    (cp $SPRO_PATH/sfbcep $MODEL_DIR && echo  "sfbcep  copied") || (echo "sfbcep not copied" && installation_correct=false)
}

if [[ ($# == 2 )]]; then
    copy_LIA_RAL $1
    copy_SPro $2
else
    echo "Enter the path to the LIA_RAL directory (including the LIA_RAL directory):"
    read LIA_RAL_PATH
    copy_LIA_RAL $LIA_RAL_PATH
    
    echo "Enter the path to the SPro directory (including the SPro directory):"
    read SPRO_PATH
    copy_SPro $SPRO_PATH
fi
