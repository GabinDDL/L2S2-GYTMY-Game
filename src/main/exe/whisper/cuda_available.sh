#!/bin/bash

ENV_NAME=amaze

source $ENV_NAME/bin/activate &>/dev/null

if python3 -c "import torch; print(torch.cuda.is_available())" | grep -q "True"; then
    CUDA_AVAILABLE="True"
else
    CUDA_AVAILABLE="False"
fi

export CUDA_AVAILABLE