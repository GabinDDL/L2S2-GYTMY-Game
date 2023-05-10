#!/bin/bash
# Define the name of the virtual environment
ENV_NAME=amaze

source $ENV_NAME/bin/activate

deactivate

rm -rf "$ENV_NAME"
