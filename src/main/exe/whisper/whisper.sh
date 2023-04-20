#!/bin/bash

ENV_NAME=amaze

source $ENV_NAME/bin/activate &>/dev/null
source ./cuda_available.sh

# Help message
if [ "$1" == "-h" ] || [ "$1" == "--help" ]; then
    echo "Usage: whisper.sh [options] -a <audio_path> -o <output_path>"
    echo "Options:"
    echo "  -m, --model <model_name>  Name of the model to use. Default: tiny.en"
    echo "  -a, --audio <audio_path>  Path to the audio file."
    echo "  -o, --output <output_path>  Path to the output file."
    echo "  -h, --help  Show this help message and exit."
    exit 0
fi

# set default values for flags and options
if [ "$CUDA_AVAILABLE" == "True" ]; then
    DEVICE="cuda"
else
    DEVICE="cpu"
fi

MODEL="tiny.en"

# parse command line arguments
while [[ $# -gt 0 ]]; do
    key="$1"
    case $key in
    -m | --model)
        MODEL="$2"
        shift
        shift
        ;;
    -a | --audio)
        AUDIO_PATH="$2"
        shift
        shift
        ;;
    -o | --output)
        OUTPUT_PATH="$2"
        shift
        shift
        ;;
    *) # unknown option
        shift
        ;;
    esac
done

# execute the command
whisper-ctranslate2 --device "$DEVICE" --output_format json --model "$MODEL" "$AUDIO_PATH" -o "$OUTPUT_PATH"